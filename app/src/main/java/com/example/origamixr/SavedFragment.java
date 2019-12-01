package com.example.origamixr;

import android.content.Context;
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

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SavedFragment extends Fragment {
    GridView listView;

    public static ArrayList<String> savedDesigns = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saved_fragment, container, false);

        listView = view.findViewById(R.id.saved_list_view);
        CustomAdapter adapter = new CustomAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), MainHomeActivity.origamiTitles[position], Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() { return getActivity().getSharedPreferences("Saved", MODE_PRIVATE).getAll().size(); }

        @Override
        public Object getItem(int position) { return null; }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.origami_listview_item, null);

            SharedPreferences designIndexes = getActivity().getSharedPreferences("Saved", MODE_PRIVATE);
            SharedPreferences.Editor editor = designIndexes.edit();

            ImageView imgView = view.findViewById(R.id.origami_icon);
            TextView txtView = view.findViewById(R.id.origami_name);
            TextView subtitle = view.findViewById(R.id.origami_info);
            ToggleButton button = view.findViewById(R.id.bookmark_icon);

            int index = designIndexes.getInt(savedDesigns.get(position), 0);

            imgView.setImageResource(MainHomeActivity.origamiDesigns[index]);
            txtView.setText(MainHomeActivity.origamiTitles[index]);
            subtitle.setText(MainHomeActivity.origamiInfo[index]);
            button.setChecked(true);

            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!isChecked) {
                        editor.remove(MainHomeActivity.origamiTitles[index]);
                        savedDesigns.remove(MainHomeActivity.origamiTitles[index]);
                        editor.commit();
                        notifyDataSetChanged();
                        Toast.makeText(buttonView.getContext(), "Removed " + txtView.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return view;
        }
    }
}
