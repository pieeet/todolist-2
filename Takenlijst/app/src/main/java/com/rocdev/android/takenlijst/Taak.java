package com.rocdev.android.takenlijst;

/**
 * Created by piet on 23-09-15.
 */
public class Taak {
    private int taakId;
    private int lijstId;
    private String naam;
    private String notitie;
    private long datumMillisVoltooid;
    private int verborgen;

    public static final int TRUE = 1;
    public static final int False = 0;

    public Taak() {
        naam = "";
        notitie = "";
        datumMillisVoltooid = 0L;
        verborgen = 0;
    }

    public Taak(int lijstId, String naam, String notitie, long datumMillisVoltooid, int verborgen) {
        this.lijstId = lijstId;
        this.naam = naam;
        this.notitie = notitie;
        this.datumMillisVoltooid = datumMillisVoltooid;
        this.verborgen = verborgen;
    }

    public Taak(int taakId, int lijstId, String naam, String notitie, long datumMillisVoltooid,
                int verborgen) {
        this.taakId = taakId;
        this.lijstId = lijstId;
        this.naam = naam;
        this.notitie = notitie;
        this.datumMillisVoltooid = datumMillisVoltooid;
        this.verborgen = verborgen;
    }

    public int getTaakId() {
        return taakId;
    }

    public void setTaakId(int taakId) {
        this.taakId = taakId;
    }

    public int getLijstId() {
        return lijstId;
    }

    public void setLijstId(int lijstId) {
        this.lijstId = lijstId;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNotitie() {
        return notitie;
    }

    public void setNotitie(String notitie) {
        this.notitie = notitie;
    }

    public long getDatumMillisVoltooid() {
        return datumMillisVoltooid;
    }

    public void setDatumMillisVoltooid(long datumMillisVoltooid) {
        this.datumMillisVoltooid = datumMillisVoltooid;
    }

    public int getVerborgen() {
        return verborgen;
    }

    public void setVerborgen(int verborgen) {
        this.verborgen = verborgen;
    }
}
