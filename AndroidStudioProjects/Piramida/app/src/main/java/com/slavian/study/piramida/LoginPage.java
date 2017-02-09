package com.slavian.study.piramida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.slavian.study.piramida.entities.Person;

import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }
    public void logIn(View view){
        String email = ((EditText)findViewById(R.id.editTextEmail)).getText().toString();
        String pass = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        for(Person person: MainActivity.personlist) {
            if (email.equals(person.getE_mail()) && pass.equals(person.getPassword())) {
                MainActivity.current_id = person.getUser_Id();
                MainActivity.logged_in = true;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
        if(!MainActivity.logged_in){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Неверный e-mail или пароль!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void NewAccount(View view){
        Intent intent = new Intent(this, RegistrationPage.class);
        startActivity(intent);
    }
}
