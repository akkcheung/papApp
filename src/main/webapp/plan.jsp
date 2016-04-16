<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="./jqwidgets/styles/jqx.base.css"
	type="text/css" />
<link rel="stylesheet" href="./jqwidgets/styles/jqx.classic.css"
	type="text/css" />
<script type="text/javascript" src="./scripts/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="./jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="./jqwidgets/jqxvalidator.js"></script>
<script type="text/javascript" src="./jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript" src="./jqwidgets/jqxdatetimeinput.js"></script>
<script type="text/javascript" src="./jqwidgets/jqxcalendar.js"></script>
<script type="text/javascript"
	src="./jqwidgets/globalization/globalize.js"></script>

<script type="text/javascript" src="./jqwidgets/jqxscrollbar.js"></script>
<script type="text/javascript" src="./jqwidgets/jqxlistbox.js"></script>
<script type="text/javascript" src="./jqwidgets/jqxdropdownlist.js"></script>

</head>
<body>

<jsp:include page="/menu.jsp"></jsp:include>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#sendButton').jqxButton({
				width : 60,
				height : 25,
				theme : 'classic'
			});
			
			 
			$('#saveButton').bind('click', function() {
				$('#planForm').jqxValidator('validate');
				
				// document.getElementById("planForm").submit();
				
				id = $("#id").val() ;
	            code = $("#code").val();
	            signDt =  $("#signDt").val();
	            status = $("#status").val();
	            loanUsage = $("#status").val();
				
	           var dataString = 'id=' + id + '&code=' + code + '&signDt=' + signDt + 
	           '&status=' + status +  "&loanUsage=" + usage;
	                
				// AJAX code to submit form.
				$.ajax({
				type: "POST",
				url: "./json/newPlan.do",
				data: dataString,
				cache: false,
				success: function(html) {
				alert(html);
				}
				});
				}
				return false;
			});
			
			$('#planForm').jqxValidator({
				rules : [
				/*
				{ input: '#userInput', message: 'Your username must be between 3 and 12 characters!', action: 'keyup', rule: 'length=3,12' },
				{ input: '#passwordInput', message: 'Your password must be between 4 and 12 characters!', action: 'keyup', rule: 'length=4,12' },
				{ input: '#passwordConfirmInput', message: 'Passwords doesn\'t match!', action: 'keyup, focus', rule: function (input) {
				    if (input.val() === $('#passwordInput').val()) {
				        return true;
				    }
				    return false;
				}
				},
				{ input: '#emailInput', message: 'Invalid e-mail!', action: 'keyup', rule: 'email' } 
				 */
				{ input: '#code', message: 'Code is required!', action: 'keyup, blur', rule: 'required' }
				],
				theme : 'classic'
			});

			$("#signDt").jqxDateTimeInput({
				showCalendarButton : true,
				width : '300px',
				height : '25px'
			});

			var source = [ "", "已簽約", "未簽約", "已批", "催收中", "取消", "已約" ];
			// Create a jqxListBox
			$("#status").jqxDropDownList({
				source : source,
				selectedIndex : 0,
				width : 200,
				height : 25
			});
			
			 /* attach a submit handler to the form */
            $("#planForm").submit(function(event) {

                /* stop form from submitting normally */
                event.preventDefault();

                /* get some values from elements on the page: */
                var $form = $(this),
                    // term = $form.find('input[name="s"]').val(),
                    
                    
	                
	                
                    url = $form.attr('action');

                /* Send the data using post */
                var posting = $.post(url, {
                    // s: term
                    
                	id: $("#id").val(),
	                code: $("#code").val(),
	                signDt: $("#signDt").val(),
	                status : $("#status").val(),
	                loanUsage : $("#status").val()
	                
                });

                /* Put the results in a div */
                posting.done(function(data) {
                	/* 
                    var content = $(data).find('#content');
                    $("#result").empty().append(content);
                     */
                });
            });
			 
		});
	</script>

	<div>
		<h3>Plan</h3>
	</div>
	<!-- <form id="testForm" action="./">
    <table class="register-table">
        <tr>
            <td>Username:</td>
            <td><input type="text" id="userInput" class="text-input" /></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" id="passwordInput" class="text-input" /></td>
        </tr>
        <tr>
            <td>Confirm password:</td>
            <td><input type="password" id="passwordConfirmInput" class="text-input" /></td>
        </tr>
        <tr>
            <td>E-mail:</td>
            <td><input type="text" id="emailInput" class="text-input" /></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <input type="button" value="Send" id="sendButton" />
            </td>
        </tr>
    </table>
</form> -->


	<form id="planForm" action="./json/newPlan.do">

	<fieldset>
	
		<label>id</label>
		<div>
			<input type="text" id="id" class="text-input" />
		</div>
		
		<label>Office</label>
		<div>
			<input type="text" id="office" class="text-input" />
		</div>

		
		<label>Code:</label>
		<div>
			<input type="text" id="code" class="text-input" />
		</div>

		<label>簽約日期</label>
		<div id="signDt"></div>

		<label>Status</label>
		<div id="status"></div>

		<label>貸款目的</label>
		<div>
			<input type="text" id="loanUsage" class="text-input" />
		</div>


		<input type="button" value="New" id="sendButton" /> 
		<input
			type="submit" value="Save" id="saveButton" />
	</fieldset>
	
	</form>

	<script>
           
            
     </script>

	<form id="financeCoForm" action="./">

		<table class="register-table">
			<tr>
				<td>財務公司</td>
				<td><input type="password" id="passwordInput"
					class="text-input" /></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input
					type="button" value="Add" id="sendButton" /></td>
			</tr>
		</table>
	</form>

	<form id="EventForm" action="./">

		<fieldset>
		<label>日期</label>
		<div>
			<input type="password" id="passwordInput"
					class="text-input" />
		</div>
		
		
		<label>進度</label>
				<input type="password" id="passwordInput"
					class="text-input" />
		
		<input
					type="button" value="Add" id="sendButton" />
		</fieldset>
		
	</form>

	<form id="custForm" action="./">
		
		<fieldset>
		
		<label>Name:</label>
		<div>
		<input type="text" id="name" class="text-input" />
		</div>
		
		
		<label>Identity:</label>
		<div>
			<input type="input" id="identity"
					class="text-input" />
		</div>
		
			<input
					type="button" value="Select" id="sendButton" />
					
		</fieldset>
		
	</form>
</html>
</body>