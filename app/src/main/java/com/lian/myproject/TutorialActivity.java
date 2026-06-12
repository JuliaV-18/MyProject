package com.lian.myproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TutorialActivity extends AppCompatActivity {

    private ImageView tutorialImage;

    private int[] tutorialImages = {
            R.drawable.tutorial_1,
            R.drawable.tutorial_2,
            R.drawable.tutorial_3,
            R.drawable.tutorial_4,
            R.drawable.tutorial_5,
            R.drawable.tutorial_6
    };

    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tutorial);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        tutorialImage = findViewById(R.id.tutorialImage);

        Button btnPrev = findViewById(R.id.btnPrev);
        Button btnNext = findViewById(R.id.btnNext);

        // Show first image
        tutorialImage.setImageResource(tutorialImages[currentPage]);

        btnNext.setOnClickListener(v -> {
            if (currentPage < tutorialImages.length - 1) {
                currentPage++;
                tutorialImage.setImageResource(tutorialImages[currentPage]);
            }
        });

        btnPrev.setOnClickListener(v -> {
            if (currentPage > 0) {
                currentPage--;
                tutorialImage.setImageResource(tutorialImages[currentPage]);
            }
        });
    }
}