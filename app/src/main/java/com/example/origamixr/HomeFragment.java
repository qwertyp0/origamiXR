package com.example.origamixr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    int[] origamiDesigns = {R.drawable.airplane, R.drawable.sailboat,
            R.drawable.tulip, R.drawable.butterfly, R.drawable.frog,
            R.drawable.crane, R.drawable.pinwheel, R.drawable.dog};
    String[] origamiTitles = {"Airplane", "Boat", "Tulip", "Butterfly",
            "Frog", "Crane", "Pinwheel", "Dog"};
    String[] origamiInfo = {"5 steps | 5 minutes", "7 steps | 5 minutes",
            "10 steps | 15 minutes", "8 steps | 15 minutes",
            "9 steps | 20 minutes", "8 steps | 10 minutes",
            "5 steps | 5 minutes", "10 steps | 15 minutes"};

    ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        listView = view.findViewById(R.id.list_view);
        CustomAdapter adapter = new CustomAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "This is the " + origamiTitles[position], Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return origamiTitles.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.origami_listview_item, null);
            ImageView imgView = view.findViewById(R.id.origami_icon);
            TextView txtView = view.findViewById(R.id.origami_name);
            TextView subtitle = view.findViewById(R.id.origami_info);

            imgView.setImageResource(origamiDesigns[position]);
            txtView.setText(origamiTitles[position]);
            subtitle.setText(origamiInfo[position]);

            return view;
        }
    }
}
