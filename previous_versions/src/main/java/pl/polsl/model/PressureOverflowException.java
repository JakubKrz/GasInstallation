/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

/**
 * Custom exception class.
 * Extends the standard Exception class.
 * 
 * @author Jakub Krzywoń
 * @version 1.1
 */
public class PressureOverflowException extends Exception {
    
    /**
     * Constructor
     * Takes string and calls the contructor of superclass (Exception) with provided string
     * 
     * @param message The error message describing the pressure overflow situation.
     */
    public PressureOverflowException(String message) {
        super(message);
    }
}   
