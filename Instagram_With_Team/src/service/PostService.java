package service;

import model.Post;

import java.util.ArrayList;
import java.util.UUID;

public class PostService {
    private static final String LAST_PATH = "posts.json";
    private final FileUtilService<Post> fileUtilService;

    public PostService(FileUtilService<Post> fileUtilService) {
        this.fileUtilService = fileUtilService;
    }

    public Post add(Post post) {
        ArrayList<Post> posts = fileUtilService.read(LAST_PATH);

        if (!has(post)) {
            posts.add(post);
            fileUtilService.write(posts, LAST_PATH);
            return post;
        }
        return null;
    }

    private boolean has(Post post) {
        ArrayList<Post> list = fileUtilService.read(LAST_PATH);

        for (Post post1: list) {
            if (post1.getUserId().equals(post.getUserId()) && post1.getLink().equals(post.getLink())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Post> listMyPosts(UUID userId) {
        ArrayList<Post> list = fileUtilService.read(LAST_PATH);
        ArrayList<Post> myPosts = new ArrayList<>();

        for (Post post: list) {
            if (post.getUserId().equals(userId)) {
                myPosts.add(post);
            }
        }
        return myPosts;
    }

    public ArrayList<Post> listOthersPosts(UUID userId) {
        ArrayList<Post> list = fileUtilService.read(LAST_PATH);
        ArrayList<Post> othersPosts = new ArrayList<>();

        for (Post post: list) {
            if (!post.getUserId().equals(userId)) {
                othersPosts.add(post);
            }
        }
        return othersPosts;
    }
}
