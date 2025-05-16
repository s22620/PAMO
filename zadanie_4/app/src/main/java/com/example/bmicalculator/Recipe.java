package com.example.bmicalculator;

import java.util.List;

public class Recipe {
    private String name;
    private String description;
    private int minCalories;
    private int maxCalories;



    public Recipe(String name, String description, int minCalories, int maxCalories, List<String> ingredients) {
        this.name = name;
        this.description = description;
        this.minCalories = minCalories;
        this.maxCalories = maxCalories;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean matchesCalorieRange(double userCalories) {
        return userCalories >= minCalories && userCalories <= maxCalories;

    }
    private List<String> ingredients;
    public List<String> getIngredients() {
        return ingredients;
    }
}