package com.slavian.study.piramida;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.slavian.study.piramida.adapter.ListItemGood;
import com.slavian.study.piramida.adapter.MyListGoogsAdapter;
import com.slavian.study.piramida.entities.Good;
import com.slavian.study.piramida.fragments.PiramidaFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GoodsActivity extends AppCompatActivity {
    private ListView listViewGoods;
    private EditText editSearch;

    public static ArrayList<ListItemGood> goodlist;
    public static ArrayList<ListItemGood> goodlist1;
    private ArrayList<Integer> iconsPh;
    private ArrayList<Integer> iconsLap;
    private ArrayList<Integer> iconsDesk;
    private static ArrayList<String> descriptions;
    public static ListItemGood selected;
    public static Good selectedGood;
    String s="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        goodlist = new ArrayList<>();
        goodlist1 = new ArrayList<>();
        for (int i=0;i<MainActivity.goods.size();i++){
            Good good = MainActivity.goods.get(i);

            String ab="";
            String textName = good.getDescription();
            //int text = getResources().getIdentifier(textName , "raw", getPackageName());
            int text=0;
            try {
                text = R.raw.class.getField(textName).getInt(getResources());
            }
            catch(Exception e){
                e.printStackTrace();
            }
            try {
                InputStream input = getResources().openRawResource(text);
                if (input != null) {
                    InputStreamReader isr = new InputStreamReader(input);
                    BufferedReader reader = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();

                    try {
                        while ((line = reader.readLine()) != null) {
                            builder.append(line+"\n");
                        }
                        input.close();
                    } catch (Exception ex) {
                    }

                    ab = builder.toString();
                }
            }catch(Exception ex){ex.printStackTrace();}

            String mDrawableName = good.getIcon();

            int ic = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
            if(PiramidaFragment.cat.equals(good.getCategory())) {
                goodlist.add(new ListItemGood(ab, good.getGood_name(), good.getPrice(), ic, good.getGood_id()));
                goodlist1.add(new ListItemGood(ab, good.getGood_name(), good.getPrice(), ic, good.getGood_id()));
            }
        }

        final MyListGoogsAdapter adapter = new MyListGoogsAdapter(this, R.layout.pattern_good_list, goodlist,false);

        listViewGoods = (ListView)findViewById(R.id.list_view_goods);

        View header = (View)getLayoutInflater().inflate(R.layout.header, null);
        listViewGoods.addHeaderView(header);
        listViewGoods.setAdapter(adapter);
        listViewGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3){
                //switch (position)
                selected = goodlist1.get(position-1);
                for (Good g:MainActivity.goods) {
                    if(selected.good_id==g.getGood_id()) {
                        selectedGood = new Good(g);
                    }
                }
                Intent intent = new Intent(getApplicationContext(), GoodPage.class);
                startActivity(intent);
            }
        });

        editSearch = (EditText)findViewById(R.id.editTextSearch);
        //editSearch.setOnFocusChangeListener();
        editSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                MyListGoogsAdapter adapter1 = new MyListGoogsAdapter(GoodsActivity.this, R.layout.pattern_good_list, goodlist1,false);
                listViewGoods.setAdapter(null);
                listViewGoods.setAdapter(adapter1);
                editSearch.requestFocus();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                goodlist1 = new ArrayList<>();
                for (ListItemGood lig:goodlist){
                    if(lig.description.contains(editSearch.getText())) {
                        goodlist1.add(lig);
                    }
                }
                editSearch.requestFocus();
            }
        });
    }
}
