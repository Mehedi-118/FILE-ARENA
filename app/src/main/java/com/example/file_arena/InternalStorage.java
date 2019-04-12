package com.example.file_arena;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import java.util.ArrayList;

public class InternalStorage extends AppCompatActivity {
    ListView lview;
    int count = 0;
    String[] P = new String[0];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF0000"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
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
                view.setSelected(true);

                String s = paths[position].getAbsolutePath();
                view.setSelected(true);
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
        /**Item On Long clicked  Start*/
        final ArrayList<String> clickedpath = null;
        lview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                if (checked)
                    clickedpath.add(paths[position].toString());
                count++;
                if (!checked) {
                    clickedpath.remove(paths[position].toString());
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
                        Uri screenshotUri = Uri.parse(path);
                        sharingIntent.setType("*/*");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        startActivity(Intent.createChooser(sharingIntent, "Share With:"));




                        return true;

                    }
                    case R.id.Deleteid: {

                    }
                    case R.id.CopyId: {
                        ClipboardManager clipboard = (ClipboardManager)
                                getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("simple text", clickedpath.get(0));
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

    @Override

    /** MENU ITEM WITH LISTENER */

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
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(InternalStorage.this);
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
                            Toast.makeText(InternalStorage.this, "New Folder created", Toast.LENGTH_SHORT).show();
                            InternalStorage.this.finish();
                            Intent InternalStorage = new Intent(InternalStorage.this, InternalStorage.class);
                            startActivity(InternalStorage);

                        } catch (SecurityException se) {
                            Toast.makeText(InternalStorage.this, "Cannot create folder here", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        int i = 0;
                        while (true) {
                            File theDirx = new File(path + "//new folder" + "(" + (++i) + ")");
                            if (!theDirx.exists()) {
                                try {
                                    theDirx.mkdir();
                                    Toast.makeText(InternalStorage.this, "new folder" + "(" + (i) + ")" + " created", Toast.LENGTH_SHORT).show();
                                    InternalStorage.this.finish();
                                    Intent InternalStorage = new Intent(InternalStorage.this, InternalStorage.class);
                                    startActivity(InternalStorage);
                                    break;
                                } catch (SecurityException se) {
                                    Toast.makeText(InternalStorage.this, "Cannot create folder here", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(InternalStorage.this, "\"" + folderName + "\" folder created", Toast.LENGTH_SHORT).show();
                            InternalStorage.this.finish();
                            Intent InternalStorage = new Intent(InternalStorage.this, InternalStorage.class);
                            startActivity(InternalStorage);

                        } catch (SecurityException se) {
                            Toast.makeText(InternalStorage.this, "Cannot create folder here", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(InternalStorage.this, "A folder named \"" + folderName + "\" already exists", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
        mBuilder.setView(mView);
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

}

