package com.example.sandilemazibuko.groovappbeta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sandilemazibuko on 15/11/23.
 */
public class CustomEventsAdapater extends BaseAdapter {
    List<String> result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomEventsAdapater(Events mainActivity, List<String> prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_events_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.postDate);
        //holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

        String restaurant_name = result.get(position).toString()
                .substring( result.get(position).toString().indexOf('*') + 1,
                        result.get(position).toString().indexOf('#'));



        holder.tv.setText(restaurant_name);
        //holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                String place_type_id = result.get(position).toString()
//                        .substring( 0,
//                                result.get(position).toString().indexOf('*'));
//
//                Intent intent = new Intent(context, Profile.class);
//                intent.putExtra("place_type_id", place_type_id);
//                context.startActivity(intent);

                Toast.makeText(context, "Sandile", Toast.LENGTH_SHORT).show();


                // TODO Auto-generated method stub
                //Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}