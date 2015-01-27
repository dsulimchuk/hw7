package com.ds.auction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bid {
    private int id;
    private Product product;
    private BigDecimal amount;
    private int desiredQuantity; // How many items the user wants
    private User user;
    private LocalDateTime bidTime;
    private boolean isWinning;

    public Bid(Product product, BigDecimal amount, int desiredQuantity, User user) {
        this.product = product;
        this.amount = amount;
        this.desiredQuantity = desiredQuantity;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getDesiredQuantity() {
        return desiredQuantity;
    }

    public void setDesiredQuantity(int desiredQuantity) {
        this.desiredQuantity = desiredQuantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }

    public boolean isWinning() {
        return isWinning;
    }

    public void setWinning(boolean isWinning) {
        this.isWinning = isWinning;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "product=" + product +
                ", amount=" + amount +
                ", desiredQuantity=" + desiredQuantity +
                ", user=" + user +
                '}';
    }
}
