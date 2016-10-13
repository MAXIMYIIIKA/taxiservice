package by.nichipor.taxiservice.entity.type;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Max Nichipor on 28.08.2016.
 */

/**
 * Stores current date and time of the order.
 * <p>
 *     This type is like a DATETIME type in SQL.
 * </p>
 */
public class DateTime {
    private Date date;
    private Time time;

    public DateTime (Date date, Time time) {
        this.date = (Date) date.clone();
        this.time = (Time) time.clone();
    }

    /**
     * Returns current date and time.
     * @return a new instance of {@link DateTime} with current date and time.
     */
    public static DateTime getCurrentDateTime(){
        java.util.Date today = new java.util.Date();
        return new DateTime(new Date(today.getTime()), new Time(today.getTime()));
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public Time getTime() {
        return (Time) time.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DateTime dateTime = (DateTime) o;
        if (!getDate().equals(dateTime.getDate())) {
            return false;
        }
        return getTime().equals(dateTime.getTime());

    }

    @Override
    public int hashCode() {
        int result = getDate().hashCode();
        result = 31 * result + getTime().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return date + " " + time;
    }
}
