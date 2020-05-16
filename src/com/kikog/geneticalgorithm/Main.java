package com.kikog.geneticalgorithm;

import com.kikog.geneticalgorithm.model.Population;

public class Main {

    public static void main(String[] args) {

        // initialize the population
        Population population = new Population();
        int daysWithoutEvolve = 0;

        do {
            population.goToNextDay();

            // get the fitness at the start of the day
            double startDayFitness = population.peopleWithAntibody();

            population.selection();
            population.reproduction();
            population.mutation();
            population.logExecution();

            // get the fitness at the end of the day
            double endDayFitness = population.peopleWithAntibody();

            // the population didn't evolved
            if (Math.abs(endDayFitness - startDayFitness) < 0.00001) {
                daysWithoutEvolve++;
            } else {
                daysWithoutEvolve = 0;
            }
        } while (daysWithoutEvolve < Database.STOP_CRITERIA);

        population.logResults();
    }

}
