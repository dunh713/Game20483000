package com.dungha.game2048;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

public class OSoAdapter extends ArrayAdapter<Integer> {
    private Context ct;
    private ArrayList<Integer> arr;
    public OSoAdapter( @NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.ct=context;
        this.arr= new ArrayList<>(objects);
    }

    @Override
    public void notifyDataSetChanged() {//hàm ghi đè
        arr=Datagame.getDatagame().getArrSO();
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_o_vuong,null);
        }
        if (arr.size()>0){//nếu lớn hơn 1 phần tử thì sẽ tạo ovuong và ánh xạ nó
            OVuong o= (OVuong)convertView.findViewById(R.id.txOVuong);
            o.setTett(arr.get(position));
        }
        return convertView;
    }
}
