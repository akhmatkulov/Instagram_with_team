package service;

import model.Like;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LikeService {
    private static final String LAST_PATH = "likes.json";
    private final FileUtilService<Like> fileUtilService = new FileUtilService<>(Like.class);

    public Like add(Like like){
        ArrayList<Like> likes = fileUtilService.read(LAST_PATH);
        if (!hasLike(like)){
            likes.add(like);
            fileUtilService.write(likes, LAST_PATH);
            return like;
        }
        return null;
    }

    public boolean hasLike(Like like){
        ArrayList<Like> likes = fileUtilService.read(LAST_PATH);
        return likes.contains(like);
    }

    public List<Like> listLikesByPostId(UUID postId){
        ArrayList<Like> likes1 = new ArrayList<>();
        ArrayList<Like> likes = fileUtilService.read(LAST_PATH);
        for (Like like : likes){
            if (postId.equals(like.getPostId())){
                likes1.add(like);

            }
        }
        return likes1;
    }




}
