package com.beta.fantasytm;

public enum Position {

    CAPTAIN(2.0, "captainBanner.png"),
    REGULAR(1.0, "regularBanner.png"),
    UNDERDOG(1.5, "underdogBanner.png");

    private double modifier;
    private String banner;

    private Position(double modifier, String banner) {
        this.modifier = modifier;
        this.banner = banner;
    }

    public double getModifier() {
        return this.modifier;
    }

    public String getBanner() {
        return this.banner;
    }
}
