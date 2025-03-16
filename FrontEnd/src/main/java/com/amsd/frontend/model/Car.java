package com.amsd.frontend.model;

public class Car{
    private static long carCounter = 0;
    private Long id;
    private String plateNumber;
    private int price;
    private boolean rented;

    // Constructeurs
    public Car() {
        this.id = ++carCounter;
    }

    public Car( String plateNumber, int price, boolean rented) {
        this.id = ++carCounter;
        this.plateNumber = plateNumber;
        this.price = price;
        this.rented = rented;
    }

    // Getters et setters
    public Long getId() { return id; }
    public String getPlateNumber() { return plateNumber; }
    public int getPrice() { return price; }
    public boolean isRented() { return rented; }

    public void setId(Long id) { this.id = id; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public void setPrice(int price) { this.price = price; }
    public void setRented(boolean rented) { this.rented = rented; }

    public static void setCarCounter(long value) {
        carCounter = value;
    }
}
