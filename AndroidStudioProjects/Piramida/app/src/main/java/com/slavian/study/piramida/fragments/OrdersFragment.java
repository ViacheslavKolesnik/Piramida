package com.slavian.study.piramida.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.slavian.study.piramida.LoginPage;
import com.slavian.study.piramida.MainActivity;
import com.slavian.study.piramida.R;
import com.slavian.study.piramida.adapter.ExpandAdapter;
import com.slavian.study.piramida.entities.Good;
import com.slavian.study.piramida.entities.OrderEntity;
import com.slavian.study.piramida.entities.OrderList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OrdersFragment extends Fragment {
    private Button mButton;
    private ListView listViewOrders;
    private ExpandableListView expandableListView;

    private ArrayList<Long> ordersID;
    private HashMap<Long,List<Good>> hashGoods;
    private ArrayList<OrderList> chosenLists;
    public static ArrayList<OrderEntity> chosenOrders;
    private ArrayList<Good> chosenGoods;


    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(MainActivity.logged_in) {
            view = inflater.inflate(R.layout.fragment_orders, container, false);

            choose();

            ExpandAdapter adapter = new ExpandAdapter(getActivity(),ordersID,hashGoods, chosenLists);

            expandableListView = (ExpandableListView)view.findViewById(R.id.ListViewExp);

            expandableListView.setAdapter(adapter);
        }
        else {
            view = inflater.inflate(R.layout.fragment_notin_orders, container, false);

            mButton = (Button) view.findViewById(R.id.button_orders);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginPage.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }

    private void choose(){
        chosenOrders = new ArrayList<>();
        chosenLists = new ArrayList<>();
        chosenGoods = new ArrayList<>();
        ordersID = new ArrayList<>();
        hashGoods = new HashMap<Long,List<Good>>();
        for (OrderEntity oe: MainActivity.orderArr){
            if(oe.getUser_id()==MainActivity.current_id){
                chosenOrders.add(oe);
                ordersID.add(oe.getOrder_id());
            }
        }
        for(OrderEntity oe:chosenOrders) {
            for (OrderList ol : MainActivity.orderListArr) {
                if (oe.getOrder_id()==ol.getOrder_id()) {
                    chosenLists.add(ol);
                }
            }
        }
        for(Good g:MainActivity.goods){
            for(OrderList ol: chosenLists) {
                if (g.getGood_id() ==ol.getGood_id()){
                    chosenGoods.add(g);
                }
            }
        }
        for (int i=0;i<ordersID.size();i++){
            List<Good> newList = new ArrayList<>();
            for (int j=0; j<chosenLists.size();j++){
                if(ordersID.get(i)==chosenLists.get(j).getOrder_id()){
                    for (Good g:chosenGoods) {
                        if(chosenLists.get(j).getGood_id()==g.getGood_id())
                        newList.add(g);
                    }
                }
            }
            hashGoods.put(ordersID.get(i),newList);
        }
    }

}
