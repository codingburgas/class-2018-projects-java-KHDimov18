package controllers;

import services.CategoryService;

public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void printManagement() {
    }

}
