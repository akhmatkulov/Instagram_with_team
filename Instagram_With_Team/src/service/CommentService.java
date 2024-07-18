package service;

import model.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentService {
    private static final String LAST_PATH = "comments.json";
    private final FileUtilService<Comment> fileUtilService;

    private CommentService(FileUtilService<Comment> fileUtilService){
        this.fileUtilService = fileUtilService;
    }

    public Comment add(Comment comment) {
        ArrayList<Comment> comments = fileUtilService.read(LAST_PATH);
        comments.add(comment);
        fileUtilService.write(comments, LAST_PATH);
        return comment;

    }

    public ArrayList<Comment> listMainComment(UUID postId) {
        ArrayList<Comment> comments = fileUtilService.read(LAST_PATH);
        ArrayList<Comment> comments1 = new ArrayList<>();
        for(Comment comment: comments){
            if(comment.getPostId().equals(postId) && comment.getParentCommentId() == null){
                comments1.add(comment);
            }
        }
        return comments1;
    }

    public List<Comment> listSubCommentsByCommentId(UUID commentId, UUID postId) {
        ArrayList<Comment> comments = fileUtilService.read(LAST_PATH);
        ArrayList<Comment> subCom = new ArrayList<>();
        for (Comment comment: comments){
            if(comment.getId().equals(commentId) && comment.getPostId().equals(postId)){
                subCom.add(comment);
            }
        }
        return subCom;
    }
}
