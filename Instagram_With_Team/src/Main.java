import model.Comment;
import model.Like;
import model.Post;
import model.User;
import service.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import service.PostService;
import service.ReadService;
import service.UserService;

import java.util.UUID;

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
            System.out.println("\u001B[34m1.Register:  2.Login:\u001B[0m");
            try {
                step = scannerInt.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31mYou enter incorrect type argument.\u001B[0m");
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
        System.out.print("\u001B[34mEnter name:\u001B[0m ");
        String name = scannerStr.nextLine();
        System.out.print("\u001B[34mEnter username:\u001B[0m ");
        String username = scannerStr.nextLine();
        System.out.print("\u001B[34mEnter password:\u001B[0m ");
        String password = scannerStr.nextLine();
        System.out.print("\u001B[34mEnter email:\u001B[0m ");
        String email = scannerStr.nextLine();
        User user = userService.add(new User(name, username, email, password));
        if (user != null) {
            System.out.println("\u001B[32mUser is successfully added.\u001B[0m");
            readService.addReadForNewUser(user.getId());
        } else {
            System.out.println("\u001B[31mThis user is already exist.\u001B[0m");
        }
    }

    private static void login() {
        System.out.print("\u001B[34mEnter username:\u001B[0m ");
        String username = scannerStr.nextLine();
        System.out.print("\u001B[34mEnter password:\u001B[0m ");
        String password = scannerStr.nextLine();
        User user = userService.login(username, password);
        if (user != null) {
            int stepCode = 11;
            while (stepCode != 0) {
                int number = readService.numberOfUnreadPosts(user.getId());
                System.out.println("\u001B[34m1.Posts:  2. Notification:\u001B[0m  " + (number == 0 ? "" : number));
                try {
                    stepCode = scannerInt.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\u001B[31mYou enter incorrect type argument.\u001B[0m");
                    scannerInt.next();
                }
                if (stepCode == 1) {
                    posts(user);
                } else if (stepCode == 2) {
                    notification(user);
                }
            }
        } else {
            System.out.println("\u001B[31mThis user not found!\u001B[0m");
        }
    }
  
    private static void posts(User user) {
        int stepCode = 10;
        while (stepCode != 0) {
            System.out.println("\u001B[34m1. MyPosts,  2. ReadPosts.\u001B[0m");
            try {
                stepCode = scannerInt.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31mYou enter incorrect type argument.\u001B[0m");
                scannerInt.next();
            }

            if (stepCode == 1) {
                myPosts(user);
            } else if (stepCode == 2) {
                 readPosts(user);
            }
        }
    }

    private static void myPosts(User user) {
        ArrayList<Post> myPosts = postService.listMyPosts(user.getId());
        if (myPosts.isEmpty()) {
            System.out.println("\u001B[34m1. Create a new post.\u001B[0m");
            int step = -1;
            try {
                step = scannerInt.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31mYou enter incorrect type argument.\u001B[0m");
                scannerInt.next();
            }

            if (step == 1) {
                createPosts(user);
            }
        } else {
            listAllPosts(user);

            System.out.println("\u001B[34m1. Create a new post,  2. Choose from existing posts.\u001B[0m");
            int step = -1;
            try {
                step = scannerInt.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31mYou enter incorrect type argument.\u001B[0m");
                scannerInt.next();
            }

            if (step == 1) {
                createPosts(user);
            } else if (step == 2) {
                try {
                    System.out.print("\u001B[32mChoose one of the posts:\u001B[0m");
                    int postIndex = scannerInt.nextInt();
                    viewPostDetails(myPosts.get(postIndex - 1), user);
                } catch (InputMismatchException e) {
                    System.out.println("\u001B[31mYou enter incorrect type argument.\u001B[0m");
                    scannerInt.next();
                } catch (IndexOutOfBoundsException i) {
                    System.out.println("\u001B[31mYou enter number which is out of bound.\u001B[0m");
                    scannerInt.next();
                }
            }
        }
    }

    private static void createPosts(User user) {
        System.out.print("\u001B[34mEnter post name:\u001B[0m");
        String name = scannerStr.nextLine();
        System.out.print("\u001B[34mEnter post link:\u001B[0m");
        String link = scannerStr.nextLine();
        Post createdPost = postService.add(new Post(link, name, user.getId()));
        if (createdPost != null) {
            System.out.println("\u001B[32mPost is successfully created\u001B[0m");
            readService.addReadForAllUsers(createdPost.getId(), user.getId());
        } else {
            System.out.println("\u001B[31mFailed to created post\u001B[0m");
        }
    }

    private static void listAllPosts(User user) {
        ArrayList<Post> allPosts = postService.listMyPosts(user.getId());
        int i = 1;
        for (Post post : allPosts) {
            System.out.println(i + ". " + post.getName());
            i++;
        }
    }

    private static void viewPostDetails(Post post, User user) {
        User user1 = userService.getUserByUserId(post.getUserId());
        System.out.println("Id: " + post.getId() + "\t" + "name: " + post.getName() + "\t" +
                "link: " + post.getLink() + "\t" + "user: " + user1.getUsername());
        System.out.println();
        System.out.println("\u001B[34m1. Comments,  2. Likes.\u001B[0m");
        int step = -1;
        try {
            step = scannerInt.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\u001B[31mYou enter incorrect type argument.\u001B[0m");
            scannerInt.next();
        }
        if (step == 1) {
            comments(post, user);
        } else if (step == 2) {
            likes(post, user);
        }
    }

    private static void notification(User user) {
         ArrayList<Post> posts = readService.listUnreadPost(user.getId());
         unreadPosts(user.getId());
         try {
             int choice = scannerInt.nextInt();

             readService.setReadForUser(user.getId(), posts.get(choice - 1).getId());
             viewPostDetails(posts.get(choice-1), user);
         } catch (InputMismatchException e) {
             System.out.println("\u001B[31mYou enter incorrect type argument.\u001B[0m");
             scannerInt.next();
         } catch (IndexOutOfBoundsException i) {
             System.out.println("\u001B[31mYou enter number which is out of bound.\u001B[0m");
             scannerInt.next();
         }
    }

    private static void unreadPosts(UUID userId){
        ArrayList<Post> posts = readService.listUnreadPost(userId);
        readService.listUnreadPost(userId);
        int count = 0;
        for(Post post: posts) {
            System.out.println(count + 1 + ". " + post.getName());
            count++;
        }
        System.out.println(0 + ".\u001B[31mExit\u001B[0m ");
    }

    private static void readPosts(User user) {
        ArrayList<Post> posts = readService.listReadPost(user.getId());
        int count = listReadPosts(user);
        if (count != 0) {
            int n = -1;
            try {
                n = scannerInt.nextInt();

                viewPostDetails(posts.get(n-1), user);
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31mYou enter incorrect type argument.\u001B[0m");
                scannerInt.next();
            } catch (IndexOutOfBoundsException i) {
                System.out.println("\u001B[31mYou enter number which is out of bound.\u001B[0m");
                scannerInt.next();
            }
        } else {
            System.out.println("\u001B[31mPosts are not exist.\u001B[0m");
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

    private static void comments(Post post, User user) {
        ArrayList<Comment> mainComments = commentService.listMainComment(post.getId());
        int count = listComments(post);

        if (count != 0 && post.getUserId().equals(user.getId())) {
            System.out.println("\u001B[32mDo you want to reply to comment: Y/N\u001B[0m");
            String s = scannerStr.nextLine();
            if (s.equalsIgnoreCase("Y")) {
                System.out.print("\u001B[32mChoose comment:\u001B[0m");
                try {
                    int n = scannerInt.nextInt();

                    System.out.print("\u001B[34mEnter text:\u001B[0m 1");
                    String text = scannerStr.nextLine();
                    commentService.add(new Comment(text, post.getId(), mainComments.get(n - 1).getId(), user.getId()));
                } catch (InputMismatchException e) {
                    System.out.println("\u001B[31mYou enter incorrect type argument.\u001B[0m");
                    scannerInt.next();
                }
            }
        }else if (count != 0 && !post.getUserId().equals(user.getId())) {
            System.out.println("\u001B[34mDo you want to write new comment or reply one of them: W/R\u001B[0m");
            String s = scannerStr.nextLine();
            if (s.equalsIgnoreCase("W")) {
                System.out.print("\u001B[34mEnter text:\u001B[0m ");
                String text = scannerStr.nextLine();
                commentService.add(new Comment(text, post.getId(), null, user.getId()));
            } else if (s.equalsIgnoreCase("R")) {
                System.out.print("\u001B[34mChoose comment:\u001B[0m ");
                try {
                    int n = scannerInt.nextInt();

                    System.out.print("\u001B[34mEnter text:\u001B[0m ");
                    String text = scannerStr.nextLine();
                    commentService.add(new Comment(text, post.getId(), mainComments.get(n - 1).getId(), user.getId()));
                } catch (InputMismatchException e) {
                    System.out.println("\u001B[31mYou enter incorrect type argument.\u001B[0m");
                    scannerInt.next();
                }
            }
        } else if (!post.getUserId().equals(user.getId())) {
            System.out.println("\u001B[34Do you want to write a comment: Y/N\u001B[0m");
            String s = scannerStr.nextLine();
            if (s.equalsIgnoreCase("Y")) {
                System.out.print("\u001B[34Enter text:\u001B[0m ");
                String text = scannerStr.nextLine();
                commentService.add(new Comment(text, post.getId(), null, user.getId()));
            }
        }
    }

    private static int listComments(Post post) {
        ArrayList<Comment> mainComments = commentService.listMainComment(post.getId());
        int count = 0;

        for (Comment comment: mainComments) {
            User user = userService.getUserByUserId(comment.getUserId());
            System.out.println(count + 1 + ". " + "{" + user.getUsername() + "}" + comment.getText());
            count++;
            ArrayList<Comment> subComments = commentService.listSubCommentsByCommentId(comment.getId(), post.getId());
            for (Comment comment1: subComments) {
                User user1 = userService.getUserByUserId(comment1.getUserId());
                System.out.println("\t\t\t\t" + "{" + user1.getUsername() + "}" + comment1.getText());
            }
        }
        return count;
    }

    private static void likes(Post post, User user) {
        if (likeService.hasLike(new Like(user.getId(), post.getId())) || post.getUserId().equals(user.getId())) {
            listLikes(post);
        } else {
            System.out.println("\u001B[34mDo you want to pass like or watch: P/W\u001B[0m");
            String s = scannerStr.nextLine();
            if (s.equalsIgnoreCase("P")) {
                likeService.add(new Like(user.getId(), post.getId()));
            } else if (s.equalsIgnoreCase("W")) {
                listLikes(post);
            }
        }
    }

    private static void listLikes(Post post) {
        ArrayList<Like> likes = likeService.listLikesByPostId(post.getId());
        int count = 0;

        for (Like like: likes) {
            User user1 = userService.getUserByUserId(like.getUserId());
            System.out.print(count + 1 + ". ");
            System.out.println("❤️" + "\t" + "[" + user1.getUsername() + "]");
            count++;
        }
    }

}
