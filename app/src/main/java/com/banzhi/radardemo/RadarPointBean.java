package com.banzhi.radardemo;

import android.graphics.Rect;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/6/12.
 * @desciption :
 * @version :
 * </pre>
 */

public class RadarPointBean {
    float startX;
    float startY;
    Rect rect;


    public RadarPointBean(float startX, float startY, Rect rect) {
        this.startX = startX;
        this.startY = startY;
        this.rect = rect;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }


    private static final int DEF_PADDING = 25;//为文字增加点击区域 相当于padding

    public boolean isIn(float x, float y) {
        float endX = startX + Math.abs(rect.right - rect.left) + DEF_PADDING;
        float endY = startY - Math.abs(rect.bottom - rect.top) - DEF_PADDING;
        float startX = getStartX() - DEF_PADDING;
        float startY = getStartY() + DEF_PADDING;
        return startX < x && x < endX && startY > y && y > endY;
    }

}
