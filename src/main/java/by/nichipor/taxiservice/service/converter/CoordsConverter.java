package by.nichipor.taxiservice.service.converter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Max Nichipor on 26.09.2016.
 */

/**
 * This is a converter for decimal coordinates
 * to convert them in degrees minutes seconds (DMS) format.
 * @author Max Nichipor
 */
public class CoordsConverter {
    private static String degreesMinutesSeconds;
    private static Lock lock = new ReentrantLock();

    /**
     * This static method is used to convert decimal coordinates
     * to degrees,minutes and seconds.
     * @param coords decimal coordinates as a {@link String string}.
     * @return the DMS coordinates as a {@link String string}.
     */
    public static String convert(String coords) {
        lock.lock();
        try {
            parseDergees(coords);
            return degreesMinutesSeconds;
        } finally {
            CoordsConverter.lock.unlock();
        }
    }

    private static void parseDergees(String coords) {
        CoordsConverter.degreesMinutesSeconds = coords.split("\\.")[0] + "°";
        parseMinutes(coords.split("\\.")[1]);
    }

    private static void parseMinutes(String minutes) {
        minutes = String.valueOf(Double.parseDouble("0." + minutes) * 60);
        CoordsConverter.degreesMinutesSeconds += minutes.split("\\.")[0] + "\'";
        parseSeconds(minutes.split("\\.")[1]);
    }

    private static void parseSeconds(String seconds) {
        CoordsConverter.degreesMinutesSeconds += String.valueOf(Double.parseDouble("0." + seconds) * 60).split("\\.")[0] + "\"";
    }
}
