package io.execube.notesapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements LoginFragment.FragmentChangeListener,NotesViewFragment.FragmentChangeListener,Communicator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View view = findViewById(R.id.container);

        FragmentManager fragmentManager= getSupportFragmentManager();

        Fragment fragment= fragmentManager.findFragmentById(R.id.container);

        if(fragment==null)
        {
            fragment=new LoginFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.container,fragment)
                    .commit();

        }

    }

    @Override
    public void replaceFragment() {

        NotesViewFragment fragment= new NotesViewFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }
    @Override
    public void swapFragment() {
        EditorFragment fragment= new EditorFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }


    @Override
    public void respond(long id) {

        FragmentManager manager= getSupportFragmentManager();
        EditorFragment fragment= (EditorFragment) manager.findFragmentById(R.id.container);

        fragment.loadEditorWithData(id);
    }
}
