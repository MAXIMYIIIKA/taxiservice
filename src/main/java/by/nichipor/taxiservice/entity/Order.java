package by.nichipor.taxiservice.entity;

/**
 * Created by Max Nichipor on 21.08.2016.
 */

import by.nichipor.taxiservice.entity.type.DateTime;

/**
 * This is the order instance.
 */
public class Order {
    private int orderId;
    private String username;
    private Location currentLocation;
    private Location targetLocation;
    private OrderStatus status;
    private DateTime dateTime;
    private String phone;

    /**
     * This constructor creates a new order only with current location.
     * @param username username of the user who makes the order.
     * @param currentLocation current location of the user.
     * @param phone the user's phone number.
     */
    public Order(String username, Location currentLocation, String phone){
        this.username = username;
        this.currentLocation = currentLocation;
        this.targetLocation = new Location(0,0);
        this.phone = phone;
        newOrder();
    }

    /**
     * This constructor creates a new order with both current and target location.
     * @param username username of the user who makes the order.
     * @param currentLocation current location of the user.
     * @param targetLocation target location of the user.
     * @param phone the user's phone number.
     */
    public Order(String username,
                 Location currentLocation,
                 Location targetLocation, String phone){
        this.username = username;
        this.currentLocation = currentLocation;
        this.targetLocation = targetLocation;
        this.phone = phone;
        newOrder();
    }

    /**
     * This constructor creates the order object to be filled from the database.
     * @param id order ID.
     * @param username name of the user who made the order.
     * @param currentLocation start location of the user.
     * @param targetLocation target location of the user.
     * @param dateTime order's date and time.
     * @param status order's status.
     * @param phone the user's phone number.
     */
    public Order(int id, String username,
                 Location currentLocation,
                 Location targetLocation,
                 DateTime dateTime,
                 OrderStatus status, String phone){
        this.orderId = id;
        this.username = username;
        this.currentLocation = currentLocation;
        if (targetLocation != null) {
            this.targetLocation = targetLocation;
        }
        this.dateTime = dateTime;
        this.status = status;
        this.phone = phone;
    }

    private void newOrder(){
        this.status = OrderStatus.PROCESSING;
        this.dateTime = DateTime.getCurrentDateTime();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        if (getOrderId() != order.getOrderId()) {
            return false;
        }
        if (!getUsername().equals(order.getUsername())) {
            return false;
        }
        if (!getCurrentLocation().equals(order.getCurrentLocation())) {
            return false;
        }
        if (getTargetLocation() != null ? !getTargetLocation().equals(order.getTargetLocation()) : order.getTargetLocation() != null)
            return false;
        if (getStatus() != order.getStatus()) {
            return false;
        }
        if (!getDateTime().equals(order.getDateTime())) {
            return false;
        }
        return getPhone().equals(order.getPhone());

    }

    @Override
    public int hashCode() {
        int result = getOrderId();
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getCurrentLocation().hashCode();
        result = 31 * result + (getTargetLocation() != null ? getTargetLocation().hashCode() : 0);
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + getDateTime().hashCode();
        result = 31 * result + getPhone().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", username='" + username + '\'' +
                ", currentLocation=" + currentLocation +
                ", targetLocation=" + targetLocation +
                ", status=" + status +
                ", dateTime=" + dateTime +
                ", phone='" + phone + '\'' +
                '}';
    }
}
