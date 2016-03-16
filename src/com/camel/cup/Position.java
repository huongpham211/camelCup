package com.camel.cup;

import java.util.ArrayList;
import java.util.List;

/**
 * User: jan-marc
 * Date: 06.01.15
 * Time: 00:46
 */
public class Position {

    private final int value;
    private int level;
    private boolean isDecreased = false;
    private boolean isIncreased = false;
    private Player player = null;


    private Player positionCardPlayer = null;

    private final List<Camel> camels = new ArrayList<>();

    public boolean isDecreased() {
        return isDecreased;
    }

    public boolean isIncreased() {
        return isIncreased;
    }

    public void setManipulatedBy(Player player) {
        this.player = player;
    }

    public Player getManipulatedBy(){
        return this.player;
    }


    public void setDecreased(boolean decreased) {
        isDecreased = decreased;
    }

    public void setIncreased(boolean increased) {
        isIncreased = increased;
    }


    public Position(int value) {
        this.value = value;
        this.level = -1;
    }

    public void setModifierMinus(Player player) {
        positionCardPlayer = player;
    }

    public Player getModifierPlayer() {
        return positionCardPlayer;
    }

    public int getValue() {
        return value;
    }

    public ArrayList<Camel> getCamels() {
        return (ArrayList<Camel>) camels;
    }

    public void setCamel(Camel camel) {
        camels.add(camel);
        this.level++;

    }

    public int getCamelLevel(Camel camel) {

        int camelPosition = 0;
        for (Camel c : camels) {
            if (c.getColor().equals(camel.getColor())) {
                return camelPosition;
            }
            camelPosition++;
        }
        return camelPosition;
    }

    public int getHighestLevel() {
        return this.level;
    }

    public void decreaseHighestLevel() {
        this.level--;
    }

    public void setCamels(List<Camel> positionCamels) {
        for (Camel camel : positionCamels) {
            this.setCamel(camel);
        }
    }

    public void removeCamel(Camel camel) {
        camels.remove(camel);
    }

    public void decreaseHighestLevel(int size) {
        this.level = this.level - size;
    }


    public void removeManipulationCard() {
        setDecreased(false);
        setIncreased(false);
    }
}
