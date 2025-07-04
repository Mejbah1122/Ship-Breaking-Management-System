package model;

public class PartSale {
    private int partSaleId;
    private int partId;
    private int saleId;
    private int quantity;
    private double sellingPrice;
    private double meltingPrice;

    public PartSale() {}

    public PartSale(int partId, int saleId, int quantity, double sellingPrice, double meltingPrice) {
        this.partId = partId;
        this.saleId = saleId;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.meltingPrice = meltingPrice;
    }

    // Getters and setters
    public int getPartSaleId() { return partSaleId; }
    public void setPartSaleId(int partSaleId) { this.partSaleId = partSaleId; }

    public int getPartId() { return partId; }
    public void setPartId(int partId) { this.partId = partId; }

    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }

    public double getMeltingPrice() { return meltingPrice; }
    public void setMeltingPrice(double meltingPrice) { this.meltingPrice = meltingPrice; }
}
