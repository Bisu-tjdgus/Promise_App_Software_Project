package com.example.promiseapp;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bookmark, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView posterImageView = (ImageView) convertView.findViewById(R.id.poster) ;
        TextView korTextView = (TextView) convertView.findViewById(R.id.korname) ;
        TextView engTextView = (TextView) convertView.findViewById(R.id.engname) ;
//        Button starButton = (Button) convertView.findViewById(R.id.starbutton);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);



        // 아이템 내 각 위젯에 데이터 반영
        posterImageView.setImageDrawable(listViewItem.getPoster());
        korTextView.setText(listViewItem.getKorname());
        engTextView.setText(listViewItem.getEngname());


        return convertView;

    }

    public void addItem(Drawable poster, String korname, String engname) {
        ListViewItem item = new ListViewItem();

        item.setPoster(poster);
        item.setEngname(korname);
        item.setKorname(engname);

        listViewItemList.add(item);
    }
}
