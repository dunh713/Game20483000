package com.dungha.game2048;

import android.content.Context;
import android.content.SharedPreferences;

public class PlayUser {
    public static final String nameApp="app";

    public int point=0;
    public String name ="";

    private String keyPoint = "point";
    private String keyName = "name";

    public void saveInfor(Context ct){
        SharedPreferences settings = ct.getSharedPreferences(nameApp, 0);
        SharedPreferences.Editor editor = settings.edit();//tại đối tượng editor để lưu thay đổi
        editor.putInt(keyPoint, point);//lưu vào editor
        editor.putString(keyName, name);
        editor.commit();//lưu trạng thái,chấp nhận lưu xuống file
    }

    public void getInfor(Context ct){// cách đọc trạng thái đã lưu
        SharedPreferences settings = ct.getSharedPreferences(nameApp, 0);
        point = settings.getInt(keyPoint, 0);
        name  = settings.getString(keyName,"noName");
    }
}
