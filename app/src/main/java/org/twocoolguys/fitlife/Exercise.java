package org.twocoolguys.fitlife;


public class Exercise {
    private String name;
    private String isCardio; //this has to be 0 or 1 since SQL databases dont support boolean values therefor we are using 0 or 1
    private String time;
    private String weight;
    private String sets;
    private String reps;
    private String owner;
    private String date;

    public Exercise(String name, String isCardio, String time, String weight, String sets, String reps, String owner, String date) {
        this.name = name;
        this.isCardio = isCardio;
        this.time = time;
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
        this.owner = owner;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsCardio() {
        return isCardio;
    }

    public void setIsCardio(String isCardio) {
        this.isCardio = isCardio;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

