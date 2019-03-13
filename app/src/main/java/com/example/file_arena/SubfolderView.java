package com.example.file_arena;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.net.URLConnection;

public class SubfolderView extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subfolder_view);


        listView = findViewById(R.id.sublist);
        String folder = getIntent().getStringExtra("subfolder");
        File f = new File(folder);
        final File[] paths = f.listFiles();
        final String[] values = f.list();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, values);
        listView.setAdapter(adapter);//setting the adapter
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = paths[position].getAbsolutePath();

                File f = new File(s);

                if (f.isDirectory()) {
                    Intent myIntent = new Intent(SubfolderView.this, SubfolderView.class);
                    myIntent.putExtra("subfolder", s);
                    startActivity(myIntent);

                    Toast.makeText(SubfolderView.this, s, Toast.LENGTH_SHORT).show();
                } else {
                    // MimeTypeMap myMime=MimeTypeMap.getSingleton();
                    try {
                        Intent newIntent = new Intent(Intent.ACTION_VIEW);

                        newIntent.setDataAndType(Uri.fromFile(f), URLConnection.guessContentTypeFromName(s));

                        Intent j = Intent.createChooser(newIntent, "Choose an application to open with: ");

                        startActivity(j);
                    } catch (Exception e) {
                        Toast.makeText(SubfolderView.this, "No application to open this file", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
