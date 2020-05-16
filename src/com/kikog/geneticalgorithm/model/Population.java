package com.kikog.geneticalgorithm.model;

import com.kikog.geneticalgorithm.Database;

import java.util.Random;

public class Population {

    private final People[] peoples;
    private final Random random = new Random();
    private int day = 0;

    private int sicksPeak = 0;
    private int sicksPeakDay = 0;

    private int deathsPeak = 0;
    private int deathsPeakDay = 0;

    private int newCasesPeak = 0;
    private int newCasesPeakDay = 0;

    private double moreAgressiveVirus = Database.DEFAULT_LETALITY;
    private double lessAgressiveVirus = Database.DEFAULT_LETALITY;

    public Population() {
        peoples = new People[Database.POPULATION];

        for (int i = 0; i < Database.POPULATION; i++) {
            peoples[i] = new People();
        }

        for (int i = 0; i < Database.START_CORONAVIRUS; i++) {
            int indexToInfect;
            do indexToInfect = random.nextInt(Database.POPULATION);
            while (peoples[indexToInfect].getCoronavirus() != null);
            peoples[indexToInfect].infect(Database.DEFAULT_LETALITY, day);
        }
    }

    public double peopleWithAntibody() {
        int peopleWithAntibody = 0;
        for (People people : peoples) {
            if (!people.isInfectable()) peopleWithAntibody++;
        }
        return (double) peopleWithAntibody / peoples.length;
    }

    public void selection() {
        int newDeaths = 0;
        for (People people : peoples) {
            if (people.hasActiveVirus()) {
                boolean killed = people.virusAct();
                if (killed) newDeaths++;
            }
        }
        if (newDeaths > deathsPeak) {
            deathsPeak = newDeaths;
            deathsPeakDay = day;
        }
    }

    public void reproduction() {
        int newCases = 0;
        for (People people : peoples) {
            if (people.hasActiveVirus()) {
                if (day > people.getInfectedDay()) people.getCoronavirus().setInactive();
                for (int i = 0; i < people.peopleToInfect(); i++) {
                    int indexToInfect = random.nextInt(Database.POPULATION);
                    if (peoples[indexToInfect].isInfectable()) {
                        peoples[indexToInfect].infect(people.getCoronavirus().getLethality(), day);
                        newCases++;
                    }
                }
            }
        }
        if (newCases > newCasesPeak) {
            newCasesPeak = newCases;
            newCasesPeakDay = day;
        }
    }

    public void goToNextDay() {
        day++;
    }

    public void mutation() {
        for (People people : peoples) {
            if (people.hasActiveVirus()) {
                if (random.nextDouble() <= Database.MUTATION_CHANCE) {
                    double currentLethality = people.getCoronavirus().getLethality();
                    double mutation = (random.nextDouble() - 0.5) / 100;

                    if (currentLethality + mutation > moreAgressiveVirus) {
                        moreAgressiveVirus = currentLethality + mutation;
                    } else if (currentLethality + mutation < lessAgressiveVirus) {
                        lessAgressiveVirus = currentLethality + mutation;
                    }

                    people.getCoronavirus().mutate(currentLethality + mutation);
                }
            }
        }
    }

    public void logExecution() {
        if (getSicksCount() > sicksPeak) {
            sicksPeak = getSicksCount();
            sicksPeakDay = day;
        }
        System.out.println("At the end of day " + day + " the population have:" +
                "\n\t" + rawToPercentage(getSusceptibleCount()) + "% susceptible;" +
                "\n\t" + rawToPercentage(getCuredCount()) + "% cured;" +
                "\n\t" + rawToPercentage(getSicksCount()) + "% sick;" +
                "\n\t" + rawToPercentage(getDeathsCount()) + "% death;" +
                "");
    }

    public void logResults() {
        System.out.println("At the end of the simulation, we have the following results: " +
                "\n\t" + peoples.length + " total people;" +
                "\n\t" + getCuredCount() + " cured people;" +
                "\n\t" + getDeathsCount() + " deaths;" +
                "\n\tThe deaths day peak was at day " + deathsPeakDay + " with " + deathsPeak + " deaths;" +
                "\n\tThe day with more simultaneous sick people was at day " + sicksPeakDay + " with " + sicksPeak + " people. With the known statistics, it means that " + Math.round(sicksPeak * Database.INTERNATION_RATE) + " would need hospital care and " + Math.round(sicksPeak * Database.ICU_RATE) + " would need ICU beds (Brazil only have " + Database.BRAZIL_ICU_PER_MILLION + " ICU beds per million of people);" +
                "\n\tThe new cases day peak was at day " + newCasesPeakDay + " with " + newCasesPeak + " new cases;" +
                "\n\tThe more aggressive virus that surrounded through the population had " + ((double) Math.round(moreAgressiveVirus * 100 * 100) / 100) + "% of lethality, whereas the less aggressive had " + ((double) Math.round(lessAgressiveVirus * 100 * 100) / 100) + "% of lethality;" +
                "");
    }

    private int getCuredCount() {
        int cureds = 0;
        for (People people : peoples) {
            if (people.isAlive() && !people.isInfectable() && !people.hasActiveVirus()) {
                cureds++;
            }
        }
        return cureds;
    }

    private int getSusceptibleCount() {
        int susceptible = 0;
        for (People people : peoples) {
            if (people.isInfectable()) {
                susceptible++;
            }
        }
        return susceptible;
    }

    private int getSicksCount() {
        int sicks = 0;
        for (People people : peoples) {
            if (people.hasActiveVirus()) {
                sicks++;
            }
        }
        return sicks;
    }

    private int getDeathsCount() {
        int deaths = 0;
        for (People people : peoples) {
            if (!people.isAlive()) {
                deaths++;
            }
        }
        return deaths;
    }

    private double rawToPercentage(int raw) {
        return ((double) Math.round((double) raw / peoples.length * 100 * 100)) / 100;
    }
}
