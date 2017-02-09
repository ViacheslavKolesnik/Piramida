package com.slavian.study.piramida.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.slavian.study.piramida.MainActivity;
import com.slavian.study.piramida.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {


    String s;
    private DatabaseReference mSimpleFirechatDatabaseReference;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView t = (TextView)view.findViewById(R.id.info_text);
        t.setText("Piramida\n" +
                "В нашем интернет-магазине вы можете выбрать себе телефон/ноутбук/компьютер \n" +
                "из огромного ассортимента товаров, из разных ценовый категорий, а также большого количества производителей\n" +
                "\n" +
                "Оплата\n" +
                "Оплатить товары в нашем интернет-магазине вы можете с помощью банковской карты или,\n" +
                "если вы выбрали доставку \"Нова Пошта\", при получении товаров\n" +
                "\n" +
                "Доставка\n" +
                "Доставку товаров из нашего магазина осуществляют \"Нова Пошта\" и \"УкрПошта\"\n" +
                "\n" +
                "Контакты\n" +
                "Будем рады видеть Вас по адресу: г. Харьков, ул. Харьковская,57\n" +
                "Тел. +380998887766");

        return view;
    }

}
