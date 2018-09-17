package com.example.sample.sample_quiz0819;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {

        /*
            ここで任意のデータベースファイル名と、バージョンを指定する
            データベースファイル名 = MyTestTable.db
            バージョン = 1
         */
        super(context, "MyTestTable.db", null, 1);
    }

    //onCreateメソッドはデータベースを初めて使用するときにじっこうされる処理
    //ここでテーブルの作成や初期データの投入を行う

    public void onCreate(SQLiteDatabase db) {
        //デーブルの作成
        db.execSQL(
                "CREATE TABLE MyTesttable" +
                        "(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                        ", Pref TEXT" +
                        ", City0 TEXT" +
                        ", City1 TEXT" +
                        ", City2 TEXT" +
                        ", City3 TEXT" +
                        ", City4 TEXT" +
                        ", Clear INTEGER" +
                        ", Dupli INTEGER"+
                ")");


        //初期データ投入
        db.execSQL("INSERT INTO MyTestTable(Pref,City0,City1,City2,City3,City4,Clear,Dupli) values('北海道の県庁所在地は？','札幌','青森','盛岡','仙台','札幌',0,0);");
        db.execSQL("INSERT INTO MyTestTable(Pref,City0, City1,   City2, City3, City4, Clear,Dupli) values ('山形県の県庁所在地は？','山形','山形','宇都宮','前橋','東京',0,0);");
        db.execSQL("INSERT INTO MyTestTable(Pref,City0, City1, City2, City3, City4, Clear,Dupli) values ('群馬県の県庁所在地は？','前橋','横浜','前橋','京都','水戸',0,0);");
        db.execSQL("INSERT INTO MyTestTable(Pref,City0, City1, City2, City3, City4, Clear,Dupli) values ('福井県の県庁所在地は？','福井','秋田','盛岡','仙台','福井',0,0);");
        db.execSQL("INSERT INTO MyTestTable(Pref,City0, City1, City2, City3, City4, Clear,Dupli) values ('石川県の県庁所在地は？','金沢','前橋','京都','金沢','盛岡',0,0);");
        db.execSQL("INSERT INTO MyTestTable(Pref,City0, City1, City2, City3, City4, Clear,Dupli) values ('兵庫県の県庁所在地は？','神戸','神戸','京都','和歌山','盛岡',0,0);");
        db.execSQL("INSERT INTO MyTestTable(Pref,City0, City1, City2, City3, City4, Clear,Dupli) values ('山梨県の県庁所在地は？','甲府','前橋','京都','金沢','甲府',0,0);");
        db.execSQL("INSERT INTO MyTestTable(Pref,City0, City1, City2, City3, City4, Clear,Dupli) values ('長野県の県庁所在地は？','長野','前橋','東京','長野','盛岡',0,0);");
        db.execSQL("INSERT INTO MyTestTable(Pref,City0, City1, City2, City3, City4, Clear,Dupli) values ('岐阜県の県庁所在地は？','岐阜','前橋','岐阜','仙台','札幌',0,0);");
        db.execSQL("INSERT INTO MyTestTable(Pref,City0, City1, City2, City3, City4, Clear,Dupli) values ('静岡県の県庁所在地は？','静岡','静岡','神戸','京都','和歌山',0,0);");
    }

    /**
     * データベースをバージョンアップしたときに呼ばれる
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
          //未実装
    }

}
