package org.twocoolguys.fitlife;

public class Nutrition {
    private String calories;
    private String fats;
    private String protein;
    private String carbs;

    public Nutrition(String calories, String fats, String protein, String carbs) {
        this.calories = calories;
        this.fats = fats;
        this.protein = protein;
        this.carbs = carbs;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }
}