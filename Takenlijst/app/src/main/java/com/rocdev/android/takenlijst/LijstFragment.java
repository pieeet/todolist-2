package com.rocdev.android.takenlijst;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link LijstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LijstFragment extends Fragment {
    //    TextView taakTextView;
    ListView taakListView;
    int tabPos;


    public LijstFragment() {
        // Required empty public constructor
    }
    public void setTabPos(int tabPos) {
        this.tabPos = tabPos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lijst, container, false);

        // get references to widget
        taakListView = (ListView) view.findViewById (R.id.takenlijst_listView);
        refreshTaskList();
        return view;
    }



    public void refreshTaskList() {

        //get takenlijst voor huidige tab
        Context context = getActivity().getApplicationContext();
        TakenlijstDB db = new TakenlijstDB(context);
        String lijstnaam = db.getLijst(tabPos + 1).getNaam();
        ArrayList<Taak> taken = db.getTaken(lijstnaam);


        //maak custom adapter
        TakenLijstAdapter adapter = new TakenLijstAdapter(context, taken);
        taakListView.setAdapter(adapter);




    }

    @Override
    public void onResume() {
        super.onResume();
        refreshTaskList();
    }

}
