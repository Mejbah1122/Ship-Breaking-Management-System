package model;

public class Part {
    private int partId;
    private int machineId;
    private String partName;
    private String category;
    private double weightKg;
    private double wastageWeightKg;
    private double minSellingPrice;
    private String status;

    // Default constructor
    public Part() {}

    // Full constructor (with wastage weight)
    public Part(int machineId, String partName, String category, double weightKg, double wastageWeightKg, double minSellingPrice, String status) {
        this.machineId = machineId;
        this.partName = partName;
        this.category = category;
        this.weightKg = weightKg;
        this.wastageWeightKg = wastageWeightKg;
        this.minSellingPrice = minSellingPrice;
        this.status = status;
    }

    // Overloaded constructor (without wastage weight)
    public Part(int machineId, String partName, String category, double weightKg, double minSellingPrice, String status) {
        this.machineId = machineId;
        this.partName = partName;
        this.category = category;
        this.weightKg = weightKg;
        this.wastageWeightKg = 0.0; // default value
        this.minSellingPrice = minSellingPrice;
        this.status = status;
    }

    // Getters and setters

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public double getWastageWeightKg() {
        return wastageWeightKg;
    }

    public void setWastageWeightKg(double wastageWeightKg) {
        this.wastageWeightKg = wastageWeightKg;
    }

    public double getMinSellingPrice() {
        return minSellingPrice;
    }

    public void setMinSellingPrice(double minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
