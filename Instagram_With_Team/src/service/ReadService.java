package service;

import model.Post;
import model.Read;
import model.User;

import java.util.ArrayList;
import java.util.UUID;

public class ReadService {
    private static final String LAST_PATH = "read.json";
    private final UserService userService;
    private final PostService postService;
    private final FileUtilService<Read> fileUtilService = new FileUtilService<>(Read.class);

    public ReadService(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    public void addReadForAllUsers(UUID postId, UUID userId) {
        ArrayList<Read> reads = fileUtilService.read(LAST_PATH);
        ArrayList<User> users = userService.list();

        for (User user: users) {
            if (!user.getId().equals(userId)) {
                reads.add(new Read(user.getId(), postId));
            }
        }
        fileUtilService.write(reads, LAST_PATH);
    }

    public void addReadForNewUser(UUID userId) {
        ArrayList<Read> reads = fileUtilService.read(LAST_PATH);
        ArrayList<Post> posts = postService.listOthersPosts(userId);

        for (Post post: posts) {
            reads.add(new Read(userId, post.getId()));
        }
        fileUtilService.write(reads, LAST_PATH);
    }

    public void setReadForUser(UUID userId, UUID postId) {
        ArrayList<Read> reads = fileUtilService.read(LAST_PATH);

        for (Read read: reads) {
            if (read.getUserId().equals(userId) && read.getPostId().equals(postId)) {
                read.setRead(true);
            }
        }
    }

    public ArrayList<Post> listReadPost(UUID userId) {
        ArrayList<Read> reads = fileUtilService.read(LAST_PATH);
        ArrayList<Post> list = postService.listOthersPosts(userId);
        ArrayList<Post> readPosts = new ArrayList<>();

        for (Post post: list) {
            for (Read read: reads) {
                if (post.getId().equals(read.getPostId()) && read.getUserId().equals(userId) &&
                read.isRead()) {
                    readPosts.add(post);
                }
            }
        }
        return readPosts;
    }

    public ArrayList<Post> listUnreadPost (UUID userId) {
        ArrayList<Read> reads = fileUtilService.read(LAST_PATH);
        ArrayList<Post> list = postService.listOthersPosts(userId);
        ArrayList<Post> unreadPosts = new ArrayList<>();

        for (Post post: list) {
            for (Read read: reads) {
                if (post.getId().equals(read.getPostId()) && read.getUserId().equals(userId) &&
                        !read.isRead()) {
                    unreadPosts.add(post);
                }
            }
        }
        return unreadPosts;
    }

    public int lnumberOfUnreadPosts (UUID userId) {
        return listUnreadPost(userId).size();
    }
}
