<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="./jqwidgets/styles/jqx.base.css"
	type="text/css" />
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
 <script type="text/javascript" src="./jqwidgets/jqxcombobox.js"></script>
 
 <script type="text/javascript" src="./jqwidgets/jqxdatetimeinput.js"></script>
 <script type="text/javascript" src="./jqwidgets/jqxcalendar.js"></script>
 <script type="text/javascript" src="./jqwidgets/globalization/globalize.js"></script>

</head>
<body class='default'>

	<jsp:include page="/menu.jsp"></jsp:include>

	<script type="text/javascript">
		$(document).ready(
				function() {

					var url = "./json/getPlan.do";

					// prepare the data
					var source = {
						dataType : "json",
						dataFields : [ {
							name : 'id',
							type : 'string'
						}, {
							name : 'createdDt',
							type : 'date'
						}, {
							name : 'code',
							type : 'string'
						}, {
							name : 'loanAmt',
							type : 'string'
						}, {
							name : 'loanUsage',
							type : 'string'
						}, {
							name : 'loanRcvDt',
								type : 'date'
						}, {
							name : 'status',
							type : 'string'
						}, {
							name : 'signDt',
							type : 'date'
						}, {
							 name: 'cust', map: 'cust>name'
						},  {
							 name: 'custId', map: 'cust>id'
						}, {
							 name: 'sales', map: 'sales>name'
						},  {
							 name: 'salesId', map: 'sales>id'
						}
						],
						id : 'id',
						url : url,
						beforeprocessing : function(data) {
							source.totalrecords = data.total;

							/*
							console.log(data.total);
							console.log(data.pagenum);
							console.log(data.pagesize);
							 */
						},
						processdata: function(data)
						{
							data.pagesize = 10;
						},
						addRow: function (rowID, rowData, position, commit) {
		                    
							rowData.id =null;
							
							$.ajax({
								url: './json/addPlan.do',
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
			                     $.ajax({
									url: './json/editPlan.do',
									cache: false,
									type: 'POST',
									data: rowData,
									success: function (result,status,xhr) {
									// update command is executed.
										commit(true);
										$("#dataTable").jqxDataTable('updateBoundData');
									},
									error: function (xhr,status,error) {
										commit(false);
									}
			                    });           
			             }, 
			             deleteRow: function (rowID,  commit) {
			                    	console.log("deleteRow");
			                    	var selection = $("#dataTable").jqxDataTable('getSelection');
			                    	var rowData = selection[0];
			                    	
			                    	console.log("rowData.id:" + rowData.id);
			                    	
			                		$.post('./json/removePlan.do', {	
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
					
					var dataAdapter = new $.jqx.dataAdapter(source, {
						formatData : function(data) {
							if (source.totalRecords) {

								data.$skip = data.pagenum * data.pagesize;
								data.$top = data.pagesize;
								if (data.sortdatafield && data.sortorder) {
									data.$orderby = data.sortdatafield + " "
											+ data.sortorder;
								}
							}

							// console.log(data);

							return data;
						},
						downloadComplete : function(data, status, xhr) {
							/* if (!source.totalRecords) {
							    source.totalRecords = data.value.length;
							} */
						},
						loadError : function(xhr, status, error) {
							throw new Error("http://services.odata.org: "
									+ error.toString());
						}
					});

					/* 
					var url2 = "./json/getFinComp.do";
					
					// prepare the data
					var source2 =
					{
					    dataType: "json",
					    dataFields: [
							{ name: 'id', type: 'string' },
					        { name: 'name', type: 'string' }
					    ],
					    id: 'id',
					    url: url2,                
					    beforeprocessing: function(data)
						{		
							source.totalrecords = data.total;
							
							
							console.log(data.total);
							console.log(data.pagenum);
							console.log(data.pagesize);
							
						}
					};
					
					var dataAdapter2 = new $.jqx.dataAdapter(source2, {
						formatData : function(data) {
							if (source.totalRecords) {

								data.$skip = data.pagenum * data.pagesize;
								data.$top = data.pagesize;
								if (data.sortdatafield && data.sortorder) {
									data.$orderby = data.sortdatafield + " "
											+ data.sortorder;
								}
							}

							// console.log(data);

							return data;
						},
						downloadComplete : function(data, status, xhr) {
							if (!source.totalRecords) {
							    source.totalRecords = data.value.length;
							}
						},
						loadError : function(xhr, status, error) {
							throw new Error("http://services.odata.org: "
									+ error.toString());
						}
					});					
					
					 */
					 
					$("#dataTable").jqxDataTable({

						width : 1050,
						pageable : true,
						filterable : true,
						pagerButtonsCount : 10,
						serverProcessing : true,
						source : dataAdapter,
						//showtoolbar: true,
						sortable : true,
						showtoolbar: true,
						columnsResize : true,
						columns : [ {
							text : 'ID',
							dataField : 'id',
							width : 50
						}, {
							text : 'Created',
							dataField : 'createdDt',
							width : 100,
							cellsformat: 'dd-MM-yyyy' 
						}, {
							text : 'Code',
							dataField : 'code',
							width : 100
						}, {
							text : 'Loan Amount',
							dataField : 'loanAmt',
							width : 100
						}, {
							text : 'Loan Usage',
							dataField : 'loanUsage',
							width : 200
						}, {
							text : 'Loan Receive Date.',
							dataField : 'loanRcvDt',
							width : 200,
							cellsformat: 'dd-MM-yyyy'
						}, {
							text : 'Status',
							dataField : 'status',
							width : 100
						}, {
							text : 'Signed Date',
							dataField : 'signDt',
							width : 100,
							cellsformat: 'dd-MM-yyyy'
						}, {
							text : 'Cust',
							dataField : 'cust',
							width : 100
						}, {
							text : 'Cust ID',
							dataField : 'custId',
							width : 100,
							hidden: true
						}, {
							text : 'Sales',
							dataField : 'sales',
							width : 100
						}, {
							text : 'Sales ID',
							dataField : 'salesId',
							width : 100,
							hidden: true
						} ],
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
			                    	
			                        $("#id").val("");
			                        $("#createdDt").val("");				                   
			                        $("#code").val("");
			                        $("#loanAmt").val("");
			                        $("#loanUsage").val("");
			                        $("#loanRcvDt").val("");			                       
			                        $("#signDt").val("");
			                        $("#status").val("");
			                        $("#CommissionPercent").val("");
			                        $("#cust").val("");
			                        $("#custId").val("");
			                        
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
			                ready: function () {
			                	 $("#save").mousedown(function () {
				                      // close jqxWindow.
				                      $("#dialog").jqxWindow('close');
				                      // update edited row.
				                      var editRow = parseInt($("#dialog").attr('data-row'));
				                      var rowData = {
				                          id: $("#id").val(),
				                          createdDt: $("#createdDt").val(),
				                          code: $("#code").val(),
				                          loanAmt: $("#loanAmt").val(),
				                          loanUsage: $("#loanUsage").val(), 
				                          loanRcvDt: $("#loanRcvDt").val(),
				                          signDt : $("#signDt").val(),
				                          status : $("#status").val(),
				                          CommissionPercent : $("#CommissionPercent").val(),
				                          
				                          custId : $("#custId").val(),
				                          salesId : $("#salesId").val()
				                      };
				                      
				                      /* 
				                      console.log('editRow ' + editRow);
				                      console.log('rowData ' + rowData);
				                       */
				                      console.log("id : " + $("#id").val());
				                       
				                      if ( $("#id").val() === "") 
				                    	  $("#dataTable").jqxDataTable('addRow', null, rowData);	                
				                      else
				                    	  $("#dataTable").jqxDataTable('updateRow', editRow, rowData);
				                 }); 
			                	
			                	$("#cancel").mousedown(function () {
				                      // close jqxWindow.
				                      $("#dialog").jqxWindow('close');
				                });
			                	
			                	$("#dialog").jqxWindow({
				                      resizable: false,
				                      position: { left: $("#dataTable").offset().left + 75, top: $("#dataTable").offset().top + 35 },
				                      width: 600, height: 600,
				                      autoOpen: false
				                  });
				                
			                	$("#dialog").on('close', function () {
				                      $("#dataTable").jqxDataTable({ disabled: false });
				                  });
			                	
			                	$("#dialog").css('visibility', 'visible');
			                }
					});
										
					 
					$("#dataTable").on('rowSelect', function(event) {

						/* 
						var row = event.args.rowindex;
						var id = $("#dataTable").jqxDataTable('getrowdata', row)['CustomerID'];
						 */

						var selection = $("#dataTable").jqxDataTable('getSelection');
						var rowData = selection[0];

						console.log("rowData.id:" + rowData.id);

						var source = {
							// url : './json/getFinCompByPlan.do',
							url : './json/getActivitiesByPlan.do',
							async : false,
							dataType : 'json',
							data : {
								plan_id : rowData.id
							},
							datatype : "json",
							datafields : [ {
								name : 'id'
							}, {
								name : 'created',
								type : 'date'
							}, {
								 name: 'finComp', map: 'finComp>name'
							}, {
								 name: 'finComp_id', map: 'finComp>id'
							},  {
								name : 'descr'
							}, {
								name : 'remark'
							}, {
								name : 'status'
							} , {
								name : 'alarm',
								type : 'date'
							}, {
								name : 'plan_id', map: 'plan>id'
							}],
							addRow: function (rowID, rowData, position, commit) {
			                    
								rowData.id =null;
								
								$.ajax({
									url: './json/addActivity.do',
									cache: false,
									type: 'POST',
									data: rowData,
									success: function (result,status,xhr) {
										// console.log(data);
										// $("#dataTable").jqxDataTable('updateBoundData');
										
			                    		commit(true);
			                    		$("#dataTable2").jqxDataTable('updateBoundData');
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
				                     $.ajax({
										url: './json/editActivity.do',
										cache: false,
										type: 'POST',
										data: rowData,
										success: function (result,status,xhr) {
										// update command is executed.
											commit(true);
											$("#dataTable2").jqxDataTable('updateBoundData');
										},
										error: function (xhr,status,error) {
											commit(false);
										}
				                    });           
				             },
				             deleteRow: function (rowID,  commit) {
			                    	console.log("deleteRow");
			                    	var selection = $("#dataTable2").jqxDataTable('getSelection');
			                    	var rowData = selection[0];
			                    	
			                    	console.log("rowData.id:" + rowData.id);
			                    	
			                    	
			                    	
			                		$.post('./json/removeActivity.do', {	
											id : rowData.id
										}, function(result) {
											if (result.success) {		
									// update command is executed.
									
										// console.log("rowID :" + rowID);
										console.log("result : " + result.success);
										
										commit(true);
										$("#dataTable2").jqxDataTable('updateBoundData');
									} else
									{
										commit(false);
									}
			                    });
			                    
			                }
						};

						var dataAdapter = new $.jqx.dataAdapter(source);
						// initialize jqxGrid
						$("#dataTable2").jqxDataTable({
							source : dataAdapter,
							pageable : true,
							filterable : true,
							sortable : true,
							showtoolbar: true,
							columns : [ {
								text : 'id',
								datafield : 'id',
								width : 50
							}, {
								text : 'Financial company',
								datafield : 'finComp',
								width : 100
							}, {
								text : 'Fin company ID',
								datafield : 'finComp_id',
								width : 100,
								hidden: false
							}, {
								text : 'Created',
								datafield : 'created',
								width : 100,
								cellsformat: 'dd-MM-yyyy' 
							}, {
								text : 'Description',
								datafield : 'descr',
								width : 300
							}, {
								text : 'Remark',
								datafield : 'remark',
								width : 300
							} , {
								text : 'Alarm',
								datafield : 'alarm',
								width : 100,
								cellsformat: 'dd-MM-yyyy' 
							}, {
								text : 'Plan ID',
								datafield : 'plan_id',
								width : 100,
								hidden: true
							}],
							rendertoolbar: function (toolbar) {
			                 	var me = this;
			                 	var container = $("<div style='margin: 5px;'></div>");
			                 	toolbar.append(container);
			                 	container.append('<input id="addrowbutton2" type="button" value="Add New Row" />')
			                 	container.append('<input style="margin-left: 5px;" id="deleterowbutton2" type="button" value="Delete Selected Row" />');
			                 	$("#addrowbutton2").jqxButton();
			                 	$("#deleterowbutton2").jqxButton();
			                 	
			                 	// create new row.
			                    $("#addrowbutton2").on('click', function () {
			                        
			                    	$("#dialog2").jqxWindow('setTitle', "Add Row: ");
			                    	$("#dialog2").jqxWindow('open');
			                    	/* 
			                        $("#id").val("");
			                        $("#created").val("");				                   
			                        $("#descr").val("");
			                        $("#remark").val("");
			                        $("#alarm").val("");
			                        */
			                        
			                        clear_data_2();
			              
			                    });
			                 	
			                 	// delete row.
			                    $("#deleterowbutton2").on('click', function () {
			                        
			                    	//var selectedrowindex = $("#dataTable").jqxDataTable('getselectedrowindex');
			                        // var rowscount = $("#dataTable").jqxDataTable('getdatainformation').rowscount;
			                        var selection = $("#dataTable2").jqxDataTable('getSelection');
			                        var rows = $("#dataTable2").jqxDataTable('getRows');
			                        
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
			                           		var commit = $("#dataTable2").jqxDataTable('deleteRow', selectedRow);
			                            }
			                        }
			                    });
			                },
			                ready: function () {
			                	 $("#save2").mousedown(function () {
				                      // close jqxWindow.
				                      $("#dialog2").jqxWindow('close');
				                      // update edited row.
				                      var editRow = parseInt($("#dialog2").attr('data-row'));
				                      			
				                      var plan_id
				                      
									  if ( $("#id2").val() === "") {
										  var selection = $("#dataTable").jqxDataTable('getSelection');
										  var rowDataPlan = selection[0];
										  plan_id = rowDataPlan.id ;
									  } else {
										  plan_id = $("#plan_id").val();
									  }
									  
				                      console.log("plan_id " + plan_id);
				                      
				                      var rowData = {
				                          id: $("#id2").val(),
				                          created: $("#created").val(),
				                          descr: $("#descr").val(),
				                          alarm: $("#alarm").val(),
				                          remark: $("#remark").val(), 
				                          finComp_id: $("#finComp_id").val(),
				                          plan_id: plan_id
				                      };
				                      
				                      /* 
				                      console.log('editRow ' + editRow);
				                      console.log('rowData ' + rowData);
				                       */
				                      
				                      console.log("id2 : " + $("#id2").val());
				                       
				                      if ( $("#id2").val() === "") 
				                    	  $("#dataTable2").jqxDataTable('addRow', null, rowData);	                
				                      else
				                    	  $("#dataTable2").jqxDataTable('updateRow', editRow, rowData);
				                 }); 
			                	
			                	$("#cancel2").mousedown(function () {
				                      // close jqxWindow.
				                      $("#dialog2").jqxWindow('close');
				                });
			                	
			                	$("#dialog2").jqxWindow({
				                      resizable: false,
				                      position: { left: $("#dataTable2").offset().left + 75, top: $("#dataTable2").offset().top + 35 },
				                      width: 600, height: 400,
				                      autoOpen: false
				                  });
				                
			                	$("#dialog2").on('close', function () {
				                      $("#dataTable2").jqxDataTable({ disabled: false });
				                  });
			                	
			                	$("#dialog2").css('visibility', 'visible');
			                }
						
						});

					});
					
					$("#dataTable").on('rowDoubleClick', function (event) {
		                var args = event.args;
		                var index = args.index;
		                var row = args.row;
		                
		                clear_data();
		                
		                // update the widgets inside jqxWindow.
		                $("#dialog").jqxWindow('setTitle', "Edit Row: " + row.id);
		                $("#dialog").jqxWindow('open');
		                $("#dialog").attr('data-row', index);
		                $("#dataTable").jqxDataTable({ disabled: true });
		                $("#id").val(row.id);
		                $("#createdDt").val(row.createdDt);
		                 $("#code").val(row.code);
                        $("#loanAmt").val(row.loanAmt);
                        $("#loanRcvDt").val(row.loanRcvDt);
                        $("#loanUsage").val(row.loanUsage);
                        $("#signDt").val(row.signDt);
                        $("#status").val(row.status);
                        $("#CommissionPercent").val(row.commissionPercent);
                        
                        $("#cust").val(row.cust);
                        $("#custId").val(row.custId);
                        
                        $("#sales").val(row.sales);
                        $("#salesId").val(row.salesId);
					});
					
					var timer;
					
					$("#cust").jqxInput({ 
						placeHolder: "Enter a customer name",
						source: function (query, response) {
		                        var dataAdapter = new $.jqx.dataAdapter
		                        (
		                            {
		                                // datatype: "jsonp",
		                                datatype: "json",
		                                datafields:
		                                [
		                                /*  
		                                    { name: 'countryName' }, { name: 'name' },
		                                    { name: 'population', type: 'float' },
		                                    { name: 'continentCode' },
		                                    { name: 'adminName1' }
		                                 */
		                                	{ name: 'name' }, 
		                                	{ name: 'tel' },
		                                	{ name: 'id'}
		                                ],
		                                url: "./json/searchCust.do"
		                                
		                                , data:
		                                {
		                                    // featureClass: "P",
		                                    style: "full",
		                                    maxRows: 12,
		                                    // username: "jqwidgets"
		                                } 
		                                
		                            }
		                            
		                            , {
		                                autoBind: true,
		                                formatData: function (data) {
		                                    data.name_startsWith = query;
		                                    return data;
		                                },
		                                loadComplete: function (data) {
		                                    // if (data.geonames.length > 0) {
		                                    	
		                                    console.log("data.rows.length " + data.rows);
		                                    
		                                    if (data.rows.length > 0) {
		                                        // response($.map(data.geonames, function (item) {
		                                        response($.map(data.rows, function (item) {	
		                                            
		                                        	// $("#cust_id").val("");
		                                        	// $("#cust_id").val(item.id);
		                                        	
		                                        	// console.log("cust_id " + item.id);
		                                        	
		                                        	return {		                                        		
		                                        		// label: item.name + (item.adminName1 ? ", " + item.adminName1 : "") + ", " + item.countryName,
		                                                label: item.name + ", " + item.tel + ", "  + item.id,
		                                                // value : item.id
		                                                // value_2 : $("#cust_id").val(item.id)
		                                            }
		                                        }));
		                                    }
		                                }
		                            }
		                                  
		                        );
		                    }
		                            
		                });
							                                       
		             function clear_data() {                           
		                $("#id").val("");
                        $("#createdDt").val("");				                   
                        $("#code").val("");
                        $("#loanAmt").val("");
                        $("#loanUsage").val("");
                        $("#loanRcvDt").val("");			                       
                        $("#signDt").val("");
                        $("#status").val("");
                        $("#CommissionPercent").val("");
                        
                        $("#cust").val("");
                        $("#custId").val("");
                        
                        $("#sales").val("");
                        $("#salesId").val("");
                        
		             };
		             
		             function clear_data_2() {
		            	 	$("#id2").val("");
	                        $("#created").val("");				                   
	                        $("#descr").val("");
	                        $("#remark").val("");
	                        $("#alarm").val("");
	                        $("#finComp_id").val("");
	                        $("#plan_id").val("");
	                        $("#finComp_id").jqxComboBox('clearSelection');
		             }
		             
					 
		             $('#cust').on('select', function () { 
		            	 // var value = $('#jqxInput').val(); 		            	 
		            	 // console.log("cust " + $("#cust").val());
		            	 
		            	 var result = $("#cust").val().split(",");
		            	 // $("#custId").val(result[2].trim());
		            	 
		            	 if(result[2].trim())
		            		 $("#custId").val(result[2].trim());		            		 
		            	 else 
		            		 $("#custId").val("");
		            });
 					
 					 $("#clear_cust").mousedown(function () {
 						 $("#cust").val("");
 						 $("#custId").val("");
 					 });
 					
 					$("#sales").jqxInput({ 
						placeHolder: "Enter a Sales name",
						source: function (query, response) {
		                        var dataAdapter = new $.jqx.dataAdapter
		                        (
		                            {
		                                datatype: "json",
		                                datafields:
		                                [
		                                	{ name: 'name' }, 
		                                	{ name: 'id'}
		                                ],
		                                url: "./json/searchSales.do"
		                                
		                                , data:
		                                {
		                                    style: "full",
		                                    maxRows: 12,
				                        } 		                                
		                            }		                            
		                            , {
		                                autoBind: true,
		                                formatData: function (data) {
		                                    data.name_startsWith = query;
		                                    return data;
		                                },
		                                loadComplete: function (data) {
		                                    console.log("data.rows.length " + data.rows);
		                                    
		                                    if (data.rows.length > 0) {
		                                        response($.map(data.rows, function (item) {	

		                                        	return {		                                        		
			                                            label: item.name + ", "  + item.id,		                                                
		                                            }
		                                        }));
		                                    }
		                                }
		                            }
		                                  
		                        );
		                    }
		                            
		                });
 					
 					   $("#clear_sales").mousedown(function () {
 						 $("#sales").val("");
 						 $("#salesId").val("");
 					   });
 					   
 					  $('#sales').on('select', function () {             	 
 		            	 var result = $("#sales").val().split(",");
 			             if(result[1].trim())
 		            		 $("#salesId").val(result[1].trim());		            		 
 		            	 else 
 		            		 $("#salesId").val("");
 		            });
 					  
 					$("#createdDt").jqxDateTimeInput({ width: '100px', height: '20px',  culture: 'en-CA' , formatString: 'dd-MM-yyyy' });
 					$("#loanRcvDt").jqxDateTimeInput({ width: '100px', height: '20px',  culture: 'en-CA' , formatString: 'dd-MM-yyyy' });
 					$("#signDt").jqxDateTimeInput({ width: '100px', height: '20px',  culture: 'en-CA' , formatString: 'dd-MM-yyyy' });
 					 
 					 
 					 $("#dataTable2").on('rowDoubleClick', function (event) {
 		                var args = event.args;
 		                var index = args.index;
 		                var row = args.row;
 		                
 		                clear_data_2();
 		                
 		               console.log("finComp_id : " + row.finComp_id);
 		                
 		                // update the widgets inside jqxWindow.
 		                $("#dialog2").jqxWindow('setTitle', "Edit Row: " + row.id);
 		                $("#dialog2").jqxWindow('open');
 		                $("#dialog2").attr('data-row', index);
 		                $("#dataTable2").jqxDataTable({ disabled: true });
 		                $("#id2").val(row.id);
 		                $("#created").val(row.created);
 		                $("#descr").val(row.descr);
                        $("#remark").val(row.remark);
                        $("#alarm").val(row.alarm);
                        
                        $("#finComp_id").val(row.finComp_id);
                        $("#plan_id").val(row.plan_id);
                        
                       
                        
 					});
 					
 					$("#created").jqxDateTimeInput({ width: '100px', height: '20px',  culture: 'en-CA' , formatString: 'dd-MM-yyyy' });
 					$("#alarm").jqxDateTimeInput({ width: '100px', height: '20px',  culture: 'en-CA' , formatString: 'dd-MM-yyyy' });
 					
 					 
 					var source_3 =
 		            {
 		                datatype: "json",
 		                datafields: [
 		                    // { name: 'countryName' },
 		                    { name: 'name' },
 		                    { name: 'id'}
 		                    // { name: 'population', type: 'float' },
 		                    // { name: 'continentCode' }
 		                ],
 		                url: "./json/getFinComp.do",
 		                data: {
 		                    // featureClass: "P",
 		                    style: "full",
 		                    maxRows: 50,
 		                    // username: "jqwidgets"
 		                }
 		            };
 		            var dataAdapter_3 = new $.jqx.dataAdapter(source_3);
 		            $("#finComp_id").jqxComboBox(
 		            {
 		                width: 200,
 		                height: 25,
 		                source: dataAdapter_3,
 		                selectedIndex: 0,
 		                // displayMember: "countryName",
 		                // valueMember: "name"
 		                displayMember: "name",
 		                valueMember: "id"
 		            });
 		            
 		           var source_4 = [
 		                        "已約",
 		                        "催收中",
 		                        "已簽",
 		                        "取消"
 		                        ];
 		           
 		          $("#status").jqxComboBox({ 
 		        	  source: source_4, 
 		        	  selectedIndex: 0, 
 		        	  width: '200', height: '25'});
 		          
				});  // End function
		
	</script>

	<!-- <h3>Plan</h3>
	 -->
	
	 
	<div id="dataTable"></div>

	<br></br> 

	<!-- <h3>Activities</h3>
	-->	
	
	<div id="dataTable2"></div>

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
                    <td align="right">Created Date:
                    </td>
                    <td align="left">
                        <!-- <input id="createdDt" type="text"> -->
                        <div id="createdDt" ></div>
                    </td>
                </tr>
                <tr>
                    <td align="right">Code:
                    </td>
                    <td align="left">
                        <input id="code" type="text">
                    </td>
                </tr>
                <tr>
                    <td align="right">Loan Amount:
                    </td>
                    <td align="left">
                        <input id="loanAmt" type="text">
                    </td>
                </tr>
                 <tr>
                    <td align="right">Loan Purpose:
                    </td>
                    <td align="left">
                        <!-- <input id="loanUsage" type="text"> -->
                        <textarea id="loanUsage"></textarea>
                    </td>
                </tr>
                <tr>
                    <td align="right">Loan Received Date:
                    </td>
                    <td align="left">
                        <!-- <input id="loanRcvDt" /> -->
                        <div id="loanRcvDt"></div>
                    </td>
                </tr>
                <tr>
                    <td align="right">Status:
                    </td>
                    <td align="left">
                        <!-- <input id="status" /> -->
                        <div id="status"></div>
                    </td>
                </tr> 
                 <tr>
                    <td align="right">Commission %:
                    </td>
                    <td align="left">
                        <input id="commissionPercent" />
                    </td>
                </tr>                  
              	 <tr>
                    <td align="right">Signed Date:
                    </td>
                    <td align="left">
                        <!-- <input id="signDt" type="text"> -->
                        <div id=signDt></div>
                    </td>
                </tr>
                <tr>
                    <td align="right">Cust:
                    </td>
                    <td align="left">
                        <input type="text" id="cust" /> <button id="clear_cust">X</button> 
                    </td>                 
                    
                </tr>
                <tr>
                <!-- 
                 	<td align="right">Cust ID:
                    </td>
	             -->
	                 <td align="left">
	                        <input id="custId" type="hidden"/>
	                </td>
                </tr>
                <tr>
                    <td align="right">Sales:
                    </td>
                    <td align="left">
                        <input type="text" id="sales" /> <button id="clear_sales">X</button> 
                    </td>                 
                    
                </tr>
                <tr>
                 	<!-- <td align="right">Sales ID:
                    </td> -->
	                <td align="left">
	                        <input id="salesId" type="hidden"/>
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
    
    <div style="visibility: hidden;" id="dialog2">
        <div>Edit Dialog</div>
        <div style="overflow: hidden;">
            <table style="table-layout: fixed; border-style: none;">
                <tr>
                    <td align="right">ID:
                    </td>
                    <td align="left">
                        <input id="id2" type="text" readonly="true" disabled="disabled" />
                    </td>
                </tr>
                <tr>
                    <td align="right">Financial Company:
                    </td>
                    <td align="left">
                        <!-- <input id="finComp" type="text"> -->
                        <div id="finComp_id"></div>
                    </td>                
                </tr>
                <tr>
                    <td align="right">Created Date:
                    </td>
                    <td align="left">
                        <!-- <input id="created" type="text"> -->
                        <div id="created" ></div>
                    </td>
                </tr>
                <tr>
                    <td align="right">Description:
                    </td>
                    <td align="left">
                       <!--  <input id="descr" type="text"> -->
                       <textarea id="descr"></textarea>
                    </td>
                </tr>
                <tr>
                    <td align="right">Remark:
                    </td>
                    <td align="left">
                       <!--  <input id="remark" type="text"> -->
                       <textarea id="remark"></textarea>
                    </td>
                </tr>
                 <tr>
                    <td align="right">Alarm:
                    </td>
                    <td align="left">
                       <!--  <input id="alarm" type="text"> -->
                       <div id="alarm" ></div>
                    </td>
                </tr>
                 <tr>
                   <!--  
                   <td align="right">Plan ID:
         			</td> 
         			-->
                    <td align="left">
                       <!--  <input id="plan_id" type="text"> -->
                       <input id="plan_id" type="hidden">
                    </td>
                </tr> 

                <tr>
                    <td colspan="2" align="right">
                         <br />
                         <button id="save2">Save</button> <button style="margin-left: 5px;" id="cancel2">Cancel</button></td>                    
                 </tr>
            </table>
         </div>
      </div>

</body>
</html>