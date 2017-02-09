package com.slavian.study.piramida;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.slavian.study.piramida.entities.Good;
import com.slavian.study.piramida.entities.OrderEntity;
import com.slavian.study.piramida.entities.OrderList;
import com.slavian.study.piramida.entities.Person;
import com.slavian.study.piramida.fragments.BasketFragment;
import com.slavian.study.piramida.fragments.InfoFragment;
import com.slavian.study.piramida.fragments.OrdersFragment;
import com.slavian.study.piramida.fragments.PiramidaFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static boolean logged_in=false;
    public static long current_id;
    public static String s="";
    public static ArrayList<Person> personlist;
    public static ArrayList<Good> goods;
    public static ArrayList<OrderEntity> orderArr;
    public static ArrayList<OrderList> orderListArr;

    private DatabaseReference mSimpleFirechatDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new PiramidaFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.rel_layout, fragment).commit();

        getGoods();
        DBGet();

        mSimpleFirechatDatabaseReference = FirebaseDatabase.getInstance().getReference().child("person");

        personlist = new ArrayList<>();
        mSimpleFirechatDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personlist = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Person person = data.getValue(Person.class);
                    personlist.add(person);
                    s+=person.getUser_Id()+" "+ person.getName()+" "+person.getSurname()+" "+ person.getE_mail()+" "+person.getPassword()+"\n";
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logged_in=false;
            current_id=0;
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        selectItem(id);
        return true;
    }

    private void selectItem(int id) {
        // Update the main content by replacing fragments
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_piramida:
                fragment = new PiramidaFragment();
                break;
            case R.id.nav_account:
                fragment = new OrdersFragment();
                break;
            case R.id.nav_basket:
                fragment = new BasketFragment();
                break;
            case R.id.nav_info:
                fragment = new InfoFragment();
                break;
            default:
                break;
        }

        // Insert the fragment by replacing any existing fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.rel_layout, fragment).commit();

            // Highlight the selected item, update the title, and close the drawer
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // Error
            Log.e(this.getClass().getName(), "Error. Fragment is not created");
        }
    }
    private void getGoods(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("good");

        s="";
        goods = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                goods = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Good good = data.getValue(Good.class);
                    goods.add(good);
                    s+=good.getGood_name()+" "+ good.getPrice()+" ";
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void DBGet(){
        mSimpleFirechatDatabaseReference = FirebaseDatabase.getInstance().getReference().child("order");

        orderArr = new ArrayList<>();
        mSimpleFirechatDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderArr = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    OrderEntity person = data.getValue(OrderEntity.class);
                    orderArr.add(person);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSimpleFirechatDatabaseReference = FirebaseDatabase.getInstance().getReference().child("order_list");

        orderListArr = new ArrayList<>();
        mSimpleFirechatDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderListArr = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    OrderList person = data.getValue(OrderList.class);
                    orderListArr.add(person);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
