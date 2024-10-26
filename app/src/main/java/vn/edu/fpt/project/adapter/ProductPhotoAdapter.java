package vn.edu.fpt.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.fpt.project.R;

public class ProductPhotoAdapter extends RecyclerView.Adapter<ProductPhotoAdapter.ViewHolder> {

    private String[] photoUrls; // Assume URLs of photos are passed

    public ProductPhotoAdapter(String[] photoUrls) {
        this.photoUrls = photoUrls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Load image using Glide or Picasso
        Glide.with(holder.itemView.getContext())
                .load(photoUrls[position])
                .into(holder.photoImageView);
    }

    @Override
    public int getItemCount() {
        return photoUrls.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
        }
    }
}
