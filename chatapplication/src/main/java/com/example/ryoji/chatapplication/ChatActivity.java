package com.example.ryoji.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static android.view.View.OnClickListener;
import static android.view.animation.Animation.AnimationListener;


public class ChatActivity extends ActionBarActivity implements OnClickListener{

    private EditText mInputMessages;
    private Button mSendMessages;
    private LinearLayout mMessagelog;
    private TextView mCpuMessages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //XMLのViewを取得
        mInputMessages = (EditText)findViewById(R.id.input_messages);
        mSendMessages = (Button)findViewById(R.id.send_messages);
        mMessagelog = (LinearLayout)findViewById(R.id.message_log);

        mCpuMessages = (TextView)findViewById(R.id.cpu_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mSendMessages.setOnClickListener(this);
        setSupportActionBar(toolbar);



        FloatingActionButton actionSpeak = (FloatingActionButton) findViewById(R.id.action_voice_input);
        actionSpeak.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, 0);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(mSendMessages)) {
            send();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);

        if(requestCode==0 && resultCode == RESULT_OK && data.hasExtra(RecognizerIntent.
                EXTRA_RESULTS)){
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(results.size() > 0){
                mInputMessages.setText(results.get(0));
                send();
            }
        }
    }

    private void send(){

        //送られた処理
        String inputText = mInputMessages.getText().toString();
        TextView userMessages = new TextView(this);
        userMessages.setBackgroundResource(R.drawable.user_messages);
        userMessages.setText(inputText);
        LinearLayout.LayoutParams userMessageLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        userMessageLayout.gravity = Gravity.END;
        final int marginSize = getResources().getDimensionPixelSize(R.dimen.message_margin);
        final int messageColor = getResources().getColor(R.color.message_color);

        userMessages.setTextColor(messageColor);

        userMessageLayout.setMargins(0,marginSize,0,marginSize);

        mMessagelog.addView(userMessages, 0, userMessageLayout);
        String answer;
        if(inputText.contains("hello") || inputText.contains("hi")){
            answer = "hello! Im Sakanaberu!";
        } else if(inputText.contains("how are you")) {
            answer = "Im always very good because Im technology！";
        } else if(inputText.contains("fortune")) {
            double random = (Math.random()*5d);
            if(random < 1d){
                answer = "You will be cursed";
            } else if(random < 2d){
                answer = "not good...";
            } else if(random < 3d){
                answer = "soso!";
            } else if(random < 4d){
                answer = "Good!";
            } else{
                answer = "You must have a great day bro!";
            }

        } else if(inputText.contains("time")) {
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR);
            int minute = cal.get(Calendar.MINUTE);
            int sec = cal.get(Calendar.SECOND);

            answer = String.format("Time is %1$d:%2$d.%3$d.",hour,minute,sec);

        } else{
            answer = "Im sorry, I can't Understand what you said ";
        }

        final TextView CpuMessages = new TextView(this);
        CpuMessages.setBackgroundResource(R.drawable.cpu_messages);
        CpuMessages.setText(answer);
        CpuMessages.setTextColor(messageColor);
        final LinearLayout.LayoutParams cpuMessageLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cpuMessageLayout.gravity = Gravity.START;
        cpuMessageLayout.setMargins(marginSize,marginSize,marginSize,marginSize);


        mInputMessages.setText("");

        TranslateAnimation userMessageAnimation = new TranslateAnimation(mMessagelog.getWidth(),
                0,0,0);
        userMessageAnimation.setDuration(1000);
        userMessageAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mMessagelog.addView(CpuMessages, 0, cpuMessageLayout);
                TranslateAnimation cpuAnimation = new TranslateAnimation(-mMessagelog.getWidth()
                        ,0,0,0);
                cpuAnimation.setDuration(1000);
                CpuMessages.setAnimation(cpuAnimation);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        userMessages.startAnimation(userMessageAnimation);

        //小さなログを表示
        Toast.makeText(this,"Sent...",Toast.LENGTH_SHORT).show();
    }
}
