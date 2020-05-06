<%@page import="com.Appointment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Appointments Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Appo.js"></script>

</head>
<body>
<div class="container">
	<div class="row" >
		<div class="col-6">
			<h1>Appointment Management V10.1</h1>
			
			<form id="formappointment" name="formappointment" method="post" action="items.jsp">

				Appointment code:
				<input id="appointmentCode" name="appointmentCode" type="text" class="form-control form-control-sm">
				<br>
				 
				Appointment name:
				<input id="appointmentName" name="appointmentName" type="text" class="form-control form-control-sm">
				<br>
				
				Appointment price:
				<input id="appointmentPrice" name="appointmentPrice" type="text" class="form-control form-control-sm">
				<br>
				 
				Appointment description:
				<input id="appointmentDesc" name="appointmentDesc" type="text" class="form-control form-control-sm">
				<br>
				
				<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
				<input type="hidden" id="hidappointmentIDSave" name="hidappointmentIDSave" value="">
			</form>
			
			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
			<br>

			<div id="divAppointmentsGrid">
				<%
					Appointment appointmentObj = new Appointment();
					out.print(appointmentObj.readItems());
				%>
			</div>
		</div>
	</div>
</div>
</body>
</html>