package vn.edu.fpt.project.activity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.project.R;
import vn.edu.fpt.project.adapter.ProductListAdapter;
import vn.edu.fpt.project.adapter.SliderAdapter;
import vn.edu.fpt.project.model.SliderItem;
import vn.edu.fpt.project.viewmodel.ProductViewModel;

import android.content.Intent;

public class MainActivity extends BaseActivity {

    private long backPressedTime;
    private Toast backToast;
    private EditText searchView;
    private RecyclerView.Adapter adapterAllProducts, adapterFeaturedProducts, adapterLastestProducts;
    private RecyclerView recyclerAllProducts, recyclerFeaturedProducts, recyclerLastestProducts;

    private ProgressBar loadingAllProducts, loadingFeaturedProducts, loadingLastestProducts;
    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();

    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main);

        initView();
        banners();
        setupSearch();

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        fetchFeaturedProducts();
        fetchAllProducts();
        fetchLastestProducts();
    }

    private void setupSearch() {
        searchView = findViewById(R.id.searchView);
        searchView.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN && !keyEvent.isShiftPressed())) {

                String query = searchView.getText().toString().trim();
                if (!query.isEmpty()) {
                    // Start the search results activity
                    Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
                    intent.putExtra("query", query);
                    startActivity(intent);
                    // Use custom animations
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    // Clear the text in searchView
                    searchView.setText("");
                }
                return true;
            }
            return false;
        });
    }


    private void fetchFeaturedProducts() {
        loadingFeaturedProducts.setVisibility(View.VISIBLE);
        productViewModel.getFeaturedProducts().observe(this, listProduct -> {
            loadingFeaturedProducts.setVisibility(View.GONE);

            if (listProduct != null) {
                adapterFeaturedProducts = new ProductListAdapter(listProduct);
                recyclerFeaturedProducts.setAdapter(adapterFeaturedProducts);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch featured products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchLastestProducts() {
        loadingLastestProducts.setVisibility(View.VISIBLE);
        productViewModel.getLastestProducts().observe(this, listProduct -> {
            loadingLastestProducts.setVisibility(View.GONE);

            if (listProduct != null) {
                adapterLastestProducts = new ProductListAdapter(listProduct);
                recyclerLastestProducts.setAdapter(adapterLastestProducts);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch new products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchAllProducts() {
        loadingAllProducts.setVisibility(View.VISIBLE);
        productViewModel.getAllProducts().observe(this, listProduct -> {
            loadingAllProducts.setVisibility(View.GONE);
            if (listProduct != null) {
                adapterAllProducts = new ProductListAdapter(listProduct);
                recyclerAllProducts.setAdapter(adapterAllProducts);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch all products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void banners() {
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.fit2d));
        sliderItems.add(new SliderItem(R.drawable.specialoffer));
        sliderItems.add(new SliderItem(R.drawable.newproduct));
        sliderItems.add(new SliderItem(R.drawable.banner));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
            }
        });
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable, 5000);
    }

    private void initView() {
        searchView = findViewById(R.id.searchView);
        viewPager2 = findViewById(R.id.viewPagerSlider);

        recyclerFeaturedProducts = findViewById(R.id.recyclerFeaturedProducts);
        recyclerLastestProducts = findViewById(R.id.recyclerLastestProducts);
        recyclerAllProducts = findViewById(R.id.recyclerViewAllProducts);


        recyclerFeaturedProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerLastestProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Set up a GridLayoutManager with 2 columns and center items with padding/margin adjustments
        recyclerAllProducts.setLayoutManager(new GridLayoutManager(this, 2));

        int spacing = 16; // Define spacing in pixels, adjust as needed
        recyclerAllProducts.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = spacing / 2;
                outRect.right = spacing / 2;
                outRect.bottom = spacing;
            }
        });
        recyclerAllProducts.setHasFixedSize(true);
        recyclerAllProducts.setNestedScrollingEnabled(false);

        loadingFeaturedProducts = findViewById(R.id.progressBarFeaturedProducts);
        loadingAllProducts = findViewById(R.id.progressBarAllProducts);
        loadingLastestProducts = findViewById(R.id.progressBarLastestProducts);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            // If the back button is pressed within 2 seconds, close the app
            if (backToast != null) {
                backToast.cancel();
            }
            super.onBackPressed();
            return;
        } else {
            // If the back button is pressed for the first time, show the message
            backToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        // Record the time of the first back press
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        if (backToast != null) {
            backToast.cancel();
        }
        super.onDestroy();
    }
}
