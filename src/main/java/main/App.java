package main;

import controllers.*;
import models.Category;
import repositories.*;
import services.*;

import java.util.concurrent.CancellationException;

public class App {
    public static void main( String... args ) {
        UserRepository userRepository = new UserRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        UserService userService = new UserService(userRepository, customerRepository);

        UserController userController = new UserController(userService);

        CategoryRepository categoryRepository = new CategoryRepository();
        ProductRepository productRepository = new ProductRepository();
        CategoryService categoryService = new CategoryService(categoryRepository, productRepository);

        CategoryController categoryController = new CategoryController(categoryService);

        ProductService productService = new ProductService(productRepository);
        ProductController productController = new ProductController(productService);

        OrderRepository orderRepository = new OrderRepository();
        OrderService orderService = new OrderService(orderRepository);
        OrderController orderController = new OrderController(orderService);

        CustomerService customerService = new CustomerService(customerRepository, orderRepository);
        CustomerController customerController = new CustomerController(customerService);
        HomeController homeController = new HomeController(userController, productController , categoryController, orderController, customerController);
        homeController.run();
    }
}
