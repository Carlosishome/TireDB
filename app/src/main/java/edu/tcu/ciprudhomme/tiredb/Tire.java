package edu.tcu.ciprudhomme.tiredb;

import java.util.ArrayList;
import java.util.Date;

public class Tire {
    public static ArrayList<Tire> tireArrayList = new ArrayList<>();
    public static String TIRE_EDIT_EXTRA = "tireEdit";

    private int id;
    private String tireSize;
    private String dot;
    private int treadLife;
    private String brand;
    private int quantity;
    private boolean hasPatches;
    private String location;
    private Date deleted;

    // Constructor for creating a tire object
    public Tire(int id, String tireSize, String dot, int treadLife, String brand, int quantity, boolean hasPatches, String location, Date deleted) {
        this.id = id;
        this.tireSize = tireSize;
        this.dot = dot;
        this.treadLife = treadLife;
        this.brand = brand;
        this.quantity = quantity;
        this.hasPatches = hasPatches;
        this.location = location;
        this.deleted = deleted;
    }

    // Constructor without deletion info
    public Tire(int id, String tireSize, String dot, int treadLife, String brand, int quantity, boolean hasPatches, String location) {
        this.id = id;
        this.tireSize = tireSize;
        this.dot = dot;
        this.treadLife = treadLife;
        this.brand = brand;
        this.quantity = quantity;
        this.hasPatches = hasPatches;
        this.location = location;
        deleted = null;
    }

    // Getters and setters for each field
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTireSize() {
        return tireSize;
    }

    public void setTireSize(String tireSize) {
        this.tireSize = tireSize;
    }

    public String getDot() {
        return dot;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

    public int getTreadLife() {
        return treadLife;
    }

    public void setTreadLife(int treadLife) {
        this.treadLife = treadLife;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean hasPatches() {
        return hasPatches;
    }

    public void setHasPatches(boolean hasPatches) {
        this.hasPatches = hasPatches;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    public static ArrayList<Tire> nonDeletedTires() {
        ArrayList<Tire> nonDeleted = new ArrayList<>();
        for (Tire tire : tireArrayList) {
            if (tire.getDeleted() == null)
                nonDeleted.add(tire);
        }
        return nonDeleted;
    }

    public static Tire getTireForID(int passedTireID) {
        for (Tire tire : tireArrayList) {
            if (tire.getId() == passedTireID)
                return tire;
        }
        return null;
    }
}
