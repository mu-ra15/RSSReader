package com.mura.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
/**
 * Created by mura on 16/04/04.
 */
public class ChoiceActivity extends AppCompatActivity {
    Button btnWithActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        btnWithActivity = (Button)findViewById(R.id.btn_with_activity);
        btnWithActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChoiceActivity.this, DetailActivity.class);
                startActivity(i);
            }
        });
    }
}
