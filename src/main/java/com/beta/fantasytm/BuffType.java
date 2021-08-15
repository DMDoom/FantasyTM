package com.beta.fantasytm;

public enum BuffType {

    NULL(1.0, "buffIconNULL.png"),
    FIRE_WEEK(1.5, "buffIconFIREWEEK.png"),
    TRIPLE_CAPTAIN(3.0, "buffIconTRIPLECAPTAIN.png"),
    QUAD_UNDERDOG(4.0, "buffIconQUADUNDERDOG.png");

    private double modifier;
    private String icon;

    private BuffType(double modifier, String icon) {
        this.modifier = modifier;
        this.icon = icon;
    }

    public double getModifier() {
        return this.modifier;
    }

    public String getIcon() {
        return this.icon;
    }
}