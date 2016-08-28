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
        this.date = date;
        this.time = time;
    }

    public static DateTime getCurrentDateTime(){
        java.util.Date today = new java.util.Date();
        return new DateTime(new Date(today.getTime()), new Time(today.getTime()));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
