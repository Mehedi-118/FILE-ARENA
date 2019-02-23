package com.example.file_arena;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ImageViewScreen extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_screen);
        String picture_path = getIntent().getStringExtra("img");
        imageView = findViewById(R.id.SelectableImageId);
        imageView.setImageURI(Uri.parse(picture_path));

    }
}
