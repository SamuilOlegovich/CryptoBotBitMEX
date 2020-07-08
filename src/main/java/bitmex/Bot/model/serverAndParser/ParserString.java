package bitmex.Bot.model.serverAndParser;


import bitmex.Bot.model.Gasket;
import bitmex.Bot.model.StrategyFactory;
import bitmex.Bot.model.StringHelper;
import bitmex.Bot.model.enums.TimeFrame;
import bitmex.Bot.model.enums.TypeData;
import bitmex.Bot.view.ConsoleHelper;
import bitmex.Bot.model.enums.BidAsk;
import bitmex.Bot.model.DatesTimes;
import bitmex.Bot.view.WriterAndReadFile;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static bitmex.Bot.model.StringHelper.giveData;


public class ParserString {
    private StrategyFactory strategyFactory;
    private boolean linkWithIndicator;



    public ParserString() {
        this.strategyFactory = StrategyFactory.getInstance();
        this.linkWithIndicator = true;
        ConsoleHelper.writeMessage(DatesTimes.getDateTerminal() + " --- ПАРСЕР ЗАПУЩЕН");
    }

    public synchronized void parserStringJson(String string) {
        WriterAndReadFile.writerFile(string, Gasket.getFilesAndPathCreator().getPathFullHistory(), true);

        if (linkWithIndicator) {
            if (string.length() > 20) {
                linkWithIndicator = false;
                ConsoleHelper.writeMessage(DatesTimes.getDateTerminal()
                        + " --- СВЯЗЬ С ИНДИКАТОРОМ УСТАНОВЛЕНА");
                ConsoleHelper.writeMessage(DatesTimes.getDateTerminal()
                        + " --- " + string);
            }
        }
//        showAllAvailableLevels(string);

//        String in = string.replaceAll("\\{", "");
//        in = in.replaceAll("}", "");
//        in = in.replaceAll("\"", " ");
//        String[] strings = in.split(" , ");

//        TimeFrame period = getTimeFrame(strings[0].trim().replaceAll("period : {2}",""));
//        int preview = Integer.parseInt(strings[1].trim().replaceAll("preview : {2}", ""));
//        Date time = getDate(strings[2].trim().replaceAll("time : {2}", ""));
//        double price = getDouble(strings[3].trim().replaceAll("price : {2}", ""));
//        long value = Long.parseLong(strings[4].trim().replaceAll("value : {2}", ""));
//        BidAsk type = getType(strings[5].trim().replaceAll("type : {2}", ""));
//        long avg = Long.parseLong(strings[6].trim().replaceAll("avg : {2}", ""));
//        int dir = Integer.parseInt(strings[7].trim().replaceAll("dir : {2}", ""));
//        double open = getDouble(strings[8].trim().replaceAll("open : {2}", ""));
//        double close = getDouble(strings[9].trim().replaceAll("close : {2}", ""));
//        double high = getDouble(strings[10].trim().replaceAll("high : {2}", ""));
//        double low = getDouble(strings[11].trim().replaceAll("low : {2}", ""));

        double price = Double.parseDouble(giveData(TypeData.price, string));
        double close = Double.parseDouble(giveData(TypeData.close, string));
        TimeFrame period = getTimeFrame(giveData(TypeData.period, string));
        int preview = Integer.parseInt(giveData(TypeData.preview, string));
        double open = Double.parseDouble(giveData(TypeData.open, string));
        double high = Double.parseDouble(giveData(TypeData.high, string));
        double low = Double.parseDouble(giveData(TypeData.low, string));
        long value = Long.parseLong(giveData(TypeData.value, string));
        int dir = Integer.parseInt(giveData(TypeData.dir, string));
        long avg = Long.parseLong(giveData(TypeData.avg, string));
        BidAsk type = getType(giveData(TypeData.type, string));
        Date time = getDate(giveData(TypeData.time, string));


        InfoIndicator infoIndicator =
                new InfoIndicator(period, preview, time, price, value, type, avg, dir, open, close, high, low);
        strategyFactory.onOff(infoIndicator);
    }



    private Date getDate(String string) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date dateFromString = null;

        try {
            dateFromString = simpleDateFormat.parse(string);
        } catch (Exception e) {
            ConsoleHelper.writeMessage("неверный формат даты");
        }
        return dateFromString;
    }



    private Double getDouble(String string) {
        return Double.parseDouble(string.replaceAll(",", "."));
    }



    private TimeFrame getTimeFrame(String string) {
        TimeFrame[] timeFrames = TimeFrame.values();

        for (TimeFrame timeFrame : timeFrames) {
            if (timeFrame.toString().equalsIgnoreCase(string)) {
                return timeFrame;
            }
        }
        return null;


    }



    private BidAsk getType(String string) {
        BidAsk[] bidAsks = BidAsk.values();

        for (BidAsk bidAsk : bidAsks) {
            if (bidAsk.toString().replaceAll("_", "").equalsIgnoreCase(string)) {
                return bidAsk;
            }
        }
        return null;
    }
}
