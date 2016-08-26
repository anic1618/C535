package com.example.ac.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER = "com.example.ac.myapplication.answer";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.ac.myapplication.answer_is_shown";
    private static final String TAG = "CheatActivity";
    private TextView mAnswerTextView;
    private Button mButton;
    private String mAnswer[];
    private boolean mIsAnswerShown;
    private String answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"in onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswer= getIntent().getStringExtra(EXTRA_ANSWER).split(" ");
        mAnswerTextView = (TextView) findViewById(R.id.answer_text);
        mButton = (Button) findViewById(R.id.show_answer_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mIsAnswerShown = true;
                if(mAnswer[1].equals("true")) {
                    mAnswerTextView.setText((answer = String.format("%s is prime number" ,mAnswer[0])));
                }
                else {
                    mAnswerTextView.setText((answer = String.format("%s is composite number" ,mAnswer[0])));
                }
                mButton.setClickable(false);
                setAnswerShownResult(true);

            }

        });

        if( (savedInstanceState!= null) && (savedInstanceState.getBoolean("IsAnswerShown",false))){
            Log.d(TAG, "onCreate: isAnswerShown");
            mButton.callOnClick();
            mButton.setClickable(false);

        }
    }
    public static Intent newIntent(Context packageContext, String mAnswer) {
        Log.d(TAG,"in newIntent");
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER, mAnswer);
        return i;
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Log.d(TAG,"in setAnswerShownResult");
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        Log.d(TAG, "setAnswerShownResult: "+data.getBooleanExtra(EXTRA_ANSWER_SHOWN,false));
        setResult(RESULT_OK, data);
    }
    public static boolean wasAnswerShown(Intent result) {
        Log.d(TAG,"in wasAnswerShown");
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState);
        outState.putBoolean("IsAnswerShown",mIsAnswerShown);
        outState.putString("answer",answer);

    }
}
