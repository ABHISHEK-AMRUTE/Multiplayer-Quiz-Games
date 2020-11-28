package com.amrute_studio.game_multiplayer;

import android.content.Context;
import android.content.SharedPreferences;

public class shared_prefference {
    String name;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;

    public shared_prefference(Context context) {

        sharedPreferences =  context.getSharedPreferences("file_info",Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();

    }

    public String getName() {
        name = sharedPreferences.getString("name","guest_user");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        edit.putString("name",name);
        edit.apply();
        edit.commit();
    }
}
