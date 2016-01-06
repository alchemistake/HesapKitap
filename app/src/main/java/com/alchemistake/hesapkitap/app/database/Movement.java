package com.alchemistake.hesapkitap.app.database;

/**
 * Created by Alchemistake on 26/08/15.
 */
public class Movement {
    private int id,day,month,year;
    double income,outcome;
    private String name;

    public int getId() {
        return id;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public double getIncome() {
        return income;
    }

    public double getOutcome() {
        return outcome;
    }

    public String getName() {
        return name;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setOutcome(double outcome) {
        this.outcome = outcome;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Movement(int id, int day, int month, int year, String name, double income, double outcome) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.income = income;
        this.outcome = outcome;
        this.name = name;
    }
}
