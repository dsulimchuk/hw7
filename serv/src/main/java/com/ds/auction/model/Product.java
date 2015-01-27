package com.ds.auction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Product {
    private int id;
    private String title;
    private String thumb;
    private String description;
    private int quantity;   // How many items the seller has
    private LocalDateTime auctionEndTime;
    private int watchers;
    private BigDecimal minimalPrice;     // Don't sell unless the bid is more than min price
    private BigDecimal reservedPrice;   // If a bidder offers reserved price, the auction is closed

    public Product(String title, int quantity, LocalDateTime auctionEndTime, BigDecimal minimalPrice, BigDecimal reservedPrice) {
        this.title = title;
        this.quantity = quantity;
        this.auctionEndTime = auctionEndTime;
        this.minimalPrice = minimalPrice;
        this.reservedPrice = reservedPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(LocalDateTime auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public BigDecimal getMinimalPrice() {
        return minimalPrice;
    }

    public void setMinimalPrice(BigDecimal minimalPrice) {
        this.minimalPrice = minimalPrice;
    }

    public BigDecimal getReservedPrice() {
        return reservedPrice;
    }

    public void setReservedPrice(BigDecimal reservedPrice) {
        this.reservedPrice = reservedPrice;
    }

    @Override
    public String toString() {
        return "{" +
                "'" + title + '\'' +
                ", Qty=" + quantity +
                ", " + minimalPrice +
                " - " + reservedPrice +
                '}';
    }
}
