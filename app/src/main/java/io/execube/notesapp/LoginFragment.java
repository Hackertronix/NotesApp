package io.execube.notesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Prateek Phoenix on 10/26/2016.
 */

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_login,container,false);

        final EditText nameEditText= (EditText) view.findViewById(R.id.name_edit_text);
        final EditText passwordEditText= (EditText) view.findViewById(R.id.password_edit_text);
        Button loginButton=(Button)view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ename= nameEditText.getText().toString().trim();
                String Epassword=passwordEditText.getText().toString().trim();

                if(Ename.length()==0||Epassword.length()==0)
                {
                    Toast.makeText(getContext(), "Please Enter Credentials", Toast.LENGTH_SHORT).show();
                }

                else{

                    SharedPreferences sharedPreferences= getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                    String S_user=sharedPreferences.getString("user",null);
                    String S_password=sharedPreferences.getString("password",null);
                    if(S_password==null&&S_user==null)
                    {
                        SharedPreferences.Editor editor= sharedPreferences.edit();

                        editor.putString("user",Ename);
                        editor.putString("password",Epassword);
                        editor.commit();

                        Toast.makeText(getContext(),"Added as NEW USER",Toast.LENGTH_SHORT).show();
                        ((FragmentChangeListener)getActivity()).replaceFragment();
                    }

                    else if (!Ename.equals(S_user)||!Epassword.equals(S_password)){
                        Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

//                    if (Ename.equals(user)&&Epassword.equals(password))
                    else
                    {
                        Toast.makeText(getContext(), "Logged In!!", Toast.LENGTH_SHORT).show();
                        ((FragmentChangeListener)getActivity()).replaceFragment();
                    }
                }



            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public interface FragmentChangeListener
    {
        public void replaceFragment();
    }
}
