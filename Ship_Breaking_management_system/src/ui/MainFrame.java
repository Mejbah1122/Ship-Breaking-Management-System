package ui;

import dao.*;
import model.*;
import model.SalesEvent;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    private DefaultTableModel partTableModel, machineTableModel, expenseTableModel, saleTableModel, partSaleTableModel, saleServiceTableModel;
    private JTable partTable, machineTable, expenseTable, saleTable, partSaleTable, saleServiceTable;
    private JTextField partNameField, categoryField, weightField, wastageField, statusField, machineIdField;
    private JTextField machineNameField, buyingPriceField, buyingDateField, machineWeightField, boughtFromField;
    private JTextField craneFeeField, deliveryFeeField, laborFeeField, brokerFeeField, wastageCostField, expenseDateField, expenseNoteField, expenseMachineIdField;
    private JTextField saleDateField, soldToField, totalAmountField;
    private JTextField partSalePartIdField, partSaleSaleIdField, partSaleQtyField, partSalePriceField, partSaleMeltingPriceField;
    private JTextField saleServiceBuyerField, saleServiceDateField, saleServicePartIdField, saleServiceQtyField, saleServicePriceField, saleServiceMeltingField;
    private JButton addPartButton, addMachineButton, addExpenseButton, addSaleButton, addPartSaleButton, addSaleServiceButton;
    private JTextField minSellingPriceField;


    public MainFrame() {
        setTitle("Ship Breaking Management System");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        setupMachineTab();
        setupPartTab();
        setupExpenseTab();
        setupSalesTab();
        setupPartSalesTab();
        setupSaleServiceTab();

        add(tabbedPane);
    }

    private void setupMachineTab() {
        JPanel panel = new JPanel(new BorderLayout());

        machineTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Date", "Weight", "From"}, 0);
        machineTable = new JTable(machineTableModel);
        panel.add(new JScrollPane(machineTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(3, 4));
        machineNameField = new JTextField(); buyingPriceField = new JTextField(); buyingDateField = new JTextField();
        machineWeightField = new JTextField(); boughtFromField = new JTextField();
        addMachineButton = new JButton("Add Machine");

        form.add(new JLabel("Name")); form.add(machineNameField);
        form.add(new JLabel("Price")); form.add(buyingPriceField);
        form.add(new JLabel("Date (YYYY-MM-DD)")); form.add(buyingDateField);
        form.add(new JLabel("Weight (kg)")); form.add(machineWeightField);
        form.add(new JLabel("From")); form.add(boughtFromField);
        form.add(new JLabel("")); form.add(addMachineButton);

        panel.add(form, BorderLayout.SOUTH);
        tabbedPane.addTab("Machines", panel);

        refreshMachineTable();

        addMachineButton.addActionListener(e -> {
            try {
                Machine m = new Machine(machineNameField.getText(), Double.parseDouble(buyingPriceField.getText()),
                        LocalDate.parse(buyingDateField.getText()), Double.parseDouble(machineWeightField.getText()), boughtFromField.getText());

                new MachineDAO().insert(m);
                refreshMachineTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    private void refreshMachineTable() {
        try {
            machineTableModel.setRowCount(0);
            List<Machine> list = new MachineDAO().getAll();
            for (Machine m : list) {
                machineTableModel.addRow(new Object[]{m.getMachineId(), m.getMachineName(), m.getBuyingPrice(),
                        m.getBuyingDate(), m.getMachineWeightKg(), m.getBoughtFrom()});
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

   private void setupPartTab() {
    JPanel panel = new JPanel(new BorderLayout());

    partTableModel = new DefaultTableModel(new String[]{
        "ID", "Machine ID", "Name", "Category", "Weight", "Wastage", "Min Price", "Status"
    }, 0);
    partTable = new JTable(partTableModel);
    panel.add(new JScrollPane(partTable), BorderLayout.CENTER);

    // Create input fields
    machineIdField = new JTextField();
    partNameField = new JTextField();
    categoryField = new JTextField();
    weightField = new JTextField();
    wastageField = new JTextField();
    minSellingPriceField = new JTextField(); // NEW
    statusField = new JTextField();
    addPartButton = new JButton("Add Part");

    // Form layout: now 4 rows, 4 columns to fit 7 fields + button
    JPanel form = new JPanel(new GridLayout(4, 4, 5, 5)); // Added spacing for clarity

    form.add(new JLabel("Machine ID")); form.add(machineIdField);
    form.add(new JLabel("Part Name")); form.add(partNameField);
    form.add(new JLabel("Category")); form.add(categoryField);
    form.add(new JLabel("Weight (kg)")); form.add(weightField);
    form.add(new JLabel("Wastage (kg)")); form.add(wastageField);
    form.add(new JLabel("Min Selling Price")); form.add(minSellingPriceField); // NEW
    form.add(new JLabel("Status")); form.add(statusField);
    form.add(new JLabel("")); form.add(addPartButton); // Empty label for spacing

    panel.add(form, BorderLayout.SOUTH);
    tabbedPane.addTab("Parts", panel);

    refreshPartTable();

    addPartButton.addActionListener(e -> {
        try {
            Part p = new Part(
                Integer.parseInt(machineIdField.getText()),
                partNameField.getText(),
                categoryField.getText(),
                Double.parseDouble(weightField.getText()),
                Double.parseDouble(wastageField.getText()),
                Double.parseDouble(minSellingPriceField.getText()), // NEW
                statusField.getText()
            );
            new PartDAO().insert(p);
            refreshPartTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    });
}


    private void refreshPartTable() {
        try {
            partTableModel.setRowCount(0);
            List<Part> list = new PartDAO().getAll();
            for (Part p : list) {
                partTableModel.addRow(new Object[]{p.getPartId(), p.getMachineId(), p.getPartName(), p.getCategory(),
                        p.getWeightKg(), p.getWastageWeightKg(), p.getStatus()});
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void setupExpenseTab() {
        JPanel panel = new JPanel(new BorderLayout());

        expenseTableModel = new DefaultTableModel(new String[]{"ID", "Machine ID", "Crane Fee", "Delivery Fee", "Labor Fee", "Broker Fee", "Wastage Cost", "Date", "Notes"}, 0);
        expenseTable = new JTable(expenseTableModel);
        panel.add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(5, 4));
        expenseMachineIdField = new JTextField(); craneFeeField = new JTextField(); deliveryFeeField = new JTextField();
        laborFeeField = new JTextField(); brokerFeeField = new JTextField(); wastageCostField = new JTextField();
        expenseDateField = new JTextField(); expenseNoteField = new JTextField();
        addExpenseButton = new JButton("Add Expense");

        form.add(new JLabel("Machine ID")); form.add(expenseMachineIdField);
        form.add(new JLabel("Crane Fee")); form.add(craneFeeField);
        form.add(new JLabel("Delivery Fee")); form.add(deliveryFeeField);
        form.add(new JLabel("Labor Fee")); form.add(laborFeeField);
        form.add(new JLabel("Broker Fee")); form.add(brokerFeeField);
        form.add(new JLabel("Wastage Cost")); form.add(wastageCostField);
        form.add(new JLabel("Expense Date (YYYY-MM-DD)")); form.add(expenseDateField);
        form.add(new JLabel("Notes")); form.add(expenseNoteField);
        form.add(new JLabel("")); form.add(addExpenseButton);

        panel.add(form, BorderLayout.SOUTH);
        tabbedPane.addTab("Expenses", panel);

        refreshExpenseTable();

        addExpenseButton.addActionListener(e -> {
            try {
                Expense e1 = new Expense(Integer.parseInt(expenseMachineIdField.getText()), Double.parseDouble(craneFeeField.getText()),
                        Double.parseDouble(deliveryFeeField.getText()), Double.parseDouble(laborFeeField.getText()),
                        Double.parseDouble(brokerFeeField.getText()), Double.parseDouble(wastageCostField.getText()),
                        LocalDate.parse(expenseDateField.getText()), expenseNoteField.getText());
                new ExpenseDAO().insert(e1);
                refreshExpenseTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    private void refreshExpenseTable() {
        try {
            expenseTableModel.setRowCount(0);
            List<Expense> list = new ExpenseDAO().getAll();
            for (Expense e : list) {
                expenseTableModel.addRow(new Object[]{e.getExpenseId(), e.getMachineId(), e.getCraneFee(), e.getDeliveryFee(),
                        e.getLaborFee(), e.getBrokerFee(), e.getWastageCost(), e.getExpenseDate(), e.getNotes()});
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void setupSalesTab() {
        JPanel panel = new JPanel(new BorderLayout());

        saleTableModel = new DefaultTableModel(new String[]{"ID", "Buyer", "Date", "Notes"}, 0);
        saleTable = new JTable(saleTableModel);
        panel.add(new JScrollPane(saleTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 4));
        soldToField = new JTextField();
        saleDateField = new JTextField(LocalDate.now().toString());
        addSaleButton = new JButton("Add Sale");

        form.add(new JLabel("Buyer Name")); form.add(soldToField);
        form.add(new JLabel("Sale Date (YYYY-MM-DD)")); form.add(saleDateField);
        form.add(new JLabel("")); form.add(addSaleButton);

        panel.add(form, BorderLayout.SOUTH);
        tabbedPane.addTab("Sales Events", panel);

        refreshSaleTable();

        addSaleButton.addActionListener(e -> {
            try {
                SalesEvent se = new SalesEvent(soldToField.getText(),
                        LocalDate.parse(saleDateField.getText()), "");
                new SalesEventDAO().insert(se);
                refreshSaleTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    private void refreshSaleTable() {
        try {
            saleTableModel.setRowCount(0);
            List<SalesEvent> list = new SalesEventDAO().getAll();
            for (SalesEvent se : list) {
                saleTableModel.addRow(new Object[]{se.getSaleId(), se.getBuyerName(), se.getSaleDate(), se.getNotes()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupPartSalesTab() {
        JPanel panel = new JPanel(new BorderLayout());

        partSaleTableModel = new DefaultTableModel(new String[]{"ID", "Part ID", "Sale ID", "Quantity", "Selling Price", "Melting Price"}, 0);
        partSaleTable = new JTable(partSaleTableModel);
        panel.add(new JScrollPane(partSaleTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(3, 4));
        partSalePartIdField = new JTextField();
        partSaleSaleIdField = new JTextField();
        partSaleQtyField = new JTextField("1");
        partSalePriceField = new JTextField();
        partSaleMeltingPriceField = new JTextField();
        addPartSaleButton = new JButton("Add Part Sale");

        form.add(new JLabel("Part ID")); form.add(partSalePartIdField);
        form.add(new JLabel("Sale ID")); form.add(partSaleSaleIdField);
        form.add(new JLabel("Quantity")); form.add(partSaleQtyField);
        form.add(new JLabel("Selling Price")); form.add(partSalePriceField);
        form.add(new JLabel("Melting Price")); form.add(partSaleMeltingPriceField);
        form.add(new JLabel("")); form.add(addPartSaleButton);

        panel.add(form, BorderLayout.SOUTH);
        tabbedPane.addTab("Part Sales", panel);

        refreshPartSaleTable();

        addPartSaleButton.addActionListener(e -> {
            try {
                PartSale ps = new PartSale(Integer.parseInt(partSalePartIdField.getText()),
                        Integer.parseInt(partSaleSaleIdField.getText()),
                        Integer.parseInt(partSaleQtyField.getText()),
                        Double.parseDouble(partSalePriceField.getText()),
                        Double.parseDouble(partSaleMeltingPriceField.getText()));
                new PartSaleDAO().insert(ps);
                refreshPartSaleTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    private void refreshPartSaleTable() {
        try {
            partSaleTableModel.setRowCount(0);
            List<PartSale> list = new PartSaleDAO().getAll();
            for (PartSale ps : list) {
                partSaleTableModel.addRow(new Object[]{ps.getPartSaleId(), ps.getPartId(), ps.getSaleId(), ps.getQuantity(), ps.getSellingPrice(), ps.getMeltingPrice()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupSaleServiceTab() {
        JPanel panel = new JPanel(new BorderLayout());

        saleServiceTableModel = new DefaultTableModel(new String[]{"Buyer", "Date", "Part ID", "Qty", "Price", "Melting"}, 0);
        saleServiceTable = new JTable(saleServiceTableModel);
        panel.add(new JScrollPane(saleServiceTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(4, 4));
        saleServiceBuyerField = new JTextField();
        saleServiceDateField = new JTextField(LocalDate.now().toString());
        saleServicePartIdField = new JTextField();
        saleServiceQtyField = new JTextField("1");
        saleServicePriceField = new JTextField();
        saleServiceMeltingField = new JTextField();
        addSaleServiceButton = new JButton("Sell Part to Buyer");

        form.add(new JLabel("Buyer Name")); form.add(saleServiceBuyerField);
        form.add(new JLabel("Sale Date")); form.add(saleServiceDateField);
        form.add(new JLabel("Part ID")); form.add(saleServicePartIdField);
        form.add(new JLabel("Quantity")); form.add(saleServiceQtyField);
        form.add(new JLabel("Selling Price")); form.add(saleServicePriceField);
        form.add(new JLabel("Melting Price")); form.add(saleServiceMeltingField);
        form.add(new JLabel("")); form.add(addSaleServiceButton);

        panel.add(form, BorderLayout.SOUTH);
        tabbedPane.addTab("Sale Service", panel);

        addSaleServiceButton.addActionListener(e -> {
            try {
                SalesEvent se = new SalesEvent(saleServiceBuyerField.getText(),
                        LocalDate.parse(saleServiceDateField.getText()), "Auto from SaleService Tab");
                int saleId = new SalesEventDAO().insertAndGetId(se);

                PartSale ps = new PartSale(Integer.parseInt(saleServicePartIdField.getText()), saleId,
                        Integer.parseInt(saleServiceQtyField.getText()),
                        Double.parseDouble(saleServicePriceField.getText()),
                        Double.parseDouble(saleServiceMeltingField.getText()));
                new PartSaleDAO().insert(ps);

                saleServiceTableModel.addRow(new Object[]{se.getBuyerName(), se.getSaleDate(),
                        ps.getPartId(), ps.getQuantity(), ps.getSellingPrice(), ps.getMeltingPrice()});
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }
}
