/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

/**
 *
 * @author HP user
 */
public class Period2 implements IPeriod {

    private ITime bt;
    private long duration;

    /**
     *
     * A stretch of time with a begin time and end time. The end time is always
     * later then the begin time; the length of the period is always positive
     *
     *
     * WARNING: length of period should be smaller than Integer.MAXINT; this
     * restriction will never be checked
     *
     * @author Eric de Regter
     */
    public Period2(ITime bt, ITime et) {
        if (bt.compareTo(et) < 0) {
            this.bt = bt;
            duration = bt.difference(et);
        } else {
            throw new IllegalArgumentException("bt is later than et");
        }
    }

    /**
     *
     * @return the begin time of this period
     */
    @Override
    public ITime getBeginTime() {
        return bt;
    }

    /**
     *
     * @return the end time of this period
     */
    @Override
    public ITime getEndTime() {
        return bt.plus((int) duration);
    }

    /**
     *
     * @return the length of this period expressed in minutes (always positive)
     */
    @Override
    public int length() {
        return (int) duration;
    }

    /**
     * beginTime will be the new begin time of this period
     *
     * @param beginTime must be earlier than the current end time of this period
     */
    @Override
    public void setBeginTime(ITime beginTime) {
        if (beginTime.compareTo(bt.plus((int) duration)) < 0) {
            bt = beginTime;
        } else {
            throw new IllegalArgumentException("begin time is not earlier than the current end time");
        }
    }

    /**
     * endTime will be the new end time of this period
     *
     * @param endTime must be later than the current begin time of this period
     */
    @Override
    public void setEndTime(ITime endTime) {
        if (endTime.compareTo(bt) > 0) {
            duration = bt.difference(endTime);
        } else {
            throw new IllegalArgumentException("end time is not later than the current begin time");
        }
    }

    /**
     * the begin and end time of this period will be moved up both with
     * [minutes] minutes
     *
     * @param minutes (a negative value is allowed)
     */
    @Override
    public void move(int minutes) {
        bt = bt.plus(minutes);
    }

    /**
     * the end time of this period will be moved up with [minutes] minutes
     *
     * @param minutes minutes + length of this period must be positive
     */
    @Override
    public void changeLengthWith(int minutes) {
        if (bt.plus((int) duration).plus(minutes).compareTo(bt) > 0) {
            duration += minutes;
        } else {
            throw new IllegalArgumentException("Period will be negative");
        }
    }

    /**
     *
     * @param period
     * @return true if all moments within this period are included within
     * [period], otherwise false
     */
    @Override
    public boolean isPartOf(IPeriod period) {
        return bt.compareTo(period.getBeginTime()) > 0 && bt.plus((int) duration).compareTo(period.getEndTime()) < 0;
    }

    /**
     *
     * @param period
     * @return if this period and [period] are consecutive or possess a common
     * intersection, then the smallest period p will be returned, for which this
     * period and [period] are part of p, otherwise null will be returned
     */
    @Override
    public IPeriod unionWith(IPeriod period) {
        Period p;
        ITime et = bt.plus((int) duration);
        //kijkt of de periods opeenvolgend zijn, zo ja dan wordt de kleinste period gereturnd
        if (bt.compareTo(period.getEndTime()) == 0) {
            p = new Period(period.getBeginTime(), et);
            return p;
        } else if (et.compareTo(period.getBeginTime()) == 0) {
            p = new Period(bt, period.getEndTime());
            return p;
        } // deze periode ligt voor [period] en is een intersect
        else if (bt.compareTo(period.getBeginTime()) < 0 && (et.compareTo(period.getBeginTime()) > 0 && et.compareTo(period.getEndTime()) < 0)) {
            p = new Period(bt, period.getEndTime());
            return p;
        } // deze periode ligt na [period] en is een intersect
        else if ((bt.compareTo(period.getBeginTime()) > 0 && bt.compareTo(period.getEndTime()) < 0) && et.compareTo(period.getEndTime()) > 0) {
            p = new Period(period.getBeginTime(), et);
            return p;
        } // [period] ligt helemaal in deze period
        else if (bt.compareTo(period.getBeginTime()) < 0 && et.compareTo(period.getEndTime()) > 0) {
            p = new Period(bt, et);
            return p;
        } // deze period ligt helemaal in [period]
        else if (bt.compareTo(period.getBeginTime()) > 0 && et.compareTo(period.getEndTime()) < 0) {
            p = new Period(period.getBeginTime(), period.getEndTime());
            return p;
        } else {
            return null;
        }
    }

    /**
     *
     * @param period
     * @return the largest period which is part of this period and [period] will
     * be returned; if the intersection is empty null will be returned
     */
    @Override
    public IPeriod intersectionWith(IPeriod period) {
        Period p;
        ITime et = bt.plus((int) duration);
        // deze periode ligt voor [period] en is een intersect
        if (bt.compareTo(period.getBeginTime()) < 0 && (et.compareTo(period.getBeginTime()) > 0 && et.compareTo(period.getEndTime()) < 0)) {
            p = new Period(period.getBeginTime(), et);
            return p;
        } // deze periode ligt na [period] en is een intersect
        else if ((bt.compareTo(period.getBeginTime()) > 0 && bt.compareTo(period.getEndTime()) < 0) && et.compareTo(period.getEndTime()) > 0) {
            p = new Period(period.getBeginTime(), et);
            return p;
        } //[period] ligt helemaal in deze period
        else if (bt.compareTo(period.getBeginTime()) < 0 && et.compareTo(period.getEndTime()) > 0) {
            p = new Period(period.getBeginTime(), period.getEndTime());
            return p;
        } // deze period ligt helemaal in [period]
        else if (bt.compareTo(period.getBeginTime()) > 0 && et.compareTo(period.getEndTime()) < 0) {
            p = new Period(bt, et);
            return p;
        } else {
            return null;
        }
    }

}
