package vn.edu.fpt.project.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.fpt.project.R;

public abstract class BaseActivity extends AppCompatActivity {

    private View noInternetView;
    private View contentView;
    private BroadcastReceiver networkReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);  // Use the base layout with the header and footer

        // Initialize the no internet layout
        noInternetView = getLayoutInflater().inflate(R.layout.layout_no_internet, null);
        Button retryButton = noInternetView.findViewById(R.id.retry_button);
        retryButton.setOnClickListener(v -> {
            if (isConnectedToInternet()) {
                hideNoInternetView();
            }
        });

        // Register network receiver
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isConnectedToInternet()) {
                    hideNoInternetView();
                } else {
                    showNoInternetView();
                }
            }
        };
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        // Set up the common header functionality
        TextView shopTitle = findViewById(R.id.shopTitle);
        shopTitle.setOnClickListener(v -> {
            Intent intent = new Intent(BaseActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver);
    }

    // Method to check internet connection
    private boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    // Show no internet view
    private void showNoInternetView() {
        if (contentView != null) {
            ViewGroup contentFrame = findViewById(R.id.content_frame);
            contentFrame.removeView(contentView);
            contentFrame.addView(noInternetView);
        }
    }

    // Hide no internet view
    private void hideNoInternetView() {
        if (noInternetView.getParent() != null) {
            ViewGroup contentFrame = findViewById(R.id.content_frame);
            contentFrame.removeView(noInternetView);
            contentFrame.addView(contentView);
        }
    }

    // This method allows child activities to set their own content layout
    protected void setContentLayout(int layoutResID) {
        contentView = getLayoutInflater().inflate(layoutResID, null);
        ViewGroup contentFrame = findViewById(R.id.content_frame);
        contentFrame.removeAllViews();  // Clear any previous content
        contentFrame.addView(contentView);
    }
}
