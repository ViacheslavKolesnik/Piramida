package com.slavian.study.piramida.entities;

/**
 * Created by Slavian on 1/11/2017.
 */

public class OrderList {
    private long good_id;
    private long o_good_number;
    private long order_id;
    public OrderList(){}

    public OrderList(long good_id, long o_good_number, long order_id) {
        this.good_id = good_id;
        this.o_good_number = o_good_number;
        this.order_id = order_id;
    }

    public long getGood_id() {
        return good_id;
    }

    public void setGood_id(long good_id) {
        this.good_id = good_id;
    }

    public long getO_good_number() {
        return o_good_number;
    }

    public void setO_good_number(long o_good_number) {
        this.o_good_number = o_good_number;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }
}
