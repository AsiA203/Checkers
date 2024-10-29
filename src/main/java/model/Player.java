package model;

import Enums.Color;

public class Player {
    String name;
    Color color;

    public Player(String name,Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor(){
        return color;
    }
}
