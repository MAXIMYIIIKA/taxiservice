package by.nichipor.taxiservice.entity;


/**
 * Created by Max Nichipor on 21.08.2016.
 */

public class Order {
    private int orderId;
    private Location currentLocation;
    private Location targetLocation;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Location getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }
}
