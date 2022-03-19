package com.example.tututesttask.data;

import java.util.ArrayList;
import java.util.List;

public class Weather {

    private int id;
    private String main;
    private String descriptions;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Weather(int id, String main, String descriptions, String icon) {
        this.id = id;
        this.main = main;
        this.descriptions = descriptions;
        this.icon = icon;
    }
}

