package com.example.lab1;

public class Addition {

    private int firstNum;
    private int secondNum;

    public void setFirstNum(int firstNum) {
        this.firstNum = firstNum;
    }
    public void setSecondNum(int secondNum) {
        this.secondNum = secondNum;
    }

    public int getFirstNum() {
        return firstNum;
    }
    public int getSecondNum() {
        return secondNum;
    }

    public int add() {
        return firstNum + secondNum;
    }
}
