package com.example.sample.sample_quiz0819;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Button;

import java.util.Random;

public class MainGame extends AppCompatActivity {

    //全問題数-1
    int allQuestion=9;
    //出題数
    int Shutudaisuu=5;
    //問題の解答回数
    int Kaitousuu=1;

    String QuestionNo;
    int randomQuestionNo;
    String Seikai;

    //効果音再生
    private SoundPool mSoundPool;
    private int[] mSoundId = new int[2];//使う効果音の数だけ配列作成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //タイトルバーを非表示にする。setContentViewの前でないとエラー起きる場合あり
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //レイアウトをセットする
        setContentView(R.layout.activity_main_game);

        //ステージセレクトActivityから送られてきたデータを取得 受け取るデータはボタンに記載された数字
        Intent intent = getIntent();
        QuestionNo = intent.getStringExtra("QuestionNo");
        QuestionNo = Integer.toString(getRandomQuestionNo());
    }


    @Override
    protected void onResume() {
        super.onResume();

        //効果音を使えるように読み込む
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mSoundId[0] = mSoundPool.load(getApplicationContext(), R.raw.se_seikai, 1);
        mSoundId[1] = mSoundPool.load(getApplicationContext(), R.raw.se_huseikai, 1);


        //画面↑にあるテキストを「クイズNo,+問題Noで表示」
        //((TextView) findViewById(R.id.textNo)).setText("クイズNo." + QuestionNo+"だよ！");

        //問題文セット処理呼び出し
        setQuestion();
    }


    /**
     * 問題文セット処理
     */
    protected void setQuestion() {
        //作成したDatabaseHelperクラスに読み取り専用でアクセス
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //SELECT文　テーブル名　MyTestTableから　_idとQuestionNoがマッチする項目を取得する条件式
        //String sql = "SELECT Pref ,City0,City1,City2,City3,City4 FROM MyTestTable WHERE _id" + QuestionNo;
        String sql = "SELECT Pref, City0, City1, City2, City3, City4 FROM MyTestTable WHERE _id=" + QuestionNo;

        //SQL文を実行してカーソルを取得
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();

        //データベースからとってきたデータを変数にセット
        String Kenmei = c.getString(c.getColumnIndex("Pref"));//問題文となる都道府県
        String Choice1 = c.getString(c.getColumnIndex("City1"));//選択肢１
        String Choice2 = c.getString(c.getColumnIndex("City2"));
        String Choice3 = c.getString(c.getColumnIndex("City3"));
        String Choice4 = c.getString(c.getColumnIndex("City4"));

        Seikai = c.getString(c.getColumnIndex("City0"));//答えをSeikaiに格納

        //データベースクローズ
        c.close();
        db.close();

        //画面↑にあるテキストを「クイズNo,+問題Noで表示」
        ((TextView) findViewById(R.id.textNo)).setText("クイズNo." + QuestionNo+"だよ！");
        ((TextView) findViewById(R.id.textQuestion)).setText(Kenmei);//問題文をテキストビューにセット
        ((TextView) findViewById(R.id.button1)).setText(Choice1);//選択肢1をボタンビューにセット
        ((TextView) findViewById(R.id.button2)).setText(Choice2);//選択肢2をボタンビューにセット
        ((TextView) findViewById(R.id.button3)).setText(Choice3);//選択肢3をボタンビューにセット
        ((TextView) findViewById(R.id.button4)).setText(Choice4);//選択肢4をボタンビューにセット
        QuestionNo=Integer.toString(nextQuestionNo());
    }

    public int nextQuestionNo() {
        //解答数を加算
        Kaitousuu++;
        //問題文情報を、PRYMARY　KEYであるQuestionNoで取得しているため、加算して次の問題へ
        return getRandomQuestionNo();
    }

    /**
     * @return
     * 問題をランダム表示するために、乱数生成とその乱数に対応する問題がすでに使用されたかを確認し、
     * 重複がない場合はその生成した乱数を返す
     */
    public int getRandomQuestionNo(){

        Random rand =new Random();

        String Dupli;

        //作成したDatabaseHelperクラスに読み取り専用でアクセス
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        do {
            //問題をランダムで選定するため、乱数を生成
            randomQuestionNo = rand.nextInt(allQuestion);
            //SELECT文　テーブル名　MyTestTableから　_idと生成した乱数がマッチする項目を取得する条件式
            //String sql = "SELECT Pref ,City0,City1,City2,City3,City4 FROM MyTestTable WHERE _id" + QuestionNo;
            String sql = "SELECT Dupli FROM MyTestTable WHERE _id=" + (Integer.toString(randomQuestionNo));

            //SQL文を実行してカーソルを取得
            Cursor c = db.rawQuery(sql, null);
            c.moveToFirst();

            //データベースからとってきたデータを変数にセット
            Dupli = c.getString(c.getColumnIndex("Dupli"));//問題文となる都道府県

        }while (Dupli.equals("1")); //Dupli=重複フラグが1の場合、違う問題を選ぶ

        //使用済みでない場合、今回選出されたためフラグを使用済みにする
        //データベースを更新処理
        ContentValues values = new ContentValues();
        //使用済みフラグ 0→１　に書き換え、正解の状態にする
        values.put("Dupli", 1);

        return randomQuestionNo;
    }

    //選択肢がクリックされた時の処理
    public void onClick(View v) {

        //データベースと接続
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //押されたボタンのテキストと正解を比較
        if (((Button) v).getText().equals(Seikai)) {
            //正解の処理 正解音再生
            mSoundPool.play(mSoundId[0], 1.0f, 1.0f, 0, 0, 1.0f);

            //データベースを更新処理
            ContentValues values = new ContentValues();
            //Clear 0→１　に書き換え、正解の状態にする
            values.put("Clear", 1);
            //カラム選択
            String whereClause = "_id = ?";

            //データベース更新
            int ret;
            try {
                ret = db.update("MyTestTable", values, whereClause, new String[]{String.valueOf((Integer.parseInt(QuestionNo)))});
            } finally {
               db.close();
            }

            if (ret == -1) {
                //処理未実装
            } else {
                //処理未実装
            }

        } else {
            //不正解の処理
            mSoundPool.play(mSoundId[1], 1.0f, 1.0f, 0, 0, 1.0f);

        }



        //正解・不正解にかかわらず、次の問題へ
        //次の問題をDBから呼び出し、セットする
        if(Kaitousuu<Shutudaisuu){//出題数が既定の数へ達していない場合
          setQuestion();
        }
          else{//既定の回数出し終わったとき 結果画面へ遷移
            Intent intent = new Intent(MainGame.this,Result.class);
            startActivity(intent);
        }
    }


    /**
     * 効果音に関するものはすべて解放する
     */
    @Override
    protected void onPause() {
        super.onPause();

        //SoundPool解放
        mSoundPool.unload(mSoundId[0]);
        mSoundPool.unload(mSoundId[1]);

        mSoundPool.release();

    }

}










