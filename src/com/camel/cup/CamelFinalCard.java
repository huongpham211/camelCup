package com.camel.cup;

class CamelFinalCard {

    private final Player player;
    private final Color color;

    CamelFinalCard(Player player, Color color) {
        this.player = player;
        this.color = color;
    }


    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }
}
