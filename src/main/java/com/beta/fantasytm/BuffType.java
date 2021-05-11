package com.beta.fantasytm;

public enum BuffType {

    NULL(1.0),
    FIRE_WEEK(1.5),
    TRIPLE_CAPTAIN(3.0),
    QUAD_UNDERDOG(4.0);

    private double modifier;

    private BuffType(double modifier) {
        this.modifier = modifier;
    }

    public double getModifier() {
        return this.modifier;
    }
}