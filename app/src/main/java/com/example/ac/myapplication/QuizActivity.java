package com.example.ac.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mHintButton;
    private Button mCheatButton;
    private TextView mQuestionText;
    private Questioner mQuestioner;
    private boolean mIsAnswered;
    //private boolean mIsHintTaken;
    private boolean mIsCheated;
    private String mAnswer[];
    private static final int REQUEST_CODE_CHEAT = 0;

    private static final String TAG = "QuizActivity";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Inside onSaveInstance");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsCheated = mIsAnswered= false;
        mQuestionText = (TextView)findViewById(R.id.question_text);
        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mNextButton = (Button)findViewById(R.id.next_button);
        mHintButton = (Button)findViewById(R.id.hint_button);
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mQuestioner = new Questioner();
        if(savedInstanceState != null) {
            mAnswer  = savedInstanceState.getString("Q/A").split(" ");
            mIsCheated = savedInstanceState.getBoolean("isCheated");
            mQuestionText.setText(String.format("Is %s prime number?",mAnswer[0]));
        }

        else {
            updateQuestion();
        }



        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer("true");
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 checkAnswer("false");
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((!mIsCheated && !mIsAnswered)){
                    Log.d(TAG, "onClick: !mIsCheated && !mIsAnswered");
                    Toast.makeText(QuizActivity.this,"please answer first then press next",Toast.LENGTH_SHORT).show();
                }
                else {
                    updateQuestion();

                }

            }
        });

        mHintButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizActivity.this,"prime number cannot be factorised as product of two number",Toast.LENGTH_SHORT).show();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = CheatActivity.newIntent(QuizActivity.this,mAnswer[0]+" "+mAnswer[1]);
                //startActivity(i);
                startActivityForResult(i,REQUEST_CODE_CHEAT);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Inside OnSTop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Inside OnDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Inside OnPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Inside OnREsume");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Log.d(TAG, "Inside OnContentChanged");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "Inside OnSaveInstance");
        outState.putString("Q/A", mAnswer[0]+" "+mAnswer[1]);
        outState.putBoolean("isCheated",mIsCheated);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Inside OnStart");
    }

    public void checkAnswer(String answer){
        if(mIsAnswered){
            Log.d(TAG, "checkAnswer: already answered");
            Toast.makeText(QuizActivity.this,R.string.already_answered_toast,Toast.LENGTH_SHORT).show();
            return;
        }
        if(mIsCheated){
            Toast.makeText(QuizActivity.this,R.string.cheated_toast,Toast.LENGTH_SHORT).show();
            return;
        }


        mIsAnswered = true;
        //Log.d(TAG,"answer is "+mAnswer[1]+"---"+answer);
        if(mAnswer[1].equals(answer)) {
            Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    public void updateQuestion() {
        mAnswer = (mQuestioner.getQuestion()).split(" ");
        mIsCheated = false;
        mQuestionText.setText(String.format("Is %s prime number?",mAnswer[0]));
        mIsAnswered = false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"in onActivityResult");
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            Log.d(TAG,"got REQUEST_CODE_CHEAT");
            mIsCheated = CheatActivity.wasAnswerShown(data);

            Log.d(TAG, "onActivityResult: "+mIsCheated);
        }
    }

}
