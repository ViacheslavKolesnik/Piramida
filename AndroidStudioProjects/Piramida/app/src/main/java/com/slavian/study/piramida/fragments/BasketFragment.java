package com.slavian.study.piramida.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.slavian.study.piramida.GoodPage;
import com.slavian.study.piramida.LoginPage;
import com.slavian.study.piramida.MainActivity;
import com.slavian.study.piramida.Order;
import com.slavian.study.piramida.R;
import com.slavian.study.piramida.adapter.ListItemGood;
import com.slavian.study.piramida.adapter.MyListGoogsAdapter;
import com.slavian.study.piramida.entities.Good;

import java.util.ArrayList;

import static com.slavian.study.piramida.GoodsActivity.goodlist1;
import static com.slavian.study.piramida.GoodsActivity.selected;
import static com.slavian.study.piramida.GoodsActivity.selectedGood;


public class BasketFragment extends Fragment{
    private Button mButton;
    private Button orderButton;
    private ListView listViewBasket;
    public static ArrayList<ListItemGood> basketList= new ArrayList<>();
    public static ArrayList<Integer> basketListNum = new ArrayList<>();

    public BasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(MainActivity.logged_in) {
            view = inflater.inflate(R.layout.fragment_basket, container, false);

            MyListGoogsAdapter adapter = new MyListGoogsAdapter(getActivity(),R.layout.pattern_good_list, basketList,true);

            listViewBasket = (ListView)view.findViewById(R.id.listViewBasket);

            listViewBasket.setAdapter(adapter);
            listViewBasket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3){
                    //switch (position)
                    selected = basketList.get(position);
                    for (Good g:MainActivity.goods) {
                        if(selected.good_id==g.getGood_id()) {
                            selectedGood = new Good(g);
                        }
                    }
                    Intent intent = new Intent(getActivity(), GoodPage.class);
                    startActivity(intent);
                }
            });
            registerForContextMenu(listViewBasket);
            orderButton = (Button) view.findViewById(R.id.buttonFormOrder);
            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(basketList.size()==0){
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                "Для оформления заказа вам необходимо добавить товары в корзину", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        Intent intent = new Intent(getContext(), Order.class);
                        startActivity(intent);
                    }
                }
            });
        }
        else {
            view = inflater.inflate(R.layout.fragment_notin_basket, container, false);

            mButton = (Button) view.findViewById(R.id.button_basket);
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
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, 101, Menu.NONE, "Удалить");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if (item.getItemId() == 101) {
            // получаем инфу о пункте списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            basketList.remove((int)acmi.id);
            basketListNum.remove((int)acmi.id);
            listViewBasket.setAdapter(null);
            MyListGoogsAdapter adapter = new MyListGoogsAdapter(getActivity(),R.layout.pattern_good_list, basketList,true);
            listViewBasket.setAdapter(adapter);
            return true;
        }

        return true;
    }
}
