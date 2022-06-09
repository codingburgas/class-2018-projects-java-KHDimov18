package controllers;

import models.Category;
import models.Product;
import services.CategoryService;
import services.ProductService;
import utils.PrintUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    public void printManagement() {
        Scanner sc = new Scanner(System.in);
        printManageProductsMenu();

        Integer command = Integer.parseInt(sc.nextLine());
        while(command!=5) {
            switch(command) {
                case 1:
                    listAllProducts();
                    break;
                case 2:
                    editProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    addNewProduct();
                    break;

                default:
                    System.out.println("Wrong command!");
            }

            printManageProductsMenu();

            command = Integer.parseInt(sc.nextLine());
        }
    }

    private void listAllProducts() {
        List<Product> products = productService.getProducts();
        System.out.println(new String("-").repeat(75));
        System.out.println(String.format("|%s|", PrintUtils.center("PRODUCTS LIST", 73)));
        System.out.println(new String("-").repeat(75));
        System.out.println(
                String.format(
                        "|%1$-5s|%2$-15s|%3$-10s|%4$-10s|%5$-30s|",
                        "ID", "Title", "Price", "Quantity", "Categories"
                )
        );

        for (Product product : products) {
            String categoriesList = String.join(", ", product.getCategories().stream().map(item -> item.getCategoryName()).toList());

            System.out.println(
                    String.format(
                            "|%1$-5s|%2$-15s|%3$-10s|%4$-10s|%5$-30s|",
                            product.getProductId(), product.getTitle(), product.getPrice(), product.getQuantity(), categoriesList
                    )
            );
        }
        System.out.println(new String("-").repeat(75));

    }

    private void editProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the product that you want to edit: ");
        Long productId = Long.parseLong(sc.nextLine());

        Product product = productService.getProductById(productId);
        if(product!=null)
        {
            String categoriesList = String.join(", ", product.getCategories().stream().map(item -> item.getCategoryName()).toList());

            System.out.println("Current product title: " + product.getTitle());
            System.out.println("Current product description: " + product.getDescription());
            System.out.println("Current product price: " + product.getPrice());
            System.out.println("Current product quantity: " + product.getQuantity());
            System.out.println("Current product categories: " + categoriesList);

            System.out.println("Enter new product title: ");
            String productTitle = sc.nextLine();

            System.out.println("Enter new product description: ");
            String productDescription = sc.nextLine();

            System.out.println("Enter new product price: ");
            Double productPrice = Double.parseDouble(sc.nextLine());

            System.out.println("Enter new product quantity: ");
            Double productQuantity = Double.parseDouble(sc.nextLine());

            System.out.println("Enter new categories (by IDs split by interval): ");
            List<Long> categoryIds = Arrays.stream(sc.nextLine().split(" "))
                    .mapToLong(item -> Long.parseLong(item)).boxed().collect(Collectors.toList());
            List<Category> categories = new ArrayList<>();
            for (Long categoryId : categoryIds) {
                categories.add(categoryService.getCategoryById(categoryId));
            }
            Boolean result = productService.updateProduct(productId, productTitle, productDescription, productPrice, productQuantity, categories);
            if(result)
            {
                System.out.println("Product updated successfully!");
            }
            else
            {
                System.out.println("There was a problem with updating the product.");
            }
        }
        else
        {
            System.out.println("The id is not valid!");
        }
    }

    private void deleteProduct()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of the product that you want to delete: ");
        Long productId = Long.parseLong(sc.nextLine());

        Boolean result = productService.deleteProduct(productId);

        if(result)
        {
            System.out.println("The product has been deleted successfully!");
        }
        else
        {
            System.out.println("There was a problem with deleting the product.");
        }
    }

    private void addNewProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new product title: ");
        String productTitle = sc.nextLine();

        System.out.println("Enter new product description: ");
        String productDescription = sc.nextLine();

        System.out.println("Enter new product price: ");
        Double productPrice = Double.parseDouble(sc.nextLine());

        System.out.println("Enter new product quantity: ");
        Double productQuantity = Double.parseDouble(sc.nextLine());

        System.out.println("Enter new categories (by IDs splitted by interval): ");
        List<Long> categoryIds = Arrays.stream(sc.nextLine().split(" "))
                .mapToLong(item -> Long.parseLong(item)).boxed().collect(Collectors.toList());
        List<Category> categories = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            categories.add(categoryService.getCategoryById(categoryId));
        }

        Boolean result = productService.addProduct(productTitle, productDescription, productPrice, productQuantity, categories);

        if(result)
        {
            System.out.println("Product added successfully!");
        }
        else
        {
            System.out.println("There was a problem with adding the product");
        }
    }
    private void printManageProductsMenu() {
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%s|", PrintUtils.center("PRODUCTS MANAGEMENT", 58)));
        System.out.println(new String("-").repeat(60));
        System.out.println(String.format("|%1$-58s|", "1. List all products"));
        System.out.println(String.format("|%1$-58s|", "2. Edit product"));
        System.out.println(String.format("|%1$-58s|", "3. Delete product"));
        System.out.println(String.format("|%1$-58s|", "4. Add new product"));
        System.out.println(String.format("|%1$-58s|", "5. Go back"));
        System.out.println(new String("-").repeat(60));
    }
}
