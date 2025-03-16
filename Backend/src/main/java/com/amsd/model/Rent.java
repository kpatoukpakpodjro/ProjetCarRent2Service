package com.amsd.model;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class Rent implements HasId{

    private Long id;
    private Car car;
    private User user;
    private static long rentCounter = 0;
    private LocalDate beginDate;
    private LocalDate endDate;
    private float invoice;

    // Constructeur sans arguments (obligatoire pour la désérialisation)
    public Rent() {
        this.id = ++rentCounter;
    }

    public Rent( Car car, User user, LocalDate beginDate, LocalDate endDate) {
        this.id = ++rentCounter;
        this.car = car;
        this.user = user;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.invoice = 0;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public Car getCar() { return car; }
    public User getUser() { return user; }
    public LocalDate getBeginDate() { return beginDate; }
    public LocalDate getEndDate() { return endDate; }
    public float getInvoice() { return invoice; }

    public void setId(Long id) { this.id = id; }
    public void setCar(Car car) { this.car = car; }
    public void setUser(User user) { this.user = user; }
    public void setBeginDate(LocalDate beginDate) { this.beginDate = beginDate; }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        if (this.beginDate != null && this.endDate != null) {
            Long daysBetween = ChronoUnit.DAYS.between(this.endDate, this.beginDate);
            daysBetween = daysBetween + 1;
            this.invoice = daysBetween * this.car.getPrice();
        }
    }
    public void setInvoice(float invoice) { this.invoice = invoice; }

    public static void setRentCounter(long value) {
        rentCounter = value;
    }
}