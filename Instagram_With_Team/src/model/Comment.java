package model;

import java.util.UUID;

public class Comment {
    private final UUID id;
    private String text;
    private UUID postId;
    private UUID parentCommentId;
    private UUID userId;

    public Comment() {
        this.id = UUID.randomUUID();
    }

    public Comment(String text, UUID postId, UUID parentCommentId, UUID userId) {
        this();
        this.text = text;
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(UUID parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
