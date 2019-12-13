package com.example.origamixr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import kotlin.text.Regex;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    GridView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        listView = view.findViewById(R.id.list_view);
        CustomAdapter adapter = new CustomAdapter();
        listView.setAdapter(adapter);

        // TODO: start activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(view.getContext(), "This is the " + MainHomeActivity.origamiTitles[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), InstructionStartActivity.class);
                intent.putExtra("title", MainHomeActivity.origamiTitles[position]);
                intent.putExtra("info", MainHomeActivity.origamiInfo[position]);
                intent.putExtra("design", MainHomeActivity.origamiDesigns[position]);
                startActivity(intent);
            }
        });

        return view;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return MainHomeActivity.origamiTitles.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.origami_listview_item, null);

            SharedPreferences designIndexes = getActivity().getSharedPreferences("Saved", MODE_PRIVATE);
            SharedPreferences.Editor editor = designIndexes.edit();
            /*editor.clear();
            editor.commit();*/

            ImageView imgView = view.findViewById(R.id.origami_icon);
            TextView txtView = view.findViewById(R.id.origami_name);
            TextView subtitle = view.findViewById(R.id.origami_info);
            ToggleButton button = view.findViewById(R.id.bookmark_icon);

            imgView.setImageResource(MainHomeActivity.origamiDesigns[position]);
            txtView.setText(MainHomeActivity.origamiTitles[position]);
            subtitle.setText(MainHomeActivity.origamiInfo[position]);
            if(designIndexes.contains(txtView.getText().toString())) {
                button.setChecked(true);
            }

            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        editor.putInt(MainHomeActivity.origamiTitles[position], position);
                        SavedFragment.savedDesigns.add(MainHomeActivity.origamiTitles[position]);
                        editor.commit();
                        //Toast.makeText(buttonView.getContext(), "Saved " + txtView.getText().toString() + " with index: " + position, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        editor.remove(MainHomeActivity.origamiTitles[position]);
                        SavedFragment.savedDesigns.remove(MainHomeActivity.origamiTitles[position]);
                        editor.commit();
                        //Toast.makeText(buttonView.getContext(), "Removed " + txtView.getText().toString() + " from saved  with index: " + position, Toast.LENGTH_SHORT).show();
                    }

                }
            });

            return view;
        }
    }
}
