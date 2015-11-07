package com.zhuiji7.jigsaw.view;

/**
 * Created by cww on 15-11-5.
 */
public class PicPoint {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(PicPoint picPoint){
        if(x == picPoint.getX() && y == picPoint.getY()){
            return true;
        }else{
            return false;
        }
    }
}
