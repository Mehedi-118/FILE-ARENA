package com.example.file_arena;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;

public class Document extends AppCompatActivity {
    ListView lview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF0000"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        setContentView(R.layout.activity_document);


        lview = findViewById(R.id.appsListid);
        ArrayList<String> myList = new ArrayList<String>();


        String root_sd = getIntent().getStringExtra("file_path");
        File file = new File(root_sd);

        //list content of root sd

        final File list[] = file.listFiles();

        for (int i = 0; i < list.length; i++) {

            //check the contents of each folder before adding to list


            if (list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".pdf")
                    || list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".docx")
                    || list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".html")) {
                myList.add(list[i].getName());

            }


        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, myList);
        lview.setAdapter(adapter);//setting the adapter


        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);

                String s = list[position].getAbsolutePath();
                File f = new File(s);


                // MimeTypeMap myMime=MimeTypeMap.getSingleton();
                Intent newIntent = new Intent(Intent.ACTION_VIEW);

                newIntent.setDataAndType(Uri.fromFile(f), URLConnection.guessContentTypeFromName(s));

                Intent j = Intent.createChooser(newIntent, "Choose an application to open with: ");
                startActivity(j);
            }


        });

    }
}




