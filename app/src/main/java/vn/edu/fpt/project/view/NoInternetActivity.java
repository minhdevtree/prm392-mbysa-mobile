package vn.edu.fpt.project.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.edu.fpt.project.R;

public class NoInternetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_no_internet);

        // Set up the retry button
        Button retryButton = findViewById(R.id.retry_button);
        TextView messageTextView = findViewById(R.id.message_text_view);

        messageTextView.setText("No Internet Connection");
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to VirtualTryOnActivity when retry is clicked
                finish(); // Close NoInternetActivity
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}
