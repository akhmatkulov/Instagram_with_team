package model;

import java.util.UUID;

public class Read {
    private final UUID id;
    private UUID userId;
    private boolean read = false;
    private UUID postId;

    public Read() {
        this.id = UUID.randomUUID();
    }

    public Read(UUID userId, UUID postId) {
        this();
        this.userId = userId;
        this.postId = postId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }
}
