package com.pigmalionstudios.todolist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by pigmalionstudios on 4/7/15.
 */
public class FragmentPendientes extends Fragment {
    public ListView listView;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fm_pendientes, container, false);
//        listView = (ListView)rootViewfindViewById(R.id.listView);
//        ArrayAdapter<String> adapter = {"Terminar esto", "Seguir con esto"};
//        listView.setAdapter(adapter);
        return rootView;
    }
}
