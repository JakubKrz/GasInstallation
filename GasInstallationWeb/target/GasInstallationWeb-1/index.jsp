<%-- 
    Document   : index
    Created on : 1 gru 2023, 18:54:42
    Author     : Jakub Krzywoń
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gas Installation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        .panel {
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
        }

        .label {
            font-weight: bold;
            margin-right: 10px;
        }

        .inputField {
            width: 80px;
            margin-right: 10px;
        }

        .value {
            margin-right: 10px;
        }

        .buttonGroup {
            display: flex;
        }

        .button {
            padding: 5px 10px;
            margin-right: 20px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Gas Installation</h1>
    <form action="CalculationServlet" method="post">
    <div class="panel">
        <div class="label">Pressure:</div>
        <div class="value" id="pressureDisplay">${pressureNumber} [hPa]</div>
        <div class="buttonGroup">
            <button class="button" id="updatePressure" type="submit" name="action" value="updatePressure">Update</button>
        </div>
        <div class="error">
            <% String pressureError = (String) request.getAttribute("pressureError"); %>
            <% if (pressureError != null) { %>
            <%= pressureError %>
            <% } %>
        </div>
    </div>

   
    <div class="panel">
        <div class="label">Inflow:</div>
        <div class="value" id="inflowDisplay">${inflowNumber}</div>
        <input type="text" class="inputField" id="inflowNumber" name="inflow" placeholder="Enter inflow">
        <div class="buttonGroup">
            <button class="button" id="setInflow" type="submit" name="action" value="setInflow">Set</button>
            <button class="button" id="randomInflow" type="submit" name="action" value="randomInflow">Random</button>
        </div>
        <div class="error">
            <% String inflowError = (String) request.getAttribute("inflowError"); %>
            <% if (inflowError != null) { %>
            <%= inflowError %>
            <% } %>
        </div>
    </div>

    <div class="panel">
        <div class="label">Outflow:</div>
        <div class="value" id="outflowDisplay">${outflowNumber}</div>
        <input type="text" class="inputField" id="outflowNumber" name="outflow" placeholder="Enter outflow">
        <div class="buttonGroup">
            <button class="button" id="setOutflow" type="submit" name="action" value="setOutflow">Set</button>
            <button class="button" id="randomOutflow" type="submit" name="action" value="randomOutflow">Random</button>
        </div>
            <% String outflowError = (String) request.getAttribute("outflowError"); %>
            <% if (outflowError != null) { %>
            <%= outflowError %>
            <% } %>
        </div>
    </form>

    <form action="HistoryServlet" method="post">
    <div class="panel">
        <div class="buttonGroup">
            <button class="button" id="showHistory" type="submit" name="action" value="ShowHistory">Show Pressure History</button>
            <button class="button" id="showMaxPressure" type="submit" name="action" value="ShowMaxPressure">Max Pressure</button>
            <button class="button" id="showMinPressure" type="submit" name="action" value="ShowMinPressure">Min Pressure</button>
            <button class="button" id="showAvgPressure" type="submit" name="action" value="ShowAvgPressure">Avg Pressure</button>
            <button class="button" id="Database" type="submit" name="action" value="ShowDatabase">Show Database tables</button>
        </div>
    </div>
        
    </form>
</body>
</html>
