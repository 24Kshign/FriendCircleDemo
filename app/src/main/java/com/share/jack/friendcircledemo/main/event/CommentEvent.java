package com.share.jack.friendcircledemo.main.event;

/**
 *
 */
public class CommentEvent {
    private int position;
    private boolean isRootComment;

    public CommentEvent(int position, boolean isRootComment) {
        this.position = position;
        this.isRootComment = isRootComment;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isRootComment() {
        return isRootComment;
    }

    public void setIsRootComment(boolean isRootComment) {
        this.isRootComment = isRootComment;
    }
}
