package vn.edu.fpt.project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.edu.fpt.project.model.Comment;
import vn.edu.fpt.project.repository.CommentRepository;

public class CommentViewModel extends ViewModel {

    private CommentRepository commentRepository;

    public CommentViewModel() {
        commentRepository = new CommentRepository();
    }

    public LiveData<List<Comment>> getComments(String productId) {
        return commentRepository.getComments(productId);
    }
}
