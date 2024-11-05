package model;

import Enums.Color;

public class Player {
    String name;
    Color color;

    public Player(){

    }

    public Player(Color color) {
        this.color = color;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public Color getColor(){
        return color;
    }
}