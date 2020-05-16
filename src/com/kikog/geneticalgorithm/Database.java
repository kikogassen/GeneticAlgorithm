package com.kikog.geneticalgorithm;

public class Database {
    // population count
    public static final int POPULATION = 1000000;

    // how many will start with coronavirus
    public static final int START_CORONAVIRUS = 100;

    // lethality of the virus, according to Chinese Center for Disease Control and Prevention
    public static final double DEFAULT_LETALITY = 0.023;

    // the range of popularity, according to the average of infectable rate
    public static final int RANGE_POPULARITY = 5;

    // days without evolving to stop the algorithm
    public static final int STOP_CRITERIA = 5;

    // mutation chance of the virus, changing it own lethality
    public static final double MUTATION_CHANCE = 0.05;

    // hospitalar data, according to OMS
    public static final double INTERNATION_RATE = 0.14;
    public static final double ICU_RATE = 0.05;

    // ICU beds by one million people in brazil
    public static final int BRAZIL_ICU_PER_MILLION = 244;
}
