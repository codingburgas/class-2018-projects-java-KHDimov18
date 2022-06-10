package controllers;

import models.Category;
import models.Order;
import models.Product;
import services.OrderService;
import services.ProductService;
import utils.PrintUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class OrderController {
    private OrderService orderService;
    private ProductService productService;

    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    public void printManagement() throws ParseException {
        Scanner sc = new Scanner(System.in);
        printManageOrdersMenu();

        Integer command = Integer.parseInt(sc.nextLine());
        while(command!=6) {
            switch(command) {
                case 1:
                    listAllOrders();
                    break;
                case 2:
                    editOrder();
                    break;
                case 3:
                    deleteOrder();
                    break;
                case 4:
                    addNewOrder();
                    break;
                case 5:
                    viewOrderDetails();
                    break;

                default:
                    System.out.println("Wrong command!");
            }

            printManageOrdersMenu();

            command = Integer.parseInt(sc.nextLine());
        }
    }

    private void listAllOrders() {
        List<Order> orders = orderService.getOrders();
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%s|", PrintUtils.center("ORDERS LIST", 58)));
        System.out.println(new String("-").repeat(60));
        System.out.println(
                String.format(
                        "|%1$-5s|%2$-5s|%3$-35s|%4$-10s|",
                        "ID", "Customer Id", "Delivery address", "Shipment date"
                )
        );

        for (Order order : orders) {
            System.out.println(
                    String.format(
                            "|%1$-5s|%2$-5s|%3$-35s|%4$-10s|",
                            order.getOrderId(), order.getCustomerId(), order.getDeliveryAddress(), order.getShipmentDate()
                    )
            );
        }
        System.out.println(new String("-").repeat(60));

    }

    public void listAllOrdersByCustomer(Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);

        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%s|", PrintUtils.center("ORDERS LIST", 58)));
        System.out.println(new String("-").repeat(60));
        System.out.println(
                String.format(
                        "|%1$-5s|%2$-5s|%3$-35s|%4$-10s|",
                        "ID", "Customer Id", "Delivery address", "Shipment date"
                )
        );

        for (Order order : orders) {
            System.out.println(
                    String.format(
                            "|%1$-5s|%2$-5s|%3$-35s|%4$-10s|",
                            order.getOrderId(), order.getCustomerId(), order.getDeliveryAddress(), order.getShipmentDate()
                    )
            );
        }
        System.out.println(new String("-").repeat(60));

    }

    public void viewOrderDetailsById(Long customerId)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the order ID you want to view: ");
        Long orderId = Long.parseLong(sc.nextLine());

        Order order = orderService.getOrderById(orderId);

        if(order!=null)
        {
            if(order.getCustomerId() == customerId)
            {
                System.out.println("Customer ID: " + order.getCustomerId());
                System.out.println("Order delivery address: " + order.getDeliveryAddress());
                System.out.println("Order shipment date: " + order.getShipmentDate());

                System.out.println("Products: ");
                List<Product> products = order.getProducts();
                Double total = 0.0;
                for (Product product : products) {
                    System.out.println(String.format("Title: %s, Price: %f", product.getTitle(), product.getPrice()));
                    total += product.getPrice();
                }
                System.out.println("Total: " + total);
            }
            else
            {
                System.out.println("You can't view this order.");
            }
        }
        else
        {
            System.out.println("Order not found!");
        }
    }

    public void makeOrder(Long customerId) {

        System.out.println("Enter ID of product that you want to order: ");
        Scanner sc = new Scanner(System.in);
        List<Product> products = new ArrayList<>();
        Boolean orderMore = true;
        while(orderMore) {
            Long productId = Long.parseLong(sc.nextLine());
            Product product = productService.getProductById(productId);
            if (product != null) {
                products.add(product);

                System.out.println("Do you want to order more products? (Y/N)");
                String answer = sc.nextLine();

                if(answer.toUpperCase(Locale.ROOT).equalsIgnoreCase("N"))
                {
                    orderMore = false;
                }

            } else {
                System.out.println("This product Id is invalid, please try again!");
            }
        }

        if(products.size() > 0) {


            System.out.println("Enter order delivery address: ");
            String deliveryAddress = sc.nextLine();
            System.out.println("Enter order delivery date: ");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 3);
            Date shipmentDate = calendar.getTime();


            Boolean result = orderService.addOrder(customerId, deliveryAddress, shipmentDate, products);

            if(result)
            {
                System.out.println("The order has been completed successfully!");
            }
            else
            {
                System.out.println("There was a problem with your order!");
            }
        }
    }

    private void editOrder() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the order that you want to edit: ");
        Long orderId = Long.parseLong(sc.nextLine());

        Order order = orderService.getOrderById(orderId);
        if(order!=null)
        {
            System.out.println("Current order delivery address: " + order.getDeliveryAddress());
            System.out.println("Current order shipment date: " + order.getShipmentDate());
            //We can't change the customer ID on purpose!

            System.out.println("Enter new order delivery address: ");
            String deliveryAddress = sc.nextLine();

            System.out.println("Enter new order shipment date: ");
            Date shipmentDate = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());

            Boolean result = orderService.updateOrder(orderId, deliveryAddress, shipmentDate);
            if(result)
            {
                System.out.println("Order updated successfully!");
            }
            else
            {
                System.out.println("There was a problem with updating the order.");
            }
        }
        else
        {
            System.out.println("The id is not valid!");
        }
    }

    private void deleteOrder()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the order that you want to delete: ");
        Long orderId = Long.parseLong(sc.nextLine());

        Boolean result = orderService.deleteOrder(orderId);

        if(result)
        {
            System.out.println("The order has been deleted successfully!");
        }
        else
        {
            System.out.println("There was a problem with deleting the order.");
        }
    }

    private void addNewOrder() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter order customer ID: ");
        Long customerId = Long.parseLong(sc.nextLine());

        System.out.println("Enter order delivery address: ");
        String deliveryAddress = sc.nextLine();
        System.out.println("Enter order delivery date: ");
        Date shipmentDate = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());

        List<Product> products = new ArrayList<>();
        System.out.println("Enter products to be bought (by IDs split by interval): ");
        List<Long> productIds = Arrays.stream(sc.nextLine().split(" "))
                .mapToLong(item -> Long.parseLong(item)).boxed().collect(Collectors.toList());
        for (Long productId : productIds) {
            products.add(productService.getProductById(productId));
        }

        Boolean result = orderService.addOrder(customerId, deliveryAddress, shipmentDate, products);

        if(result)
        {
            System.out.println("Order added successfully!");
        }
        else
        {
            System.out.println("There was a problem with adding the order");
        }
    }

    private void viewOrderDetails(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the order that you want to edit: ");
        Long orderId = Long.parseLong(sc.nextLine());

        Order order = orderService.getOrderById(orderId);
        if(order!=null)
        {
            System.out.println("Customer ID: " + order.getCustomerId());
            System.out.println("Order delivery address: " + order.getDeliveryAddress());
            System.out.println("Order shipment date: " + order.getShipmentDate());

            System.out.println("Products: ");
            List<Product> products = order.getProducts();
            Double total = 0.0;
            for (Product product : products) {
               System.out.println(String.format("Title: %s, Price: %f", product.getTitle(), product.getPrice()));
               total += product.getPrice();
            }
            System.out.println("Total: " + total);

        }
        else
        {
            System.out.println("The id is not valid!");
        }
    }
    private void printManageOrdersMenu() {
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%s|", PrintUtils.center("ORDERS MANAGEMENT", 58)));
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%1$-58s|", "1. List all orders"));
        System.out.println(String.format("|%1$-58s|", "2. Edit order"));
        System.out.println(String.format("|%1$-58s|", "3. Delete order"));
        System.out.println(String.format("|%1$-58s|", "4. Add new order"));
        System.out.println(String.format("|%1$-58s|", "5. View order details"));
        System.out.println(String.format("|%1$-58s|", "6. Go back"));
        System.out.println(new String("-").repeat(60));
    }
}
