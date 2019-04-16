package com.example.file_arena;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class HomePage extends AppCompatActivity {

    // private static String suffix=null;

    public static Switch switchAB;

    private ImageButton gallery, video, audio, document, compressed, favorite, download, application, recent, hardDrive, Ssd;
    /* void sdCardFunc( ) {
         Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
         Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();
         if (isSDSupportedDevice && isSDPresent) {
             Intent ssd = new Intent(HomePage.this, SdCard.class);
             startActivity(ssd);
             Toast.makeText(HomePage.this, "MEMORY CARD", Toast.LENGTH_SHORT).show();
         }
         else {
            Toast.makeText(HomePage.this,"SD CARD NOT FOUND",Toast.LENGTH_SHORT).show();
         }
     }*/
    private boolean isChecked = false;

    public static boolean hasRealRemovableSdCard(Context context) {
        return ContextCompat.getExternalFilesDirs(context, null).length >= 2;
    }

    /**
     * Get Availble Internal Storge size
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return formatSize(availableBlocks * blockSize);
    }

    /**
     * Get Total Intenal Storage Size
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return formatSize(totalBlocks * blockSize);
    }

    /**
     * Get Availble External Storge size
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getAvailableExternalMemorySize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return formatSize(availableBlocks * blockSize);
    }

    /**
     * Get Total External Storage Size
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getTotalExtternalMemorySize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return formatSize(totalBlocks * blockSize);
    }

    /**
     * END
     */

    public static String formatSize(long size) {
        String suffix = null;
        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null)
            resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(HomePage.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * END
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF0000"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        ActivityCompat.requestPermissions(HomePage.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);



        setContentView(R.layout.main_homepage);

        gallery = findViewById(R.id.galleryButton);
        video = findViewById(R.id.videoButton);
        audio = findViewById(R.id.audioButton);
        document = findViewById(R.id.documentButton);
        compressed = findViewById(R.id.zipFolderButton);
        // favorite = findViewById(R.id.favoriteButton);
        download = findViewById(R.id.downloadButton);
        application = findViewById(R.id.appsButton);
        //  recent = findViewById(R.id.recentButton);
        hardDrive = findViewById(R.id.internalStorageButton);
        Ssd = findViewById(R.id.microSdButton);

        Handler handler = new Handler();
        registerForContextMenu(hardDrive);
        registerForContextMenu(Ssd);
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

    /**
     * END
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.properties_internal_storage, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * END
     */

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.propertiesId: {


                String s = getTotalInternalMemorySize();
                String p = getAvailableInternalMemorySize();
               /* int n=Integer.parseInt(s);
                int k=Integer.parseInt(p);
                int diff=n-k;
                String used=String.valueOf(diff);*/

                propertiesWindow(s, p);
                // Toast.makeText(HomePage.this,s,Toast.LENGTH_SHORT).show();

                return true;
            }
            case R.id.propertiesId1: {
                String c = getTotalExtternalMemorySize();
                String d = getAvailableExternalMemorySize();
               /* int l = Integer.parseInt(c);
                int x = Integer.parseInt(d);
                int diff1 = l - x;
                String used1 = String.valueOf(diff1);*/

                propertiesWindow1(c, d);
                return true;
            }

        }
        return super.onContextItemSelected(item);
    }

    void propertiesWindow(String s, String p) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomePage.this);
        View mView = getLayoutInflater().inflate(R.layout.properties_window, null);
        TextView textView = mView.findViewById(R.id.totalMemoryId);
        TextView textView1 = mView.findViewById(R.id.availableMemoryId);
        //TextView textView2=mView.findViewById(R.id.usedMemoryId);

        textView.setText(s);
        textView1.setText(p);
        // textView2.setText(used);
        mBuilder.setView(mView);
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();


    }

    void propertiesWindow1(String s, String p) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomePage.this);
        View mView = getLayoutInflater().inflate(R.layout.properties_window, null);
        TextView textView = mView.findViewById(R.id.totalMemoryId);
        TextView textView1 = mView.findViewById(R.id.availableMemoryId);
        //TextView textView2=mView. findViewById(R.id.usedMemoryId);


        //  textView2.setText(used1);
        textView.setText(s);
        textView1.setText(p);

        mBuilder.setView(mView);
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        MenuItem item = menu.findItem(R.id.switchId);
        item.setActionView(R.layout.switch_item);
        Switch switchAB = item.getActionView().findViewById(R.id.switchAB);

        switchAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                } else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }

            }
        });
        return true;
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
           /* if (v.getId() == R.id.favoriteButton) {
                Intent favoriteWindow = new Intent(HomePage.this, FavoriteWindow.class);
                startActivity(favoriteWindow);
                Toast.makeText(HomePage.this, "Favorites", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.recentButton) {
                Intent recentWindow = new Intent(HomePage.this, RecentWindow.class);
                startActivity(recentWindow);
                Toast.makeText(HomePage.this, "Photo Gallery", Toast.LENGTH_SHORT).show();
            }*/
            if (v.getId() == R.id.internalStorageButton) {
                Intent InternalStorage = new Intent(HomePage.this, InternalStorage.class);
                startActivity(InternalStorage);
                Toast.makeText(HomePage.this, "INTERNAL STORAGE ", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.microSdButton) {

                if (hasRealRemovableSdCard(HomePage.this)) {
                    Intent ssd = new Intent(HomePage.this, SdCard.class);
                    startActivity(ssd);
                    Toast.makeText(HomePage.this, "MEMORY CARD", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(HomePage.this, "SD CARD NOT FOUND", Toast.LENGTH_SHORT).show();
            }

        }


    }
}




