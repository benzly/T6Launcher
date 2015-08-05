package com.letv.launcher;

public class ScreenInfo {

    public int id;
    public String tag;
    public String name;
    public LayoutType type;

    public ScreenInfo(int id, String n, LayoutType type) {
        this.id = id;
        this.name = n;
        this.type = type;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        // return super.toString();
        return name;
    }
}
