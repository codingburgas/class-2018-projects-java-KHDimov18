package controllers;

import models.User;
import services.UserService;
import utils.PrintUtils;

import java.util.List;
import java.util.Scanner;

public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String userName = sc.nextLine();
        System.out.println("Enter your password: ");
        String userPassword = sc.nextLine();

        Boolean isValid = userService.isLogin(userName, userPassword);

        if(isValid) {
            //TODO: check if regular user or admin
            System.out.println("You are logged in successfully!");
            HomeController.loggedUser = userService.getLoggedInUser(userName, userPassword);


        } else {
            System.out.println("Wrong username or password! Please try again!");
        }
    }

    public void register() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String userName = sc.nextLine();
        System.out.println("Enter your password: ");
        String userPassword = sc.nextLine();
        System.out.println("Enter your first name: ");
        String firstName = sc.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = sc.nextLine();
        System.out.println("Enter your phone: ");
        String phoneNumber = sc.nextLine();

        Boolean success = userService.addUser(userName, userPassword, firstName, lastName, phoneNumber);

        if(success)
        {
            System.out.println("User registered successfully!");
        }
        else
        {
            System.out.println("There was a problem with your registeration!");
        }
    }

    public void printManagement() {
        Scanner sc = new Scanner(System.in);
        printManageUsersMenu();

        Integer command = Integer.parseInt(sc.nextLine());
        while(command!=5) {
            switch(command) {
                case 1:
                    listAllUsers();
                    break;
                case 2:
                    editUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    addNewUser();
                    break;

                default:
                    System.out.println("Wrong command!");
            }

            printManageUsersMenu();

            command = Integer.parseInt(sc.nextLine());
        }
    }

    private void listAllUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%s|", PrintUtils.center("USERS LIST", 58)));
        System.out.println(new String("-").repeat(60));
        System.out.println(
                String.format(
                        "|%1$-5s|%2$-20s|%3$-15s|%4$-15s|",
                       "ID", "Username", "Customer ID", "Is admin?"
                )
        );

        for (User user : users) {
            String adminStatus = (user.getAdmin() ? "Yes" : "No");
            System.out.println(
                    String.format(
                            "|%1$-5s|%2$-20s|%3$-15s|%4$-15s|",
                            user.getUserId(), user.getUserName(), user.getCustomerId(), adminStatus
                    )
            );
        }
        System.out.println(new String("-").repeat(60));

    }

    private void editUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the user that you want to edit: ");
        Long userId = Long.parseLong(sc.nextLine());

        User user = userService.getUserById(userId);
        if(user!=null)
        {
            System.out.println("Current userName: " + user.getUserName());
            System.out.println("Current password: " + user.getUserPassword());
            System.out.println("Current admin status: " + user.getAdmin());

            System.out.println("Enter new userName: ");
            String userName = sc.nextLine();
            System.out.println("Enter new password: ");
            String userPassword = sc.nextLine();

            System.out.println("Enter new status: ");
            Boolean isAdmin = Boolean.parseBoolean(sc.nextLine());

            Boolean result = userService.updateUser(userId, userName, userPassword, isAdmin);
            if(result)
            {
                System.out.println("User updated successfully!");
            }
            else
            {
                System.out.println("There was a problem with updating the user.");
            }
        }
        else
        {
            System.out.println("The id is not valid!");
        }
    }

    private void deleteUser()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the user that you want to delete: ");
        Long userId = Long.parseLong(sc.nextLine());

        Boolean result = userService.deleteUser(userId);

        if(result)
        {
            System.out.println("The user has been deleted successfully!");
        }
        else
        {
            System.out.println("There was a problem with deleting the user.");
        }
    }

    private void addNewUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter userName: ");
        String userName = sc.nextLine();

        System.out.println("Enter password: ");
        String password = sc.nextLine();

        System.out.println("Enter first name: ");
        String firstName = sc.nextLine();

        System.out.println("Enter last name: ");
        String lastName = sc.nextLine();

        System.out.println("Enter phone number: ");
        String phoneNumber = sc.nextLine();

        Boolean result = userService.addUser(userName, password, firstName, lastName, phoneNumber);

        if(result)
        {
            System.out.println("User added succesfully!");
        }
        else
        {
            System.out.println("There was a problem with adding the user");
        }
    }
    private void printManageUsersMenu() {
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%s|", PrintUtils.center("USERS MANAGEMENT", 58)));
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%1$-58s|", "1. List all users"));
        System.out.println(String.format("|%1$-58s|", "2. Edit user"));
        System.out.println(String.format("|%1$-58s|", "3. Delete user"));
        System.out.println(String.format("|%1$-58s|", "4. Add new user"));
        System.out.println(String.format("|%1$-58s|", "5. Go back"));
        System.out.println(new String("-").repeat(60));
    }
}
