package com.rocdev.android.takenlijst;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by piet on 28-09-15.
 */
public class TaakLayout extends RelativeLayout implements View.OnClickListener {
    private CheckBox checkBox;
    private TextView naamTextView;
    private TextView notitieTextView;

    private Taak taak;
    private TakenlijstDB db;
    private Context context;


    public TaakLayout(Context context) { // voor Android tools
        super(context);
    }

    public TaakLayout(Context context, Taak taak) {
        super(context);
        this.context = context;
        db = new TakenlijstDB(context);
        //this.taak = taak;

        //inflate layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_taak, this, true);

        //maak referenties naar de widgets
        checkBox = (CheckBox) findViewById(R.id.voltooidCheckBox);

        naamTextView = (TextView) findViewById(R.id.NaamTextView);
        notitieTextView = (TextView) findViewById(R.id.NotitiesTextView);
        naamTextView.setTextColor(Color.BLACK);
        notitieTextView.setTextColor(Color.BLACK);

        //set Listeners
        checkBox.setOnClickListener(this);
        this.setOnClickListener(this);

        //set taak data op widgets
        setTaak(taak);
    }

    public void setTaak(Taak taak) {
        this.taak = taak;
        naamTextView.setText(taak.getNaam());
        if (taak.getNotitie().equalsIgnoreCase("")) {
            notitieTextView.setVisibility(GONE);
        } else {
            notitieTextView.setText(taak.getNotitie());
        }
        if (taak.getDatumMillisVoltooid() > 0) {
            checkBox.setChecked(true);
//            checkBox.setButtonDrawable(android.R.drawable.checkbox_on_background);

        } else {
            checkBox.setChecked(false);
//            checkBox.setButtonDrawable(android.R.drawable.checkbox_off_background);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voltooidCheckBox:
                if (checkBox.isChecked()) {
                    long l = System.currentTimeMillis();
                    taak.setDatumMillisVoltooid(l);
                } else {
                    taak.setDatumMillisVoltooid(0);
                }
                db.updateTaak(taak);
                break;
            default:
                Intent intent = new Intent(context, AddEditActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("editmode", true);
                intent.putExtra("taakId", taak.getTaakId());
                context.startActivity(intent);
        }

    }
}
