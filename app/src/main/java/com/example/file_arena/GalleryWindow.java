package com.example.file_arena;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class GalleryWindow extends AppCompatActivity {
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 882101;
    GridView gv;
    ArrayList<File> list;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_window);
        list = imageReader(new File(String.valueOf(Environment.getExternalStorageDirectory())));
        gv = findViewById(R.id.gridView);
        gv.setAdapter(new GridAdapter());
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), ImageViewScreen.class).putExtra("img", list.get(position).toString()));
            }
        });
    }

    ArrayList<File> imageReader(File root) {
        ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                a.addAll(imageReader(files[i]));
            } else {
                if ((files[i].getName().endsWith(".png")) || (files[i].getName().endsWith(".jpeg")) || (files[i].getName().endsWith(".jpg"))) {
                    a.add(files[i]);
                }
            }
        }

        return a;
    }
    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.single_grid_view, parent, false);
            ImageView img = convertView.findViewById(R.id.SingleImageView);
            img.setImageURI(Uri.parse(getItem(position).toString()));

            return convertView;
        }
    }


}

