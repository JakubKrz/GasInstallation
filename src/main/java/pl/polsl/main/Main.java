/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package pl.polsl.main;

import pl.polsl.controller.Controller;

/** 
 * Main class responsible for initiating the gas monitoring application.
 * 
 * @author Jakub Krzywoń
 * @version 1.1
 */
public class Main {

   /**
     * Main method serving as the entry point of the application.
     * Initializes the Controller class and invokes its run method.
     * 
     * @param args command line parameters
     * - "random": Generates random gas inflow and outflow values for simulation.
     * - "set": Allows the user to input specific gas inflow and outflow values.
     */
    public static void main(String args[]) {
        Controller controller = new Controller();
        controller.run(args);
    }
}
