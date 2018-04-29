<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.google.gson.JsonObject"%> to <%@page import="com.google.gson.JsonObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Charts</title>
</head>
<body>

<h1>Charts</h1>

<div id="showDonutChart"></div>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
//load google charts
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

//draw the chart and set the chart values
function drawChart() {
  var json = JSON.parse($window.sessionStorage.incomeJson);
  var array  = JSON.parse(json);
  var data = google.visualization.arrayToDataTable(array);


  //add a title and set the width and height of the chart
  var options = {'title':'Income', 'width':550, 'height':400};

  //display the chart inside the <div> element with id="showDonutChart"
  var chart = new google.visualization.PieChart(document.getElementById('showDonutChart'));
  chart.draw(data, options);
}
</script>


</body>
</html>