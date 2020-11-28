package com.amrute_studio.game_multiplayer;

import android.app.Application;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class socket_connections extends Application {
    private Socket iSocket;

    Boolean is_admin = false,is_joined = false;
    ArrayList<String> member_array;
    String roomNameOfCurrentRoom="";


  private static final String URL ="https://game-server-abhi-am.herokuapp.com/";
//    private static final String URL ="http://192.168.43.206:5000/";
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            IO.Options opts = new IO.Options();

            iSocket = IO.socket(URL, opts);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        member_array = new ArrayList<String>();
    }
    public com.github.nkzawa.socketio.client.Socket getSocketInstance(){

        return iSocket;
    }

    public void connect_to_room(String room_name,String name,int code){
        iSocket.connect();
        iSocket.emit("joinroom",room_name,name,code);
    }

//    @Override
//    public void onTerminate() {
//        disconnect_room();
//        super.onTerminate();
//
//    }

    public void disconnect_room() {
        iSocket.disconnect();
    }
}