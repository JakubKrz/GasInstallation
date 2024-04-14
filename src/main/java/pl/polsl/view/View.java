/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.view;

import pl.polsl.model.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The View class represents the user interface of the gas installation monitoring system.
 * 
 * @author Jakub Krzywoń
 * @version 2.1
 */
public class View {
    
    /** Model object */
    private final Model model;
    
    /** Scanner object */
    private final Scanner scanner;
    
    /**
     * Constructor 
     * Initializes scanner and constructs a View object with the specified Model instance
     * 
     * @param model The Model instance associated with this View.
     */
    public View(Model model)
    {
        this.model = model;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Displays the current gas pressure on the console and information whether is it in normal range.
     */
    public void updatePressure()
    {
        switch (this.model.getPressureState()) 
        {
            case HIGH: 
                System.out.print("The pressure exceeds 80% of allowed threshold! \n");
                break;
            case NORMAL: 
                System.out.print("The pressure level is within the normal range \n");
                break;
            case LOW: 
                //TO DO: show message for low pressure
                break;
        }

        {
            
        }
        System.out.print("Pressure = " + model.getPressure() + " [hPa]\n");
    }
    
    /**
     * Checks if there is any input available from the user.
     * 
     * @return True if there is input available, false otherwise.
     */
    public boolean input() {
        try {
            return System.in.available() > 0;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Prompts the user for input and reads it
     * 
     * @return array of strings containing new user input arguments.
     */
    public String[] wrongArgs()
    {
        System.out.print("Invalid argument, enter \"random\" or \"set\" ");
        String userInput = scanner.nextLine();
        return userInput.split(" ");
    }
    
    /**
     * Prompts the user to enter gas inflow and outflow values and handles invalid inputs.
     * 
     * @return arrayList of integers representing valid gas inflow and outflow values.
     */
    public ArrayList takInflowOutflow()
    {   
        ArrayList<Integer> inflowOutflow = new ArrayList<>(2);
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.print("Enter Inflow: ");
                inflowOutflow.add(scanner.nextInt());
                System.out.print("Enter Outflow: ");
                inflowOutflow.add(scanner.nextInt());
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter integers.");
                inflowOutflow.clear();
                scanner.next();
            }
        }
        return inflowOutflow;
    }
    
    
    /**
     * Displays an error message to the user.
     * 
     * @param errorMessage The error message to be displayed.
     */
    public void displayError(String errorMessage)
    {
        System.out.print("Error: " + errorMessage + "\n");
    }
    
    /**
     * Displays the pressure history on console.
     */
    public void displayPressureHistory()
    {
        System.out.print("Pressure history: \n");
        for(Double pressure : this.model.getPressureHistory())
        {
             System.out.print(pressure + " [hPa]\n");
        }
    }
}
