package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order {
    private Long orderId;
    private Long customerId;
    private String deliveryAddress;
    private Date shipmentDate;
    private List<Product> products;

    public Order(Long orderId, Long customerId, String deliveryAddress, Date shipmentDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.shipmentDate = shipmentDate;
        this.products = new ArrayList<>();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", shipmentDate='" + shipmentDate + '\'' +
                ", products=" + products +
                '}';
    }
}
