package com.slavian.study.piramida.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.slavian.study.piramida.GoodsActivity;
import com.slavian.study.piramida.R;
import com.slavian.study.piramida.fragments.BasketFragment;

import java.util.ArrayList;

/**
 * Created by Slavian on 1/6/2017.
 */

public class MyListGoogsAdapter extends ArrayAdapter<ListItemGood> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<ListItemGood> data = null;
    private boolean isBasket;

    public MyListGoogsAdapter(Context context, int layoutResourceId, ArrayList<ListItemGood> data, boolean isBasket) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.isBasket=isBasket;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WeatherHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.icon);
            holder.txtDescr = (TextView)row.findViewById(R.id.textViewText);
            holder.txtPrice = (TextView)row.findViewById(R.id.textViewPrice);
            holder.txtGoodNum= (TextView)row.findViewById(R.id.textViewNumberOfBGoods);
            holder.txtSHt=(TextView)row.findViewById(R.id.textView333);

            row.setTag(holder);
            }
        else
        {
             holder = (WeatherHolder)row.getTag();
        }

        ListItemGood weather = data.get(position);
        holder.txtDescr.setText(weather.description);
        holder.txtPrice.setText(weather.price);
        if(isBasket)
            holder.txtGoodNum.setText(BasketFragment.basketListNum.get(position)+"");
        else
            holder.txtSHt.setText("");

        holder.imgIcon.setImageResource(weather.icon);

        return row;
        }

    static class WeatherHolder
    {
        ImageView imgIcon;
        TextView txtDescr;
        TextView txtPrice;
        TextView txtGoodNum;
        TextView txtSHt;
    }

}