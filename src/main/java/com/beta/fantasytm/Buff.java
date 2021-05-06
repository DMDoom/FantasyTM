package com.beta.fantasytm;

public enum Buff {

    TRIPLE_CAPTAIN(3.0),
    QUAD_UNDERDOG(4.0),
    FIRE_WEEK(1.5),
    NULL(1.0);

    private double modifier;

    private Buff(double modifier) {
        this.modifier = modifier;
    }

    public double getModifier() {
        return this.modifier;
    }
}