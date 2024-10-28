package vn.edu.fpt.project.view;

import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;

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
    private String currentSortOption = "latest"; // Default sort option
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_search_results);

        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);
        progressBarSearchResults = findViewById(R.id.progressBarSearchResults);
        Spinner sortSpinner = findViewById(R.id.sortSpinner);

        // Initialize sort options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        // Handle sorting option changes
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSortOption = getSortOptionFromPosition(position);
                searchProducts(query); // Re-search products based on new sort option
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

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

        query = getIntent().getStringExtra("query");
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

        productViewModel.searchProducts(currentSortOption, query).observe(this, listProduct -> {
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

    // Helper method to get sort option string based on the selected position
    private String getSortOptionFromPosition(int position) {
        switch (position) {
            case 0:
                return "latest";
            case 1:
                return "oldest";
            case 2:
                return "price-asc";
            case 3:
                return "price-desc";
            default:
                return "latest";
        }
    }
}
