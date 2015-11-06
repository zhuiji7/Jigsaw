package com.zhuiji7.jigsaw.view;

/**
 * Created by cww on 15-11-5.
 */
public class Patch {
    private boolean empty = false;
    private PicPoint canvasPicPoint;
    private PicPoint bitmapPicPoint;

    public PicPoint getBitmapPicPoint() {
        return bitmapPicPoint;
    }

    public void setBitmapPicPoint(PicPoint bitmapPicPoint) {
        this.bitmapPicPoint = bitmapPicPoint;
    }

    public PicPoint getCanvasPicPoint() {
        return canvasPicPoint;
    }

    public void setCanvasPicPoint(PicPoint canvasPicPoint) {
        this.canvasPicPoint = canvasPicPoint;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
