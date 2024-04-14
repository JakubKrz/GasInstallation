/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.view;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import pl.polsl.model.Model;

/**
 *
 * @author Jakub Krzywoń
 * @version 5.1
 */
public class View {
    private final Model model;
     
    /**
     * Constructs a View object with the specified Model.
     *
     * @param model The Model object to be associated with the View.
     */
    public View(Model model) {
        this.model = model;
    }
    
     /**
     * Updates the inflow number in the session attribute of the HttpServletRequest.
     *
     * @param request The HttpServletRequest object to update.
     */
    public void updateInflow(HttpServletRequest request)
    {
        request.getSession().removeAttribute("inflowNumber");
        request.getSession().setAttribute("inflowNumber", model.getGasInflow());
    }
    /**
     * Updates the outflow number in the session attribute of the HttpServletRequest.
     *
     * @param request The HttpServletRequest object to update.
     */
    public void updateOutflow(HttpServletRequest request)
    {
        request.getSession().removeAttribute("outflowNumber");
        request.getSession().setAttribute("outflowNumber", model.getGasOutflow());
    }
    
    /**
    * Updates the pressure number in the session attribute of the HttpServletRequest.
    *
    * @param request The HttpServletRequest object to update.
    */
   public void updatePressure(HttpServletRequest request) {
       // Remove the existing pressureNumber attribute and set it to the current pressure value from the model
       request.getSession().removeAttribute("pressureNumber");
       request.getSession().setAttribute("pressureNumber", model.getPressure());
   }

   /**
    * Displays both GasFlow and PressureHistory tables in a div with flex display.
    *
    * @param out PrintWriter to write HTML content to the response.
    */
   public void showDatabase(PrintWriter out) {
       out.println("<div style='display:flex;'>");
       this.showGasFlowTable(out);
       out.println("<div style='margin-left:20px;'></div>");
       this.showPressureHistoryTable(out);
       out.println("</div>");
   }

   /**
    * Displays the GasFlow table.
    *
    * @param out PrintWriter to write HTML content to the response.
    */
   private void showGasFlowTable(PrintWriter out) {
       try {
           List<List<Object>> gasFlowDataList = model.getGasFlowDataFromDatabase();
           out.println("<table border='1'>");
           out.println("<thead><tr><th>ID</th><th>Inflow</th><th>Outflow</th></tr></thead>");
           out.println("<tbody>");

           for (List<Object> row : gasFlowDataList) {
               out.println("<tr>");
               for (Object value : row) {
                   out.println("<td>" + value + "</td>");
               }
               out.println("</tr>");
           }

           out.println("</tbody>");
           out.println("</table>");
       } catch (SQLException ex) {
           out.println(ex.getMessage());
       }
   }

   /**
    * Displays the PressureHistory table.
    *
    * @param out PrintWriter to write HTML content to the response.
    */
   private void showPressureHistoryTable(PrintWriter out) {
       try {
           List<List<Object>> pressureHistoryDataList = model.getPressureHistoryDataFromDatabase();
           out.println("<table border='1'>");
           out.println("<thead><tr><th>ID</th><th>Pressure</th><th>History Time</th><th>Calculation ID</th></tr></thead>");
           out.println("<tbody>");

           for (List<Object> row : pressureHistoryDataList) {
               out.println("<tr>");
               for (Object value : row) {
                   out.println("<td>" + value + "</td>");
               }
               out.println("</tr>");
           }

           out.println("</tbody>");
           out.println("</table>");
       } catch (SQLException ex) {
           out.println(ex.getMessage());
       }
   }

    /**
     * Displays an error message in the specified error type attribute of the HttpServletRequest.
     *
     * @param request      The HttpServletRequest object to update.
     * @param errorType    The type of error attribute to set.
     * @param errorMessage The error message to display.
     */
    public void showError(HttpServletRequest request, String errorType, String errorMessage)
    {
        request.setAttribute(errorType, errorMessage);
    }
    
    /**
     * Prints the start of the HTML template to the provided PrintWriter.
     *
     * @param out The PrintWriter to which the HTML template start is written.
     */
    public void printHtmlTemplateStart(PrintWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>History</title>");
        out.println("</head>");
        out.println("<body>");
    }
    
     /**
     * Prints the end of the HTML template to the provided PrintWriter.
     *
     * @param out The PrintWriter to which the HTML template end is written.
     */
    public void printHtmlTemplateEnd(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }
    
    /**
     * Displays the pressure history in a table format using the provided PrintWriter.
     *
     * @param out The PrintWriter to which the pressure history is written.
     */
    public void showHistory(PrintWriter out) {
        out.println("<table>");
        out.println("<thead><tr><th>Pressure History</th></tr></thead>");
        out.println("<tbody>");

        model.getPressureHistory().forEach(pressure -> {
            out.println("<tr><td>" + pressure + "</td></tr>");
        });

        out.println("</tbody>");
        out.println("</table>");
    }
    
    /**
     * Displays the average pressure using the provided PrintWriter.
     *
     * @param out The PrintWriter to which the average pressure is written.
     */
    public void showAvgPressure(PrintWriter out) {
        out.println("Average pressure: " + this.model.getAveragePressure());
    }
    
    /**
     * Displays the minimum pressure using the provided PrintWriter.
     *
     * @param out The PrintWriter to which the minimum pressure is written.
     */
    public void showMinPressure(PrintWriter out) {
        out.println("Minimal pressure: " + this.model.getMinPressure());
    }
    
    /**
     * Displays the maximum  pressure using the provided PrintWriter.
     *
     * @param out The PrintWriter to which the maximum pressure is written.
     */
    public void showMaxPressure(PrintWriter out) {
        out.println("Max pressure: " + this.model.getMaxPressure());
    }
}
