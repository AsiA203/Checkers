package model;

public class Square {
    private Stone stone;

    public void Square(){
        this.stone = null;
    }

    public Stone getSquare(){
        return stone;
    }

    public void setStone(Stone stone){
        this.stone = stone;
    }

    public void removeStone(){
        this.stone = null;
    }

    public boolean isSquareEmpty(){
        return stone == null;
    }
}
