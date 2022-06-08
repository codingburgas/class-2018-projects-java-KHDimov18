package services;

import models.Customer;
import models.Order;
import repositories.CustomerRepository;
import repositories.OrderRepository;

import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public Boolean addCustomer(String firstName, String lastName, String phoneNumber){
        Customer customer = new Customer(0L, firstName, lastName, phoneNumber);
        Long customerId = customerRepository.addCustomer(customer);
        if(customerId!=-1L){
            return true;
        }else{
            return false;
        }

    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.getCustomerById(customerId);
    }

    public List<Customer> getCustomers() {
        return customerRepository.getCustomers();
    }

    public Boolean updateCustomer(Long customerId, String firstName, String lastName, String phoneNumber) {
        Customer customer = getCustomerById(customerId);
        if(customer!=null)
        {
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setPhoneNumber(phoneNumber);
            return customerRepository.updateCustomer(customer, customer.getCustomerId());
        } else {
            return false;
        }
    }

    public Boolean deleteCustomer(Long customerId)  {
        return customerRepository.deleteCustomer(customerId);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.getOrdersByCustomerId(customerId);
    }
}
