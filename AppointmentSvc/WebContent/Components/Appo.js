$(document).ready(function()
{
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateAppointmentForm();
	
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var method = ($("#hidappointmentIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "AppoAPI",
		type : method,
		data : $("#formappointment").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onItemSaveComplete(response.responseText, status);
		}
	});
});

function onItemSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidappointmentIDSave").val("");
	$("#formappointment")[0].reset();
}


//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidItemappointment").val($(this).closest("tr").find('#hidappointmentIDUpdate').val());
	$("#appointmentCode").val($(this).closest("tr").find('td:eq(0)').text());
	$("#appointmentName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#appointmentPrice").val($(this).closest("tr").find('td:eq(2)').text());
	$("#appointmentDesc").val($(this).closest("tr").find('td:eq(3)').text());
});


//REMOVE==========================================
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
		url : "AppoAPI",
		type : "DELETE",
		data : "appointmentID=" + $(this).data("itemid"),
		dataType : "text",
		complete : function(response, status)
		{
			onItemDeleteComplete(response.responseText, status);
		}
	});
});

function onItemDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


function validateItemForm()
{
	// CODE
	if ($("#appointmentCode").val().trim() == "")
	{
		return "Insert appointment Code.";
	}
	
	// NAME
	if ($("#itemName").val().trim() == "")
	{
		return "Insert appointment Name.";
	}
	
	//PRICE-------------------------------
	if ($("#itemPrice").val().trim() == "")
	{
		return "Insert appointment Price.";
	}
	
	// is numerical value
	var tmpPrice = $("#appointmentPrice").val().trim();
	
	if (!$.isNumeric(tmpPrice))
	{
		return "Insert a numerical value for appointment Price.";
	}
	
	// convert to decimal price
	$("#appointmentPrice").val(parseFloat(tmpPrice).toFixed(2));
	
	// DESCRIPTION------------------------
	if ($("#appointmentDesc").val().trim() == "")
	{
		return "Insert appointment Description.";
	}
	
	return true;
}