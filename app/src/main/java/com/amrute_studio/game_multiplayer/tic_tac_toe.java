package com.amrute_studio.game_multiplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class tic_tac_toe extends AppCompatActivity {

    ImageView one,two,three,four,five,six,seven,eight,nine,refresh;
    int mat[][];
    Vibrator vibrator;
    MotionLayout motionLayout;

    Button join_room;

    EditText room_name;

    TextView members;

    String st_roomname="";

    int chance=0;
    private Socket socket;
    socket_connections isocket;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_tor);
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationbar_color));

        isocket = (socket_connections)getApplicationContext();
        socket = isocket.getSocketInstance();
        initialize_ui();
        listers();
        init();






    }

    private void init() {
        for(int x=0;x<3;x++)for(int y=0;y<3;y++)mat[x][y]=-1;
        one.setImageDrawable(getDrawable(R.drawable.ic_group_12));
        two.setImageDrawable(getDrawable(R.drawable.ic_group_12));
        three.setImageDrawable(getDrawable(R.drawable.ic_group_12));
        four.setImageDrawable(getDrawable(R.drawable.ic_group_12));
        five.setImageDrawable(getDrawable(R.drawable.ic_group_12));
        six.setImageDrawable(getDrawable(R.drawable.ic_group_12));
        seven.setImageDrawable(getDrawable(R.drawable.ic_group_12));
        eight.setImageDrawable(getDrawable(R.drawable.ic_group_12));
        nine.setImageDrawable(getDrawable(R.drawable.ic_group_12));

        motionLayout = findViewById(R.id.view_to_view);

    }

    private void listers() {

//       join_room.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               st_roomname = room_name.getText().toString();
//               socket.emit("joinroom",st_roomname);
//
//           }
//       });

        ////listen to the move
        socket.on("tic-tac-toe-move", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int pos = (int) args[0];
                        int value = (int) args[1];
                        int chancee = (int) args[2];
                        switch (pos)
                        {
                            case 1 :  mat[0][0]=value;
                                      if(chancee == 0)
                                      {
                                          one.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                                      }else
                                      {
                                          one.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                                      }
                                      break;
                            case 2 :  mat[0][1]=value;
                                      if(chancee == 0)
                                      {
                                          two.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                                      }else
                                      {
                                          two.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                                      }
                                      break;
                            case 3 :  mat[0][2]=value;
                                      if(chancee == 0)
                                      {
                                          three.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                                      }else
                                      {
                                          three.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                                      }
                                      break;
                            case 4 :  mat[1][0]=value;
                                      if(chancee == 0)
                                      {
                                          four.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                                      }else
                                      {
                                          four.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                                      }
                                      break;
                            case 5 :  mat[1][1]=value;
                                      if(chancee == 0)
                                      {
                                          five.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                                      }else
                                      {
                                          five.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                                      }
                                      break;
                            case 6 :  mat[1][2]=value;
                                      if(chancee == 0)
                                      {
                                          six.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                                      }else
                                      {
                                          six.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                                      }
                                      break;
                            case 7 :  mat[2][0]=value;
                                      if(chancee == 0)
                                      {
                                          seven.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                                      }else
                                      {
                                          seven.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                                      }
                                      break;
                            case 8 :  mat[2][1]=value;
                                      if(chancee == 0)
                                      {
                                          eight.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                                      }else
                                      {
                                          eight.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                                      }
                                      break;
                            case 9 :  mat[2][2]=value;
                                      if(chancee == 0)
                                      {
                                          nine.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                                      }else
                                      {
                                          nine.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                                      }
                                      break;
                        }
                    }
                });
            }
        });

    }

    private void initialize_ui() {

        //matrix
        mat = new int[3][3];

        ///image Views

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);

        //vibrator

        vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);

        //Buttons

//        join_room = findViewById(R.id.join);


        //Edit text

        room_name = findViewById(R.id.roomname);

        //Text views

        members = findViewById(R.id.members);

        //get room name from intent

        st_roomname = getIntent().getStringExtra("roomname");
    }

    public void tile_clicked(View view)
    {
        vibrator.vibrate(50);
        int position = 0;
        switch (view.getId())
        {
            case R.id.one :
                            if(mat[0][0]==-1){position = 1;
                socket.emit("turn",st_roomname,position,chance);
                if(chance%2==0)
                    {one.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                    mat[0][0] = 0;
                    socket.emit("tic-tac-toe-listen-to-move",1,0,0,st_roomname);
                    //one//value//chance
                    }
                else{
                    one.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                     mat[0][0] = 1;
                    socket.emit("tic-tac-toe-listen-to-move",1,1,1,st_roomname);
                }
                chance++;
                check_win();}
                      break;
            case R.id.two :if(mat[0][1]==-1){position = 2;
                socket.emit("turn",st_roomname,position,chance);
                if(chance%2==0)
                    {two.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                    mat[0][1] = 0;
                    socket.emit("tic-tac-toe-listen-to-move",2,0,0,st_roomname);}
                else{
                    two.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                     mat[0][1] = 1;
                        socket.emit("tic-tac-toe-listen-to-move",2,1,1,st_roomname);
                }
                chance++;
                check_win();}
                break;
            case R.id.three :if(mat[0][2]==-1){position = 3;
                socket.emit("turn",st_roomname,position,chance);
                if(chance%2==0)
                    {three.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                    mat[0][2] = 0;
                    socket.emit("tic-tac-toe-listen-to-move",3,0,0,st_roomname);}
                else{
                    three.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                     mat[0][2] = 1;
                        socket.emit("tic-tac-toe-listen-to-move",3,1,1,st_roomname);
                }
                chance++;
                check_win();}
                break;
            case R.id.four :if(mat[1][0]==-1){position = 4;
                socket.emit("turn",st_roomname,position,chance);
                if(chance%2==0)
                    {four.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                    mat[1][0] = 0;
                    socket.emit("tic-tac-toe-listen-to-move",4,0,0,st_roomname);}
                else{
                    four.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                     mat[1][0] = 1;
                        socket.emit("tic-tac-toe-listen-to-move",4,1,1,st_roomname);
                }
                chance++;
                check_win();}
                break;
            case R.id.five :if(mat[1][1]==-1){position = 5;
                socket.emit("turn",st_roomname,position,chance);
                if(chance%2==0)
                    {five.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                    mat[1][1] = 0;
                    socket.emit("tic-tac-toe-listen-to-move",5,0,0,st_roomname);}
                else{
                    five.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                     mat[1][1] = 1;
                        socket.emit("tic-tac-toe-listen-to-move",5,1,1,st_roomname);
                }
                chance++;
                check_win();}
                break;
            case R.id.six :if(mat[1][2]==-1){position = 6;
                socket.emit("turn",st_roomname,position,chance);
                if(chance%2==0)
                    {six.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                    mat[1][2] = 0;
                    socket.emit("tic-tac-toe-listen-to-move",6,0,0,st_roomname);}
                else{
                    six.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                     mat[1][2] = 1;
                        socket.emit("tic-tac-toe-listen-to-move",6,1,1,st_roomname);
                }
                chance++;
                check_win();}
                break;
            case R.id.seven :if(mat[2][0]==-1){position = 7;
                socket.emit("turn",st_roomname,position,chance);
                if(chance%2==0)
                    {seven.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                    mat[2][0] = 0;
                    socket.emit("tic-tac-toe-listen-to-move",7,0,0,st_roomname);}
                else{
                    seven.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                     mat[2][0] = 1;
                        socket.emit("tic-tac-toe-listen-to-move",7,1,1,st_roomname);
                }
                chance++;
                check_win();}
                break;
            case R.id.eight :if(mat[2][1]==-1){position = 8;
                socket.emit("turn",st_roomname,position,chance);
                if(chance%2==0)
                    {eight.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                    mat[2][1] = 0;
                    socket.emit("tic-tac-toe-listen-to-move",8,0,0,st_roomname);}
                else{
                    eight.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                     mat[2][1] = 1;
                        socket.emit("tic-tac-toe-listen-to-move",8,1,1,st_roomname);
                }
                chance++;
                check_win();}
                break;
            case R.id.nine :
                if(mat[2][2]==-1){position = 9;
                socket.emit("turn",st_roomname,position,chance);
                if(chance%2==0)
                    {nine.setImageDrawable(getDrawable(R.drawable.ic_group_8));
                    mat[2][2] = 0;
                    socket.emit("tic-tac-toe-listen-to-move",9,0,0,st_roomname);}
                else{
                    nine.setImageDrawable(getDrawable(R.drawable.ic_group_9));
                     mat[2][2] = 1;
                        socket.emit("tic-tac-toe-listen-to-move",9,1,1,st_roomname);
                }
                chance++;
                }check_win();
                break;

        }

    }

    private void updateui(int chan) {

    }

    private void check_win() {


        if(mat[0][0]==1&&mat[0][1]==1&&mat[0][2]==1)
        {
            one.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            two.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            three.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            motionLayout.transitionToEnd();

        }else if(mat[1][0]==1&&mat[1][1]==1&&mat[1][2]==1)
        {
            four.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            five.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            six.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            motionLayout.transitionToEnd();
        }else if(mat[2][0]==1&&mat[2][1]==1&&mat[2][2]==1)
        {
            seven.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            eight.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            nine.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            motionLayout.transitionToEnd();

        }else if(mat[0][0]==1&&mat[1][0]==1&&mat[2][0]==1)
        {
            one.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            four.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            seven.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            motionLayout.transitionToEnd();
        }else if(mat[0][1]==1&&mat[1][1]==1&&mat[2][1]==1)
        {
            two.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            five.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            eight.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            motionLayout.transitionToEnd();
        }else if(mat[0][2]==1&&mat[1][2]==1&&mat[2][2]==1)
        {
            three.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            six.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            nine.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            motionLayout.transitionToEnd();
        }else if(mat[0][0]==1&&mat[1][1]==1&&mat[2][2]==1)
        {
            one.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            five.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            nine.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            motionLayout.transitionToEnd();
        }else if(mat[2][0]==1&&mat[1][1]==1&&mat[0][2]==1)
        {
            three.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            five.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            seven.setImageDrawable(getDrawable(R.drawable.ic_group_10));
            motionLayout.transitionToEnd();
        }else


              if(mat[0][0]==0&&mat[0][1]==0&&mat[0][2]==0)
        {
            one.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            two.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            three.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            motionLayout.transitionToEnd();

        }else if(mat[1][0]==0&&mat[1][1]==0&&mat[1][2]==0)
        {
            four.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            five.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            six.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            motionLayout.transitionToEnd();
        }else if(mat[2][0]==0&&mat[2][1]==0&&mat[2][2]==0)
        {
            seven.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            eight.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            nine.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            motionLayout.transitionToEnd();

        }else if(mat[0][0]==0&&mat[1][0]==0&&mat[2][0]==0)
        {
            one.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            four.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            seven.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            motionLayout.transitionToEnd();
        }else if(mat[0][1]==0&&mat[1][1]==0&&mat[2][1]==0)
        {
            two.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            five.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            eight.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            motionLayout.transitionToEnd();
        }else if(mat[0][2]==0&&mat[1][2]==0&&mat[2][2]==0)
        {
            three.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            six.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            nine.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            motionLayout.transitionToEnd();
        }else if(mat[0][0]==0&&mat[1][1]==0&&mat[2][2]==0)
        {
            one.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            five.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            nine.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            motionLayout.transitionToEnd();
        }else if(mat[2][0]==0&&mat[1][1]==0&&mat[0][2]==0)
        {
            three.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            five.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            seven.setImageDrawable(getDrawable(R.drawable.ic_group_11));
            motionLayout.transitionToEnd();
        }

    }

}