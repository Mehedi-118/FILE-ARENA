package com.example.file_arena;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.Locale;

public class Document extends AppCompatActivity {
    ListView lview;
    ArrayList<String> mylist1 = new ArrayList<>();
    ArrayList<String> clickedpath = new ArrayList<>();
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF0000"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        setContentView(R.layout.activity_document);


        lview = findViewById(R.id.documentListid);
        ArrayList<String> myList = new ArrayList<String>();


        String root_sd = getIntent().getStringExtra("file_path");
        File file = new File(root_sd);

        //list content of root sd

        final File list[] = file.listFiles();

        for (int i = 0; i < list.length; i++) {

            //check the contents of each folder before adding to list


            if (list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".docx")
                    || list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".html")
                    || list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".pdf")
                    || list[i].getName().toLowerCase(Locale.getDefault()).endsWith(".pptx")) {
                myList.add(list[i].getName());
                mylist1.add(list[i].getAbsolutePath());


            }
            // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, myList);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, R.id.list_content, myList);
            lview.setAdapter(adapter);//setting the adapter


            lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    view.setSelected(true);

                    String s = mylist1.get(position);
                    File f = new File(s);


                    // MimeTypeMap myMime=MimeTypeMap.getSingleton();
                    Intent newIntent = new Intent(Intent.ACTION_VIEW);

                    newIntent.setDataAndType(Uri.fromFile(f), URLConnection.guessContentTypeFromName(s));

                    Intent j = Intent.createChooser(newIntent, "Choose an application to open with: ");
                    startActivity(j);
                }


            });

        }


        lview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                if (checked) {
                    clickedpath.add(mylist1.get(position));

                    count++;
                }
                if (!checked) {
                    clickedpath.remove(mylist1.get(position));
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
                        Uri screenshotUri = Uri.parse(clickedpath.get(0));
                        sharingIntent.setType("*/*");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        startActivity(Intent.createChooser(sharingIntent, "Share image using"));

                        return true;

                    }
                    case R.id.Deleteid: {
                        for (int i = 0; i != clickedpath.size(); i++) {
                            File f = new File(clickedpath.get(i));
                            f.delete();
                        }
                        Toast.makeText(Document.this, "deleted", Toast.LENGTH_SHORT).show();

                    }
                    case R.id.CopyId: {
                        String cp = "";
                        for (int i = 0; i != clickedpath.size(); i++) {
                            cp += clickedpath.get(i) + "|";
                        }
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("File path", cp);
                        clipboard.setPrimaryClip(clip);
                        return true;
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




}




