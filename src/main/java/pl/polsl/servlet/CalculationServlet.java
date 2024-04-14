/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.model.Model;
import pl.polsl.model.PressureOverflowException;
import pl.polsl.view.View;

/**
 *Servlet handling requests related to calculations.
 *Manages model data and updates the view.
 * 
 * @author Jakub Krzywoń
 * @version 5.1
 */

@WebServlet(name = "CalculationServlet", urlPatterns = {"/CalculationServlet"})
public class CalculationServlet extends HttpServlet {
    /** Model object */
    private Model model;
    /** View object */
    private View view; 
    /** Random number generator */
    private final Random random = new Random();

    /**
     * Handles the user's request to set the gas inflow.
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException in case of servlet handling issues
     * @throws IOException in case of I/O operation issues
     */
    private void handleSetInflow(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String inflowParameter = request.getParameter("inflow");
            double inflowValue = Double.parseDouble(inflowParameter);
            this.updateModelPressure(request);
            this.model.setGasInflow(inflowValue);
            this.view.updateInflow(request);
            saveCookie(response, "inflowCookie", String.valueOf(this.model.getGasInflow()));
        } catch (NumberFormatException e) {
            this.handleErrorAndSaveToDatabase(request, "inflowError", "Please enter a valid inflow value.");
        }
        try {
            this.model.saveGasFlowToDatabase();
        } catch (SQLException ex) {
            this.view.showError(request, "inflowError", ex.getMessage());
        } finally {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles the user's request to set random gas inflow.
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException in case of servlet handling issues
     * @throws IOException in case of I/O operation issues
     */
    private void handleRandomInflow(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.updateModelPressure(request);
        this.model.setGasInflow(random.nextDouble(30));
        this.view.updateInflow(request);
        saveCookie(response, "inflowCookie", String.valueOf(this.model.getGasInflow()));
        try {
            this.model.saveGasFlowToDatabase();
        } catch (SQLException ex) {
            this.view.showError(request, "inflowError", ex.getMessage());
        } finally {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles the user's request to set the gas outflow.
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException in case of servlet handling issues
     * @throws IOException in case of I/O operation issues
     */
    private void handleSetOutflow(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String outflowParameter = request.getParameter("outflow");
            double outflowValue = Double.parseDouble(outflowParameter);
            this.updateModelPressure(request);
            this.model.setGasOutflow(outflowValue);
            this.view.updateOutflow(request);
            saveCookie(response, "outflowCookie", String.valueOf(this.model.getGasOutflow()));
        } catch (NumberFormatException e) {
            this.handleErrorAndSaveToDatabase(request, "outflowError", "Please enter a valid outflow value.");
        } try {
            this.model.saveGasFlowToDatabase();
        } catch (SQLException ex) {
            this.view.showError(request, "outflowError", ex.getMessage());
        }finally {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles the user's request to set random gas outflow.
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException in case of servlet handling issues
     * @throws IOException in case of I/O operation issues
     */
    private void handleRandomOutflow(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.updateModelPressure(request);
        this.model.setGasOutflow(random.nextDouble(30));
        this.view.updateOutflow(request);
        saveCookie(response, "outflowCookie", String.valueOf(this.model.getGasOutflow()));
        try {
            this.model.saveGasFlowToDatabase();
        } catch (SQLException ex) {
            this.view.showError(request, "outflowError", ex.getMessage());
        } finally {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    
    /**
     * Updates the model pressure data and shows it.
     *
     * @param request HTTP request
     */
    private void handlePressureUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.updateModelPressure(request);
        this.view.updatePressure(request);
        try {
            this.model.saveGasFlowToDatabase();
        } catch (SQLException ex) {
            this.view.showError(request, "pressureError", ex.getMessage());
        } finally {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    
    /**
     * Updates the model by invoking updatePressure, and shows if there is PressureOverflow exception.
     * 
     * @param request HTTP request
     */
    private void updateModelPressure(HttpServletRequest request) {
        try {
            this.model.updatePressure();
        } catch (PressureOverflowException ex) {
            this.view.showError(request, "pressureError", ex.getMessage());
        }
    }

    /**
     * Common method to handle errors, display error messages, and save data to the database.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param errorAttribute Name of the error attribute to set in the request
     * @param errorMessage Error message to display
     */
    private void handleErrorAndSaveToDatabase(HttpServletRequest request,
            String errorAttribute, String errorMessage) throws ServletException, IOException {
        this.view.showError(request, errorAttribute, errorMessage);
        try {
            this.model.saveGasFlowToDatabase();
        } catch (SQLException ex) {
            this.view.showError(request, errorAttribute, ex.getMessage());
        }
    }
    
    /**
     * Saves a key-value pair to a cookie and adds it to the HTTP response.
     *
     * @param response HTTP response
     * @param name     cookie name
     * @param value    cookie value
     */
    private void saveCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
    }
    
     /**
     * Loads values from cookies and sets inflow and outflow values.
     *
     * @param request HTTP request
     */
    private void loadCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for(Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "inflowCookie":
                        double inflowValue = Double.parseDouble(cookie.getValue());
                        this.model.setGasInflow(inflowValue);
                        this.view.updateInflow(request);
                        break;
                    case "outflowCookie":
                        double outflowValue = Double.parseDouble(cookie.getValue());
                        this.model.setGasOutflow(outflowValue);
                        this.view.updateOutflow(request);
                        break;
                }
            }
        }
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        if (Objects.isNull(request.getServletContext().getAttribute("Model"))) {
            request.getServletContext().setAttribute("Model", new Model());
        }
        this.model = (Model) request.getServletContext().getAttribute("Model");
        
        if (Objects.isNull(request.getServletContext().getAttribute("View"))) {
            request.getServletContext().setAttribute("View", new View(this.model));
        }
        this.view = (View) request.getServletContext().getAttribute("View");
        this.loadCookies(request);
       
        String action = request.getParameter("action");
        if (action != null) {
                switch (action) {
                    case "setInflow":
                        handleSetInflow(request, response);
                        break;
                    case "randomInflow":
                        handleRandomInflow(request, response);
                        break;
                    case "setOutflow":
                        handleSetOutflow(request, response);
                        break;
                    case "randomOutflow":
                        handleRandomOutflow(request, response);
                        break;
                    case "updatePressure":
                        handlePressureUpdate(request, response);
                        break; 
                }
        }
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}


