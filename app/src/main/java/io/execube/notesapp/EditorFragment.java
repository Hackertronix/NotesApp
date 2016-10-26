package io.execube.notesapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import io.execube.notesapp.data.NoteContract;
import io.execube.notesapp.data.NoteDbHelper;

/**
 * Created by Prateek Phoenix on 10/26/2016.
 */
public class EditorFragment extends Fragment{

    boolean noteHasChanged=false;
    NoteDbHelper mNoteDbHelper;
    private EditText title_editText;
    private EditText body_editText;
    private Cursor cursor;
    private long id=0;
    private String Body;
    private String Title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.editor,container,false);

     title_editText=(EditText)view.findViewById(R.id.title);
        body_editText=(EditText)view.findViewById(R.id.body);

        mNoteDbHelper= new NoteDbHelper(getContext());
        setHasOptionsMenu(true);
        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.save);

        Bundle bundle= new Bundle();
        bundle=getArguments();

       if(bundle!=null) {
          id = getArguments().getLong("ID");

           if (id != 0) {
               loadEditorWithData(id);
           }
       }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                savePet();
        }});



        return view;
    }

    private void savePet() {
        String title=title_editText.getText().toString().trim();
        String body=body_editText.getText().toString().trim();

        if(title.length()==0||body.length()==0)
        {
            Toast.makeText(getContext(), "Enter Title & Body", Toast.LENGTH_SHORT).show();
        }

        else
        {

            if(id==0&&!entryAlreadyExists(id,Title,Body))
            {
                SQLiteDatabase db= mNoteDbHelper.getWritableDatabase();

                ContentValues contentValues= new ContentValues();

                contentValues.put(NoteContract.NoteEntry.COLUMN_NOTE_TITLE,title);
                contentValues.put(NoteContract.NoteEntry.COLUMN_NOTE_BODY,body);

                db.insert(NoteContract.NoteEntry.TABLE_NAME,null,contentValues);

                Toast.makeText(getContext(), "Note Saved", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
            else if(id!=0&&entryAlreadyExists(id,Title,Body)){

                //Toast.makeText(getContext(), "ALREADY IN", Toast.LENGTH_SHORT).show();

                SQLiteDatabase db= mNoteDbHelper.getWritableDatabase();
                        ContentValues values= new ContentValues();

                        values.put(NoteContract.NoteEntry.COLUMN_NOTE_TITLE,title);
                        values.put(NoteContract.NoteEntry.COLUMN_NOTE_BODY,body);
                int rowsAffected=db.update(NoteContract.NoteEntry.TABLE_NAME,values,null,null);
                getFragmentManager().popBackStack();
            }

        }
    }


    private boolean entryAlreadyExists(long id,String title,String body) {
        SQLiteDatabase db= mNoteDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery("SELECT * FROM "+ NoteContract.NoteEntry.TABLE_NAME+" WHERE "+ NoteContract.NoteEntry._ID+"=?",new String[]{id+""});


        if(cursor!=null&&cursor.getCount()>0)
        {
            return true;
        }

        else{
            return false;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_editor_view,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_delete_note:
                deleteNote(id);
                getFragmentManager().popBackStack();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onPause () {
        super.onPause();
        //cursor.close();
    }


    private void deleteNote(long id) {

        if (id == 0) {
            return;
        } else {
            SQLiteDatabase db = mNoteDbHelper.getWritableDatabase();

            db.delete(NoteContract.NoteEntry.TABLE_NAME, NoteContract.NoteEntry._ID+"="+id,null );
            Toast.makeText(getContext(), "NOTE DELETED", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadEditorWithData(long id){
        SQLiteDatabase db= mNoteDbHelper.getReadableDatabase();
        cursor= db.rawQuery("SELECT * FROM "+NoteContract.NoteEntry.TABLE_NAME+" WHERE "+NoteContract.NoteEntry._ID+"=?", new String[] {id + ""});

        if(cursor.moveToFirst())
        {
             Title= cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NOTE_TITLE));
            Body= cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NOTE_BODY));

            title_editText.setText(Title);
            body_editText.setText(Body);
            //Toast.makeText(getContext(), "YAY", Toast.LENGTH_SHORT).show();

        }
    }
}
