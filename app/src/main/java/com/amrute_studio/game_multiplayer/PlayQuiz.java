package com.amrute_studio.game_multiplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PlayQuiz extends AppCompatActivity {

    Boolean is_admin = false;
    ArrayList<String> member_array,question_array,optiona_array,optionb_array,optionc_array,optiond_array,correct_ans;
    String roomname;
    HashMap<String,Integer> score;
    String url ;

    CardView one,two,three,four;
    TextView score_list,question,optiona,optionb,optionc,optiond,time_field,numberOfQuestion, scoreTextField;

    socket_connections isocket;
    Socket socket;
    CountDownTimer countDownTimer;

    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;
    View promptsView;LayoutInflater li;

    Boolean is_my_chance = false;
    int chance_member = 0;
    int time= 120; /// in seconds;
    int counter;
    int question_number =0 ;
    String myname;


    int localScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_quiz_layout);

        isocket = (socket_connections)getApplicationContext();
        socket = isocket.getSocketInstance();

        init();
        listners();

        show_dialog();

    }

    private void show_dialog() {
        alertDialog.show();
       if(is_admin==true)
       {



           Button fetch = promptsView.findViewById(R.id.fetch);
           Button start = promptsView.findViewById(R.id.startGame);
           promptsView.findViewById(R.id.nonAdmin).setVisibility(View.GONE);

           fetch.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   fetch_question();
               }
           });

           start.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  socket.emit("quiz-event",roomname,2);
               }
           });

       }
       else
       {
           promptsView.findViewById(R.id.admin).setVisibility(View.GONE);
       }




    }

    private void init() {

        score_list = findViewById(R.id.score);

        question = findViewById(R.id.question);
        optiona = findViewById(R.id.option_1);
        optionb = findViewById(R.id.option_2);
        optionc = findViewById(R.id.option_3);
        optiond = findViewById(R.id.option_4);
        time_field = findViewById(R.id.timer);
        numberOfQuestion = findViewById(R.id.numnerOfQuestion);
        scoreTextField = findViewById(R.id.scoreTextField);

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);

        ///get from intents
        member_array = getIntent().getStringArrayListExtra("members");
        roomname = getIntent().getStringExtra("roomname");
        url = getIntent().getStringExtra("url");
        time = getIntent().getIntExtra("time",120);
        is_admin = getIntent().getBooleanExtra("is_admin",false);
        myname = getIntent().getStringExtra("myname");

        ///pre-processing
        score = new HashMap<>();
        question_array = new ArrayList<>();
        optiona_array = new ArrayList<>();
        optionb_array = new ArrayList<>();
        optionc_array = new ArrayList<>();
        optiond_array = new ArrayList<>();
        correct_ans  = new ArrayList<>();

        for (String temp: member_array
        ) {
            score.put(temp,0);
        }
        update_leader_board();



        ////building dialog view
        li = LayoutInflater.from(this);
        promptsView = li.inflate(R.layout.start_quiz_dialog_box, null);
        alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder
                .setView(promptsView)
                .setCancelable(false)
        ;

        alertDialog = alertDialogBuilder.create();

        countDownTimer = new CountDownTimer(time*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                if(counter==30)time_field.setTextColor(Color.parseColor("#FF0505"));
                if(counter==10)time_field.setTextColor(Color.RED);
                time_field.setText(""+counter);
                counter--;
            }

            @Override
            public void onFinish() {

                       check_answer(5);
                       serve_question();
            }
        };
    }


    private void listners() {

        socket.on("quiz-event-callback", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                 final int caseCode = (int)args[0];
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         switch (caseCode)
                         {
                             case 1 :  /////////admin fetching question/////////
                                 break;
                             case 2 : //////// Host started Quiz game//////////
                                 serve_question();
                                 alertDialog.dismiss();
                                 break;
                         }
                     }
                 });
            }
        });
        socket.on("take-quiz-question", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        JSONObject obj = null;
                        try {
                            obj = new JSONObject((String) args[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONArray optio;
                         try {
                            JSONArray array = obj.getJSONArray("results");
                            optiond.setText(array.length() + "\n"+array.toString());
                           for(int x=0;x<array.length();x++){

                               obj = array.getJSONObject(x);
                              question_array.add(obj.getString("question"));
//                              question.append(obj.getString("question")+"\n");
                              correct_ans.add(obj.getString("correct_answer"));
                              optio =  obj.getJSONArray("incorrect_answers");

                               Random number = new Random();
                               int n = number.nextInt()%3;
                               if(n<0)n=n*-1;

                               optiona_array.add(obj.getString("correct_answer"));n++;

                               optionb_array.add(optio.getString(n%3));n++;

                               optionc_array.add(optio.getString(n%3));n++;

                               optiond_array.add(optio.getString(n%3));n++;


                           }
                             promptsView.findViewById(R.id.progressBar2).setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });


        socket.on("listen-answer", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        update_score(args[0].toString(),Integer.parseInt(args[1].toString()));
                        if(question_number>question_array.size()-1){
                            LayoutInflater li = LayoutInflater.from(getBaseContext());
                            View promptsView = li.inflate(R.layout.score_board_layout, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    PlayQuiz.this);
                            alertDialogBuilder
                                    .setView(promptsView)

                                    .setTitle("Scores")
                                    .setCancelable(false)
                                    .setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(PlayQuiz.this,Home.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                            startActivity(i);
                                            finish();

                                        }
                                    });
                            ;

                            final AlertDialog alertDialog1 = alertDialogBuilder.create();
                            alertDialog1.show();
                            TextView finalScores = promptsView.findViewById(R.id.scores);
                            finalScores.setText(score_list.getText());

                        }
                    }
                });
            }
        });
        //////answer listner
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_answer(1);


            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_answer(2);

            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_answer(3);

            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_answer(4);

            }
        });


        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serve_question();
            }
        });

    }



    private void update_leader_board()
    {
        String text = "Final scores are: \n";
        for (String temp: score.keySet()
             ) {
            text += temp + ":   "+ score.get(temp) +"\n";
        }
        score_list.setText(text);

    }

    private void fetch_question()
    {
       if(is_admin)
       {
           promptsView.findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
           RequestQueue queue  = Volley.newRequestQueue(getApplicationContext());
           StringRequest st = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   socket.emit("distribute-quiz-question",response,roomname);
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   Toast.makeText(PlayQuiz.this, "error", Toast.LENGTH_SHORT).show();
               }
           });
           queue.add(st);
       }
    }

    private void update_score(String name , int sco)
    {
        score.put(name,score.get(name)+sco);
        update_leader_board();
    }

   private void serve_question(){

        time_field.setTextColor(Color.BLACK);
        one.setCardBackgroundColor(getResources().getColor(R.color.option_background));
        two.setCardBackgroundColor(getResources().getColor(R.color.option_background));
        three.setCardBackgroundColor(getResources().getColor(R.color.option_background));
        four.setCardBackgroundColor(getResources().getColor(R.color.option_background));
        one.setEnabled(true);
        two.setEnabled(true);
        three.setEnabled(true);
        four.setEnabled(true);
       numberOfQuestion.setText(question_number + "/" + question_array.size());
       if(question_number<question_array.size())
       {
           countDownTimer.cancel();
           question.setText(Html.fromHtml(question_array.get(question_number)));
           optiona.setText(Html.fromHtml(optiona_array.get(question_number)));
           optionb.setText(Html.fromHtml(optionb_array.get(question_number)));
           optionc.setText(Html.fromHtml(optionc_array.get(question_number)));
           optiond.setText(Html.fromHtml(optiond_array.get(question_number)));

           counter = time;
           countDownTimer.start();

       }
   }

   private  void check_answer(int option){
        switch (option)
        {
            case 1:
                    if(correct_ans.get(question_number).matches(optiona_array.get(question_number))){
                        socket.emit("here-quiz-answer",roomname,myname,10);
                        localScore +=10;
                        one.setCardBackgroundColor(getResources().getColor(R.color.touch_correct_option));
                    }else
                    {
                           one.setCardBackgroundColor(getResources().getColor(R.color.incorrect_answer));
                    }
                   break;
            case 2:
                    if(correct_ans.get(question_number).matches(optionb_array.get(question_number))){
                        socket.emit("here-quiz-answer",roomname,myname,10);
                        localScore +=10;
                        two.setCardBackgroundColor(getResources().getColor(R.color.touch_correct_option));
                    }else
                    {
                        two.setCardBackgroundColor(getResources().getColor(R.color.incorrect_answer));
                    }
                   break;
            case 3:
                    if(correct_ans.get(question_number).matches(optionc_array.get(question_number))){
                        socket.emit("here-quiz-answer",roomname,myname,10);
                        localScore +=10;
                        three.setCardBackgroundColor(getResources().getColor(R.color.touch_correct_option));
                    }else
                    {
                        three.setCardBackgroundColor(getResources().getColor(R.color.incorrect_answer));
                    }
                   break;
            case 4:
                    if(correct_ans.get(question_number).matches(optiond_array.get(question_number))){
                        socket.emit("here-quiz-answer",roomname,myname,10);
                        localScore +=10;
                        four.setCardBackgroundColor(getResources().getColor(R.color.touch_correct_option));
                    }else
                    {
                        four.setCardBackgroundColor(getResources().getColor(R.color.incorrect_answer));
                    }
                   break;

            default:
                Toast.makeText(isocket, "Time Up! Serving next question", Toast.LENGTH_SHORT).show();
                break;
        }
        one.setEnabled(false);
        two.setEnabled(false);
        three.setEnabled(false);
        four.setEnabled(false);
        scoreTextField.setText(localScore+"");
        question_number++;


   }

    @Override
    protected void onPause() {
        super.onPause();
        alertDialog.dismiss();
    }
}