package com.beta.fantasytm;

public enum Position {

    CAPTAIN(2.0),
    REGULAR(1.0),
    UNDERDOG(1.5);

    private double modifier;

    private Position(double modifier) {
        this.modifier = modifier;
    }

    public double getModifier() {
        return this.modifier;
    }
}
