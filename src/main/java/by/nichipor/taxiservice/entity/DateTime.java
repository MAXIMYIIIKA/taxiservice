package by.nichipor.taxiservice.entity;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Max Nichipor on 28.08.2016.
 */
public class DateTime {
    private Date date;
    private Time time;

    public DateTime (Date date, Time time) {
        this.date = (Date) date.clone();
        this.time = (Time) time.clone();
    }

    public static DateTime getCurrentDateTime(){
        java.util.Date today = new java.util.Date();
        return new DateTime(new Date(today.getTime()), new Time(today.getTime()));
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }

    public Time getTime() {
        return (Time) time.clone();
    }

    public void setTime(Time time) {
        this.time = (Time) time.clone();
    }

    @Override
    public String toString() {
        return date + " " + time;
    }
}
