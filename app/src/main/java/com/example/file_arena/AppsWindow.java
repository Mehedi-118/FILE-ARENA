package com.example.file_arena;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class AppsWindow extends AppCompatActivity {

    static final int MY_PERMISSION_REQUEST = 1;
    ArrayAdapter<String> adapter;
    ArrayList<String> fileList = new ArrayList<String>();
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
        setContentView(R.layout.activity_apps_window);


        if (ContextCompat.checkSelfPermission(AppsWindow.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AppsWindow.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(AppsWindow.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(AppsWindow.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }

        } else {
            result();

        }
    }

    public void result() {
        listView = findViewById(R.id.appsListid);
        arrayList = new ArrayList<>();

        //final ArrayList<String> NewPath = getMusic();
        final ArrayList<String> path = getFile(Environment.getExternalStorageDirectory().getAbsoluteFile());
        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i != path.size(); i++) {
            File f = new File(path.get(i));
            values.add(f.getName());
        }

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s = path.get(position);
                view.setSelected(true);


                Intent newIntent = new Intent(AppsWindow.this, Apps.class);
                newIntent.putExtra("file_path", s);
                startActivity(newIntent);

            }
        });
    }

    public ArrayList<String> getFile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (File file : listFile) {
                if (file.isDirectory()) {
                    getFile(file);
                } else {
                    if (file.getName().endsWith(".apk")) {
                        String temp = file.getPath().substring(0, file.getPath().lastIndexOf('/'));
                        if (temp.contains("data") || temp.startsWith(".")) {
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(AppsWindow.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AppsWindow.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        result();
                    }
                } else {
                    Toast.makeText(AppsWindow.this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                    finish();

                }
                return;
            }
        }
    }
}


