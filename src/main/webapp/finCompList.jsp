<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="./jqwidgets/styles/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="./scripts/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="./jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="./jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="./jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="./jqwidgets/jqxdata.js"></script>
    <script type="text/javascript" src="./jqwidgets/jqxdata.export.js"></script> 
    <script type="text/javascript" src="./jqwidgets/jqxdatatable.js"></script> 
    <!-- <script type="text/javascript" src="./scripts/demos.js"></script> -->
    <script type="text/javascript" src="./jqwidgets/jqxwindow.js"></script>
    <script type="text/javascript" src="./jqwidgets/jqxinput.js"></script>
    <script type="text/javascript" src="./jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="./jqwidgets/jqxdropdownlist.js"></script>
    <script type="text/javascript" src="./jqwidgets/jqxnotification.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var url = "./json/getFinComp.do";
            // prepare the data
            var source =
            {
                dataType: "json",
                dataFields: [
					{ name: 'id', type: 'string' },
					{ name: 'name', type: 'string' }                    
                ],
                id: 'id',
                url: url,                
                beforeprocessing: function(data)
				{		
					source.totalrecords = data.total;
					
					/*
					console.log(data.total);
					console.log(data.pagenum);
					console.log(data.pagesize);
					*/
				},
				addRow: function (rowID, rowData, position, commit) {
                    // synchronize with the server - send insert command
                    // call commit with parameter true if the synchronization with the server is successful 
                    // and with parameter false if the synchronization failed.
                    // you can pass additional argument to the commit callback which represents the new ID if it is generated from a DB.
                    
                    rowData.id =null;
                    
                    $.ajax({
						// dataType: 'json',
						url: './json/addFinComp.do',
						cache: false,
						type: 'POST',
						data: rowData,
						success: function (result,status,xhr) {
							// console.log(data);
							// $("#dataTable").jqxDataTable('updateBoundData');
							
                    		commit(true);
                    		$("#dataTable").jqxDataTable('updateBoundData');
						},
						error: function (xhr,status,error) {
							
							console.log(xhr.responseText);
							/* 
							$("#messageContent").text("Duplicate tel number.");
			                $("#messageNotification").jqxNotification("open");
							 */
							commit(false);
						}
                     });
                },
                updateRow: function (rowID, rowData, commit) {
                    // synchronize with the server - send update command
                    // call commit with parameter true if the synchronization with the server is successful 
                    // and with parameter false if the synchronization failed.
                    // commit(true);
                    $.ajax({
						// dataType: 'json',
						url: './json/editFinComp.do',
						cache: false,
						type: 'POST',
						data: rowData,
						success: function (result,status,xhr) {
						// update command is executed.
							commit(true);
						},
						error: function (xhr,status,error) {
							commit(false);
						}
                    });
                },
                deleteRow: function (rowID,  commit) {
                    // synchronize with the server - send delete command
                    // call commit with parameter true if the synchronization with the server is successful 
                    // and with parameter false if the synchronization failed.
                    // commit(true);
                    
                    	// console.log("rowID :" + rowID);
                    
                    	console.log("deleteRow");
                    	
                    	// var rowData = $("#dataTable").getRowData(rowID); 
                    	
                    	var selection = $("#dataTable").jqxDataTable('getSelection');
                    	var rowData = selection[0];
                    	
                    	console.log("rowData.id:" + rowData.id);
                    	
                		$.post('./json/removeFinComp.do', {	
								id : rowData.id
							}, function(result) {
								if (result.success) {		
						// update command is executed.
						
							// console.log("rowID :" + rowID);
							console.log("result : " + result.success);
							
							commit(true);
							$("#dataTable").jqxDataTable('updateBoundData');
						} else
						{
							commit(false);
						}
                    });
                    
                }
				
            };
            var dataAdapter = new $.jqx.dataAdapter(source,
            {
	            formatData: function (data) {
	               	if (source.totalRecords) {
	               		
		               	 data.$skip = data.pagenum * data.pagesize;
	                     data.$top = data.pagesize;
	                     if (data.sortdatafield && data.sortorder) {
	                         data.$orderby = data.sortdatafield + " " + data.sortorder;
	                     }
	               	}
	               	
	             	// console.log(data);
	               	
	            	return data;
            	},
            	downloadComplete: function (data, status, xhr) {
	                /* if (!source.totalRecords) {
	                    source.totalRecords = data.value.length;
	                } */
            	},
            	loadError: function (xhr, status, error) {
                	// throw new Error("http://services.odata.org: " + error.toString());
            	}
        	}
            );
            $("#dataTable").jqxDataTable(
            {
            	
                width: 300,
                pageable: true,
                filterable: true,
                pagerButtonsCount: 10,
                serverProcessing: true,
                source: dataAdapter,
                showtoolbar: true,
                sortable: true,
                rendertoolbar: function (toolbar) {
                 	var me = this;
                 	var container = $("<div style='margin: 5px;'></div>");
                 	toolbar.append(container);
                 	container.append('<input id="addrowbutton" type="button" value="Add New Row" />')
                 	container.append('<input style="margin-left: 5px;" id="deleterowbutton" type="button" value="Delete Selected Row" />');
                 	$("#addrowbutton").jqxButton();
                 	$("#deleterowbutton").jqxButton();
                 	
                 	// create new row.
                    $("#addrowbutton").on('click', function () {
                        
                    	$("#dialog").jqxWindow('setTitle', "Add Row: ");
                        $("#dialog").jqxWindow('open');
                    	
                        /* 
                        $("#id").val("");                      
                        $("#name").val("");                   
                        $("#team").val("");
                        */
                        
                        clear_data();
                        
                    	// var datarow = generaterow();
                        // var commit = $("#dataTable").jqxDataTable('addrow', null, datarow);
                    });
                 	
                 	// delete row.
                    $("#deleterowbutton").on('click', function () {
                        
                    	//var selectedrowindex = $("#dataTable").jqxDataTable('getselectedrowindex');
                        // var rowscount = $("#dataTable").jqxDataTable('getdatainformation').rowscount;
                        var selection = $("#dataTable").jqxDataTable('getSelection');
                        var rows = $("#dataTable").jqxDataTable('getRows');
                        
                       /*   
                       console.log("selection:" + selection);
                         console.log("rows:" + rows);
                          */
                         if (selection && selection.length == 1) {
                       		
                        	 var rowData = selection[0];
                        	 
                        	 selectedRow = rows.indexOf(rowData);
                        	 
                            // var id = $("#dataTable").jqxDataTable('getrowid', selectedrowindex);
                            // var id = rowData.id;
                            // var commit = $("#dataTable").jqxDataTable('deleterow', id);
                            // var commit = $("#dataTable").jqxDataTable('deleteRow', rowData.id);
                            console.log("rowData.id :" + rowData.id);
                            console.log("selectedRow :" + selectedRow);
                           
                            var r = confirm("Are you sure to delete ?");
                            if (r == true) {
                            	var commit = $("#dataTable").jqxDataTable('deleteRow', selectedRow);
                            }
                        }
                    });
                },
                columnsResize: true,
                columns: [
				  { text: 'ID', dataField: 'id', width: 50 },
			      { text: 'Name', dataField: 'name', width: 100 }              
                ],
	            ready: function () {
	            	  $("#id").jqxInput({disabled: true, width: 150, height: 30 });
	            	  $("#name").jqxInput({width: 150, height: 30 });	            	  
	            	  	           
	            	  $("#save").jqxButton({ height: 30, width: 80 });
	                  $("#cancel").jqxButton({ height: 30, width: 80 });
	                  
	                  $("#cancel").mousedown(function () {
	                      // close jqxWindow.
	                      $("#dialog").jqxWindow('close');
	                  });                  
	                  $("#save").mousedown(function () {
	                      // close jqxWindow.
	                      $("#dialog").jqxWindow('close');
	                      // update edited row.
	                      var editRow = parseInt($("#dialog").attr('data-row'));
	                      var rowData = {
	                          id: $("#id").val(),
	      	                  name: $("#name").val(),	                       
	                       };
	                      
	                      /* 
	                      console.log('editRow ' + editRow);
	                      console.log('rowData ' + rowData);
	                       */
	                      // console.log("id : " + $("#id").val());
	                       
	                      if ( $("#id").val() === "") 
	                    	  $("#dataTable").jqxDataTable('addRow', null, rowData);	                
	                      else
	                    	  $("#dataTable").jqxDataTable('updateRow', editRow, rowData);
	                  }); 
	                  $("#dialog").on('close', function () {
	                      // enable jqxDataTable.
	                      $("#dataTable").jqxDataTable({ disabled: false });
	                  });
	                  $("#dialog").jqxWindow({
	                      resizable: false,
	                      position: { left: $("#dataTable").offset().left + 75, top: $("#dataTable").offset().top + 35 },
	                      width: 270, height: 350,
	                      autoOpen: false
	                  });
	                  $("#dialog").css('visibility', 'visible');
	              }
	            });
            $("#dataTable").on('rowDoubleClick', function (event) {
                var args = event.args;
                var index = args.index;
                var row = args.row;
                // update the widgets inside jqxWindow.
                $("#dialog").jqxWindow('setTitle', "Edit Row: " + row.id);
                $("#dialog").jqxWindow('open');
                $("#dialog").attr('data-row', index);
                $("#dataTable").jqxDataTable({ disabled: true });
                
                $("#id").val(row.id);
                $("#name").val(row.name);
             });
            
           /*  
            $("#csvExport").jqxButton();            
            $("#csvExport").click(function () {
                $("#dataTable").jqxDataTable('exportData', 'csv');
            });
            */
            
            $("#messageNotification").jqxNotification({
                width: 250, position: "top-right", opacity: 0.9,
                autoOpen: false, animationOpenDelay: 800, autoClose: true, autoCloseDelay: 3000, template: "info"
            });
            
        });
        
        function clear_data() {                           
            $("#id").val("");
            $("#name").val("");				                   
         };
       
    </script>

</head>


<body class='default'>

<jsp:include page="/menu.jsp"></jsp:include>

     <div id="dataTable"></div>
     
     <div style="visibility: hidden;" id="dialog">
        <div>Edit Dialog</div>
        <div style="overflow: hidden;">
            <table style="table-layout: fixed; border-style: none;">
                <tr>
                    <td align="right">ID:
                    </td>
                    <td align="left">
                        <input id="id" type="text" readonly="true" disabled="disabled" />
                    </td>
                </tr>
                <tr>
                    <td align="right">Name:
                    </td>
                    <td align="left">
                        <input id="name" type="text">
                    </td>
                </tr>                                       
                <tr>
                    <td colspan="2" align="right">
                         <br />
                         <button id="save">Save</button> <button style="margin-left: 5px;" id="cancel">Cancel</button></td>                    
                 </tr>
            </table>
        </div>
    </div>
    
    <br></br>
         
   <!--Notifications-->
    <div id="messageNotification">
        <div>
             <div><span id="messageContent" style="font-weight: bold;"></span>.</div>
        </div>
    </div>
    
</body>

</html>