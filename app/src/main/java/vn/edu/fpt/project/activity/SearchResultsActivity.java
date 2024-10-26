package vn.edu.fpt.project.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import vn.edu.fpt.project.R;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.fpt.project.adapter.ProductListAdapter;
import vn.edu.fpt.project.viewmodel.ProductViewModel;

public class SearchResultsActivity extends BaseActivity {

    private RecyclerView recyclerViewSearchResults;
    private ProgressBar progressBarSearchResults;
    private ProductListAdapter adapterSearchResults;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_search_results);

        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);
        progressBarSearchResults = findViewById(R.id.progressBarSearchResults);

        // Set up a GridLayoutManager with 2 columns and center items with padding/margin adjustments
        recyclerViewSearchResults.setLayoutManager(new GridLayoutManager(this, 2));

        int spacing = 16; // Define spacing in pixels, adjust as needed
        recyclerViewSearchResults.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = spacing / 2;
                outRect.right = spacing / 2;
                outRect.bottom = spacing;
            }
        });
        recyclerViewSearchResults.setHasFixedSize(true);
        recyclerViewSearchResults.setNestedScrollingEnabled(false);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        String query = getIntent().getStringExtra("query");
        searchProducts(query);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void searchProducts(String query) {
        progressBarSearchResults.setVisibility(View.VISIBLE);

        TextView searchResultTextView = findViewById(R.id.searchResultTextView);
        searchResultTextView.setText("Search results for: " + query);
        searchResultTextView.setVisibility(View.VISIBLE);

        productViewModel.searchProducts("latest", query).observe(this, listProduct -> {
            progressBarSearchResults.setVisibility(View.GONE);
            if (listProduct != null && !listProduct.isEmpty()) {
                adapterSearchResults = new ProductListAdapter(listProduct);
                recyclerViewSearchResults.setAdapter(adapterSearchResults);
                adapterSearchResults.notifyDataSetChanged();
            } else {
                Toast.makeText(SearchResultsActivity.this, "No products found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
