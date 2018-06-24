package org.twocoolguys.fitlife;

public class Nutrition {
    private String calories;
    private String fats;
    private String protein;
    private String carbs;
    private String date;
    private String owner;

    public Nutrition(String calories, String fats, String protein, String carbs, String date, String owner) {
        this.calories = calories;
        this.fats = fats;
        this.protein = protein;
        this.carbs = carbs;
        this.date = date;
        this.owner = owner;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}