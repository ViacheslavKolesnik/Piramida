package com.slavian.study.piramida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.slavian.study.piramida.adapter.ListItemGood;
import com.slavian.study.piramida.entities.Good;
import com.slavian.study.piramida.entities.OrderEntity;
import com.slavian.study.piramida.entities.OrderList;
import com.slavian.study.piramida.entities.Person;
import com.slavian.study.piramida.fragments.BasketFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.slavian.study.piramida.MainActivity.orderArr;

public class Order extends AppCompatActivity {
    private DatabaseReference mSimpleFirechatDatabaseReference;

    private TextView goodsNumber;
    private TextView price;
    private EditText editAddress;

    private RadioGroup radioGroup;
    private RadioGroup radioGroup2;
    private RadioButton radioButtonNovaPoshta;
    private RadioButton radioButtonUkrPoshta;
    private RadioButton radioButtonPrivat;
    private RadioButton radioButtonCash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mSimpleFirechatDatabaseReference = FirebaseDatabase.getInstance().getReference();
        goodsNumber = (TextView)findViewById(R.id.textViewOrderGoodsNumber);
        price = (TextView)findViewById(R.id.textViewFormOrderPrice);
        editAddress=(EditText)findViewById(R.id.editTextAddress);

        int num =0;
        for (Integer i: BasketFragment.basketListNum)
            num+=i;
        goodsNumber.setText(num+"");
        int pr = 0;
        for (int i=0;i<BasketFragment.basketList.size();i++){
            ListItemGood lig= BasketFragment.basketList.get(i);
            pr+=Integer.parseInt(lig.price)*BasketFragment.basketListNum.get(i);
        }
        price.setText(pr+"");

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupPayment);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroupDelivery);
        radioButtonNovaPoshta = (RadioButton) findViewById(R.id.radioButtonNewPost);
        radioButtonUkrPoshta = (RadioButton) findViewById(R.id.radioButtonUkrPost);
        radioButtonPrivat = (RadioButton) findViewById(R.id.radioButtonPrivat24);
        radioButtonCash = (RadioButton) findViewById(R.id.radioButtonCash);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(((RadioButton)findViewById(R.id.radioButtonUkrPost)).isChecked()){
                    if(((RadioButton)findViewById(R.id.radioButtonCash)).isChecked()) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Наложенный платёж доступен только для службы доставки \"Нова пошта\"", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    radioButtonPrivat.setChecked(true);
                }
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radioButtonUkrPost)
                    radioButtonPrivat.setChecked(true);
            }
        });
    }
    public void saveOrder(View view){
        try {
            String address;
            long del_id = 0;
            String or_date;
            long or_id = 0;
            long pay_id = 0;
            long us_id = 0;
            for (OrderEntity p : orderArr) {
                if (p.getOrder_id() > or_id)
                    or_id = p.getOrder_id();
            }
            or_id++;

            if (radioButtonNovaPoshta.isChecked())
                del_id = 1;
            else
                del_id = 2;

            address = editAddress.getText().toString();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            or_date = dateFormat.format(new Date());

            if (radioButtonPrivat.isChecked())
                pay_id = 1;
            else
                pay_id = 2;

            us_id = MainActivity.current_id;

            OrderEntity orderEntity = new OrderEntity(address, del_id, or_date, or_id, pay_id, us_id);
            mSimpleFirechatDatabaseReference.child("order").push().setValue(orderEntity);

            for (int i = 0; i < BasketFragment.basketList.size(); i++) {
                OrderList ol = new OrderList(BasketFragment.basketList.get(i).good_id, BasketFragment.basketListNum.get(i), or_id);
                mSimpleFirechatDatabaseReference.child("order_list").push().setValue(ol);
                String das =BasketFragment.basketList.get(i).good_id+"";
                long gc=0;
                for (Good g:MainActivity.goods){
                    if(g.getGood_id()==BasketFragment.basketList.get(i).good_id)
                        gc=g.getGood_count();
                }
                long ddas=gc-BasketFragment.basketListNum.get(i);
                mSimpleFirechatDatabaseReference.child("good").child(das).child("good_count").setValue(ddas);
            }
            Intent intent = new Intent(this, SuccessfullOrder.class);
            startActivity(intent);
        }
        catch(Exception ex){
            ex.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Не удалось подтвердить заказ!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
