package com.example.sample.sample_quiz0819;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class RandomGame extends AppCompatActivity {

    //効果音再生
    private SoundPool mSoundPool;
    private int[] mSoundId = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_random_game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //効果音を使えるように読み込む
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mSoundId[0] = mSoundPool.load(getApplicationContext(), R.raw.se_seikai, 1);
        mSoundId[1] = mSoundPool.load(getApplicationContext(), R.raw.se_huseikai, 1);

        ((TextView)findViewById(R.id.textNo)).setText("ランダム");
    }


    /**
     * @param v
     * 選択肢がクリックされたときの処理
     */
    public void onClick(View v){

    }

    @Override
    protected void onPause(){
        super.onPause();
        //SoundPool 解放
        mSoundPool.unload(mSoundId[0]);
        mSoundPool.unload(mSoundId[1]);
        mSoundPool.release();
    }
}










