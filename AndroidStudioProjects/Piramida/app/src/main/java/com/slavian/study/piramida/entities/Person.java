package com.slavian.study.piramida.entities;

/**
 * Created by Slavian on 1/3/2017.
 */

public class Person {
    private String e_mail;
    private String name;
    private String password;
    private String surname;
    private long user_id;

    public Person(){}
    public Person(long user_id, String name, String surname, String e_mail, String password){
        this.user_id=user_id;
        this.name=name;
        this.surname=surname;
        this.password=password;
        this.e_mail=e_mail;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUser_Id() {
        return user_id;
    }

    public void setUser_Id(long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
