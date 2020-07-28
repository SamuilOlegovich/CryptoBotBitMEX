package bitmex.Bot.model.strategies.IIUser;

import bitmex.Bot.model.enums.TypeData;
import bitmex.Bot.model.StringHelper;
import bitmex.Bot.model.DatesTimes;
import bitmex.Bot.model.Gasket;
import bitmex.Bot.view.ConsoleHelper;

import static bitmex.Bot.model.enums.TypeData.MARTINGALE;


public class TestOrderSellPatternMartingale extends Thread {
    private OpenTransactions openTransactions;
    private Martingale martingale;
    private double priseTakeOrder;
    private double priseStopOrder;
    private String zeroString;
    private String steeps;
    private String IDs;


    public TestOrderSellPatternMartingale(String zeroString, double priseOpenOrder) {
        this.steeps = StringHelper.giveData(MARTINGALE, zeroString);
        this.IDs = StringHelper.giveData(TypeData.ID, zeroString);
        this.priseTakeOrder = priseOpenOrder - Gasket.getTake();
        this.priseStopOrder = priseOpenOrder + Gasket.getStop();
        this.openTransactions = Gasket.getOpenTransactions();
        this.martingale = Gasket.getMartingaleClass();
        this.zeroString = zeroString;
        start();
    }



    @Override
    public void run() {
        ConsoleHelper.writeMessage(DatesTimes.getDateTerminal() + " --- "
                + IDs + " --- RUN класса TestOrderSellPatternMartingale начал считать");

        while (true) {
            double priceAsk = Gasket.getBitmexQuote().getAskPrice();
            double priceBid = Gasket.getBitmexQuote().getBidPrice();

            if (priceAsk >= priseStopOrder) {
                setStop();
                // меняем число положительных / отрицательных сделок
                // а так же максимальный шаг мартина
                String data = (Integer.parseInt(StringHelper.giveData(TypeData.BUY, zeroString)) - 1) + "";
                String out = StringHelper.setData(TypeData.BUY, data, zeroString);

                new UpdatingStatisticsDataUser(out);
                openTransactions.zeroSteepTest(IDs);

                ConsoleHelper.writeMessage(DatesTimes.getDateTerminal() + " --- "
                        + IDs + " --- Сработал СТОП ЛОСС USER " + MARTINGALE.toString() + " - по цене - "
                        + Gasket.getBitmexQuote().getAskPrice()
                );
                break;
            }

            if (priceBid <= priseTakeOrder) {
                setTake();

                // меняем число положительных / отрицательных сделок
                // а так же максимальный шаг мартина
                String data = (Integer.parseInt(StringHelper.giveData(TypeData.SELL, zeroString)) + 1) + "";
                String out = StringHelper.setData(TypeData.SELL, data, zeroString);

                new UpdatingStatisticsDataUser(out);
                openTransactions.zeroSteepTest(IDs);
                martingale.zeroSteep(IDs);

                ConsoleHelper.writeMessage(DatesTimes.getDateTerminal() + " --- "
                        + IDs + " --- Сработал ТЕЙК ПРОФИТ USER " + MARTINGALE.toString() + " - по цене - "
                        + Gasket.getBitmexQuote().getBidPrice()
                );
                break;
            }

            try {
                Thread.sleep(Gasket.getSECOND());
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage(DatesTimes.getDateTerminal() + " --- "
                        + IDs + " --- Не смогли проснуться в методе RUN класса TestOrderSellPatternMartingale");
            }
        }
        ConsoleHelper.printStatisticsMartingale();
    }


    private void setStop() {
        double openLot = openTransactions.getOrderVolumeTest(IDs);
        double priceLot = Gasket.getStop() / 10000.0;
        double result = 0.0;
        result = priceLot * openLot;
        martingale.setMartingalePROFIT(martingale.getMartingalePROFIT() - result);
    }

    private void setTake() {
        double priceLot = Gasket.getTake() / 10000.0;
        double openLot = openTransactions.getOrderVolumeTest(IDs);
        double result = 0.0;
        result = priceLot * openLot;
        martingale.setMartingalePROFIT(martingale.getMartingalePROFIT() + result);
    }
}