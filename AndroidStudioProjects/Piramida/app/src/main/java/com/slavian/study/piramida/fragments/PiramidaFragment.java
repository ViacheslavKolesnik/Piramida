package com.slavian.study.piramida.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.slavian.study.piramida.GoodsActivity;
import com.slavian.study.piramida.R;


public class PiramidaFragment extends Fragment {
    public static String cat;

    public PiramidaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cat="";
        View view =  inflater.inflate(R.layout.fragment_piramida, container, false);
        ListView listView = (ListView)view.findViewById(R.id.list_view);
        String[] shopCategories = getResources().getStringArray(R.array.shop_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_1, shopCategories);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3){
                switch (position) {
                    case 0: {
                        cat="phone";
                        Intent intent = new Intent(getActivity(), GoodsActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        cat="laptop";
                        Intent intent = new Intent(getActivity(), GoodsActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        cat="desktop";
                        Intent intent = new Intent(getActivity(), GoodsActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
        return view;
    }
}
