package com.dungha.game2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements setData {
    private GridView gdvGamePlay;
    private View.OnTouchListener listener; //vuốt màn hình
    private float X, Y;//nhận tọa độ người dùng kích vào màn hình
    private OSoAdapter adapter;
    private ImageView newGame,back;
    TextView txvPoint, txvMAX;
    boolean win = false;
    boolean No;
    PlayUser user= new PlayUser();

    //txvPoint

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

           newGame = (ImageView) findViewById(R.id.newGame);



//        PlayUser playUser = new PlayUser();
//        playUser.getInfor(this);
//        System.out.println("Điểm "+playUser.name);
//        System.out.println("Điểm "+playUser.point);

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                win = false;
                Datagame.thuaGame=false;
                anhXa();
                khoiTao();

                setData();
                win();
            }
        });

        anhXa();
        khoiTao();
        setData();


    }

    private void anhXa() {
        gdvGamePlay = (GridView) findViewById(R.id.gdvGamePlay);
        txvPoint = findViewById(R.id.txvPoint);
        txvMAX = findViewById(R.id.txvMAX);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Datagame.getInstance().back();
                    adapter.notifyDataSetChanged();
                  //  txvPoint.setText(""+Datagame.getInstance().getSoDiem(MainActivity.this));
                    Datagame.getInstance().setKhoaback(false);

            }
        });
    }

    private void khoiTao() {
        Datagame.getDatagame().win = false;
        No = false;
        Datagame.getDatagame().intt(MainActivity.this);
        adapter = new OSoAdapter(MainActivity.this, 0, Datagame.getDatagame().getArrSO());

        listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
             if(win){
                 adapter.notifyDataSetChanged();
                 return true;
             }
                switch (event.getAction()) {//trả các sự kiện
                    case MotionEvent.ACTION_DOWN://kịch sự kiện chạm vào màn hình
                        X = event.getX();//tọa độ người dùng
                        Y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP://sự kiện nhả
                        if (Math.abs(event.getX() - X) > Math.abs(event.getY() - Y)) {
                            if (event.getX() > X) {
                                Datagame.getDatagame().vuotPhai();
                                adapter.notifyDataSetChanged();

                            } else {
                                if (event.getX() < X)
                                Datagame.getDatagame().vuotTrai();
                                adapter.notifyDataSetChanged();
                            }

                        } else if (Math.abs(event.getX() - X) < Math.abs(event.getY() - Y)){
                            if (event.getY() > Y) {
                                Datagame.getDatagame().vuotXuong();
                                adapter.notifyDataSetChanged();
                            } else {
                                if (event.getY() < Y)
                                Datagame.getDatagame().vuotLen();
                                adapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }

//                if(user.point <Datagame.getDatagame().getPoint()) {
//                    txvMAX.setText("" + Datagame.getDatagame().getPoint());
//                }
                txvPoint.setText("" + Datagame.getDatagame().getPoint());
                Datagame.getDatagame().getSoDiem(MainActivity.this);
                if (user.point < Datagame.getDatagame().getPoint()) {
                    user.point = Datagame.getDatagame().getPoint();
                    user.saveInfor(MainActivity.this);
                }
                txvMAX.setText("" +user.point);
              //  txvPoint.setText("" + Datagame.getDatagame().getSoDiem(MainActivity.this));

                if(Datagame.getDatagame().isThuaGame()){
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,"GAME OVER",Toast.LENGTH_SHORT).show();
                    win = true;
                    return true;
                }
                if (Datagame.getDatagame().over) {
                    setdd();
                }
                return true;
            }
        };
        txvPoint.setText("" + Datagame.getDatagame().getPoint());
        txvMAX.setText("" + user.point);

    }

    private boolean b=false;

    private void setData() {
        gdvGamePlay.setAdapter(adapter);
        gdvGamePlay.setOnTouchListener(listener);
        txvPoint.setText("" + Datagame.getDatagame().getPoint());
        user.getInfor(this);
        txvMAX.setText("" + user.point);
}

    int i = 0;
    boolean k;


    public void onBackPressed() {
        k = false;
        new AlertDialog.Builder(this)
                .setTitle("Bạn Có Muốn thoát Game ?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        k = true;
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        k = false;
                    }
                })
                .show();
        if (k) {
            super.onBackPressed();
        }

    }


    @Override
    public void setdd() {

    }

    @Override
    public void win() {
//        if(!win) {
      /*      win = true;
            final EditText edt = new EditText(this);
            AlertDialog diaLogSavePoint = new AlertDialog.Builder(this)
                    .setTitle("YOU WIN")

                    .setMessage("you want to continue playing it?")

                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PlayUser u = new PlayUser();
                            u.point = Datagame.getDatagame().getPoint();
                            u.name = edt.getText().toString();
                            Datagame.getDatagame().win = false;
                            Datagame.getDatagame().choitiep = true;


                            u.saveInfor(MainActivity.this);
                        }
                    })

                    .setNegativeButton("NO", null)
                    .create();
            diaLogSavePoint.show()*/;

        if (Datagame.getDatagame().win == true) {
           /* new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle("Bạn đã thắng")
                    .setMessage("Bạn có muốn chơi tiếp hay không ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Datagame.getDatagame().choitiep = true;
                            Datagame.getDatagame().win = false;
                            No = false;
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           No = true;
                            setdd();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            Datagame.getDatagame().win = false;*/
            final AlertDialog.Builder altBx = new AlertDialog.Builder(this);
//            final EditText input = new EditText(this);
            altBx.setTitle("YOU WIN!");
//            altBx.setView(input);
            altBx.setMessage("YOU WANT TO CONTINUE PLAYING IT ?");
            altBx.setIcon(R.drawable.cuoi2);

            altBx.setPositiveButton("YES", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    Datagame.getDatagame().choitiep = true;
                    Datagame.getDatagame().win = false;
                    No = false;

//                    PlayUser u = new PlayUser();
//                    u.point = Datagame.getDatagame().getPoint();
//                    u.name = altBx.getContext().toString();
//                    u.saveInfor(MainActivity.this);
                }
            });
            altBx.setNeutralButton("NO", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {

                    No = true;
                    Datagame.thuaGame=true;

                    Toast.makeText(MainActivity.this, "GAME OVER", Toast.LENGTH_SHORT).show();

                }

            });
            altBx.show();
            Datagame.getDatagame().win = false;


        }

    }



  //  }


}
