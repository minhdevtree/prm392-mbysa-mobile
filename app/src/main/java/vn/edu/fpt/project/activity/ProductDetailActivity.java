package vn.edu.fpt.project.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.project.R;
import vn.edu.fpt.project.adapter.CommentAdapter;
import vn.edu.fpt.project.adapter.ProductListAdapter;
import vn.edu.fpt.project.adapter.ProductPhotoAdapter;
import vn.edu.fpt.project.model.Comment;
import vn.edu.fpt.project.model.Product;
import vn.edu.fpt.project.viewmodel.CommentViewModel;
import vn.edu.fpt.project.viewmodel.ProductViewModel;

public class ProductDetailActivity extends BaseActivity {

    private TextView productNameTextView;
    private TextView productDescriptionTextView;
    private TextView productPriceTextView;
    private TextView productRatingTextView;
    private ImageView productImageView;
    private Button virtualTryOnButton;
    private Button buyNowButton;
    private RecyclerView recyclerViewProductPhotos;
    private ProductPhotoAdapter productPhotoAdapter;
    private ProductViewModel productViewModel;
    private ProgressBar progressBar;
    private RecyclerView.Adapter adapterRelatedProducts;
    private RecyclerView recyclerRelatedProducts;
    private ProgressBar loadingRelatedProducts;
    private String productId;

    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private CommentViewModel commentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_product_detail);
        initializeViews();
        setupViewModel();
        productId = getIntent().getStringExtra("productId");

        if (productId != null) {
            fetchProductDetails(productId);
            fetchRelatedProducts(productId);
            fetchComments(productId);
        }

        setupListeners();
    }

    // Initialize views and set initial visibility states
    private void initializeViews() {
        productNameTextView = findViewById(R.id.productNameTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productRatingTextView = findViewById(R.id.productRatingTextView);
        productImageView = findViewById(R.id.productImageView);
        virtualTryOnButton = findViewById(R.id.virtualTryOnButton);
        buyNowButton = findViewById(R.id.buyNowButton);
        recyclerViewProductPhotos = findViewById(R.id.recyclerViewProductPhotos);
        progressBar = findViewById(R.id.progressBar);
        recyclerRelatedProducts = findViewById(R.id.recyclerViewRelatedProducts);
        loadingRelatedProducts = findViewById(R.id.progressBarRelatedProducts);

        recyclerViewProductPhotos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerRelatedProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        virtualTryOnButton.setVisibility(View.GONE);
        buyNowButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        // Initialize comment list and adapter
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        recyclerViewComments.setAdapter(commentAdapter);
    }

    // Setup ViewModel
    private void setupViewModel() {
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
    }

    // Setup listeners for button actions
    private void setupListeners() {
        virtualTryOnButton.setOnClickListener(v -> navigateToVirtualTryOn());
    }

    // Fetch product details using the product ID
    private void fetchProductDetails(String productId) {
        productViewModel.getProductById(productId).observe(this, product -> {
            progressBar.setVisibility(View.GONE);

            if (product != null) {
                showProductDetails(product);
            } else {
                showErrorToast("Failed to load product details");
            }
        });
    }

    private void fetchComments(String productId) {
        commentViewModel.getComments(productId).observe(this, comments -> {
            commentList.clear();
            TextView noCommentsTextView = findViewById(R.id.noCommentsTextView);
            if (comments != null && !comments.isEmpty()) {
                noCommentsTextView.setVisibility(View.GONE);
                commentList.addAll(comments);
            } else {
                noCommentsTextView.setVisibility(View.VISIBLE);
            }
            commentAdapter.notifyDataSetChanged();
        });
    }

    // Display product details in the UI
    private void showProductDetails(Product product) {
        virtualTryOnButton.setVisibility(View.VISIBLE);
        buyNowButton.setVisibility(View.VISIBLE);

        productNameTextView.setText(product.getName());
        productDescriptionTextView.setText(product.getDescription());
        productPriceTextView.setText(String.valueOf(product.getPrice()));
        productRatingTextView.setText(String.valueOf(product.getRate()));

        setupProductPhotoAdapter(product.getPhotos());
        setupBuyNowButton(product.getLink());
    }

    // Setup adapter for product photos
    private void setupProductPhotoAdapter(String[] photoUrls) {
        productPhotoAdapter = new ProductPhotoAdapter(photoUrls);
        recyclerViewProductPhotos.setAdapter(productPhotoAdapter);
    }

    // Setup Buy Now button action to open Shopee link
    private void setupBuyNowButton(String shopeeLink) {
        buyNowButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(shopeeLink));
            startActivity(intent);
        });
    }

    // Fetch related products by product ID
    private void fetchRelatedProducts(String productId) {
        loadingRelatedProducts.setVisibility(View.VISIBLE);
        productViewModel.getRelatedProducts(productId).observe(this, listProduct -> {
            loadingRelatedProducts.setVisibility(View.GONE);

            if (listProduct != null) {
                setupRelatedProductsAdapter(listProduct);
            } else {
                showErrorToast("Failed to fetch related products");
            }
        });
    }

    // Setup adapter for related products
    private void setupRelatedProductsAdapter(List<Product> products) {
        adapterRelatedProducts = new ProductListAdapter(products);
        recyclerRelatedProducts.setAdapter(adapterRelatedProducts);
    }

    // Navigate to Virtual Try-On Activity with animations
    private void navigateToVirtualTryOn() {
        Intent intent = new Intent(ProductDetailActivity.this, VirtualTryOnActivity.class);
        System.out.println(">>>>Product ID: " + productId);
        intent.putExtra("productId", productId);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    // Show error toast message
    private void showErrorToast(String message) {
        Toast.makeText(ProductDetailActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    // Handle back press with custom animation
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
