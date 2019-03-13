package com.example.file_arena;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.net.URLConnection;

public class InternalStorage extends AppCompatActivity {
    ListView lview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);

        lview = findViewById(R.id.internal_storage);
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File f = new File(path);
        final File[] paths = f.listFiles();

        final String[] values = f.list();//getting the list of files in string array
        //now presenting the data into screen
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, values);
        lview.setAdapter(adapter);//setting the adapter

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s = paths[position].getAbsolutePath();

                File f = new File(s);

                if (f.isDirectory()) {
                    Intent myIntent = new Intent(InternalStorage.this, SubfolderView.class);
                    myIntent.putExtra("subfolder", s);
                    startActivity(myIntent);
                    Toast.makeText(InternalStorage.this, s, Toast.LENGTH_SHORT).show();
                } else {
                    // MimeTypeMap myMime=MimeTypeMap.getSingleton();
                    Intent newIntent = new Intent(Intent.ACTION_VIEW);

                    newIntent.setDataAndType(Uri.fromFile(f), URLConnection.guessContentTypeFromName(s));

                    Intent j = Intent.createChooser(newIntent, "Choose an application to open with: ");
                    startActivity(j);
                }

            }
        });






       /* lview.setAdapter(new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return paths.length;
            }

            @Override
            public Object getItem(int position) {
                return paths[position];
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = getLayoutInflater().inflate(R.layout.activity_subfolder_view, parent, false);
                ListView listView = convertView.findViewById(R.id.sublist);
                listView.(Uri.parse(getItem(position).toString()));
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });
*/
    }
}
