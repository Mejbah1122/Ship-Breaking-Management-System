package model;

import java.time.LocalDate;

public class SalesEvent {
    private int saleId;
    private String buyerName;
    private LocalDate saleDate;
    private String notes;

    // Constructor with ID (for fetching from DB)
    public SalesEvent(int saleId, String buyerName, LocalDate saleDate, String notes) {
        this.saleId = saleId;
        this.buyerName = buyerName;
        this.saleDate = saleDate;
        this.notes = notes;
    }

    // Constructor without ID (for inserting new records)
    public SalesEvent(String buyerName, LocalDate saleDate, String notes) {
        this.buyerName = buyerName;
        this.saleDate = saleDate;
        this.notes = notes;
    }

    // Getters and Setters

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
