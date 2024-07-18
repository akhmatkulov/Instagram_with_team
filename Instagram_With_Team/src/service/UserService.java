package service;

import model.User;

import java.util.ArrayList;

public class UserService {
    private static final String LAST_PATH = "users.json";
    private final FileUtilService<User> fileUtilService;

    public UserService (FileUtilService<User> fileUtilService) {
        this.fileUtilService = fileUtilService;
    }

    public ArrayList<User> list() {
        return fileUtilService.read(LAST_PATH);
    }
}
