package com.example.sample.sample_quiz0819;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //タイトルバーを非表示にする
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //レイアウトのセット
        setContentView(R.layout.activity_main);
    }

    //ボタンがタッチされたときの処理
    public void onClick(View v) {
        switch (v.getId()) {
            //タッチされたボダンがノーマルの場合
            case R.id.button_normal:
                //遷移先のActivityを指定
                //Intent()の引数に、遷移先のActivityを設定する　引数（遷移元クラス,遷移先クラス）
                Intent intent = new Intent(MainActivity.this,StageSelect.class);
                //遷移開始
                startActivity(intent);
                break;

            //タッチされたボダンがランダムの場合
            case R.id.button_random:
                //遷移先のActivityを指定
                //Intent()の引数に、遷移先のActivityを設定する　引数（遷移元クラス,遷移先クラス）
                intent = new Intent(MainActivity.this,RandomGame.class);
                //遷移開始
                startActivity(intent);
                break;

            //タッチされたボダンがタイムアタックの場合
            case R.id.button_time:
                Toast.makeText(this, "タイムアタックがタッチされました", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
