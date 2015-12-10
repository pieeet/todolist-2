package com.rocdev.android.takenlijst;

/**
 * Created by piet on 23-09-15.
 */
public class Lijst {
    private int id;
    private String naam;

    public Lijst() {}

    public Lijst(String naam) {
        this.naam = naam;
    }

    public Lijst(int id, String naam) {
        this.id = id;
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return naam;
    }
}
