package com.example.file_arena;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.VideoView;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;

public class Restricted_video extends AppCompatActivity {

    ListView lview;
    GridView gv;
    VideoView iv;


    ArrayList<String> myList = new ArrayList<String>();
    ArrayList<String> myList1 = new ArrayList<String>();
    ArrayList<Uri> myList3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF0000"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        setContentView(R.layout.activity_restricted_video);


        String root_sd = getIntent().getStringExtra("file_path");
        File file = new File(root_sd);

        //list content of root sd

        final File list[] = file.listFiles();

        for (int i = 0; i < list.length; i++) {

            //check the contents of each folder before adding to list


            if (list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".mp4")
                    || list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".3gp")
                    || list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".wmv")
                    || list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".avi")
                    || list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".webm")
                    || list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".mkv")
            ) {
                myList.add(list[i].getName());
                myList1.add(list[i].getAbsolutePath());

            }


        }
        gv = findViewById(R.id.gridView);

        gv.setAdapter(new GridAdapter());



       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, myList);
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
*/

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String s = myList1.get(position);


                File f = new File(s);


                // MimeTypeMap myMime=MimeTypeMap.getSingleton();
                Intent newIntent = new Intent(Intent.ACTION_VIEW);

                newIntent.setDataAndType(Uri.fromFile(f), URLConnection.guessContentTypeFromName(s));

                Intent j = Intent.createChooser(newIntent, "Choose an application to open with: ");
                startActivity(j);

            }
        });
    }

    class GridAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return myList1.size();
        }

        @Override
        public Object getItem(int position) {
            return myList1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.single_video, parent, false);
            iv = convertView.findViewById(R.id.VideoViewID);
            iv.setVideoURI(Uri.parse(myList1.get(position)));

            return convertView;
        }
    }
}



