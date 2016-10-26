package io.execube.notesapp.data;

import android.provider.BaseColumns;

/**
 * Created by Prateek Phoenix on 10/26/2016.
 */

public final class NoteContract {

    private NoteContract()
    {

    }


    public static final class NoteEntry implements BaseColumns
    {
        public static final String TABLE_NAME="notes";
        public static final String _ID=BaseColumns._ID;
        public static final String COLUMN_NOTE_TITLE="title";
        public static final String COLUMN_NOTE_BODY="body";


    }
}
