package controllers;

import models.Customer;
import services.CustomerService;
import utils.PrintUtils;

import java.util.List;
import java.util.Scanner;

public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void printManagement() {
        Scanner sc = new Scanner(System.in);
        printManageCustomersMenu();

        Integer command = Integer.parseInt(sc.nextLine());
        while(command!=5) {
            switch(command) {
                case 1:
                    listAllCustomers();
                    break;
                case 2:
                    editCustomer();
                    break;
                case 3:
                    deleteCustomer();
                    break;
                case 4:
                    addNewCustomer();
                    break;

                default:
                    System.out.println("Wrong command!");
            }

            printManageCustomersMenu();

            command = Integer.parseInt(sc.nextLine());
        }
    }

    private void listAllCustomers() {
        List<Customer> customers = customerService.getCustomers();
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%s|", PrintUtils.center("CUSTOMERS LIST", 58)));
        System.out.println(new String("-").repeat(60));
        System.out.println(
                String.format(
                        "|%1$-5s|%2$-20s|%3$-20s|%4$-10s|",
                        "ID", "First name", "Last name", "Phone"
                )
        );

        for (Customer customer : customers) {
            System.out.println(
                    String.format(
                            "|%1$-5s|%2$-20s|%3$-20s|%4$-10s|",
                            customer.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getPhoneNumber()
                    )
            );
        }
        System.out.println(new String("-").repeat(60));

    }

    public void editOwnDetails(Long customerId) {
        Scanner sc = new Scanner(System.in);
        Customer customer = customerService.getCustomerById(customerId);
        if(customer!=null)
        {
            System.out.println("Current first name: " + customer.getFirstName());
            System.out.println("Current last name: " + customer.getLastName());
            System.out.println("Current phone: " + customer.getPhoneNumber());

            System.out.println("Enter new first name: ");
            String firstName = sc.nextLine();

            System.out.println("Enter new last name: ");
            String lastName = sc.nextLine();

            System.out.println("Enter new phone: ");
            String phoneNumber = sc.nextLine();

            Boolean result = customerService.updateCustomer(customerId, firstName, lastName, phoneNumber);
            if(result)
            {
                System.out.println("Customer updated successfully!");
            }
            else
            {
                System.out.println("There was a problem with updating the customer.");
            }
        }
        else
        {
            System.out.println("The id is not valid!");
        }
    }

    private void editCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the customer that you want to edit: ");
        Long customerId = Long.parseLong(sc.nextLine());

        Customer customer = customerService.getCustomerById(customerId);
        if(customer!=null)
        {
            System.out.println("Current customer first name: " + customer.getFirstName());
            System.out.println("Current customer last name: " + customer.getLastName());
            System.out.println("Current customer phone: " + customer.getPhoneNumber());

            System.out.println("Enter new customer first name: ");
            String firstName = sc.nextLine();

            System.out.println("Enter new customer last name: ");
            String lastName = sc.nextLine();

            System.out.println("Enter new customer phone: ");
            String phoneNumber = sc.nextLine();

            Boolean result = customerService.updateCustomer(customerId, firstName, lastName, phoneNumber);
            if(result)
            {
                System.out.println("Customer updated successfully!");
            }
            else
            {
                System.out.println("There was a problem with updating the customer.");
            }
        }
        else
        {
            System.out.println("The id is not valid!");
        }
    }

    private void deleteCustomer()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the customer that you want to delete: ");
        Long customerId = Long.parseLong(sc.nextLine());

        Boolean result = customerService.deleteCustomer(customerId);

        if(result)
        {
            System.out.println("The customer has been deleted successfully!");
        }
        else
        {
            System.out.println("There was a problem with deleting the customer.");
        }
    }

    private void addNewCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter customer first name: ");
        String firstName = sc.nextLine();
        System.out.println("Enter customer last name: ");
        String lastName = sc.nextLine();
        System.out.println("Enter customer phone number: ");
        String phoneNumber = sc.nextLine();

        Boolean result = customerService.addCustomer(firstName, lastName, phoneNumber);

        if(result)
        {
            System.out.println("Customer added successfully!");
        }
        else
        {
            System.out.println("There was a problem with adding the customer");
        }
    }
    private void printManageCustomersMenu() {
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%s|", PrintUtils.center("CUSTOMERS MANAGEMENT", 58)));
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%1$-58s|", "1. List all customers"));
        System.out.println(String.format("|%1$-58s|", "2. Edit customer"));
        System.out.println(String.format("|%1$-58s|", "3. Delete customer"));
        System.out.println(String.format("|%1$-58s|", "4. Add new customer"));
        System.out.println(String.format("|%1$-58s|", "5. Go back"));
        System.out.println(new String("-").repeat(60));
    }
}
