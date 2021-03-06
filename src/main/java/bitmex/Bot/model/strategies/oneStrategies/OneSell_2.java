package bitmex.Bot.model.strategies.oneStrategies;

import bitmex.Bot.model.serverAndParser.InfoIndicator;
import bitmex.Bot.model.Gasket;

import java.util.Date;




// плюс минус в такой комбинации стратегия работает надо изучать
// ее статистику ну и потом можно что-то додумать и дополнить
public class OneSell_2 {
    private static OneSell_2 oneSell2;

    private InfoIndicator maxOpenInterestMinus;
    private InfoIndicator openInterestPlus;
    private InfoIndicator maxDeltaMinus;
    private InfoIndicator maxDeltaPlus2;
    private InfoIndicator maxDeltaPlus;
    private InfoIndicator deltaPlus;
    private InfoIndicator volume;
    private InfoIndicator ask;

    private int countDelta = 0;


    private OneSell_2() {
    }

    public static OneSell_2 getInstance() {
        if (oneSell2 == null) oneSell2 = new OneSell_2();
        return oneSell2;
    }



    public void setInfoString(InfoIndicator infoIndicator) {
        switch (infoIndicator.getType()) {
            case OPEN_POS_MINUS_HL:
                maxOpenInterestMinus = infoIndicator;
                break;
            case OPEN_POS_PLUS:
                openInterestPlus = infoIndicator;
                break;
            case DELTA_BID_HL:
                maxDeltaMinus = infoIndicator;
                break;
            case DELTA_ASK:
                deltaPlus = infoIndicator;
                break;
            case DELTA_ASK_HL:
                twoDelta(infoIndicator);
                break;
            case VOLUME:
                volume = infoIndicator;
                break;
            case ASK:
                ask = infoIndicator;
                break;
        }
        makeADecision();
    }


    // принимаем решение
    private synchronized void makeADecision() {
        if (volume == null || ask == null || deltaPlus == null || maxOpenInterestMinus == null
                || maxDeltaPlus == null || openInterestPlus == null || maxDeltaPlus2 == null
                || maxDeltaMinus == null ) {
            return;
        }

        if (inTheRangePrice() && inTheRangeTime() /*&& isReal()*/) {
            if (Gasket.getStrategyWorkOne() == 1) {
                if (Gasket.isOb_os_Flag()) {
                    Gasket.setOb_os_Flag(false);
                    new StrategyOneSellThread(
                            ((int)(Math.round(Math.abs(Math.random()*200 - 100)) * 39))
                                    + "-OS_2", volume, getMin());
                }
            } else if (Gasket.getStrategyWorkOne() == 2) {
                if (Gasket.isOsFlag_2()) {
                    Gasket.setOsFlag_2(false);
                    new StrategyOneSellThread(
                            ((int) (Math.round(Math.abs(Math.random() * 200 - 100)) * 39))
                                    + "-OS_2", volume, getMin());
                }
            }
//            maxOpenInterestMinus = null;
//            openInterestPlus = null;
//            maxDeltaMinus = null;
//            maxDeltaPlus2 = null;
//            maxDeltaPlus = null;
//            deltaPlus = null;
            volume = null;
//            ask = null;
        }
    }


    // находим найвысший элемен, это и будет точка минимум для села
    private InfoIndicator getMin() {
        InfoIndicator infoIndicator = maxOpenInterestMinus.getPrice() > openInterestPlus.getPrice()
                ? maxOpenInterestMinus : openInterestPlus;
        infoIndicator = infoIndicator.getPrice() > maxDeltaMinus.getPrice()
                ? infoIndicator : maxDeltaMinus;
        infoIndicator = infoIndicator.getPrice() > maxDeltaPlus2.getPrice()
                ? infoIndicator : maxDeltaPlus2;
        infoIndicator = infoIndicator.getPrice() > maxDeltaPlus.getPrice()
                ? infoIndicator : maxDeltaPlus;
        infoIndicator = infoIndicator.getPrice() > deltaPlus.getPrice()
                ? infoIndicator : deltaPlus;
        return infoIndicator;
    }


    // проверяем вписываемся ли в диапазон цен
    private boolean inTheRangePrice() {
//        double topLevel = volume.getPrice() > ask.getPrice()
//                ? volume.getPrice() + Gasket.getRangeLevel() : ask.getPrice() + Gasket.getRangeLevel();
//
//        return (maxOpenInterestMinus.getPrice() >= volume.getPrice() && maxOpenInterestMinus.getPrice() <= topLevel)
//                && (openInterestPlus.getPrice() >= volume.getPrice() && openInterestPlus.getPrice() <= topLevel)
//                && (maxDeltaMinus.getPrice() >= volume.getPrice() && maxDeltaMinus.getPrice() <= topLevel)
//                && (maxDeltaPlus2.getPrice() >= volume.getPrice() && maxDeltaPlus2.getPrice() <= topLevel)
//                && (maxDeltaPlus.getPrice() >= volume.getPrice() && maxDeltaPlus.getPrice() <= topLevel)
//                && (deltaPlus.getPrice() >= volume.getPrice() && deltaPlus.getPrice() <= topLevel);

        return (maxOpenInterestMinus.getPrice() >= volume.getPrice())
                && (openInterestPlus.getPrice() >= volume.getPrice())
                && (maxDeltaMinus.getPrice() >= volume.getPrice())
                && (maxDeltaPlus2.getPrice() >= volume.getPrice())
                && (maxDeltaPlus.getPrice() >= volume.getPrice())
                && (deltaPlus.getPrice() >= volume.getPrice());
    }


    // проверяем нет ли тут предварительных уровней
    private boolean isReal() {

        return volume.getPreview() + ask.getPreview() + maxOpenInterestMinus.getPreview() + openInterestPlus.getPreview()
                + maxDeltaMinus.getPreview() + maxDeltaPlus.getPreview() + maxDeltaPlus2.getPreview()
                + deltaPlus.getPreview() == 0;
    }


    private void twoDelta(InfoIndicator iInfoIndicator) {

        if (countDelta == 0) {
            maxDeltaPlus = iInfoIndicator;
            countDelta = 1;
        } else {
            maxDeltaPlus2 = iInfoIndicator;
            countDelta = 0;
        }
    }


    // проверяем входим ли в диапазон по датам событий
    private boolean inTheRangeTime() {
//        Date before = maxDeltaMinus.getTime();
        Date after = volume.getTime();
//
//        return (maxOpenInterestMinus.getTime().getTime() >= after.getTime()
//                && maxOpenInterestMinus.getTime().getTime() <= before.getTime())
//                && (openInterestPlus.getTime().getTime() >= after.getTime()
//                && openInterestPlus.getTime().getTime() <= before.getTime())
//                && (maxDeltaPlus2.getTime().getTime() >= after.getTime()
//                && maxDeltaPlus2.getTime().getTime() <= before.getTime())
//                && (maxDeltaPlus.getTime().getTime() >= after.getTime()
//                && maxDeltaPlus.getTime().getTime() <= before.getTime())
//                && (deltaPlus.getTime().getTime() >= after.getTime()
//                && deltaPlus.getTime().getTime() <= before.getTime())
//                && (ask.getTime().getTime() >= after.getTime()
//                && ask.getTime().getTime() <= before.getTime());

        return (maxOpenInterestMinus.getTime().getTime() >= after.getTime())
                && (openInterestPlus.getTime().getTime() >= after.getTime())
                && (maxDeltaPlus2.getTime().getTime() >= after.getTime())
                && (maxDeltaMinus.getTime().getTime() >= after.getTime())
                && (maxDeltaPlus.getTime().getTime() >= after.getTime())
                && (deltaPlus.getTime().getTime() >= after.getTime());
    }
}
