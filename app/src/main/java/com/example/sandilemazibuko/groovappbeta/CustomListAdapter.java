package com.example.sandilemazibuko.groovappbeta;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<String> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context context, ArrayList<String> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.start_date_end_date = (TextView) convertView.findViewById(R.id.txtDatePost);
            holder.place_name = (TextView) convertView.findViewById(R.id.txtPlaceName);
            holder.event_description = (TextView) convertView.findViewById(R.id.txtPlaceDescription);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String newsItem = listData.get(position);

//        String event_url_image = listData.get(position);
//        String start_date = listData.get(position);
//        String end_date = listData.get(position);
//        String event_description = listData.get(position);
//        String place_id = listData.get(position);
//        String event_id = listData.get(position);



        String image_url = newsItem.substring(newsItem.indexOf("$")
                + 1,newsItem.indexOf("&"));
        String place_id = newsItem.substring(0,newsItem.indexOf("@"));
        String event_id = newsItem.substring(newsItem.indexOf("@") + 1,
                newsItem.indexOf("#"));
        String event_description = newsItem.substring(newsItem.indexOf("#") + 1,
                newsItem.indexOf("$"));
        String start_date = newsItem.substring(newsItem.indexOf("&") + 1,
                newsItem.indexOf("*"));
        String end_date = newsItem.substring(newsItem.indexOf("*") + 1,
                newsItem.indexOf("!"));
        String place_name = newsItem.substring(newsItem.indexOf("!") + 1);


        holder.start_date_end_date.setText("Post " + start_date + " / " + end_date);
        holder.place_name.setText(place_name);
        holder.event_description.setText(event_description);

        return convertView;
    }



    static class ViewHolder {
        TextView start_date_end_date;
        TextView place_name;
        TextView event_description;
        ImageView imageView;
    }
}
