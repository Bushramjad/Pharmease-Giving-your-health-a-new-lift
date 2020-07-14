package com.example.pharmease.pharmacy;
import com.google.gson.annotations.SerializedName;

public class MedicineDataClass {

    @SerializedName("name")
    private String name = "";

    @SerializedName("price")
    private String price = "";

    @SerializedName("quantity")
    private String quantity = "";

    @SerializedName("brand")
    private String brand = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}