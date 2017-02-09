package com.slavian.study.piramida;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.slavian.study.piramida.adapter.ListItemGood;
import com.slavian.study.piramida.fragments.BasketFragment;

import static com.slavian.study.piramida.GoodsActivity.selected;

public class GoodPage extends AppCompatActivity {
    private EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_page);
        TextView name = (TextView)findViewById(R.id.textViewGoodName);
        TextView numberOfGoods = (TextView)findViewById(R.id.textViewNumberOfGoods);
        TextView price = (TextView)findViewById(R.id.textViewGoodPrice);
        TextView info = (TextView)findViewById(R.id.textViewAboutGood);
        ImageView image = (ImageView) findViewById(R.id.imageView2);
        number = (EditText) findViewById(R.id.editTextNumberOfGoods);
        ListItemGood curGood = selected;

        name.setText(curGood.description);
        numberOfGoods.setText(GoodsActivity.selectedGood.getGood_count()+"");
        price.setText(curGood.price);
        image.setImageResource(curGood.icon);
        info.setText(curGood.about);
    }
    public void addToBasket(View view){
        if(MainActivity.logged_in) {
            if(!BasketFragment.basketList.contains(selected)) {
                int count=Integer.parseInt(number.getText().toString());
                if(count<GoodsActivity.selectedGood.getGood_count()&&count>0) {
                    BasketFragment.basketList.add(selected);
                    BasketFragment.basketListNum.add(count);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Товар успешно добавлен в корзину!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Число заказываемых товаров больше чем имеется в наличии или меньше нуля!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Товар уже находится в корзине!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Для добавления товаров в корзину вам необходимо авторизоваться!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
