package com.example.sample.sample_quiz0819;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class StageSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //レイアウトのセット
        setContentView(R.layout.activity_stage_select);

        //タイトルバーを非表示にする
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        //ボタン処理呼び出し
        setIcon();
    }

    //ボタンをクリア済みかどうかで色分け＆クリック不可処理
    private void setIcon() {
        //作成したDatabaseHelperクラスに読み取り専用でアクセス
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //SELECT文　テーブル名　MyTestTableからClearの項目を検索してくる条件式
        String sql = "SELECT Clear FROM MyTestTable";

        //上記のSELECT分を実行してカーソルを取得
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();

        //CLEAR_FLAGを入れる配列を作成。配列の要素数はデータの数だけ
        int[] ClearFlag = new int[c.getCount()];

        //クリア状況を取得して配列CLEAR_FLAGに入れる
        for (int i = 0; i < c.getCount(); i++) {
            ClearFlag[i] = c.getInt(0);
            c.moveToNext();
        }

        //データベースからデータを取り終わったのでクローズ処理
        c.close();
        db.close();

        //ボタンの個数だけ繰り返す
        for (int i = 1; i <= 10; i++) {
            //layoutでbuttonのidをbutton1~10を連番で振っているのでそれを使用する
            String resViewName = "button" + i;
            int viewId = getResources().getIdentifier(resViewName, "id", getPackageName());
            Button button = (Button) findViewById(viewId);

            //Clearの値によって処理を分ける
            if (ClearFlag[i - 1] == 1) {
                //Clearの値が１の場合
                button.setText(String.valueOf(i));//ボタンテキストに問題番号を表示
                button.setTextColor(0xff58BE89);//テキストの色
                button.setBackgroundColor(0xffffffff);//ボタンの色
                button.setEnabled(true);//ボタンを選択可能にする
            } else {
                //Clearの値が0の場合
                button.setText(String.valueOf(i));//ボタンテキストに問題番号を表示
                button.setTextColor(0xffffffff);//テキストの色
                button.setBackgroundColor(0xffB7B7B7);//ボタンの色
                //button.setEnabled(false);//ボタンを選択不可能にする
                button.setEnabled(true);//ボタンを選択不可能にする



            }

        }
    }

    //ボタンクリックでゲーム画面へ遷移
    public void onClick(View v) {
        //遷移先のActivityを指定 MainGameへ遷移
        Intent intent = new Intent(StageSelect.this,MainGame.class);

        //選択されたステージをボタンのテキストから取得
        //どのステージが選ばれたのかｗｐActivityに渡す
        //アクティビティ間で値を受け渡す処理
        intent.putExtra("QuestionNo",((Button)v).getText());

        //遷移開始
        startActivity(intent);
    }
}


















