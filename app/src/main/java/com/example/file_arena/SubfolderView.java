package com.example.file_arena;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.net.URLConnection;

public class SubfolderView extends AppCompatActivity {
    private ListView listView;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF0000"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        setContentView(R.layout.activity_subfolder_view);


        listView = findViewById(R.id.sublist);
        final String folder = getIntent().getStringExtra("subfolder");
        File f = new File(folder);
        final File[] paths = f.listFiles();
        final String[] values = f.list();
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, values);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, R.id.list_content, values);

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
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
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
                        Uri screenshotUri = Uri.parse(folder);
                        sharingIntent.setType("*/*");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
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


        /** Item On longclicked  End  */

    }

    /**
     * MENU ITEM WITH LISTENER
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newFolderId: {
                newFolder();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void newFolder() {
        final String path = getIntent().getStringExtra("subfolder");
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SubfolderView.this);
        View mView = getLayoutInflater().inflate(R.layout.floating_edit_textwindow, null);
        final EditText FileName = mView.findViewById(R.id.write);
        Button Done = mView.findViewById(R.id.DoneID);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FileName.getText().toString().isEmpty()) {

                    File theDir = new File(path + "//new folder");
                    if (!theDir.exists()) {
                        try {
                            theDir.mkdir();
                            Toast.makeText(SubfolderView.this, "New Folder created", Toast.LENGTH_SHORT).show();
                            SubfolderView.this.finish();
                            Intent InternalStorage = new Intent(SubfolderView.this, SubfolderView.class);
                            startActivity(InternalStorage);

                        } catch (SecurityException se) {
                            Toast.makeText(SubfolderView.this, "Cannot create folder here", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        int i = 0;
                        while (true) {
                            File theDirx = new File(path + "//new folder" + "(" + (++i) + ")");
                            if (!theDirx.exists()) {
                                try {
                                    theDirx.mkdir();
                                    Toast.makeText(SubfolderView.this, "new folder" + "(" + (i) + ")" + " created", Toast.LENGTH_SHORT).show();
                                    SubfolderView.this.finish();
                                    Intent InternalStorage = new Intent(SubfolderView.this, SubfolderView.class);
                                    startActivity(InternalStorage);
                                    break;
                                } catch (SecurityException se) {
                                    Toast.makeText(SubfolderView.this, "Cannot create folder here", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }

                        }

                    }
                } else {
                    String folderName = FileName.getText().toString();
                    File theDir = new File(path + "//" + folderName);
                    if (!theDir.exists()) {
                        try {
                            theDir.mkdir();
                            Toast.makeText(SubfolderView.this, "\"" + folderName + "\" folder created", Toast.LENGTH_SHORT).show();
                            SubfolderView.this.finish();
                            Intent InternalStorage = new Intent(SubfolderView.this, SubfolderView.class);
                            startActivity(InternalStorage);

                        } catch (SecurityException se) {
                            Toast.makeText(SubfolderView.this, "Cannot create folder here", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SubfolderView.this, "A folder named \"" + folderName + "\" already exists", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
        mBuilder.setView(mView);
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

}
