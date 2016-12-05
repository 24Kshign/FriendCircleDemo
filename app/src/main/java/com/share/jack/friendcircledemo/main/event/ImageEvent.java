package com.share.jack.friendcircledemo.main.event;

/**
 *
 */
public class ImageEvent {

    private int position;

    public ImageEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}