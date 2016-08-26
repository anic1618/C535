package com.example.ac.myapplication;

import android.util.Log;

import java.util.Random;

/**
 * Created by AC on 8/24/2016.
 */
public class Questioner {
    private boolean isNotPrime[];
    private Random random;
    Questioner(){
        isNotPrime = new boolean[10000];
        random = new Random();
        isNotPrime[1] = true;
        for(int i = 2;i < 10000;i++){
            if(!isNotPrime[i]){
              for(int j = i*i;j<10000;j+=i){
                  isNotPrime[j] = true;
              }
            }
        }

       // for(int i=2;i<100;i++){
       //     Log.d("QuestionerClass", "Questioner:"+i+" "+isNotPrime[i]);
       // }
    }

    public String getQuestion(){
        int i = random.nextInt(9999)+1;
        return i+" "+!isNotPrime[i];
    }

}
