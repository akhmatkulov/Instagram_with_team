import model.Post;
import model.User;
import service.FileUtilService;
import service.PostService;
import service.ReadService;
import service.UserService;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    static Scanner scannerInt = new Scanner(System.in);
    static Scanner scannerStr = new Scanner(System.in);
    static UserService userService = new UserService();
    static PostService postService = new PostService();
    static ReadService readService = new ReadService();
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
        User user = userService.add(new User(name, username, password, email));
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
                int number = readService.lnumberOfUnreadPosts(user.getId());
                System.out.println("1.Posts:  2. Notification:" + (number == 0 ? "" : number));
                stepCode = scannerInt.nextInt();
                if (stepCode == 1) {

                } else if (stepCode == 2) {
                    notification();
                }
            }
        } else {
            System.out.println("This user not found!");
        }
    }



    private static void notification(UUID userId, UUID postId) {
     ArrayList<Post> posts = readService.listUnreadPost(userId);
     unreadPosts(userId);
     int choice = scannerInt.nextInt();
     entryPost(posts.get(choice-1));
    }

    private static void unreadPosts(UUID userId){
        ArrayList<Post> posts = readService.listUnreadPost(userId);
        readService.listUnreadPost(userId);
        int count = 0;
        for(Post post: posts) {
            System.out.println(count+1 + ". " + post.getName());
        }
        System.out.println(0 + ". Exit");
    }

    private static void entryPost(Post post) {

    }
}