package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.Color;

import java.awt.*;

public class Appearance {

    private int bodyIndex;
    private String bodyColor;

    private int eyesIndex;
    private String eyesColor;

    private int hairIndex;
    private String hairColor;

    private Integer beardIndex = null;
    private String beardColor;

    public int getBodyIndex() {
        return bodyIndex;
    }

    public int getEyesIndex() {
        return eyesIndex;
    }

    public int getHairIndex() {
        return hairIndex;
    }

    public String getBodyColor() {
        return bodyColor;
    }

    public String getEyesColor() {
        return eyesColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public Integer getBeardIndex() {
        return beardIndex;
    }

    public String getBeardColor() {
        return beardColor;
    }

    public void setBodyIndex(int bodyIndex) {
        this.bodyIndex = bodyIndex;
    }

    public void setEyesIndex(int eyesIndex) {
        this.eyesIndex = eyesIndex;
    }

    public void setHairIndex(int hairIndex) {
        this.hairIndex = hairIndex;
    }

    public void setBodyColor(String bodyColor) {
        this.bodyColor = bodyColor;
    }

    public void setEyesColor(String eyesColor) {
        this.eyesColor = eyesColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public void setBeardIndex(int beardIndex) {
        this.beardIndex = beardIndex;
    }

    public void setBeardColor(String beardColor) {
        this.beardColor = beardColor;
    }
}
