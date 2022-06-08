package controllers;

import models.User;

import java.util.Scanner;

public class HomeController {
    public static User loggedUser = null;
    private UserController userController;
    private ProductController productController;
    private CategoryController categoryController;
    private OrderController orderController;
    private CustomerController customerController;


    public HomeController(UserController userController, ProductController productController,
                          CategoryController categoryController, OrderController orderController,
                          CustomerController customerController)
    {
        this.userController = userController;
        this.productController = productController;
        this.categoryController = categoryController;
        this.orderController = orderController;
        this.customerController = customerController;
    }

    private void printMenu() {
        System.out.println("WELCOME TO THE BIKE APP!");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
    }

    private void printAdminMenu() {
        System.out.println("Welcome to the bike app, dear admin!");
        System.out.println("1. User management");
        System.out.println("2. Product management");
        System.out.println("3. Category management");
        System.out.println("4. Order management");
        System.out.println("5. Customer management");

    }

    private void getCommand() {
        Scanner sc = new Scanner(System.in);
        printMenu();

        Integer command = Integer.parseInt(sc.nextLine());
        while(command!=3) {
            switch(command) {
                case 1:
                    userController.login();
                    break;
                case 2:
                    userController.register();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Wrong command!");
            }
            if(HomeController.loggedUser!=null) {
                if(HomeController.loggedUser.getAdmin()) {
                    getAdminCommand();
                }
                else{
                    //getUserCommand();
                }
            }
            printMenu();

            command = Integer.parseInt(sc.nextLine());
        }

    }

    private void getAdminCommand() {
        Scanner sc = new Scanner(System.in);
        printAdminMenu();

        Integer command = Integer.parseInt(sc.nextLine());
        while(command!=6) {
            switch(command) {
                case 1:
                    userController.printManagement();
                    break;
                case 2:
                    productController.printManagement();
                    break;
                case 3:
                    categoryController.printManagement();
                    break;
                case 4:
                    orderController.printManagement();
                    break;
                case 5:
                    customerController.printManagement();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong command!");
            }

            printAdminMenu();

            command = Integer.parseInt(sc.nextLine());
        }
    }

    public void run() {
        getCommand();

    }
}
