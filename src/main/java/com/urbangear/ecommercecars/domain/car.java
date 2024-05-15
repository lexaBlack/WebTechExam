package com.urbangear.ecommercecars.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "category")
    private String category;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    // Additional property for decoded image data
    @Transient
    private byte[] imageData;

    // Constructors, getters, setters, and other fields

    // Default constructor
    public car() {
    }

    // Parameterized constructor without ID
    public car(String itemName, String description, double price, String category, byte[] image) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
    }

    // Getters and setters...

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
