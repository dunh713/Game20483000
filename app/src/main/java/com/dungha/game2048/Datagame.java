package com.dungha.game2048;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class Datagame {
    public boolean khoa, khoaback = true, over, choitiep,sinhso;
    public boolean win;

    private static Datagame datagame;

    private int soO, soDiem, Point, brack;
    private boolean landau= true;


    public static boolean thuaGame;

    public void khoiTao() {
        win = false;
        khoa = true;
        soDiem = 0;
        Point =0;
        over = false;
        brack = 0;
        thuaGame = false;
    }
    public void setpoi(){
        Point =0;
    }
    public int getSoDiem(setData data) {
int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (k < mangHaiChieu[i][j]) {
                    k = mangHaiChieu[i][j];
                }
            }
        }
        if (k >=2048 && !choitiep) {
            choitiep = true;
            win =true;
            data.win();
        }
        return k;
    }

    public int getPoint() {
        return Point;
    }



    private ArrayList<Integer> arrSO = new ArrayList<>();
    private int[] mangMau;
    private int[][] mangHaiChieu = new int[4][4];
    private Random r = new Random();

    static {
        datagame = new Datagame();
    }

    private int[][] mangLui = new int[4][4];
    private boolean khoaCHuyenDoi = true;

    private void setMangLui() {
        if (!khoaCHuyenDoi) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mangLui[i][j] = mangHaiChieu[i][j];
            }
        }
    }

    public static Datagame getDatagame() {
        return datagame;
    }

    public void intt(Context context) {
        win =false;
        landau =true;
        choitiep = false;
        sinhso = true;
        this.Point = 0;
        soDiem = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mangHaiChieu[i][j] = 0;
                arrSO.add(0);
            }
        }
        TypedArray ta = context.getResources().obtainTypedArray(R.array.maunencuaso);
        mangMau = new int[ta.length()]; //lấy ra số ptu mảng màu
        for (int i = 0; i < ta.length(); i++) {
            mangMau[i] = ta.getColor(i, 0);
        }
        ta.recycle();//đóng mảng màu
        taoso();
        chuyendoi();
    }

    public ArrayList<Integer> getArrSO() {
        return arrSO;
    }

    public int colorr(int so) {
        if (so == 0) {
            return Color.WHITE;

        } else {
            int a = (int) (Math.log(so) / Math.log(2));//log2/log2=1
            return mangMau[a - 1];
        }
    }

    public boolean isThuaGame() {
        return thuaGame;
    }

    public void check() {
        over = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mangHaiChieu[i][j] == 0) {
                    over = false;
                }
            }
        }
        if (over) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (mangHaiChieu[i][j] == mangHaiChieu[i][j + 1]) {
                        over = false;
                        break;
                    }
                }
            }
            if (over) {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (mangHaiChieu[j][i] == mangHaiChieu[j + 1][i]) {
                            over = false;
                            break;
                        }
                    }
                }
            }
        }
        if (over) {
            thuaGame = true;
        }
    }

    public void taoso() { //tạo ra số random
        int so0 = 0;
        for (int i = 0; i < 16; i++) {
            if (arrSO.get(i) == 0) {
                so0++;
            }
        }
        int so0Tao;
        if (so0 > 1) {
            int i = 0;
            so0Tao = r.nextInt(2) + 1;//tạo ra từ 1 đến 2 số
            if(landau){
                landau =false;
                so0Tao =2;
            }
        } else {
            if (so0 == 1) {
                so0Tao = 1;
            } else {
                so0Tao = 0;
            }
        }
        check();
        while (so0Tao != 0&&sinhso) {
                int i = r.nextInt(4), j = r.nextInt(4);
                if (mangHaiChieu[i][j] == 0) {
                    int ix = r.nextInt(2)+1;
                    ix=ix*2;
                    mangHaiChieu[i][j] = ix;
                    so0Tao--;
                }
        }
    }

    public void chuyendoi() {
        arrSO.clear();//xóa hết mọi thứ
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {//gán tất cả các thành phần của manghaichieu sang arrlist
                arrSO.add(mangHaiChieu[i][j]);
            }
        }
    }

    public void vuotTrai() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int so = mangHaiChieu[i][j];
                if (so == 0) {

                    continue;
                } else {
                    int st = j + 1;
                    for (int k = st; k < 4; k++) {
                        int sox = mangHaiChieu[i][k];
                        if (sox == 0) {//=0 tiếp tục xét,xét đến khi nào khác 0
                            continue;
                        } else if (sox == so) {
                            mangHaiChieu[i][j] = 2 * so;//cộng 2 số với nhau
                            Point = Point + so * 2;
                            mangHaiChieu[i][k] = 0;

                        } else {
                            break;//cộng xong thì thoát
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int so = mangHaiChieu[i][j];
                if (so != 0) {
                    continue;
                } else {
                    for (int k = j + 1; k < 4; k++) {
                        if (mangHaiChieu[i][k] == 0) {
                            continue;
                        } else {
                            mangHaiChieu[i][j] = mangHaiChieu[i][k];
                            mangHaiChieu[i][k] = 0;
                            khoa = true;
                            break;
                        }
                    }
                }

            }
        }
        taoso();
        chuyendoi();
    }

    public void vuotPhai() {
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int so = mangHaiChieu[i][j];
                if (so == 0) {
                    continue;
                } else {
                    for (int k = j - 1; k >= 0; k--) {
                        int sox = mangHaiChieu[i][k];
                        if (sox == 0) {
                            continue;
                        } else {
                            if (sox == so) {
                                mangHaiChieu[i][j] = so * 2;
                                Point = Point + so * 2;
                                mangHaiChieu[i][k] = 0;
                                khoaCHuyenDoi = true;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                int so = mangHaiChieu[i][j];
                if (so != 0) {

                    continue;
                } else {
                    for (int k = j - 1; k >= 0; k--) {
                        if (mangHaiChieu[i][k] == 0) {
                            continue;
                        } else {
                            mangHaiChieu[i][j] = mangHaiChieu[i][k];
                            mangHaiChieu[i][k] = 0;
                            khoa = true;
                            break;
                        }
                    }
                }
            }
        }
        taoso();
        chuyendoi();
    }

    public void vuotXuong() {

        khoaCHuyenDoi = false;
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int h = mangHaiChieu[j][i];
                if (h == 0) {

                    continue;
                } else {
                    int st = j - 1;
                    for (int k = st; k >= 0; k--) {
                        int g = mangHaiChieu[k][i];
                        if (g == 0) {
                            continue;
                        } else if (g == h) {
                            mangHaiChieu[j][i] = 2 * h;
                            Point = Point + h * 2;
                            mangHaiChieu[k][i] = 0;

                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                int h = mangHaiChieu[j][i];
                if (h != 0) {
                    continue;
                } else {
                    for (int k = j - 1; k >= 0; k--) {
                        if (mangHaiChieu[k][i] == 0) {
                            continue;
                        } else {
                            mangHaiChieu[j][i] = mangHaiChieu[k][i];
                            mangHaiChieu[k][i] = 0;

                            break;
                        }
                    }
                }

            }
        }
        taoso();
        chuyendoi();
    }

    public void vuotLen() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int h = mangHaiChieu[j][i];
                if (h == 0) {

                    continue;
                } else {
                    int st = j + 1;
                    for (int k = st; k < 4; k++) {
                        int g = mangHaiChieu[k][i];
                        if (g == 0) {
                            continue;
                        } else if (g == h) {
                            mangHaiChieu[j][i] = 2 * h;
                            Point = Point + h * 2;
                            mangHaiChieu[k][i] = 0;

                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int h = mangHaiChieu[j][i];
                if (h != 0) {
                    continue;
                } else {
                    for (int k = j + 1; k < 4; k++) {
                        if (mangHaiChieu[k][i] == 0) {
                            continue;
                        } else {
                            mangHaiChieu[j][i] = mangHaiChieu[k][i];
                            mangHaiChieu[k][i] = 0;

                            break;
                        }
                    }
                }

            }
        }


        taoso();
        chuyendoi();
    }

    public ArrayList<Integer> getArr() {

        return arrSO;
    }

    public static Datagame getInstance() {
        return datagame;
    }


    public void back() {
        if (Point >= 5000 && khoaback) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    mangHaiChieu[i][j] = mangLui[i][j];
                }
            }
            content();
            Point = Point - 5000;

        }else {

        }
    }


    public void content() {
        arrSO.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arrSO.add(mangHaiChieu[i][j]);
            }
        }
    }

    public void setKhoaback(boolean khoaback) {
        this.khoaback = true;
    }



}
