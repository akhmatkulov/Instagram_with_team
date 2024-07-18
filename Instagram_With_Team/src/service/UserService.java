package service;

import java.util.List;
import java.util.UUID;

public class UserService {
   private static final String LAST_PAST = "users.json";
   private final FileUtilService<User>fileUtilService;

    public UserService(FileUtilService<User> fileUtilService) {
        this.fileUtilService = fileUtilService;
    }

    public User add(User user) {
        ArrayList<User> users = fileUtilService.read(LAST_PAST);
        if (!hasUser(user)) {
           users.add(user);
           fileUtilService.write(users, LAST_PAST);
           return user;
       }
       return null;
   }

   private boolean hasUser(User user) {
        ArrayList<User> list = fileUtilService.read(LAST_PAST);

       for (User user1 : list) {
           if (user1.getUsername().equals(user.getUsername()) ||
                   user1.getEmail().equals(user.getEmail())) {
                 return true;
           }
       }
       return false;
   }

   public User login(String username, String password) {
       User user = getByUserByUserName(username);
       if (user != null) {
           if (user.getPassword().equals(password)) {
               return user;
           }
       }
       return null;
   }

   public User getByUserByUserName(String username) {
        ArrayList<User> list = fileUtilService.read(LAST_PAST);
       for (User user : list) {
           if (user.getUsername().equals(username)) {
               return user;
           }
       }
       return null;
   }

   public ArrayList<User> list() {
        return fileUtilService.read(LAST_PAST);
   }
}
