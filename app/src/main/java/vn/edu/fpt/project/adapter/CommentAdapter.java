package vn.edu.fpt.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.fpt.project.R;
import vn.edu.fpt.project.model.Comment;
import vn.edu.fpt.project.util.DateUtils;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.commentContentTextView.setText(comment.getContent());
        holder.commentDateTextView.setText(DateUtils.formatDate(comment.getCreatedAt()));
        holder.commentRatingTextView.setText("Rating: " + comment.getRate());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentContentTextView;
        TextView commentDateTextView;
        TextView commentRatingTextView;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentContentTextView = itemView.findViewById(R.id.commentContentTextView);
            commentDateTextView = itemView.findViewById(R.id.commentDateTextView);
            commentRatingTextView = itemView.findViewById(R.id.commentRatingTextView);
        }
    }
}
