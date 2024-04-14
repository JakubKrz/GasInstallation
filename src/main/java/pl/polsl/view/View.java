/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import pl.polsl.model.Model;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableModel;

/**
 * The View class represents the user interface of the gas installation monitoring system.
 * 
 * @author Jakub Krzywoń
 * @version 3.1
 */
public class View {
    
    /** Model object */
    private final Model model;
    /**
     * The main frame of the graphical user interface.
     */
    private JFrame frame;
    
    private JPanel pressurePanel;
    private JLabel pressureText;
    private JLabel pressure;
    private JLabel pressureUnit;
    
    private JPanel inflowPanel;
    private JLabel inflowText;
    private JLabel inflow;
    /**
     * JTextField for entering the desired gas inflow rate.
     */
    public JTextField inflowNumber;
    /**
     * JButton for setting the gas inflow rate based on the value entered in the inflowNumber JTextField.
     */
    public JButton inflowButton;
    /**
     * JButton for generating a random gas inflow rate.
     */
    public JButton randomInflow;
    
    private JPanel outflowPanel;
    private JLabel outflowText;
    private JLabel outflow;
    /**
     * JTextField for entering the desired gas outflow rate.
     */
    public JTextField outflowNumber;
    /**
     * JButton for setting the gas outflow rate based on the value entered in the outflowNumber JTextField.
     */
    public JButton outflowButton;
    /**
     * JButton for generating a random gas outflow rate.
     */
    public JButton randomOutflow;
    
    private JPanel pressureHistoryPanel;
    /**
     * JButton for saving the pressure history to a file.
     */
    public JButton saveToFile;
    /**
     * JButton for reading the pressure history from a file.
     */
    public JButton readFromFile;
    /**
     * JButton for displaying the pressure history in a table.
     */
    public JButton showHistory;
    
    private DefaultTableModel tableModel;
    private JTable pressureHistoryTable;
    private JScrollPane scrollPane;
    
    
    /**
     * Constructor 
     * Initializes scanner and constructs a View object with the specified Model instance
     * 
     * @param model The Model instance associated with this View.
     */
    public View(Model model)
    {
        this.model = model;
        this.prepareGUI();
    }
    /**
     * Prepares the graphical user interface by initializing the main frame and its components.
     * Sets up the layout, dimensions, and visual elements of the gas installation monitoring system interface.
     */
    private void prepareGUI() {
    JFrame.setDefaultLookAndFeelDecorated(true);
    ToolTipManager.sharedInstance().setInitialDelay(0);
        frame = new JFrame("Gas Installtion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(550, 550));
        frame.setLayout(new GridLayout(5,3,5,5));
        frame.setResizable(false);
        
        pressurePanel = new JPanel();
        pressureText = new JLabel("Pressure:");
        pressure = new JLabel(Double.toString((Double)model.getPressure()));
        pressureUnit = new JLabel("[hPa]");
        
        inflowPanel = new JPanel();
        inflowPanel.setLayout(new GridLayout(1,5));
        inflowText = new JLabel("Inflow:");
        inflowNumber = new JTextField();
        inflow = new JLabel("0");
        inflowButton = new JButton("Set");
        randomInflow = new JButton("Random");
        
        outflowPanel = new JPanel();      
        outflowPanel.setLayout(new GridLayout(1,5));
        outflowText = new JLabel("Outflow:");
        outflow = new JLabel("0");
        outflowNumber = new JTextField();
        outflowButton = new JButton("Set");
        randomOutflow = new JButton("Random");
        
        pressureHistoryPanel = new JPanel();
        pressureHistoryPanel.setLayout(new GridLayout(1,3));
        saveToFile = new JButton("save");
        readFromFile = new JButton("read");
        showHistory = new JButton("show");
        
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Pressure History");
        pressureHistoryTable = new JTable(tableModel);
        
        //tolltips
        inflowButton.setToolTipText("Set the inflow value");
        outflowButton.setToolTipText("Set the outflow value");
        randomInflow.setToolTipText("Generate a random inflow value");
        randomOutflow.setToolTipText("Generate a random outflow value");
        saveToFile.setToolTipText("Save pressure history to a file");
        readFromFile.setToolTipText("Read pressure history from a file");
        showHistory.setToolTipText("Show pressure history");
        pressureText.setToolTipText("Current pressure value");
        outflowText.setToolTipText("Current outflow value");
        inflowText.setToolTipText("Current inflow value");        
        
        frame.add(pressurePanel);
        pressurePanel.add(pressureText);
        pressurePanel.add(pressure);
        pressurePanel.add(pressureUnit);
                
        frame.add(outflowPanel);
        outflowPanel.add(outflowText);
        outflowPanel.add(outflow);
        outflowPanel.add(outflowNumber);
        outflowPanel.add(outflowButton);
        outflowPanel.add(randomOutflow);
        
        frame.add(inflowPanel);
        inflowPanel.add(inflowText);
        inflowPanel.add(inflow);
        inflowPanel.add(inflowNumber);
        inflowPanel.add(inflowButton);
        inflowPanel.add(randomInflow);
        
        frame.add(pressureHistoryPanel);
        pressureHistoryPanel.add(saveToFile);
        pressureHistoryPanel.add(readFromFile);
        pressureHistoryPanel.add(showHistory);
        
        scrollPane = new JScrollPane(pressureHistoryTable);
        frame.add(scrollPane);
        
        frame.setVisible(true);
    }
    /**
     * Updates the displayed inflow value in the graphical user interface.
     */
    public void updateInflow(){
        this.inflow.setText(Double.toString((Double)model.getGasInflow()));
    }
    /**
     * Updates the displayed outflow value in the graphical user interface.
     */
    public void updateOutflow(){
        this.outflow.setText(Double.toString((Double)model.getGasOutflow()));
    }
     /**
     * Updates the displayed pressure value in the graphical user interface.
     */
    public void updatePressure(){
        this.pressure.setText(Double.toString((Double)model.getPressure()));
    }
    
    /**
     * Displays a notification that the pressure history has been successfully saved to a file.
     */
    public void  historySaved(){
        JOptionPane.showMessageDialog(null,"Pressure history has been succesfully saved to file");
    }
     /**
     * Displays a notification that the pressure history has been successfully read from a file.
     */
    public void  historyRead(){
        JOptionPane.showMessageDialog(null,"Pressure history has been read succesfully");
    }
    /**
     * Displays an error message to the user.
     * 
     * @param message The error message to be displayed.
     */
    public void showError(String message){
        JOptionPane.showMessageDialog(null,message);
    }
    /**
     * Updates the pressure history table with the latest data from the model.
     */
    public void updateTable() {
        ArrayList<Double> pressureHistoryData = model.getPressureHistory();
        tableModel.setRowCount(0);
        for (Double value : pressureHistoryData) {
        Object[] rowData = {value};
        tableModel.addRow(rowData);
        }
    }
}
