/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

/**
 * The Model class represents the core logic and data of the gas installation monitoring system.
 * 
 * @author Jakub Krzywoń
 * @version 1.1
 */

public class Model {
    
    /** Gas constant used in pressure calculations. */
    final private double gasConstant = 8.314;
    
    /** Gas conductivity factor based on tank volume and temperature. */
    final private double gasConductivityFactor;
    
    /** Timestamp representing the start time of the model's operation. */
    private long startTime;
    
    /** Tank object representing the gas storage tank. */
    private final Tank tank;
    
    /**
     * Constructor
     * Initializes the tank, computes the gas conductivity factor and sets the start time.
     */
    public Model()
    {
        this.tank = new Tank();
        this.gasConductivityFactor = this.tank.getVolume() / (this.tank.getTemperature() * this.gasConstant);
        this.startTime = System.currentTimeMillis();
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
     * Throws exception if gas pressure is too  high
     * 
     * @throws PressureOverflowException If the gas pressure exceeds the maximum allowed value.
     */
    public void updatePressure() throws PressureOverflowException
    {
        long elapsedTime = System.currentTimeMillis() - this.startTime;
        this.startTime = System.currentTimeMillis();
        double elapsedTimeMin = (double) elapsedTime / 60000;
        double change = ((this.tank.getGasInflow() - this.tank.getGasOutflow()) / this.gasConductivityFactor) * elapsedTimeMin;
        double result = this.tank.getPressure() + change;
        if(result < 0)
        {
            this.tank.setPressure(0);
        }
        else 
        {
            this.tank.setPressure(result);
            if(result > this.tank.getMaxPressure())
            {
                throw new PressureOverflowException("Pressure exceeds maximum allowed value");
            }
        }
    }
    
    /**
     * Sets the gas inflow rate to the tank.
     * 
     * @param inflow The new gas inflow rate.
     */
    public void setGasInflow(int inflow)
    {
        this.tank.setGasInflow(inflow);
    }
    
     /**
     * Sets the gas outflow rate from the tank.
     * 
     * @param outflow The new gas outflow rate.
     */
    public void setGasOutflow(int outflow)
    {
        this.tank.setGasOutflow(outflow);
    }
}
