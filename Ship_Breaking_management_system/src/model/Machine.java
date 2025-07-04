package model;

import java.time.LocalDate;

public class Machine {
    private int machineId;
    private String machineName;
    private double buyingPrice;
    private LocalDate buyingDate;  // Changed to LocalDate
    private double machineWeightKg;
    private String boughtFrom;

    public Machine() {}

    public Machine(String machineName, double buyingPrice, LocalDate buyingDate, double machineWeightKg, String boughtFrom) {
        this.machineName = machineName;
        this.buyingPrice = buyingPrice;
        this.buyingDate = buyingDate;
        this.machineWeightKg = machineWeightKg;
        this.boughtFrom = boughtFrom;
    }

    // Getters and setters
    public int getMachineId() { return machineId; }
    public void setMachineId(int machineId) { this.machineId = machineId; }

    public String getMachineName() { return machineName; }
    public void setMachineName(String machineName) { this.machineName = machineName; }

    public double getBuyingPrice() { return buyingPrice; }
    public void setBuyingPrice(double buyingPrice) { this.buyingPrice = buyingPrice; }

    public LocalDate getBuyingDate() { return buyingDate; }
    public void setBuyingDate(LocalDate buyingDate) { this.buyingDate = buyingDate; }

    public double getMachineWeightKg() { return machineWeightKg; }
    public void setMachineWeightKg(double machineWeightKg) { this.machineWeightKg = machineWeightKg; }

    public String getBoughtFrom() { return boughtFrom; }
    public void setBoughtFrom(String boughtFrom) { this.boughtFrom = boughtFrom; }
}
