package com.amrute_studio.game_multiplayer;

public class playerData {
    String name;
    String initials;
    String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public playerData(String name) {
        this.name = name;
        String arr[] = name.split(" ");
        for (String a: arr
             ) {
            this.initials = a.charAt(0) + "";
        }
        initials = initials.toUpperCase();



    }

}
