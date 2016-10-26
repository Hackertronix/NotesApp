package io.execube.notesapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

    NoteDbHelper mNoteDbHelper;
    private EditText title_editText;
    private EditText body_editText;
    private Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.editor,container,false);

     title_editText=(EditText)view.findViewById(R.id.title);
        body_editText=(EditText)view.findViewById(R.id.body);

        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.save);

        mNoteDbHelper= new NoteDbHelper(getContext());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title=title_editText.getText().toString().trim();
                String body=body_editText.getText().toString().trim();

                if(title.length()==0||body.length()==0)
                {
                    Toast.makeText(getContext(), "Enter Title & Body", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    SQLiteDatabase db= mNoteDbHelper.getWritableDatabase();

                    ContentValues contentValues= new ContentValues();

                    contentValues.put(NoteContract.NoteEntry.COLUMN_NOTE_TITLE,title);
                    contentValues.put(NoteContract.NoteEntry.COLUMN_NOTE_BODY,body);

                    db.insert(NoteContract.NoteEntry.TABLE_NAME,null,contentValues);

                    Toast.makeText(getContext(), "Note Saved", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }
            }
        });



        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        cursor.close();
    }

   /* @Subscribe
    public void onEventReceived(long id)
    {

        SQLiteDatabase db= mNoteDbHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+NoteContract.NoteEntry.TABLE_NAME+" WHERE "+NoteContract.NoteEntry._ID+"=?", new String[] {id + ""});

        if(cursor.moveToFirst())
        {
            String Title= cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NOTE_TITLE));
            String Body= cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NOTE_BODY));

            Toast.makeText(getContext(), "YAY", Toast.LENGTH_SHORT).show();

        }
    }*/

    public void loadEditorWithData(long id){
        SQLiteDatabase db= mNoteDbHelper.getReadableDatabase();
        cursor= db.rawQuery("SELECT * FROM "+NoteContract.NoteEntry.TABLE_NAME+" WHERE "+NoteContract.NoteEntry._ID+"=?", new String[] {id + ""});

        if(cursor.moveToFirst())
        {
            String Title= cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NOTE_TITLE));
            String Body= cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NOTE_BODY));

            title_editText.setText(Title);
            body_editText.setText(Body);
            Toast.makeText(getContext(), "YAY", Toast.LENGTH_SHORT).show();

        }
    }
}
