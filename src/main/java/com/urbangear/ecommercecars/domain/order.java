// com/urbangear/ecommercecars/model/carRequested.java

package com.urbangear.ecommercecars.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "items_name")
    private String itemsName;

    @Column(name = "items")
    private int items;

    @Column(name = "total_price")
    private double total_price;

    @Column(name = "users")
    private String user;


    // Getters and setters

    public order() {
    }

    public order(Long id, String itemsName, int items, double total_price, String user) {
        this.id = id;
        this.itemsName = itemsName;
        this.items = items;
        this.total_price = total_price;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
