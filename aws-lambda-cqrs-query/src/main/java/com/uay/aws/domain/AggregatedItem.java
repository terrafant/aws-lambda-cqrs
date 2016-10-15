package com.uay.aws.domain;

public class AggregatedItem {

    private String itemName;
    private Long quantity;
    private Long bought;
    private Long reserved;

    public AggregatedItem() {
    }

    public AggregatedItem(String itemName, Long quantity, Long bought, Long reserved) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.bought = bought;
        this.reserved = reserved;
    }

    public AggregatedItem(String itemName, String quantity, String bought, String reserved) {
        this(itemName, Long.valueOf(quantity), Long.valueOf(bought), Long.valueOf(reserved));
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getBought() {
        return bought;
    }

    public void setBought(Long bought) {
        this.bought = bought;
    }

    public Long getReserved() {
        return reserved;
    }

    public void setReserved(Long reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "AggregatedItem{" +
                "itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", bought=" + bought +
                ", reserved=" + reserved +
                '}';
    }
}
