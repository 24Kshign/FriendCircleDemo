package com.share.jack.friendcircledemo.main.event;

/**
 *
 */
public class PraiseEvent {

    private int position;

    public PraiseEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}