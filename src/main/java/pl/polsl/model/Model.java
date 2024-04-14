/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 * The Model class represents the core logic and data of the gas installation monitoring system.
 * 
 * @author Jakub Krzywoń
 * @version 3.1
 */

public class Model {
    
    /** Gas constant used in pressure calculations. */
    final private double gasConstant = 8.314;
    
    /** Gas conductivity factor based on tank volume and temperature. */
    final private double gasConductivityFactor;
    
    /** Timestamp representing the start time of the model's operation. */
    private long timer;
    
    /** Tank object representing the gas storage tank. */
    private final Tank tank;
    
    /** List that stores historical values of pressure */
    private ArrayList<Double> pressureHistory;
    
     /** Support for Swing property change events */
    private final SwingPropertyChangeSupport swingPropChangeFirer;
    
    /**
     * Constructor
     * Initializes the tank, computes the gas conductivity factor and sets the start time.
     */
    public Model()
    {
        this.tank = new Tank();
        this.gasConductivityFactor = this.tank.getVolume() / (this.tank.getTemperature() * this.gasConstant);
        this.timer = System.currentTimeMillis();
        this.pressureHistory = new ArrayList<>();
        swingPropChangeFirer = new SwingPropertyChangeSupport(this);//changed
    }
    
    /**
     * Adds a property change listener to the Swing property change support.
     * 
     * @param prop The property change listener to be added.
     */
    public void addListener(PropertyChangeListener prop){
        swingPropChangeFirer.addPropertyChangeListener(prop);
    }
    
    /**
     * Gets the current gas pressure in the tank.
     * 
     * @return The current gas pressure in the tank.
     */
    public double getPressure()
    {
        return this.tank.getPressure();
    }
    
     /**
     * Updates the gas pressure based on inflow, outflow, gasConductivityFactor and elapsed time.
     * Calls {@code applyChangeOfPressure} to apply calculated pressure change and 
     * throws exception if gas pressure is too high
     * 
     * @throws PressureOverflowException If the gas pressure exceeds the maximum allowed value.
     */
    public void updatePressure() throws PressureOverflowException
    {
        double oldValue = tank.getPressure();
        long elapsedTime = System.currentTimeMillis() - this.timer;
        this.timer = System.currentTimeMillis();
        double elapsedTimeMin = (double) elapsedTime / 60000;
        double change = ((this.tank.getGasInflow() - this.tank.getGasOutflow()) / this.gasConductivityFactor) * elapsedTimeMin;
        double result = this.tank.getPressure() + change;

        if(result < 0) result = 0;
        this.applyChangeOfPressure(result);   
        swingPropChangeFirer.firePropertyChange("Pressure", oldValue, result);
    }
    
   /**
    * Applies the calculated change in gas pressure to the tank. Updates pressure history.
    * Throws a PressureOverflowException if the pressure exceeds the maximum allowed value.
    * 
    * @param pressure The new gas pressure to be set in the tank.
    * @throws PressureOverflowException If the gas pressure exceeds the maximum allowed value.
    */
    private void applyChangeOfPressure(double pressure) throws PressureOverflowException
    {
        this.setPressure(pressure);
        this.updatePressureHistory();  
        if(pressure > this.tank.getMaxAllowedPressure())
        {
            throw new PressureOverflowException("Pressure exceeds maximum allowed value");
        }
    }
    
    /**
     * Sets the gas inflow rate to the tank.
     * 
     * @param inflow The new gas inflow rate.
     */
    public void setGasInflow(Double inflow)
    {
        double oldValue = this.tank.getGasInflow();
        this.tank.setGasInflow(inflow);
        swingPropChangeFirer.firePropertyChange("Inflow", oldValue, inflow);
    }
    
     /**
     * Sets the gas outflow rate from the tank.
     * 
     * @param outflow The new gas outflow rate.
     */
    public void setGasOutflow(Double outflow)
    {
        double oldValue = this.tank.getGasOutflow();
        this.tank.setGasOutflow(outflow);
        swingPropChangeFirer.firePropertyChange("Outflow", oldValue, outflow);
    }
    
    /**
     * Gets the gas inflow rate to the tank.
     * 
     * @return The gas inflow rate.
     */
    public double getGasInflow(){
        return this.tank.getGasInflow();
    }
    
     /**
     * Gets the gas outflow rate from the tank.
     * 
     * @return The gas outflow rate.
     */
    public double getGasOutflow(){
        return this.tank.getGasOutflow();
    }
    
    /**
     * Sets timer to current system time in miliseconds
     */
    public void startTime()
    {
        this.timer = System.currentTimeMillis();
    }
    
    /**
     * Adds current gas pressure to the pressureHistory.
     */
    private void updatePressureHistory()
    {
        this.pressureHistory.add(this.tank.getPressure());
    }
    
    /**
     * Getter for pressureHistory list.
     *
     * @return The list containing historical pressure values.
     */ 
    public ArrayList<Double> getPressureHistory() 
    {
        return this.pressureHistory;
    }

    /**
     * Gets the maximum pressure recorded in the pressureHistory.
     *
     * @return The maximum historical gas pressure value or -1 if pressureHistory is empty.
     */    
    public double getMaxPressure()
    {
        return this.pressureHistory.stream()
                .mapToDouble(a -> a)
                .max()
                .orElse(-1.0);
    }
    
    /**
     * Gets the minimum pressure recorded in the pressureHistory.
     *
     * @return The minimum historical gas pressure value or -1 if pressureHistory is empty.
     */  
    public double getMinPressure()
    {
        return this.pressureHistory.stream()
                .mapToDouble(a -> a)
                .min()
                .orElse(-1.0);
    }
    
    /**
     * Gets the average pressure recorded in the pressureHistory.
     *
     * @return The average historical gas pressure value or -1 if pressureHistory is empty.
     */ 
    public double getAveragePressure()
    {
        return this.pressureHistory.stream()
                .mapToDouble(a -> a)
                .average()
                .orElse(-1.0);
    }
    
    /**
     * Setter for pressureHistory.
     *
     * @param pressureHistory The ArrayList containing historical gas pressure values.
     */
    public void setPressureHistory(ArrayList pressureHistory)
    {
        this.pressureHistory = pressureHistory;
    }
    
    /**
     * Setter for pressure
     * 
     * @param pressure New pressure value
     */
    public void setPressure(double pressure)
    {
        this.tank.setPressure(pressure);
    }
    
    /**
     * Getter for maxAllowedPressure
     * 
     * @return The maximum allowed gas pressure
     */
    public double getMaxAllowedPressure()
    {
        return this.tank.getMaxAllowedPressure();
    }
    
    /**
     * Saves the pressure history to a file with the default filename "PressureHistory.txt".
     */
    public void savePressureHistory()
    {
        this.savePressureHistory("PressureHistory.txt");
    }
    
    /**
     * Saves the pressure history to a file with the specified filename.
     * 
     * @param fileName The name of the file to save the pressure history.
     */
    public void savePressureHistory(String fileName)
    {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Double value : this.pressureHistory) {
            writer.write(value.toString() + "\n");
            }
        swingPropChangeFirer.firePropertyChange("Save",null,null);
        } catch (IOException e) {
        }
    }
    
    /**
     * Reads the pressure history from the default filename "PressureHistory.txt".
     */
    public void readPressureHistory()
    {
        readPressureHistory("PressureHistory.txt");
    }
    
    /**
     * Reads the pressure history from the specified filename.
     * 
     * @param fileName The name of the file to read the pressure history from.
     */
    public void readPressureHistory(String fileName)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                double number = Double.parseDouble(line);
                this.pressureHistory.add(number);
            }
        swingPropChangeFirer.firePropertyChange("Read",null,null);
        } catch (IOException | NumberFormatException e) {
        }    
    }
}