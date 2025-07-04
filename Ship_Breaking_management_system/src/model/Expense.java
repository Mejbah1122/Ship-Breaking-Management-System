package model;

import java.time.LocalDate;

public class Expense {
    private int expenseId;
    private int machineId;
    private double craneFee;
    private double deliveryFee;
    private double laborFee;
    private double brokerFee;
    private double wastageCost;
    private LocalDate expenseDate;
    private String notes;

    public Expense() {}

    public Expense(int machineId, double craneFee, double deliveryFee, double laborFee, double brokerFee, double wastageCost, LocalDate expenseDate, String notes) {
        this.machineId = machineId;
        this.craneFee = craneFee;
        this.deliveryFee = deliveryFee;
        this.laborFee = laborFee;
        this.brokerFee = brokerFee;
        this.wastageCost = wastageCost;
        this.expenseDate = expenseDate;
        this.notes = notes;
    }

    // Getters and setters
    public int getExpenseId() { return expenseId; }
    public void setExpenseId(int expenseId) { this.expenseId = expenseId; }

    public int getMachineId() { return machineId; }
    public void setMachineId(int machineId) { this.machineId = machineId; }

    public double getCraneFee() { return craneFee; }
    public void setCraneFee(double craneFee) { this.craneFee = craneFee; }

    public double getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }

    public double getLaborFee() { return laborFee; }
    public void setLaborFee(double laborFee) { this.laborFee = laborFee; }

    public double getBrokerFee() { return brokerFee; }
    public void setBrokerFee(double brokerFee) { this.brokerFee = brokerFee; }

    public double getWastageCost() { return wastageCost; }
    public void setWastageCost(double wastageCost) { this.wastageCost = wastageCost; }

    public LocalDate getExpenseDate() { return expenseDate; }
    public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
