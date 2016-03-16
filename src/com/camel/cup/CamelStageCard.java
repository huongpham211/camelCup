package com.camel.cup;

class CamelStageCard {

    public CamelStageCard(int value, Color color) {
        this.value = value;
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    private final int value;
    private final Color color;

    @Override
    public String toString() {
        return "Card with  " + color + " and value " + value + ".";
    }
}
