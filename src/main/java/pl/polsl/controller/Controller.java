/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.controller;

import java.util.ArrayList;
import pl.polsl.model.Model;
import pl.polsl.view.View;
import pl.polsl.model.PressureOverflowException;

import java.util.Random;

/**
 * Controller class responsible for managing interactions between the model and the view.
 * 
 * @author Jakub Krzywoń
 * @version 2.1
 */
public class Controller {

    /** View object */
    private final View view;
    
    /** Model object */
    private final Model model;
    
    /** Random number generator */
    private final Random generator;

    /**
     * Constructor 
     * Initializes model, view, and the random number generator objects.
     * It initializes the View by passing a reference to its own Model object.
     */
    public Controller(){
        model = new Model();
        view = new View(this.model);
        generator = new Random();
    }

    /**
     * Method to run the controller's logic based on the arguments passed to the program.
     * It checks the input arguments to ensure correctness and invokes corresponding
     * methods in the view to obtain valid user input.
     * 
     * @param args The input arguments passed to the program.
     */
    public void run(String args[]) {
        boolean isValidInput = false;
        
        while (!isValidInput) {
            if (args.length != 1) {
                args = view.wrongArgs();
            } 
            else 
            {
                switch (args[0]) {
                    case "random":
                        this.model.startTime();
                        this.update(true);
                        isValidInput = true;
                        break;
                    case "set":
                        ArrayList<Integer> inflowOutflow = new ArrayList<>(view.takInflowOutflow());
                        this.model.setGasInflow(inflowOutflow.get(0));
                        this.model.setGasOutflow(inflowOutflow.get(1));
                        this.model.startTime();
                        this.update(false);
                        isValidInput = true;
                        break;
                    default:
                        args = view.wrongArgs();
                        isValidInput = false;
                        break;
                }
            }
        }
    }

    /**
     * Method to update the model's state in loop on based user input data.
     * The updates occur approximately every second.
     * 
     * @param random Specifies whether the input data should be generated randomly.
     */
    private void update(boolean random)
    {
        while(!view.input())
        {
            if(random)
            {
                this.model.setGasOutflow(generator.nextInt(20));
                this.model.setGasInflow(generator.nextInt(20));
            }
            
            try {
                this.model.updatePressure();
            } catch (PressureOverflowException exception) {
                view.displayError(exception.getMessage());
            }
            
            this.view.updatePressure();
            
            // Pause the loop for one second
            long startTime = System.currentTimeMillis();
            long waitingTime = 1000;
            while (System.currentTimeMillis() - startTime < waitingTime) {}
        }
    }
}