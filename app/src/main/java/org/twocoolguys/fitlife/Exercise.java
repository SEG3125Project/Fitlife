package org.twocoolguys.fitlife;


public class Exercise {
    private String name;
    private int isCardio; //this has to be an int since SQL databases dont support boolean values therefor we are using 0 or 1
    private int time;
    private int weight;
    private int sets;
    private int reps;

    public Exercise(String name, int isCardio, int time, int weight, int sets, int reps) {
        this.name = name;
        this.isCardio = isCardio;
        this.time = time;
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isCardio() {
        return isCardio;
    }

    public void setCardio(int cardio) {
        isCardio = cardio;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}