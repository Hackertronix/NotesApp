package io.execube.notesapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import io.execube.notesapp.data.NoteContract.NoteEntry;
/**
 * Created by Prateek Phoenix on 10/26/2016.
 */

public class NoteDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="notes.db";
    public static final int DATABASE_VERSION=1;

    public NoteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_TABLE= "CREATE TABLE "+NoteEntry.TABLE_NAME+
                "("+NoteEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +NoteEntry.COLUMN_NOTE_TITLE+" TEXT NOT NULL,"
                +NoteEntry.COLUMN_NOTE_BODY+" TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
