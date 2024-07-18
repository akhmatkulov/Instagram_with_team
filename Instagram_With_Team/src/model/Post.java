package model;

import java.util.UUID;

public class Post {
    private final UUID id;
    private String link;
    private String name;
    private UUID userId;

    public Post() {
        this.id = UUID.randomUUID();
    }

    public Post(String link, String name, UUID userId) {
        this();
        this.name = name;
        this.link = link;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
