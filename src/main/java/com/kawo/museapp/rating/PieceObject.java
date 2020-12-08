package com.kawo.museapp.rating;

import android.database.Cursor;
import android.media.Image;

public class PieceObject {

    int image;
    String title;
    String composer;
    String period;
    String key;
    String type;
    String opus;
    String piece_id;
    String youtube_link;
    String piece_description;
    String piece_dynamics;


    public PieceObject(int image, String title, String composer, String period, String key, String type, String opus, String piece_id, String youtube_link, String piece_description, String piece_dynamics) {

        this.image = image;
        this.title = title;
        this.composer = composer;
        this.period = period;
        this.key = key;
        this.type = type;
        this.opus = opus;
        this.piece_id = piece_id;
        this.youtube_link = youtube_link;
        this.piece_description = piece_description;
        this.piece_dynamics = piece_dynamics;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String pediod) {
        this.period = pediod;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpus() {
        return opus;
    }

    public void setOpus(String opus) {
        this.opus = opus;
    }

    public String getPiece_id() {
        return piece_id;
    }

    public void setPiece_id(String piece_id) {
        this.piece_id = piece_id;
    }

    public String getYoutube_link() {
        return youtube_link;
    }

    public void setYoutube_link(String youtube_link) {
        this.youtube_link = youtube_link;
    }

    public String getPiece_description() {
        return piece_description;
    }

    public void setPiece_description(String piece_description) {
        this.piece_description = piece_description;
    }

    public String getPiece_dynamics() {
        return piece_dynamics;
    }

    public void setPiece_dynamics(String piece_dynamics) {
        this.piece_dynamics = piece_dynamics;
    }
}
