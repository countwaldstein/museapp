package com.kawo.museapp.firstpath;

import android.provider.BaseColumns;

public class PiecesObj {

    private int id;
    private String piece_name;
    private String piece_composer;
    private String piece_period;
    private String piece_key;
    private String piece_type;
    private String piece_opus;
    //constructors


    public PiecesObj(int id, String piece_composer, String piece_period, String piece_key,String piece_type,String piece_opus) {
        this.id = id;
        this.piece_composer = piece_composer;
        this.piece_period = piece_period;
        this.piece_key = piece_key;
        this.piece_type = piece_type;
        this.piece_opus = piece_opus;
    }

    public static final class Pieces implements BaseColumns {
        public static final String TABLE_NAME = "PIECES";
        public static final String COLUMN_NAME = "PIECE_COMPOSERS";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
    public PiecesObj() {
    }

    //getters and setters


    public int getId() {
        return id;
    }
    public String getPiece_name() {
        return piece_name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPiece_name(String piece_name) {
        this.piece_name = piece_name;
    }





}
