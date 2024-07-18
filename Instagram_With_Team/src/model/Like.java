package model;

import java.util.UUID;

public class Like {
    private final UUID id;
    private UUID userId;
    private UUID postId;

    public Like() {
        this.id = UUID.randomUUID();
    }

    public Like(UUID userId, UUID postId) {
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

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }
}
