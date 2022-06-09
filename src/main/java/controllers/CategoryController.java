package controllers;

import models.Category;
import services.CategoryService;
import utils.PrintUtils;

import java.util.List;
import java.util.Scanner;

public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void printManagement() {
        Scanner sc = new Scanner(System.in);
        printManageCategoriesMenu();

        Integer command = Integer.parseInt(sc.nextLine());
        while(command!=5) {
            switch(command) {
                case 1:
                    listAllCategories();
                    break;
                case 2:
                    editCategory();
                    break;
                case 3:
                    deleteCategory();
                    break;
                case 4:
                    addNewCategory();
                    break;

                default:
                    System.out.println("Wrong command!");
            }

            printManageCategoriesMenu();

            command = Integer.parseInt(sc.nextLine());
        }
    }

    private void listAllCategories() {
        List<Category> categories = categoryService.getCategories();
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%s|", PrintUtils.center("CATEGORIES LIST", 58)));
        System.out.println(new String("-").repeat(60));
        System.out.println(
                String.format(
                        "|%1$-5s|%2$-50s|",
                        "ID", "Category name"
                )
        );

        for (Category category : categories) {
            System.out.println(
                    String.format(
                            "|%1$-5s|%2$-50s|",
                            category.getCategoryId(), category.getCategoryName()
                    )
            );
        }
        System.out.println(new String("-").repeat(60));

    }

    private void editCategory() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the category that you want to edit: ");
        Long categoryId = Long.parseLong(sc.nextLine());

        Category category = categoryService.getCategoryById(categoryId);
        if(category!=null)
        {
            System.out.println("Current category name: " + category.getCategoryName());

            System.out.println("Enter new category name: ");
            String categoryName = sc.nextLine();


            Boolean result = categoryService.updateCategory(categoryId, categoryName);
            if(result)
            {
                System.out.println("Category updated successfully!");
            }
            else
            {
                System.out.println("There was a problem with updating the category.");
            }
        }
        else
        {
            System.out.println("The id is not valid!");
        }
    }

    private void deleteCategory()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the category that you want to delete: ");
        Long categoryId = Long.parseLong(sc.nextLine());

        Boolean result = categoryService.deleteCategory(categoryId);

        if(result)
        {
            System.out.println("The category has been deleted successfully!");
        }
        else
        {
            System.out.println("There was a problem with deleting the category.");
        }
    }

    private void addNewCategory() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter category name: ");
        String categoryName = sc.nextLine();

        Boolean result = categoryService.addCategory(categoryName);

        if(result)
        {
            System.out.println("Category added succesfully!");
        }
        else
        {
            System.out.println("There was a problem with adding the category");
        }
    }
    private void printManageCategoriesMenu() {
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%s|", PrintUtils.center("CATEGORIES MANAGEMENT", 58)));
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%1$-58s|", "1. List all categories"));
        System.out.println(String.format("|%1$-58s|", "2. Edit category"));
        System.out.println(String.format("|%1$-58s|", "3. Delete category"));
        System.out.println(String.format("|%1$-58s|", "4. Add new category"));
        System.out.println(String.format("|%1$-58s|", "5. Go back"));
        System.out.println(new String("-").repeat(60));
    }
}
