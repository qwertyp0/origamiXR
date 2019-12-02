package com.example.origamixr;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

public class GalleryFragment extends Fragment {

    FloatingActionButton fab = null;
    RelativeLayout userLayout = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences sharedPreferencesGallery = getActivity().getSharedPreferences("Gallery", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesGallery.edit();

        String user = sharedPreferences.getString("username", "User");

        View view = inflater.inflate(R.layout.gallery_fragment, container, false);
        userLayout = view.findViewById(R.id.gallery_user_post);
        TextView tv = view.findViewById(R.id.username_user);
        tv.setText(user);
        if (sharedPreferencesGallery.getBoolean("posted", false)) {
            userLayout.setVisibility(View.VISIBLE);
        }

        fab = view.findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("posted", true);
                userLayout.setVisibility(View.VISIBLE);
                editor.apply();
            }
        });


        return view;
    }
}
