package com.letv.launcher;

public class ScreenInfo {

    public int id;
    public String tag;
    public String name;

    public ScreenInfo(int id, String n) {
        this.id = id;
        this.name = n;
    }

    @Override
    public String toString() {
        return name;
    }
}
