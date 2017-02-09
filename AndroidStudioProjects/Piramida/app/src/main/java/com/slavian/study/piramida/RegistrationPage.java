package com.slavian.study.piramida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.slavian.study.piramida.entities.Person;

import java.util.ArrayList;

public class RegistrationPage extends AppCompatActivity {
    private EditText ename;
    private EditText esurName;
    private EditText ee_mail;
    private EditText epassword;
    private EditText econfPassword;

    private DatabaseReference mDatabase;
    private ArrayList<Person> regpersonlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        ename = (EditText)findViewById(R.id.editTextName);
        esurName = (EditText)findViewById(R.id.editTextSurName);
        ee_mail = (EditText)findViewById(R.id.editTextEmail);
        epassword = (EditText)findViewById(R.id.editTextPassword);
        econfPassword = (EditText)findViewById(R.id.editTextConfirm);

        DBGet();
    }
    public void Registrate(View view){
        boolean checkE_mail=false, checkPassword=true;
        String name = ename.getText().toString();
        String surname = esurName.getText().toString();
        String e_mail = ee_mail.getText().toString();
        String password = epassword.getText().toString();
        String confpassword = econfPassword.getText().toString();

        if(e_mail.endsWith("@gmail.com")||e_mail.endsWith("@mail.ru")||e_mail.endsWith("@ukr.net"))
            checkE_mail=true;
        if(!password.equals(confpassword))
            checkPassword=false;
        if(!checkE_mail||!checkPassword){
            String message="";
            if(!checkE_mail) {
                message += "Некореектный e-mail!\n" +
                        "e-mail должен быть в виде:\n" +
                        "abcd@gmail.com/abcd@mail.ru/abcd@ukr.net";
                if(!checkPassword)
                    message+="\n";
            }
            if(!checkPassword)
                message+="Пароли в двух полях должны совпадать!";
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            boolean notplag=true;
            for(Person p:regpersonlist){
                if(p.getE_mail().equals(e_mail))
                    notplag=false;
            }
            if(notplag) {
                long max = 0;
                for (Person p : regpersonlist) {
                    if (p.getUser_Id() > max)
                        max = p.getUser_Id();
                }
                max++;
                Person person = new Person(max, name, surname, e_mail, password);
                mDatabase.push().setValue(person);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Уже существует аккаунт с таким e-mail!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    private void DBGet(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("person");

        regpersonlist = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                regpersonlist = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Person person = data.getValue(Person.class);
                    regpersonlist.add(person);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
