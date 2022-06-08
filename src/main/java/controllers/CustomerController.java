package controllers;

import services.CustomerService;

public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    public void printManagement() {
    }

}