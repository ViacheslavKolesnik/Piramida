package com.slavian.study.piramida.entities;

/**
 * Created by Slavian on 1/11/2017.
 */

public class OrderEntity {
    private String address;
    private long delivery_id;
    private String order_date;
    private long order_id;
    private long pay_service_id;
    private long user_id;

    public OrderEntity(){}

    public OrderEntity(String address, long delivery_id, String order_date, long order_id, long pay_service_id, long user_id) {
        this.address=address;
        this.delivery_id = delivery_id;
        this.order_date = order_date;
        this.order_id = order_id;
        this.pay_service_id = pay_service_id;
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(long delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getPay_service_id() {
        return pay_service_id;
    }

    public void setPay_service_id(long pay_service_id) {
        this.pay_service_id = pay_service_id;
    }
}
