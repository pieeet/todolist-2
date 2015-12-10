package com.rocdev.android.takenlijst;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by piet on 29-09-15.
 */
public class TakenLijstAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Taak> taken;

    public TakenLijstAdapter(Context context, ArrayList<Taak> taken) {
        this.context = context;
        this.taken = taken;
    }

    @Override
    public int getCount() {
        return taken.size();
    }

    @Override
    public Object getItem(int position) {
        return taken.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaakLayout taakLayout;
        Taak taak = taken.get(position);
        if (convertView == null) {
            taakLayout = new TaakLayout(context, taak);
        } else {
            taakLayout = (TaakLayout) convertView;
            taakLayout.setTaak(taak);
        }

        return taakLayout;
    }
}
