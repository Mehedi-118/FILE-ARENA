package com.example.file_arena;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DocumentWindow extends AppCompatActivity {

    static final int MY_PERMISSION_REQUEST = 1;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    ArrayList<String> paths;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF0000"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        setContentView(R.layout.activity_document_window);


        if (ContextCompat.checkSelfPermission(DocumentWindow.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DocumentWindow.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(DocumentWindow.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(DocumentWindow.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }

        } else {
            // result();

        }
    }

   /* public void result() {
        listView = findViewById(R.id.documentListid);
        arrayList = new ArrayList<>();

        final ArrayList<String> NewPath= getMusic();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s= NewPath.get(position);
                File f=new File(s);
                Intent newIntent = new Intent(Intent.ACTION_VIEW);

                newIntent.setDataAndType(Uri.fromFile(f), URLConnection.guessContentTypeFromName(s));

                Intent j = Intent.createChooser(newIntent, "Choose an application to open with: ");
                startActivity(j);
            }
        });
    }

    /*public ArrayList<String> getMusic() {

        paths=new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
       // Uri songUri = MediaStore..Media.EXTERNAL_CONTENT_URI;
        //Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Video.Media.TI);
            int songArtist = songCursor.getColumnIndex(MediaStore.Video.Media.ARTIST);
            int songLocation =songCursor.getColumnIndex(MediaStore.Video.Media.DATA);

            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String curretnLocation =songCursor.getString(songLocation);
                arrayList.add(currentTitle + "\n" + currentArtist);

                paths.add(curretnLocation);

            } while (songCursor.moveToNext());
        }

        return paths;

    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(DocumentWindow.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(DocumentWindow.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        // result();
                    }
                } else {
                    Toast.makeText(DocumentWindow.this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                    finish();

                }
                return;
            }
        }
    }
}








