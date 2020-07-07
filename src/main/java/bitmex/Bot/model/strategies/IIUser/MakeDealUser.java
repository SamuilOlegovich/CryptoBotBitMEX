package bitmex.Bot.model.strategies.IIUser;

import bitmex.Bot.model.strategies.oneStrategies.TradeSell;
import bitmex.Bot.model.strategies.oneStrategies.TradeBuy;
import bitmex.Bot.model.enums.TypeData;
import bitmex.Bot.view.ConsoleHelper;
import bitmex.Bot.model.StringHelper;
import bitmex.Bot.model.DatesTimes;
import bitmex.Bot.model.Gasket;

import java.util.ArrayList;

import static bitmex.Bot.model.enums.TypeData.*;





// Определяем какую сделку сделать и даем команду на ее исполнение
public class MakeDealUser extends Thread {
    private ArrayList<String> marketList;
    private String patternZeroString;


    public MakeDealUser(ArrayList<String> marketList, String patternZeroString) {
        this.marketList = new ArrayList<>(marketList);
        this.patternZeroString = patternZeroString;
        start();
    }



    @Override
    public void run() {
        ConsoleHelper.writeMessage(DatesTimes.getDateTerminal() + " --- "
                + "Определяю какую сделку сделать согласно ПАТТЕРНАМ USER");
        
        String[] strings = patternZeroString.split("===");
        String stringOut = patternZeroString;


        if (Integer.parseInt(strings[1]) > Integer.parseInt(strings[3])) {

            if (conditionsAreMet(true)) {
                if (Gasket.isTradingUser()) {
                    new TradeBuy(stringOut);
                }
                if (Gasket.isTradingTestUser()) {
                    new TestOrderBuyPatternUser(stringOut, Gasket.getBitmexQuote().getAskPrice());
                }

                ConsoleHelper.writeMessage(DatesTimes.getDateTerminal() + " --- "
                        + stringOut + " --- Согластно ПАТТЕРНУ " + strings[strings.length - 1]
                        + " сделал сделку БАЙ USER");
            } else {
                ConsoleHelper.writeMessage(DatesTimes.getDateTerminal() + " --- "
                        + stringOut + " --- Согластно ПАТТЕРНУ " + strings[strings.length - 1]
                        + " сделку БАЙ USER отменил по истечению времени");
            }

        } else if (Integer.parseInt(strings[1]) < Integer.parseInt(strings[3])) {

            if (conditionsAreMet(false)) {
                if (Gasket.isTradingUser()) {
                    new TradeSell(stringOut);
                }
                if (Gasket.isTradingTestUser()) {
                    new TestOrderSellPatternUser(stringOut, Gasket.getBitmexQuote().getBidPrice());
                }

                ConsoleHelper.writeMessage(DatesTimes.getDateTerminal() + " --- "
                        + stringOut + " --- Согластно ПАТТЕРНУ " + strings[strings.length - 1]
                        + " сделал сделку СЕЛЛ USER");

            }
        } else {
            ConsoleHelper.writeMessage(DatesTimes.getDateTerminal() + " --- "
                    + stringOut + " --- Согластно ПАТТЕРНУ " + strings[strings.length - 1]
                    + " сделку СЕЛЛ USER отменил по истечению времени");
        }
    }



    // BUY===1===SELL===0===AVERAGE===3.28===MAX===5.0===SIZE===220===BLOCK===1===TYPE===ASK===ID===4
    // тут мы находим цену выше которой надо подняться или опустится в течении определенного времени
    // что бы сделать сделку иначе отбой
    private boolean conditionsAreMet(boolean b) {
        long timeStop = 60 * Gasket.getTimeStopLiveForUserPatterns();
        int blockSearch = 1;
        double prices = 0.0;
        String types = "";
        long time = 0;
        int blocks = 0;

        blocks = Integer.parseInt(StringHelper.giveData(TypeData.BLOCK, patternZeroString));
        types = StringHelper.giveData(TypeData.TYPE, patternZeroString);

        if (types.equalsIgnoreCase(NULL.toString())) {
            if (b) {
                // если сделка бай и тайп null то сразу берем цену первой строки нужного блока
                for (String string : marketList) {
                    if (string.startsWith(BIAS.toString())) {
                        blockSearch++;
                    }

                    if (blocks == blockSearch) {
//                        String[] s = marketList.get(marketList.indexOf(string) + 1).split("===");
                        prices = Double.parseDouble(StringHelper
                                .giveData(TypeData.price, marketList.get(marketList.indexOf(string) + 1)));
                        break;
                    }

                }
            } else {
                // если сделка селл и тайп null то сразу берем цену последней строки нужного блока
                for (String string : marketList) {
                    if (string.startsWith(BIAS.toString())) {
                        blockSearch++;
                    }

                    if (blocks + 1 == blockSearch) {
                        prices = Double.parseDouble(StringHelper
                                .giveData(TypeData.price, marketList.get(marketList.indexOf(string) - 1)));
                        break;
                    }
                }
            }
        } else {
            for (String string : marketList) {
                if (!string.startsWith(BIAS.toString()) && !string.startsWith(BUY.toString())
                        && !string.startsWith(NULL.toString())) {

                    if (blocks == blockSearch) {
                        if (StringHelper.giveData(TypeData.type, string).equalsIgnoreCase(types)) {
                            prices = Double.parseDouble(StringHelper.giveData(TypeData.price, string));
                        }
                    }
                } else if (string.startsWith(BIAS.toString())) {
                    blockSearch++;
                }
            }
        }

        while (time < timeStop) {

            if (b) {
                if (Gasket.getBitmexQuote().getBidPrice() > prices) {
                    ConsoleHelper.writeMessage(DatesTimes.getDateTerminal()
                            + " --- цена уровня - " + types + " - " + prices + " - пробита");
                    return true;
                }
            } else {
                if (Gasket.getBitmexQuote().getAskPrice() < prices) {
                    ConsoleHelper.writeMessage(DatesTimes.getDateTerminal()
                            + " --- цена уровня - " + types + " - " + prices + " - пробита");
                    return true;
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            time++;
        }
        return false;
    }
}



//    0 {"period": "M5",
//    1 "preview": "1",
//    2 "time": "2020-05-27 12:28:00",
//    3 "price": "9175.0",
//    4 "value": "2920763",
//    5 "type": "ASK",
//    6 "avg": "2871888",
//    7 "dir": "1",
//    8 "open": "9167.5",
//    9 "close": "9178.5",
//    10 "high": "9183.0",
//    11 "low": "9167.0"}
//
//
//    0 period
//    1 period.toString()
//    2 ===preview=== +
//    3 preview +
//    4 "===time===" +
//    5 dateFormat.format(time)
//    6 "===price===" +
//    7 price
//    8 "===value===" +
//    9 value +
//    10 "===type===" +
//    11 type.toString() +
//    12 "===avg===" +
//    13 avg
//    14 "===dir===" +
//    15 dir + "
//    16 ===open===" +
//    17 open + "
//    18 ===close===" +
//    19 close + "
//    20 ===high===" +
//    21 high
//    22 ===low===" +
//    23 low