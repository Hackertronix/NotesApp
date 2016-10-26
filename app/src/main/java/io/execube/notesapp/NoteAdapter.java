package io.execube.notesapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import io.execube.notesapp.data.NoteContract;

/**
 * Created by Prateek Phoenix on 10/26/2016.
 */

public class NoteAdapter extends CursorAdapter {
    public NoteAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView noteTitle= (TextView) view.findViewById(R.id.note_title);
        TextView noteBody= (TextView) view.findViewById(R.id.note_body);

        String title= cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NOTE_TITLE));
        String body=cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NOTE_BODY));

        noteTitle.setText(title);
        noteBody.setText(body);

    }

}
