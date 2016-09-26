package by.nichipor.taxiservice.entity;

/**
 * Created by Max Nichipor on 21.08.2016.
 */

public class Order {
    private int orderId;
    private String username;
    private Location currentLocation;
    private Location targetLocation;
    private OrderStatus status;
    private DateTime dateTime;
    private String phone;

    public Order(String username, Location currentLocation, String phone){
        this.username = username;
        this.currentLocation = currentLocation;
        this.targetLocation = new Location(0,0);
        this.phone = phone;
        newOrder();
    }

    public Order(String username,
                 Location currentLocation,
                 Location targetLocation, String phone){
        this.username = username;
        this.currentLocation = currentLocation;
        this.targetLocation = targetLocation;
        this.phone = phone;
        newOrder();
    }

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
