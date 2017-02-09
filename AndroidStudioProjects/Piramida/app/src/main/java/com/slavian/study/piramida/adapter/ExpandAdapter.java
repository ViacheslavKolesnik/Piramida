package com.slavian.study.piramida.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.slavian.study.piramida.R;
import com.slavian.study.piramida.entities.Good;
import com.slavian.study.piramida.entities.OrderEntity;
import com.slavian.study.piramida.entities.OrderList;
import com.slavian.study.piramida.fragments.OrdersFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Slavian on 1/19/2017.
 */

public class ExpandAdapter extends BaseExpandableListAdapter {

    private Context _context;
    //Названия заголовков
    private List<Long> _listDataHeader;
    //Данные для элементов подпунктов:
    private HashMap<Long, List<Good>> _listDataChild;
    private long date;
    private ArrayList<OrderList> chosenLists;

    public ExpandAdapter(Context context, List<Long> listDataHeader,
                                 HashMap<Long, List<Good>> listChildData,ArrayList<OrderList> chosenLists) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.chosenLists=chosenLists;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View row = convertView;
        ExpandAdapter.WeatherHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.pattern_good_list, parent, false);

            holder = new ExpandAdapter.WeatherHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.icon);
            holder.txtDescr = (TextView)row.findViewById(R.id.textViewText);
            holder.txtPrice = (TextView)row.findViewById(R.id.textViewPrice);
            holder.txtGoodNum= (TextView)row.findViewById(R.id.textViewNumberOfBGoods);

            row.setTag(holder);
        }
        else
        {
            holder = (ExpandAdapter.WeatherHolder)row.getTag();
        }

        //OrderEntity weather1 = orders.get(position);

        Good weather = (Good)getChild(groupPosition, childPosition);
        //OrderList weather2 = lists.get(position);

        holder.txtDescr.setText(weather.getGood_name());
        holder.txtPrice.setText(weather.getPrice());


        String mDrawableName = weather.getIcon();
        //int ic = getContext().getResources().getIdentifier(mDrawableName , "drawable", getContext().getPackageName());
        int ic = _context.getResources().getIdentifier(mDrawableName , "drawable", _context.getPackageName());
        holder.imgIcon.setImageResource(ic);

        long headerTitle = (Long) getGroup(groupPosition);
        Good childG = (Good) getChild(groupPosition, childPosition);
        long chosenCount=0;
        for (int i=0;i<chosenLists.size();i++){
            if(chosenLists.get(i).getGood_id()==childG.getGood_id()&&chosenLists.get(i).getOrder_id()==headerTitle)
                chosenCount=chosenLists.get(i).getO_good_number();
        }

        holder.txtGoodNum.setText(chosenCount+"");

        return row;

        /*final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.pattern_good_list, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;*/
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        long headerTitle = (Long) getGroup(groupPosition);
        date = headerTitle;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.text_expand, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.textViewExpand);
        String s="";
        for (int i=0;i<OrdersFragment.chosenOrders.size();i++) {
            if(OrdersFragment.chosenOrders.get(i).getOrder_id()==headerTitle)
                s = OrdersFragment.chosenOrders.get(i).getOrder_date();
        }
        lblListHeader.setText(s);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class WeatherHolder
    {
        ImageView imgIcon;
        TextView txtDescr;
        TextView txtPrice;
        TextView txtGoodNum;
    }
}