package com.example.calculator.controllers;

import com.example.calculator.models.CreditCalc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


class ModelTest {

    static final double EPSILON = 1E-7;
    ModelController controller;
    double x;

    @BeforeEach
    void setUp() {
        x = 3.5;
        controller = ModelController.getInstance();
    }

    @ParameterizedTest
    @ValueSource(strings = {"100/3+x-(x+10)"})
    public void calcTest1(String expression) {
        controller.initExpression(expression);
        assertEquals(23.3333333, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"5-(x+10)"})
    public void calcTest2(String expression) {
        controller.initExpression(expression);
        assertEquals(-8.5, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-(3)*(-x-(7*(-(-(-(--7))))))"})
    public void calcTest3(String expression) {
        controller.initExpression(expression);
        assertEquals(-136.5, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"(8+2*5)/(1+3*2-4)"})
    public void calcTest4(String expression) {
        controller.initExpression(expression);
        assertEquals(6.0, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"(-8+x*5)/(3^2-4^(-x))"})
    public void calcTest5(String expression) {
        controller.initExpression(expression);
        assertEquals(1.0564726, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"(1+2)*4*cos(x*7-2)+sin(2*x)"})
    public void calcTest6(String expression) {
        controller.initExpression(expression);
        assertEquals(-9.82266908, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"4^acos(x/4)/tan(2*x)"})
    public void calcTest7(String expression) {
        controller.initExpression(expression);
        assertEquals(2.31214930, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ln(55/(2+x))"})
    public void calcTest8(String expression) {
        controller.initExpression(expression);
        assertEquals(2.30258509, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-sqrt(x^log(5-x))+ln(55/(2+x))"})
    public void calcTest9(String expression) {
        controller.initExpression(expression);
        assertEquals(1.18597174, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"(+1+2)*4*(cos(x*7-2)+sin(2*x))*70^(-10)+5*(-3)"})
    public void calcTest10(String expression) {
        controller.initExpression(expression);
        assertEquals(-15.0, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"asin(2/x)mod(x)+atan(+x)"})
    public void calcTest11(String expression) {
        controller.initExpression(expression);
        assertEquals(1.90074224, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"(2*x)mod5"})
    public void calcTest12(String expression) {
        controller.initExpression(expression);
        assertEquals(2.0, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"."})
    public void calcTest13(String expression) {
        controller.initExpression(expression);
        assertEquals(0.0, controller.calculateValue(x), EPSILON);
    }

    @ParameterizedTest
    @ValueSource(strings = {"5-(x+10", "(8+2*5)/(1+3)*2)-((4)"})
    public void calcTestException1(String expression) {
        assertThrows(RuntimeException.class, () -> controller.initExpression(expression));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-(3)(-x-(7(-(-(-(--7))))))", "(1+2)4(cos(x*7-2)+sin(2*x))70^(-10)+5(-3)", "(10/x)mod"})
    public void calcTestException2(String expression) {
        controller.initExpression(expression);
        assertThrows(RuntimeException.class, () -> controller.calculateValue(x));
    }

    @ParameterizedTest
    @CsvSource({"40000, 18, 0.15"})
    public void creditTest1(double sum, int num, double interest) {
        controller.calculateCredit(sum, num, interest, CreditCalc.PayType.kDiffPay);
        assertEquals(controller.getSumMain(), 40000.0);
        assertEquals(controller.getSumInterest(), 4750.0);
    }

    @ParameterizedTest
    @CsvSource({"40000, 18, 0.15"})
    public void creditTest2(double sum, int num, double interest) {
        controller.calculateCredit(sum, num, interest, CreditCalc.PayType.kAnnPay);
        assertEquals(controller.getSumMain(), 40000.0);
        assertEquals(controller.getSumInterest(), 4917.05);
    }
}
