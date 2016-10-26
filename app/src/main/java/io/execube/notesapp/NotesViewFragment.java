package io.execube.notesapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import io.execube.notesapp.data.NoteContract;
import io.execube.notesapp.data.NoteDbHelper;

/**
 * Created by Prateek Phoenix on 10/26/2016.
 */
public class NotesViewFragment extends Fragment {

    NoteDbHelper mHelper;
    private ListView noteListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.notes_view,container,false);

        FloatingActionButton fab= (FloatingActionButton)view.findViewById(R.id.add_new_note);
       noteListView=(ListView) view.findViewById(R.id.notes_list_view);

       final Communicator communicator= (Communicator) getActivity();
        View emptyView=view.findViewById(R.id.empty_view);

        mHelper=new NoteDbHelper(getContext());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentChangeListener)getActivity()).swapFragment();
            }
        });


        noteListView.setEmptyView(emptyView);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                communicator.respond(l);
            }
        });
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        //menu.clear();
        inflater.inflate(R.menu.menu_notes_view,menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_delete_all_entries:
                deleteAllNotes();
                displayDataFromDB();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {


        if (noteListView.getCount() > 0) {
            SQLiteDatabase db = mHelper.getWritableDatabase();

            db.delete(NoteContract.NoteEntry.TABLE_NAME, null, null);
            Toast.makeText(getContext(), "ALL NOTES DELETED", Toast.LENGTH_SHORT).show();

        }

        else
        {
            Toast.makeText(getContext(), "NO NOTES TO DELETE!!! ADD A NOTE", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        displayDataFromDB();
    }

    private void displayDataFromDB() {

        SQLiteDatabase db= mHelper.getReadableDatabase();

        String[] projection={
                NoteContract.NoteEntry._ID,
                NoteContract.NoteEntry.COLUMN_NOTE_TITLE,
                NoteContract.NoteEntry.COLUMN_NOTE_BODY};

        Cursor cursor=db.query(NoteContract.NoteEntry.TABLE_NAME,
                projection,
                null,null,null,null,null);


        NoteAdapter adapter= new NoteAdapter(getContext(),cursor);
        noteListView.setAdapter(adapter);
        }


    public interface FragmentChangeListener
    {
        public void swapFragment();
    }


}
