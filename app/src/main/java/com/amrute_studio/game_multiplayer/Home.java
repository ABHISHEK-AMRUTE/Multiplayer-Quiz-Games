package com.amrute_studio.game_multiplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.nkzawa.emitter.Emitter;

import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener{


    Home_fragment objectHomeFragment;
    profile_fragment objectJoinRooms;

    shared_prefference local_databse;
    socket_connections isocket;
    Socket socket;


    private TabLayout tabLayout;


    private ViewPager viewPager;

//    BottomAppBar bottomAppBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
          getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//        //make fully Android Transparent Status bar
//        if (Build.VERSION.SDK_INT >= 21) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        isocket = (socket_connections)getApplicationContext();
        socket = isocket.getSocketInstance();

        init();
        listners();






    }

//    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
//        Window win = activity.getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }

    private void init() {
//        bottomAppBar =findViewById(R.id.bottomAppBar);




        local_databse = new shared_prefference(this);

        isocket.member_array = new ArrayList<String>();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Rooms"));
        tabLayout.addTab(tabLayout.newTab().setText("Games"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        pagerAdapter adapter = new pagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        objectHomeFragment = adapter.objectHomeFragment;
        objectJoinRooms = adapter.objectJoinRooms;
//        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,objectJoinRooms).commit();



    }

    private void listners() {


                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        TabLayout.Tab obj =  tabLayout.getTabAt(position);
                        tabLayout.selectTab(obj);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });


//        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//                switch(id)
//                {
//                    case R.id.profile:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,objectJoinRooms).commit();
//                        break;
//                }
//
//                return false;
//            }
//        });
//
//        bottomAppBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(isocket, "clicked icon", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,objectHomeFragment).commit();
//
//            }
//        });



        socket.on("newbie", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                

                final String list[] = args[0].toString().split(",");

                isocket.member_array.clear();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String temp="";
                        for (String text:  list
                             ) {
                            if(!text.matches("")){
                            temp += text+"\n";
                                isocket.member_array.add(text);
                             objectJoinRooms.additem(text);}
                        }


                        objectJoinRooms.members.setText(temp);
                    }
                });

            }
        });

        socket.on("admin_here_me", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                   if( isocket.is_admin){
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           isocket.member_array.add(args[0].toString());

                           objectJoinRooms.members.setText(objectJoinRooms.members.getText()+"\n"+args[0].toString());

                           String text = "";
                           for (String te:  isocket.member_array
                                ) {
                                     text+=te+",";
                           }
                           socket.emit("new_mem_from_admin",objectJoinRooms.roomnameCreate.getText().toString(),text);
                       }
                   });
                   }
            }
        });
    }


    public  void displayMembersName(){
        String temp="";
        for (String text:  isocket.member_array
        ) {
            if(!text.matches("")){
                temp += text+"\n";
               }
        }

        objectJoinRooms.members.setText(temp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isocket.disconnect_room();
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}