package com.example.file_arena;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    private ImageButton gallery, video, audio, document, compressed, favorite, download, application, recent, hardDrive, Ssd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_homepage);
        gallery = findViewById(R.id.galleryButton);
        video = findViewById(R.id.videoButton);
        audio = findViewById(R.id.audioButton);
        document = findViewById(R.id.documentButton);
        compressed = findViewById(R.id.zipFolderButton);
        favorite = findViewById(R.id.favoriteButton);
        download = findViewById(R.id.downloadButton);
        application = findViewById(R.id.appsButton);
        recent = findViewById(R.id.recentButton);
        hardDrive = findViewById(R.id.internalStorageButton);
        Ssd = findViewById(R.id.microSdButton);

        Handler handler = new Handler();

        /** All Button With listener */

        gallery.setOnClickListener(handler);
        video.setOnClickListener(handler);
        audio.setOnClickListener(handler);
        download.setOnClickListener(handler);
        document.setOnClickListener(handler);
        application.setOnClickListener(handler);
        recent.setOnClickListener(handler);
        favorite.setOnClickListener(handler);
        compressed.setOnClickListener(handler);
        hardDrive.setOnClickListener(handler);
        Ssd.setOnClickListener(handler);


    }

    class Handler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.galleryButton) {
                Intent galleryWindow = new Intent(HomePage.this, GalleryWindow.class);
                startActivity(galleryWindow);
                Toast.makeText(HomePage.this, "Photo Gallery", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.videoButton) {
                Intent videoWindow = new Intent(HomePage.this, VideoWindow.class);
                startActivity(videoWindow);
                Toast.makeText(HomePage.this, "Video Gallery", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.audioButton) {
                Intent audioWindow = new Intent(HomePage.this, AudioWindow.class);
                startActivity(audioWindow);
                Toast.makeText(HomePage.this, "Audio Gallery", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.documentButton) {
                Intent documentWindow = new Intent(HomePage.this, DocumentWindow.class);
                startActivity(documentWindow);
                Toast.makeText(HomePage.this, "Document", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.zipFolderButton) {
                Intent zipfolderWindow = new Intent(HomePage.this, ZipfolderWindow.class);
                startActivity(zipfolderWindow);
                Toast.makeText(HomePage.this, "Compressed Files", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.appsButton) {
                Intent appsWindow = new Intent(HomePage.this, AppsWindow.class);
                startActivity(appsWindow);
                Toast.makeText(HomePage.this, "Applications", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.downloadButton) {
                Intent downloadWindow = new Intent(HomePage.this, DownloadWindow.class);
                startActivity(downloadWindow);
                Toast.makeText(HomePage.this, "Downloads", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.favoriteButton) {
                Intent favoriteWindow = new Intent(HomePage.this, FavoriteWindow.class);
                startActivity(favoriteWindow);
                Toast.makeText(HomePage.this, "Favorites", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.recentButton) {
                Intent recentWindow = new Intent(HomePage.this, RecentWindow.class);
                startActivity(recentWindow);
                Toast.makeText(HomePage.this, "Photo Gallery", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.internalStorageButton) {
                Intent InternalStorage = new Intent(HomePage.this, InternalStorage.class);
                startActivity(InternalStorage);
                Toast.makeText(HomePage.this, "INTERNAL STORAGE ", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.microSdButton) {
                boolean rslt = isAvailable();
                if (rslt == true) {
                    Intent sdCard = new Intent(HomePage.this, SdCard.class);
                    startActivity(sdCard);
                    Toast.makeText(HomePage.this, "SD CARD", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomePage.this, "NO MEMEORY CARD AVAILABLE", Toast.LENGTH_SHORT).show();
                }


            }


        }

        boolean isAvailable() {
            String state = Environment.getExternalStorageState();
            return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
        }
    }
}
