package com.example.calculator.controllers;

import com.example.calculator.models.CreditCalc;
import com.example.calculator.models.Model;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class ModelController {
    private volatile static ModelController instance;

    private ModelController() {
    }

    public static synchronized ModelController getInstance() {
        if (instance == null) {
            instance = new ModelController();
        }
        return instance;
    }

    private final Model model = new Model();
    private final CreditCalc creditCalc = new CreditCalc();

    static final int NUM_STEPS = 1000;
    private double leftBound = -10.0;
    private double rightBound = 10.0;
    private String expression;

    public void initExpression(String str) {
        model.initExpression(str);
        expression = str;
    }

    public double calculateValue(double value) {
        return model.calculateValue(value);
    }

    public ArrayList<Pair<Double, Double>> calculateGraph() {
        ArrayList<Pair<Double, Double>> graph = new ArrayList<>();
        double sizeStep = (rightBound - leftBound) / (NUM_STEPS - 1);
        for (int i = 0; i < NUM_STEPS; ++i) {
            double x = leftBound + i * sizeStep;
            double result = model.calculateValue(x);
            if (Double.isNaN(result) || Double.isInfinite(result) || Math.abs(result) > 1000) continue;
            graph.add(new Pair<>(x, result));
        }
        return graph;
    }

    public void setLeftBound(double leftBound) {
        this.leftBound = leftBound;
    }

    public void setRightBound(double rightBound) {
        this.rightBound = rightBound;
    }

    public String getExpression() {
        return expression;
    }

    public double getLeftBound() {
        return leftBound;
    }

    public double getRightBound() {
        return rightBound;
    }

    public void calculateCredit(double sum, int num, double interest, CreditCalc.PayType type) {
        creditCalc.calculateCredit(sum, num, interest, type);
    }

    public List<CreditCalc.Payment> getPayments() {
        return creditCalc.getPayments();
    }

    public String getPayment() {
        return creditCalc.getPayment();
    }

    public double getSumMain() {
        return creditCalc.getSumMain();
    }

    public double getSumInterest() {
        return creditCalc.getSumInterest();
    }
}
