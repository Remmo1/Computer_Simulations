package com.company;

public class Result {

    String someVariable;
    float result;

    public Result(String someVariable, float result) {
        this.someVariable = someVariable;
        this.result = result;
    }

    @Override
    public String toString() {
        return someVariable + " " + result;
    }
}
