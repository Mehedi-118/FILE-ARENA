package com.example.file_arena;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import java.lang.reflect.Field;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AudioWindow extends AppCompatActivity {
    static final int MY_PERMISSION_REQUEST = 1;
    ArrayAdapter<String> adapter;
    ArrayList<String> fileList=new ArrayList<String>();
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

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                if (checked)
                   // clickedpath.add(paths[position].toString());
               // Toast.makeText(AudioWindow.this,paths[position].toString(),Toast.LENGTH_SHORT).show();
                count++;
                if (!checked) {
                   // clickedpath.remove(paths[position].toString());
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
    public static String[] getStorageDirectories(Context pContext) {

        final Set<String> rv = new HashSet<>();
    //comment

        File[] listExternalDirs = ContextCompat.getExternalFilesDirs(pContext, null);
        for (int i = 0; i < listExternalDirs.length; i++) {
            if (listExternalDirs[i] != null) {
                String path = listExternalDirs[i].getAbsolutePath();
                int indexMountRoot = path.indexOf("/Android/data/");
                if (indexMountRoot >= 0 && indexMountRoot <= path.length()) {

                    rv.add(path.substring(0, indexMountRoot));
                }
            }
        }
        return rv.toArray(new String[rv.size()]);
    }

    public void result() {
        listView = findViewById(R.id.audioListid);
        arrayList = new ArrayList<>();

        //final ArrayList<String> NewPath = getMusic();
        final ArrayList<String> path=getFile(Environment.getExternalStorageDirectory().getAbsoluteFile());
        ArrayList<String> values=new ArrayList<>();
        //final  String[] sd_card=getStorageDirectories(AudioWindow.this);
        //final ArrayList<String> path2=getFile(new File(sd_card[0]));
        for(int i=0;i!=path.size();i++) {
            File f=new File(path.get(i));
            values.add(f.getName());
        }
        /*for (int i=0;i!=path2.size();i++) {
            File f=new File(path2.get(i));
            values.add(f.getName());
            path.add(path2.get(i));
        }*/
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s = path.get(position);
                view.setSelected(true);



                Intent newIntent = new Intent(AudioWindow.this, restricted.class);
                newIntent.putExtra("file_path",s);
                startActivity(newIntent);



                //newIntent.setDataAndType(Uri.fromFile(f), URLConnection.guessContentTypeFromName(s));

                //Intent j = Intent.createChooser(newIntent, "Choose an application to open with: ");
                //startActivity(j);
            }
        });
    }
    public ArrayList<String> getFile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (File file : listFile) {
                if (file.isDirectory()) {
                    getFile(file);
                }
                else {
                    if (file.getName().endsWith(".mp3")
                            || file.getName().endsWith(".wav")
                            || file.getName().endsWith(".wma")
                            || file.getName().endsWith(".mp2")
                            || file.getName().endsWith(".aac")
                            || file.getName().endsWith(".au"))
                    {
                        String temp = file.getPath().substring(0, file.getPath().lastIndexOf('/'));
                        if(temp.contains("data")) {
                            continue;
                        }

                        if (!fileList.contains(temp))
                            fileList.add(temp);
                    }
                }
            }
        }
        return fileList;
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

                String temp = curretnLocation.substring(0, curretnLocation.lastIndexOf('/'));
                if (!paths.contains(temp))
                    paths.add(temp);

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

