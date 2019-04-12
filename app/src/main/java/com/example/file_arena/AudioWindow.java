package com.example.file_arena;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;

public class AudioWindow extends AppCompatActivity {
    static final int MY_PERMISSION_REQUEST = 1;
    ArrayAdapter<String> adapter;
    ListView lview;
    int count = 0;
    ArrayList<String> arrayList;
    ArrayList<String> paths;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF0000"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        setContentView(R.layout.activity_audio_window);

        if (ContextCompat.checkSelfPermission(AudioWindow.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AudioWindow.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(AudioWindow.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(AudioWindow.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }

        } else {
            result();

        }


        /**Item On Long clicked  Start*/
        lview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                if (checked)

                    count++;
                if (!checked) {
                    count--;
                }

                mode.setTitle(count + "Selected");


            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.long_clicked_menu_item, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ShareId: {

                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        // Uri screenshotUri = Uri.parse(path);
                        sharingIntent.setType("*/*");
                        //  sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        startActivity(Intent.createChooser(sharingIntent, "Share With:"));


                        return true;

                    }
                    case R.id.Deleteid: {

                    }


                    default:
                        return false;
                }

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });


        /** Item Onlongclicked  End  */
    }

    public void result() {
        listView = findViewById(R.id.audioListid);
        arrayList = new ArrayList<>();

        final ArrayList<String> NewPath = getMusic();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = NewPath.get(position);
                File f = new File(s);
                Intent newIntent = new Intent(Intent.ACTION_VIEW);

                newIntent.setDataAndType(Uri.fromFile(f), URLConnection.guessContentTypeFromName(s));

                Intent j = Intent.createChooser(newIntent, "Choose an application to open with: ");
                startActivity(j);
            }
        });
    }

    public ArrayList<String> getMusic() {

        paths = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songLocation = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String curretnLocation = songCursor.getString(songLocation);
                arrayList.add(currentTitle + "\n" + currentArtist);

                paths.add(curretnLocation);

            } while (songCursor.moveToNext());
        }

        return paths;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(AudioWindow.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AudioWindow.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        result();
                    }
                } else {
                    Toast.makeText(AudioWindow.this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                    finish();

                }
                return;
            }
        }
    }
}

