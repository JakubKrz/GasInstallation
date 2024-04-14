/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import pl.polsl.model.Model;
import pl.polsl.view.View;
import pl.polsl.model.PressureOverflowException;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.AbstractAction;

/**
 * Controller class responsible for managing interactions between the model and the view.
 * 
 * @author Jakub Krzywoń
 * @version 3.1
 */
public class Controller implements PropertyChangeListener{

    /** View object */
    private final View view;
    
    /** Model object */
    private final Model model;
    
    /** Random number generator */
    private final Random generator;
    
    /** Timer object */
    private Timer timer;

    /**
     * Constructor 
     * Initializes the Model, View, and the random number generator objects.
     * It initializes the View by passing a reference to its own Model object.
     * Invokes the {@code viewEvent} method to set up event listeners and handlers.
     * Invokes the {@code startUpdateTimer} method to begin periodic pressure updates.
     * 
     * @param args Command-line arguments.
     */
    public Controller(String args[]){
        model = new Model();
        view = new View(this.model);
        generator = new Random();
        this.model.addListener(this);
        this.viewEvent();
        this.startUpdateTimer();
    }
    
    /**
     * Initializes and starts a timer for periodic updates of the gas pressure in the model.
     * The timer triggers the {@code updatePressure} method at a fixed rate,
     * checking for pressure overflow exceptions and updating the view accordingly.
     */
     private void startUpdateTimer() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    model.updatePressure();
                } catch (PressureOverflowException ex) {
                    view.showError(ex.getMessage());
                    this.cancel();
                }
            }
        }, 0, 500);
    }
     
    /**
     * This method defines actions for buttons and other interactive elements in the view.
     * The actions include setting gas inflow and outflow, generating random values,
     * saving and reading pressure history, and updating the displayed pressure table.
     */
    private void viewEvent()
    {
        view.inflowButton.setAction(new AbstractAction("Set"){
            @Override
            public void actionPerformed(ActionEvent arg){
            model.setGasInflow(Double.valueOf(view.inflowNumber.getText()));
            }
        });
        view.outflowButton.setAction(new AbstractAction("Set"){
            @Override
            public void actionPerformed(ActionEvent arg){
            model.setGasOutflow(Double.valueOf(view.outflowNumber.getText()));
            }
        });
        view.randomOutflow.setAction(new AbstractAction("Random"){
            @Override
            public void actionPerformed(ActionEvent arg){
            model.setGasOutflow((double)generator.nextInt(20));
            }
        });
        view.randomInflow.setAction(new AbstractAction("Random"){
            @Override
            public void actionPerformed(ActionEvent arg){
            model.setGasInflow((double)generator.nextInt(20));
            }
        });
        
         view.saveToFile.setAction(new AbstractAction("Save"){
            @Override
            public void actionPerformed(ActionEvent arg0){
            model.savePressureHistory();
            }
        });
        view.readFromFile.setAction(new AbstractAction("Read"){
            @Override
            public void actionPerformed(ActionEvent arg0){
            model.readPressureHistory();
            }
        });
        view.showHistory.setAction(new AbstractAction("Show"){
            @Override
            public void actionPerformed(ActionEvent arg0){
            view.updateTable();
            }
        });
    }
    
    /**
     * Handles property change events fired by the model.
     * This method is called when a property in the model changes, and it updates
     * the corresponding aspects of the view based on the property change.
     *
     * @param evt The PropertyChangeEvent containing information about the property change.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        switch (propName.toLowerCase()) {
          case "pressure":
            view.updatePressure();
            break;
          case "inflow":
            view.updateInflow();
            break;
          case "outflow":
            view.updateOutflow();
            break;
          case "save":
            view.historySaved();
            break;
          case "read":
            view.historyRead();
            break;
          default:
            break;
        }
    }

}