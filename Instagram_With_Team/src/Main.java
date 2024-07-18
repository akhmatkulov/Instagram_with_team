import model.Post;
import model.User;
import service.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    static Scanner scannerInt = new Scanner(System.in);
    static Scanner scannerStr = new Scanner(System.in);
    static UserService userService = new UserService();
    static PostService postService = new PostService();
    static LikeService likeService = new LikeService();
    static CommentService commentService = new CommentService();
    static ReadService readService = new ReadService(userService, postService);

    public static void main(String[] args) {
        int step = 10;
        while (step != 0) {
            System.out.println("1.Register:  2.Login:");
            try {
                step = scannerInt.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You enter incorrect type argument.");
                scannerInt.next();
            }
            if (step == 1) {
                register();
            } else if (step == 2) {
                login();
            }
        }
    }

    private static void register() {
        System.out.print("Enter name: ");
        String name = scannerStr.nextLine();
        System.out.print("Enter username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter password: ");
        String password = scannerStr.nextLine();
        System.out.print("Enter email: ");
        String email = scannerStr.nextLine();
        User user = userService.add(new User(name, username, email, password));
        if (user != null) {
            System.out.println("User is successfully added.");
        } else {
            System.out.println("This user is already exist.");
        }
    }

    private static void login() {
        System.out.println("Enter username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter password: ");
        String password = scannerStr.nextLine();
        User user = userService.login(username, password);
        if (user != null) {
            int stepCode = 11;
            while (stepCode != 0) {
                System.out.println("1.Posts:  2. Notification:");
                try {
                    stepCode = scannerInt.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You enter incorrect type argument.");
                    scannerInt.next();
                }
                if (stepCode == 1) {
                    posts(user);
                } else if (stepCode == 2) {

                }
            }
        } else {
            System.out.println("This user not found!");
        }
    }

    private static void posts(User user) {
        int stepCode = 10;
        while (stepCode != 0) {
            System.out.println("1. MyPosts,  2. ReadPosts.");
            try {
                stepCode = scannerInt.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You enter incorrect type argument.");
                scannerInt.next();
            }

            if (stepCode == 1) {
                myPosts(user);
            } else if (stepCode == 2) {

            }
        }
    }

    private static void myPosts(User user) {
        ArrayList<Post> myPosts = postService.listMyPosts(user.getId());
        if (myPosts.isEmpty()) {
            System.out.println("1. Create a new post.");
            int step = -1;
            try {
                step = scannerInt.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You enter incorrect type argument.");
                scannerInt.next();
            }

            if (step == 1) {
                createPosts(user);
            }
        } else {
            listAllPosts(user);

            System.out.println("1. Create a new post,  2. Choose from existing posts.");
            int step = -1;
            try {
                step = scannerInt.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You enter incorrect type argument.");
                scannerInt.next();
            }

            if (step == 1) {
                createPosts(user);
            } else if (step == 2) {
                try {
                    int postIndex = scannerInt.nextInt();
                    viewPostDetails(myPosts.get(postIndex - 1));
                } catch (InputMismatchException e) {
                    System.out.println("You enter incorrect type argument.");
                    scannerInt.next();
                } catch (IndexOutOfBoundsException i) {
                    System.out.println("You enter number which is out of bound.");
                    scannerInt.next();
                }
            }
        }
    }

    private static void createPosts(User user) {
        System.out.println("Enter post name: ");
        String name = scannerStr.nextLine();
        System.out.println("Enter post link: ");
        String link = scannerStr.nextLine();
        Post createdPost = postService.add(new Post(link, name, user.getId()));

        if (createdPost != null) {
            System.out.println("Post is successfully created");
        } else {
            System.out.println("Failed to created post");
        }
    }

    private static void listAllPosts(User user) {
        ArrayList<Post> allPosts = postService.listMyPosts(user.getId());
        int i = 1;
        for (Post post : allPosts) {
            System.out.println(i + post.getName());
            i++;
        }
    }

    private static void viewPostDetails(Post post) {
        User user1 = userService.getUserByUserId(post.getUserId());
        System.out.println("Id: " + post.getId() + "\t" + "name: " + post.getName() + "\t" +
                "link: " + post.getLink() + "\t" + "user: " + user1.getUsername());
        System.out.println();
        System.out.println("1. Comments,  2. Likes.");
        int step = -1;
        try {
            step = scannerInt.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("You enter incorrect type argument.");
            scannerInt.next();
        }
        if (step == 1) {

        } else if (step == 2) {

        }
    }
}

