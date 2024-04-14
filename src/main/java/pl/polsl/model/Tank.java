/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

/**
 * Tank class representing a gas tank with specific properties.
 * 
 * @author Jakub Krzywoń
 * @version 1.1
 */
public class Tank {
    
    /** The rate at which gas is flowing into the tank. */
    private int gasInflow = 0;
    
    /** The rate at which gas is flowing out of the tank. */
    private int gasOutflow = 0;
    
     /** The starting pressure inside the gas tank, measured in hPa. */
    private double pressure = 1013.25;
    
    /** The volume of the gas tank, measured in liters. */
    private final double volume = 5.84;
    
    /** The temperature of the gas inside the tank, measured in Kelvin. */
    private final double temperature = 293;
    
    /** The maximum allowed pressure in the gas tank, calculated as 5 times the starting pressure */
    private final double maxPressure = this.pressure * 5;
    
    /**
     * Setter for gasInflow
     * 
     * @param gasInflow The gas inflow rate to set.
     */
    public void setGasInflow(int gasInflow)
    {
        this.gasInflow = gasInflow;
    }
    
     /**
     * Setter for gasOutflow.
     * 
     * @param gasOutflow The gas outflow rate to set.
     */
    public void setGasOutflow(int gasOutflow)
    {
        this.gasOutflow = gasOutflow;
    }
    
    /**
     * Setter for pressure.
     * 
     * @param pressure The gas pressure to set.
     */
    public void setPressure(double pressure)
    {
        this.pressure = pressure;
    }
    
    /**
     * Getter for gasInflow
     * 
     * @return The current gas inflow rate.
     */
    public int getGasInflow()
    {
        return this.gasInflow;
    }
    
    /**
     * Getter for gasOutflow
     * 
     * @return The current gas outflow rate.
     */
    public int getGasOutflow()
    {
        return this.gasOutflow;
    }
    
    /**
     * Getter for pressure
     * 
     * @return The current pressure.
     */
    public double getPressure()
    {
        return this.pressure;
    }
    
    /**
     * Getter for volume
     * 
     * @return The volume.
     */
    public double getVolume()
    {
        return this.volume;
    }
    
    /**
     * Getter for temperatur
     * 
     * @return The temperature.
     */
    public double getTemperature()
    {
        return this.temperature;
    }
    
    /**
     * Getter for maxPressure
     * 
     * @return The maxPressure.
     */
    public double getMaxPressure()
    {
        return this.maxPressure;
    }
}
