import model.Post;
import model.User;
import service.FileUtilService;
import service.PostService;
import service.ReadService;
import service.UserService;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scannerInt = new Scanner(System.in);
    static Scanner scannerStr = new Scanner(System.in);
    static UserService userService = new UserService();
    static PostService postService = new PostService();
    static ReadService readService = new ReadService(userService, postService);

    public static void main(String[] args) {
        int step = 10;
        while (step != 0) {
            System.out.println("1.Register:  2.Login:");
            step = scannerInt.nextInt();
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
        System.out.print("Enter username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter password: ");
        String password = scannerStr.nextLine();
        User user = userService.login(username, password);
        if (user != null) {
            int stepCode = 11;
            while (stepCode != 0) {
                System.out.println("1.Posts:  2. Notification:");
                stepCode = scannerInt.nextInt();
                if (stepCode == 1) {

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
            System.out.println("1. MyPosts,  2. ReadPosts,  0. Exit.");
            stepCode = scannerInt.nextInt();
            
            if (stepCode == 1) {
                
            } else if (stepCode == 2) {
                
            }
        }
    }

    private static void readPosts(User user) {
        ArrayList<Post> posts = readService.listReadPost(user.getId());
        int count = listReadPosts(user);
        if (count != 0) {
            int n = scannerInt.nextInt();
            
            detailsPost(posts.get(n - 1));
        } else {
            System.out.println("Posts are not exist.");
        }
    }
    
    private static int listReadPosts(User user) {
        ArrayList<Post> readPosts = readService.listReadPost(user.getId());
        int count = 0;
        
        for (Post post: readPosts) {
            System.out.print(count + 1 + ". ");
            System.out.println(post.getName());
            count++;
        }
        return count;
    }
    
    private static void detailsPost(Post post) {
        User user = userService.getUserByUserId(post.getUserId());
        System.out.println(post.getId() + "Link: " + post.getLink() + "Name: " + post.getName() + "User: " + user.getUsername());
        System.out.println();
        System.out.println("1. Comments,  2. Likes.");
        int n = scannerInt.nextInt();
        if (n == 1) {
            
        } else if (n == 2) {
            
        }
    }

}