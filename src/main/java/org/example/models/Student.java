package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private List<Long> maths;
    private List<Long> french;
    private List<Long> history;
    private Double mathAverage;
    private Double frenchAverage;
    private Double historyAverage;
    private Double totalAverage;
    private String name;

    public Student(String name) {
        this.name = name;
        this.maths = new ArrayList<>();
        this.french = new ArrayList<>();
        this.history = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            this.maths.add((long) Math.floor(Math.random() * 21));
            this.french.add((long) Math.floor(Math.random() * 21));
            this.history.add((long) Math.floor(Math.random() * 21));
        }
    }

    public List<Long> getMaths() { return maths; }
    public List<Long> getFrench() { return french; }
    public List<Long> getHistory() { return history; }
    public Double getMathAverage() { return mathAverage; }
    public Double getFrenchAverage() { return frenchAverage; }
    public Double getHistoryAverage() { return historyAverage; }
    public Double getTotalAverage() { return totalAverage; }
    public String getName() { return name; }

    public void setMathAverage(Double mathAverage) { this.mathAverage = mathAverage; }
    public void setFrenchAverage(Double frenchAverage) { this.frenchAverage = frenchAverage; }
    public void setHistoryAverage(Double historyAverage) { this.historyAverage = historyAverage; }
    public void setTotalAverage(Double totalAverage) { this.totalAverage = totalAverage; }
}