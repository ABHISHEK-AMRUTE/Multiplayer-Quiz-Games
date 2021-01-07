package com.amrute_studio.game_multiplayer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class profile_fragment extends Fragment {


    public profile_fragment() {
        // Required empty public constructor
    }

    EditText roomnameJoin,roomnameCreate;
    TextView members;
    TabLayout tabLayout;
    Home baseActivity;
    RecyclerView.LayoutManager layoutManager;
    LinearLayout createLinearlayout,joinLinearLayout;
    Button join_room, host_room;
    ArrayList<playerData> list;
    RecyclerView recyclerView;
    playerNameAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        roomnameJoin = getView().findViewById(R.id.roomnameJoin);
        roomnameCreate = getView().findViewById(R.id.roomnameCreate);
        members =  getView().findViewById(R.id.members);

        join_room= getView().findViewById(R.id.join_room);
        host_room = getView().findViewById(R.id.button5);

        createLinearlayout = getView().findViewById(R.id.createRoomLinearLayout);
        joinLinearLayout = getView().findViewById(R.id.joinRoomLinearLayout);

        list = new ArrayList<playerData>();
        recyclerView = getView().findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new playerNameAdapter(list);
        recyclerView.setAdapter(adapter);

        tabLayout = getView().findViewById(R.id.tabCard);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabsele = tab.getPosition();
                switch (tabsele)
                {
                    case 0 :
                        createLinearlayout.setVisibility(View.VISIBLE);
                        joinLinearLayout.setVisibility(View.GONE);
                            break;

                    case 1:
                        createLinearlayout.setVisibility(View.GONE);
                        joinLinearLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        baseActivity = (Home) getActivity();
        roomnameJoin.setText(baseActivity.local_databse.getName());
        roomnameCreate.setText(baseActivity.local_databse.getName());

        roomnameJoin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                baseActivity.isocket.roomNameOfCurrentRoom =  roomnameJoin.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        roomnameCreate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                baseActivity.isocket.roomNameOfCurrentRoom =  roomnameCreate.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        join_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.isocket.is_joined = true;
                baseActivity.isocket.is_admin = false;
                baseActivity.isocket.connect_to_room(roomnameJoin.getText().toString(),baseActivity.local_databse.getName(),0);
                roomnameCreate.setText(roomnameJoin.getText().toString());
            }
        });

        host_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.isocket.is_admin = true;
                baseActivity.isocket.is_joined = false;
                baseActivity.isocket.connect_to_room(roomnameCreate.getText().toString(),baseActivity.local_databse.getName(),1);
                baseActivity.isocket.member_array.add("sample");
                roomnameJoin.setText(roomnameCreate.getText().toString());
            }
        });

        getView().findViewById(R.id.btncreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLinearlayout.setVisibility(View.VISIBLE);
                joinLinearLayout.setVisibility(View.GONE);

            }
        });

        getView().findViewById(R.id.btnjoin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLinearlayout.setVisibility(View.GONE);
                joinLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        if(baseActivity.isocket.is_admin)
        {
            createLinearlayout.setVisibility(View.VISIBLE);
            joinLinearLayout.setVisibility(View.GONE);
            baseActivity.displayMembersName();
            roomnameCreate.setText(baseActivity.isocket.roomNameOfCurrentRoom);
        }


        if(baseActivity.isocket.is_joined)
        {
            createLinearlayout.setVisibility(View.GONE);
            joinLinearLayout.setVisibility(View.VISIBLE);
            baseActivity.displayMembersName();
            roomnameJoin.setText(baseActivity.isocket.roomNameOfCurrentRoom);
        }

    }

    public void additem(String temp)
    {
        list.add(new playerData(temp));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fragment, container, false);
    }
}