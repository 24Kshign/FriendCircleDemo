package com.share.jack.friendcircledemo.main.event;

/**
 * Created by Jack on 16/11/21.
 */
public class PraiseUserClickEvent {
    private int position;

    public PraiseUserClickEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}