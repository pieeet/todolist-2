package com.rocdev.android.takenlijst;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * klasse die een nieuwe taak toevoegt of een bestaande taak wijzigt
 */

public class AddEditActivity extends AppCompatActivity
        implements View.OnKeyListener {

    private Spinner lijstSpinner;
    private EditText naamEditText;
    private EditText notitieEditText;
    private TakenlijstDB db;
    private boolean editMode;
    private Taak taak;
    private String huidigeTabNaam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //reference widgets
        lijstSpinner = (Spinner) findViewById(R.id.lijstSpinner);
        naamEditText = (EditText) findViewById(R.id.naamEditText);
        notitieEditText = (EditText) findViewById(R.id.noitieEditText);

        // set de listeners
        naamEditText.setOnKeyListener(this);
        notitieEditText.setOnKeyListener(this);

        // get DAO
        db = new TakenlijstDB(this);

        // set adapter voor spinner
        ArrayList<Lijst> lijsten = db.getLijsten();
        ArrayAdapter<Lijst> adapter = new ArrayAdapter<>(this, R.layout.spinner_list, lijsten);
        lijstSpinner.setAdapter(adapter);

        // get edit mode from intent
        Intent intent = getIntent();
        editMode = intent.getBooleanExtra("editmode", false);

        //als een bestaande taak ge-edit moet worden...
        if (editMode) {
            // get taak
            int taakId = intent.getIntExtra("taakId", -1);
            taak = db.getTaak(taakId);

            //update UI
            naamEditText.setText(taak.getNaam());
            notitieEditText.setText(taak.getNotitie());
        }
        // set correcte lijst voor de spinner
        int lijstId;
        if (editMode) { //gebruik dezelfde lijst voor een bestaande taak
            lijstId = taak.getLijstId();

        } else { // add mode: gebruik de huidige lijst van de tab
            huidigeTabNaam = intent.getStringExtra("tab");
            lijstId = db.getLijst(huidigeTabNaam).getId();
        }

        // set de juiste lijst in de spinner widget
        // selectie van spinner start met 0 dus = lijstId - 1
        lijstSpinner.setSelection(lijstId - 1);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_add_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuSave) {
            bewaarInDB();
            //verwijder de activity van de stack (keer terug naar MainActivity)
            this.finish();
        }

        else if (id == R.id.menuCancel) {
           this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void bewaarInDB() {
        int lijstId = lijstSpinner.getSelectedItemPosition() + 1;
        String naam = naamEditText.getText().toString();
        String notitie = notitieEditText.getText().toString();

        // als er geen naam is: exit methode
        if (naam == null || naam.replace(" ", "").equals("")) {
            return;
        }
        if (!editMode) {
            taak = new Taak();
        }

        taak.setLijstId(lijstId);
        taak.setNaam(naam);
        taak.setNotitie(notitie);
        if (editMode) {
            int countRijen = db.updateTaak(taak);

        } else {
            long rijId = db.voegTaakToe(taak);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            //verberg het soft keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            bewaarInDB();
            return false;
        }
        return false;
    }
}
