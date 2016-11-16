package ru.spbau.mit.Server;


public class Talker {
    private String name;
    public final int id;
    Talker(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
