package com.uay.aws.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Order {

    private String id;
    private String itemName;
    private long quantity;
    private String customer;
    private String status;
    private Date date;

    @JsonCreator
    public Order(@JsonProperty("id") String id,
                 @JsonProperty("itemName") String itemName,
                 @JsonProperty("quantity") long quantity,
                 @JsonProperty("customer") String customer,
                 @JsonProperty("status") String status) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.customer = customer;
        this.status = status;
        this.date = new Date();
    }

    public String getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getCustomer() {
        return customer;
    }

    public String getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}