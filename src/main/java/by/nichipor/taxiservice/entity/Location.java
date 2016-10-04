package by.nichipor.taxiservice.entity;

/**
 * Created by Max Nichipor on 21.08.2016.
 */

import by.nichipor.taxiservice.service.converter.CoordsConverter;

/**
 * This is an immutable location instance.
 */
public class Location {
    private double lat;
    private double lng;
    private String degreesMinutesSeconds;

    public Location(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
        if (lat != 0 && lng != 0) {
            degreesMinutesSeconds = "Lat: " + CoordsConverter.convert(String.valueOf(lat))
                    + "; Lng: " + CoordsConverter.convert(String.valueOf(lng));
        } else {
            degreesMinutesSeconds = "";
        }
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getDegreesMinutesSeconds() {
        return degreesMinutesSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if (Double.compare(location.getLat(), getLat()) != 0) {
            return false;
        }
        return Double.compare(location.getLng(), getLng()) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getLat());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLng());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getDegreesMinutesSeconds() != null ? getDegreesMinutesSeconds().hashCode() : 0);
        return result;
    }
}
