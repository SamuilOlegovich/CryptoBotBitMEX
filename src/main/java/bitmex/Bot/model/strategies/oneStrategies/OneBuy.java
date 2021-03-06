package bitmex.Bot.model.strategies.oneStrategies;

import bitmex.Bot.model.serverAndParser.InfoIndicator;
import bitmex.Bot.model.enums.TimeFrame;
import bitmex.Bot.model.Gasket;

import java.util.Date;

import static bitmex.Bot.model.Gasket.getTimeCalculationLevel;

public class OneBuy {
    private static OneBuy oneBuy;

    private InfoIndicator maxOpenInterestMinus;
    private InfoIndicator maxOpenInterestPlus;
    private InfoIndicator openInterestMinus;
    private InfoIndicator maxDeltaMinus;
    private InfoIndicator deltaMinus2;
    private InfoIndicator deltaMinus;
    private InfoIndicator volume2;
    private InfoIndicator volume;
    private InfoIndicator bid2;
    private InfoIndicator bid;

    private OneBuy() {
    }

    public static OneBuy getInstance() {
        if (oneBuy == null) oneBuy = new OneBuy();
        return oneBuy;
    }



    public synchronized void setInfoString(InfoIndicator infoIndicator) {
        switch (infoIndicator.getType()) {
            case OPEN_POS_MINUS_HL:
                maxOpenInterestMinus = infoIndicator;
                break;
            case OPEN_POS_PLUS_HL:
                maxOpenInterestPlus = infoIndicator;
                break;
            case OPEN_POS_MINUS:
                openInterestMinus = infoIndicator;
                break;
            case DELTA_BID_HL:
                maxDeltaMinus = infoIndicator;
                break;
            case DELTA_BID:
                setDeltaMinus(infoIndicator);
                break;
            case VOLUME:
                setVolume(infoIndicator);
                break;
            case BID:
                setBid(infoIndicator);
                break;
        }
        makeADecision();
    }



    private void setBid(InfoIndicator infoIndicator) {
        if (bid == null && bid2 == null) bid = infoIndicator;
        else if (bid != null && bid2 == null) {
            if (!isRangeTimeLevel(bid, infoIndicator)) {
                bid = infoIndicator;
            } else if (bid.getPrice() > infoIndicator.getPrice()
                    && isRangeTimeLevel(bid, infoIndicator)
                    && (bid.getPeriod() == infoIndicator.getPeriod())) {
                bid = infoIndicator;
            } else if (isRangeTimeLevel(bid, infoIndicator)
                    && isBigTimeFrame(bid, infoIndicator)) {
                bid = infoIndicator;
            } else {
                bid2 = infoIndicator;
            }
        } else if (bid != null && bid2 != null) {

            if (!isRangeTimeLevel(bid, infoIndicator)) {
                bid = bid2;
                bid2 = null;

                if (bid != null && bid2 == null) {
                    if (bid.getPrice() > infoIndicator.getPrice()
                            && isRangeTimeLevel(bid, infoIndicator)
                            && (bid.getPeriod() == infoIndicator.getPeriod())) {
                        bid = infoIndicator;
                    } else if (isRangeTimeLevel(bid, infoIndicator)
                            && isBigTimeFrame(bid, infoIndicator)) {
                        bid = infoIndicator;
                    } else {
                        bid2 = infoIndicator;
                    }
                }
            } else {

                if (bid2.getPrice() > infoIndicator.getPrice()
                        && isRangeTimeLevel(bid, infoIndicator)
                        && (bid2.getPeriod() == infoIndicator.getPeriod())) {
                    bid = infoIndicator;
                    bid2 = null;
                } else if (isRangeTimeLevel(bid, infoIndicator)
                        && isBigTimeFrame(bid2, infoIndicator)) {
                    bid = infoIndicator;
                    bid2 = null;
                } else {
                    bid2 = infoIndicator;
                }
            }
        }
    }



    private void setDeltaMinus(InfoIndicator infoIndicator) {
        if (deltaMinus == null && deltaMinus2 == null) bid = infoIndicator;
        else if (deltaMinus != null && deltaMinus2 == null) {
            if (!isRangeTimeLevel(deltaMinus, infoIndicator)) {
                deltaMinus = infoIndicator;
            } else if (deltaMinus.getPrice() > infoIndicator.getPrice()
                    && isRangeTimeLevel(deltaMinus, infoIndicator)
                    && (deltaMinus.getPeriod() == infoIndicator.getPeriod())) {
                deltaMinus = infoIndicator;
            } else if (isRangeTimeLevel(deltaMinus, infoIndicator)
                    && isBigTimeFrame(deltaMinus, infoIndicator)) {
                deltaMinus = infoIndicator;
            } else {
                deltaMinus2 = infoIndicator;
            }
        } else if (deltaMinus != null && deltaMinus2 != null) {

            if (!isRangeTimeLevel(deltaMinus, infoIndicator)) {
                deltaMinus = deltaMinus2;
                deltaMinus2 = null;

                if (deltaMinus != null && deltaMinus2 == null) {
                    if (deltaMinus.getPrice() > infoIndicator.getPrice()
                            && isRangeTimeLevel(deltaMinus, infoIndicator)
                            && (deltaMinus.getPeriod() == infoIndicator.getPeriod())) {
                        deltaMinus = infoIndicator;
                    } else if (isRangeTimeLevel(deltaMinus, infoIndicator)
                            && isBigTimeFrame(deltaMinus, infoIndicator)) {
                        deltaMinus = infoIndicator;
                    } else {
                        deltaMinus2 = infoIndicator;
                    }
                }
            } else {

                if (deltaMinus2.getPrice() > infoIndicator.getPrice()
                        && isRangeTimeLevel(deltaMinus, infoIndicator)
                        && (deltaMinus2.getPeriod() == infoIndicator.getPeriod())) {
                    deltaMinus = infoIndicator;
                    deltaMinus2 = null;
                } else if (isRangeTimeLevel(deltaMinus, infoIndicator)
                        && isBigTimeFrame(deltaMinus2, infoIndicator)) {
                    deltaMinus = infoIndicator;
                    deltaMinus2 = null;
                } else {
                    deltaMinus2 = infoIndicator;
                }
            }
        }
    }


    private void setVolume(InfoIndicator infoIndicator) {
        // если все уровни пусты - назначаем сразу
        if (volume == null && volume2 == null) volume = infoIndicator;
        // иначе если первый не пуст, а второй пуст
        else if (volume != null && volume2 == null) {
            // если первый уровень сдох по времени - томинуя второй присваиваем ему уровень
            if (!isRangeTimeLevel(volume, infoIndicator)) {
                volume = infoIndicator;
                // если же уровень по свежий, находится ниже предыдущего и имеет тот же таймфрейм
                // то опять присваеваем его первому уровню
            } else if (volume.getPrice() > infoIndicator.getPrice()
                    && isRangeTimeLevel(volume, infoIndicator)
                    && (volume.getPeriod() == infoIndicator.getPeriod())) {
                volume = infoIndicator;
                // если же уровень свежий и имеет больший таймфрейм - то присваеваем его первому уровню
                // (вот тут бы я поработал еще вставил какие-то варианты дополнительные варианты с уровнями
                // большего таймфрейма, дополнительное какое-то сравнение)
            } else if (isRangeTimeLevel(volume, infoIndicator)
                    && isBigTimeFrame(volume, infoIndicator)) {
                volume = infoIndicator;
            } else {
                // если все не подошло то второму
                volume2 = infoIndicator;
            }
        // если два уровня не пустые
        } else if (volume != null && volume2 != null) {
            // если первый уровень сдох по времени, сразу перекидываем в него второй уровень и дальше проверяем
            if (!isRangeTimeLevel(volume, infoIndicator)) {
                volume = volume2;
                volume2 = null;
                // если же уровень по свежий, находится ниже предыдущего и имеет тот же таймфрейм
                // то опять присваеваем его первому уровню
                if (volume != null && volume2 == null) {
                    if (volume.getPrice() > infoIndicator.getPrice()
                            && isRangeTimeLevel(volume, infoIndicator)
                            && (volume.getPeriod() == infoIndicator.getPeriod())) {
                        volume = infoIndicator;
                        // если же уровень свежий и имеет больший таймфрейм - то присваеваем его первому уровню
                        // (вот тут бы я поработал еще вставил какие-то варианты дополнительные варианты с уровнями
                        // большего таймфрейма, дополнительное какое-то сравнение)
                    } else if (isRangeTimeLevel(volume, infoIndicator)
                            && isBigTimeFrame(volume, infoIndicator)) {
                        volume = infoIndicator;
                    } else {
                        // если все не подошло то второму
                        volume2 = infoIndicator;
                    }
                }
                // если же первый уровень не сдох по времени и у нас заполнены два уровня
            } else {
                // если уровень два больше пришедшего уровня и и не сдох по времени и имеет тот же таймфрейм
                // то входящий уровень ставим на первое место и стираем второй уровень
                if (volume2.getPrice() > infoIndicator.getPrice()
                        && isRangeTimeLevel(volume, infoIndicator)
                        && (volume2.getPeriod() == infoIndicator.getPeriod())) {
                    volume = infoIndicator;
                    volume2 = null;
                    // делаем тоже самое если уровень еще жив но пришел тайфремй больше чем стоит у нас в уровнях
                    // (вот тут бы я поработал еще вставил какие-то варианты дополнительные варианты с уровнями
                    // большего таймфрейма, дополнительное какое-то сравнение)
                } else if (isRangeTimeLevel(volume, infoIndicator)
                        && isBigTimeFrame(volume2, infoIndicator)) {
                    volume = infoIndicator;
                    volume2 = null;
                    // ну и если ничего из этого, то назначаем входящий уровень на второе место
                } else {
                    volume2 = infoIndicator;
                }
            }
        }
    }


    // проверяем больший ли траймфрейм у данного уровня или нет
    private boolean isBigTimeFrame(InfoIndicator one, InfoIndicator two) {
//        return one.getPeriod().ordinal() < two.getPeriod().ordinal();
        return true;
    }


    // принимаем решение
    private synchronized void makeADecision() {
        if (volume == null || bid == null || deltaMinus == null || openInterestMinus == null
                || maxOpenInterestPlus == null || maxOpenInterestMinus == null
                || maxDeltaMinus == null) {
            return;
        } else if (!isTimeNotOld()) {
            volume = null;
        } else if (inTheRangePrice() && inTheRangeTime()) {
            if (Gasket.getStrategyWorkOne() == 1) {
                if (Gasket.isOb_os_Flag()) {
                    Gasket.setOb_os_Flag(false);
                    new StrategyOneBuyThread(
                            ((int) (Math.round(Math.abs(Math.random() * 200 - 100)) * 39))
                                    + "-OB", volume, getMin());
                }
            } else if (Gasket.getStrategyWorkOne() == 2) {
                if (Gasket.isObFlag()) {
                    Gasket.setObFlag(false);
                    new StrategyOneBuyThread(
                            ((int) (Math.round(Math.abs(Math.random() * 200 - 100)) * 39))
                                    + "-OB", volume, getMin());
                }
            }

            maxOpenInterestMinus = null;
            maxOpenInterestPlus = null;
            openInterestMinus = null;
            maxDeltaMinus = null;
            deltaMinus2 = null;
            deltaMinus = null;
            volume2 = null;
            volume = null;
            bid2 = null;
            bid = null;
        }
    }



    // находим цену самого нижнего уровня
    private InfoIndicator getMin() {

        InfoIndicator infoIndicator = maxOpenInterestMinus.getPrice() < maxOpenInterestPlus.getPrice()
                ? maxOpenInterestMinus : maxOpenInterestPlus;
        infoIndicator = infoIndicator.getPrice() < openInterestMinus.getPrice()
                ? infoIndicator : openInterestMinus;
        infoIndicator = infoIndicator.getPrice() < maxDeltaMinus.getPrice()
                ? infoIndicator : maxDeltaMinus;
        infoIndicator = infoIndicator.getPrice() < deltaMinus.getPrice()
                ? infoIndicator : deltaMinus;
        return infoIndicator;
    }


    // не старый ли уровень
    private boolean isTimeNotOld() {

        InfoIndicator infoIndicator = maxOpenInterestMinus.getTime().getTime() > maxOpenInterestPlus.getTime().getTime()
                ? maxOpenInterestMinus : maxOpenInterestPlus;
        infoIndicator = infoIndicator.getTime().getTime() > openInterestMinus.getTime().getTime()
                ? infoIndicator : openInterestMinus;
        infoIndicator = infoIndicator.getTime().getTime() > maxDeltaMinus.getTime().getTime()
                ? infoIndicator : maxDeltaMinus;
        infoIndicator = infoIndicator.getTime().getTime() > deltaMinus.getTime().getTime()
                ? infoIndicator : deltaMinus;

        if ((infoIndicator.getTime().getTime() - volume.getTime().getTime())
                < (long) (1000 * 60 * getTimeCalculationLevel())) {
            return true;
        } else {
            return false;
        }
    }


    // проверяем вписываемся ли в диапазон цен
    private boolean inTheRangePrice() {

        return (maxOpenInterestMinus.getPrice() <= volume.getPrice())
                && (maxOpenInterestPlus.getPrice() <= volume.getPrice())
                && (openInterestMinus.getPrice() <= volume.getPrice())
                && (maxDeltaMinus.getPrice() <= volume.getPrice())
                && (deltaMinus.getPrice() <= volume.getPrice());
    }


    private boolean isRangeTimeLevel(InfoIndicator one, InfoIndicator two) {
        if ((two.getTime().getTime() - one.getTime().getTime())
                < (long) (1000 * 60 * getTimeCalculationLevel())) {
            return true;
        } else {
            return false;
        }
    }


    // проверяем нет ли тут предварительных уровней
    private boolean isReal() {

        return volume.getPreview() + bid.getPreview() + deltaMinus.getPreview() + openInterestMinus.getPreview()
                + maxOpenInterestPlus.getPreview() + maxOpenInterestMinus.getPreview() +
                maxDeltaMinus.getPreview() == 0;
    }


    // проверяем входим ли в диапазон по датам событий
    private boolean inTheRangeTime() {

        Date after = volume.getTime();

        return (maxOpenInterestMinus.getTime().getTime() >= after.getTime())
                && (maxOpenInterestPlus.getTime().getTime() >= after.getTime())
                && (openInterestMinus.getTime().getTime() >= after.getTime())
                && (maxDeltaMinus.getTime().getTime() >= after.getTime())
                && (deltaMinus.getTime().getTime() >= after.getTime());
    }
}
