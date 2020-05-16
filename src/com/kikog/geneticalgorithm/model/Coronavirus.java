package com.kikog.geneticalgorithm.model;

public class Coronavirus {

    private double lethality;
    private boolean isActive;

    public Coronavirus(double lethality) {
        this.lethality = lethality;
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void mutate(double newLethality) {
        this.lethality = newLethality;
    }

    public void setInactive(){
        isActive = false;
    }

    public double getLethality() {
        return lethality;
    }

    public boolean willKill(){
        return Math.random() < lethality;
    }
}
