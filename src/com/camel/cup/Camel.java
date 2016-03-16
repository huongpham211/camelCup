package com.camel.cup;

public class Camel {

    private int position;
    private final Color color;
    @SuppressWarnings("UnusedDeclaration")
    private int level;

    public Camel(Color color, int position) {
        this.color = color;
        this.position = position;
        this.level = 0;
    }

    public Color getColor() {
        return color;
    }

    public int getLevel(Position position) {
        return position.getHighestLevel();
    }

    public int getPosition() {
        return position;
    }

    void setPosition(int position) {
        this.position = position;
    }


    public void updatePosition(int position) {
        Position p = new Position(position);
        p.removeCamel(this);
        setPosition(getPosition() + position);
    }


    public void updateLevel(Position position) {
        setLevel(position.getHighestLevel());
    }

    private void setLevel(int level) {
        this.level = level;
    }

}
