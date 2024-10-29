package model;

import Enums.Color;

public class Stone {
    private Color color;
    private boolean isKing;

    public Stone(Color color) {
        this.color = color;
        this.isKing = false;
    }

    public boolean isKing() {
        return isKing;
    }

    public void reachedTheEnd() {
        this.isKing = true;
    }

    public Color getColor() {
        return color;
    }
}
