package com.kikog.geneticalgorithm.model;

import com.kikog.geneticalgorithm.Database;

public class People {

    private Coronavirus coronavirus;
    private double popularity;
    private boolean isAlive;
    private int infectedDay;

    public People() {
        coronavirus = null;
        popularity = Math.random();
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void infect(double lethality, int day) {
        this.coronavirus = new Coronavirus(lethality);
        this.infectedDay = day;
    }

    public int getInfectedDay() {
        return infectedDay;
    }

    public boolean isInfectable() {
        return coronavirus == null;
    }

    public boolean virusAct() {
        boolean killed = coronavirus.willKill();
        if (killed) isAlive = false;
        return killed;
    }

    public boolean hasActiveVirus() {
        return coronavirus != null && coronavirus.isActive() && isAlive;
    }

    public int peopleToInfect() {
        return (int) (popularity * Database.RANGE_POPULARITY);
    }

    public Coronavirus getCoronavirus() {
        return coronavirus;
    }
}
