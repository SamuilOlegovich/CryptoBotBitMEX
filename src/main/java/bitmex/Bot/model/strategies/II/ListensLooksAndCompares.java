package bitmex.Bot.model.strategies.II;


import bitmex.Bot.model.strategies.oneStrategies.TradeSell;
import bitmex.Bot.model.strategies.oneStrategies.TradeBuy;
import bitmex.Bot.model.serverAndParser.InfoIndicator;
import bitmex.Bot.view.ConsoleHelper;
import bitmex.Bot.model.DatesTimes;
import bitmex.Bot.model.Gasket;


import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;



public class ListensLooksAndCompares {

    private static ListensLooksAndCompares listensLooksAndCompares;
    private ArrayList<ArrayList<String>> listInListString;
    private ArrayList<InfoIndicator> listInfoIndicator;
    private SavedPatterns savedPatterns;
    private boolean timeFlag;



    private ListensLooksAndCompares() {
        ConsoleHelper.writeMessage("Класс ListensLooksAndCompares начал работать ---- "
                + DatesTimes.getDateTerminal());
        this.savedPatterns = Gasket.getSavedPatternsClass();
        this.listInfoIndicator = new ArrayList<>();
        this.listInListString = new ArrayList<>();
        this.timeFlag = false;
    }


    public static ListensLooksAndCompares getInstance() {
        if (listensLooksAndCompares == null) listensLooksAndCompares = new ListensLooksAndCompares();
        return listensLooksAndCompares;
    }



    // принимаем объекты и если еще не запускали метод их обработки то запускаем его,
    // если он уже запущен то просто кладем объекты в массив
    // так же получаем текущую цену
    public synchronized void setInfoString(InfoIndicator infoIndicator) {
        listInfoIndicator.add(infoIndicator);
        if (!timeFlag) {
            timeFlag = true;
            //priceNow = Gasket.getBitmexQuote().getBidPrice();
            listSortedAndCompares();
        }
    }



    // отсыпаемся и начинаем работать
    private synchronized void listSortedAndCompares() {
        if (timeFlag) {
            try {
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage("Не смог проснуться в методе listSorter() "
                        + "класса ListensToLooksAndFills");
                e.printStackTrace();
            }
            timeFlag = false;
        }

        // сортируем и добавляем
        sortPrice();
        // удаляем ненужное
        removeUnnecessaryLists();

        ConsoleHelper.writeMessage("Сравниваю рынок с ПАТТЕРНАМИ ---- "
                + DatesTimes.getDateTerminal());
        // сравниваем оставшееся с патернами
        for (ArrayList<String> thisArrayListString : listInListString) {
            // получаем равные по размеру патерны
            ArrayList<ArrayList<String>> inListPatterns = savedPatterns.getListFoSize(thisArrayListString.size());

            // если равные патерны есть то начинаем сравнивать
            if (inListPatterns != null) {

                for (ArrayList<String> inArrayListString : inListPatterns) {
                    boolean result = true;

                    for (int i = 1; i < inArrayListString.size(); i++) {
                        String[] arr1 = thisArrayListString.get(i).split("\"type\": \"");
                        String[] arr2 = inArrayListString.get(i).split("\"type\": \"");
                        String[] strings1 = arr1[1].split("\"");
                        String[] strings2 = arr2[1].split("\"");

                        // если хоть один объект не равен то прирываем цикл
                        if (!strings1[0].equals(strings2[0])) {
                            result = false;
                            break;
                        }
                    }

                    if (result) {
                        ConsoleHelper.writeMessage("Нашел совпадения в рынке с ПАТТЕРНАМИ передаю на сделку ---- "
                                + DatesTimes.getDateTerminal());
                        makeDeal(inArrayListString.get(0));
                    }
                }
            }
        }
    }


    // Определяем какую сделку сделать и даем команду на ее исполнение
    private void makeDeal(String string) {
        ConsoleHelper.writeMessage("Определяю какую сделку сделать согласно ИНФО ПАТТЕРНАМ ---- "
                + DatesTimes.getDateTerminal());
        String[] strings = string.split("===");

        if (Integer.parseInt(strings[1]) > Integer.parseInt(strings[3])) {
            String stringOut = string + "-PAT";

            if (Gasket.isTrading()) new TradeBuy(stringOut);
            new TestOrderBuyPattern(stringOut, Gasket.getBitmexQuote().getAskPrice());
            ConsoleHelper.writeMessage(stringOut + " --- Согластно ПАТТЕРНУ сделал сделку БАЙ ---- "
                    + DatesTimes.getDateTerminal());
        } else if (Integer.parseInt(strings[1]) < Integer.parseInt(strings[3])) {
            String stringOut = string + "-PAT";

            if (Gasket.isTrading()) new TradeSell(stringOut);
            new TestOrderSellPattern(stringOut, Gasket.getBitmexQuote().getAskPrice());
            ConsoleHelper.writeMessage(stringOut + " --- Согластно ПАТТЕРНУ сделал сделку СЕЛЛ ---- "
                    + DatesTimes.getDateTerminal());
        }
    }


    // удаляем листы размеры которых длиннее паттернов
    private void removeUnnecessaryLists() {
        if (listInListString.size() > 0) {
            int maxArraySize = savedPatterns.getMaxArraySize();
            ArrayList<Integer> lineNumbersToDelete = new ArrayList<>();

            for (int i = 0; i < listInListString.size(); i++) {
                if (listInListString.get(i).size() > maxArraySize) {
                    lineNumbersToDelete.add(i);
                }
            }

            Collections.reverse(lineNumbersToDelete);

            for (Integer integer : lineNumbersToDelete) {
                listInListString.remove((int) integer);
            }
        }
    }


    // сортируем и наполняем лист сравнений листами строк
    // очищаем лист входящих объектов
    private void sortPrice() {
        Comparator sortPriceComparator = new ListensLooksAndCompares.SortPrice();
        Collections.sort(listInfoIndicator, sortPriceComparator);

        if (listInListString.size() > 0) {
            for (ArrayList<String> arrayListString : listInListString) {
                arrayListString.addAll(getListString());
            }
        } else {
            listInListString.add(getListString());
        }
        listInfoIndicator.clear();
    }


    // добавляем нулевую строку для ровности списка и далее добавляем остальные строки
    private ArrayList<String> getListString() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("0");

        for (InfoIndicator infoIndicator : listInfoIndicator) {
            arrayList.add(infoIndicator.toString());
        }

        return arrayList;
    }


    private class SortPrice implements Comparator<InfoIndicator> {
        @Override
        public int compare(InfoIndicator o1, InfoIndicator o2) {
            double result = o1.getPrice() - o2.getPrice();
            if (result > 0) return 1;
            else if (result < 0) return -1;
            else return 0;
        }
    }
}
