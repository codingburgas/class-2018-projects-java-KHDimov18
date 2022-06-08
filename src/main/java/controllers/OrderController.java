package controllers;

import services.OrderService;

public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    public void printManagement() {
    }

}