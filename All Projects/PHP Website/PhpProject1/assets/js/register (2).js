function checkall(){
	if(checkname() && checkpass() && checkpass2() && checkphone()){
		return true;
	}
	else
		event.preventDefault();
}
function checkname(){
	var name = document.getElementById('uname');
	var name_len = name.value.length;
	if (name_len==0) {
		var msg = "<div style='padding:2px; display:inline-block;' class='alert alert-danger' role='alert'>"+
		"<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span><span class='sr-only'>Error:</span>"+
		"Name is Required</div>";
		document.getElementById("name_err").innerHTML = msg;
		name.focus();  
		return false;
	} 
	else if (name_len<5) {
		var msg = "<div style='padding:2px; display:inline-block;' class='alert alert-danger' role='alert'>"+
		"<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span><span class='sr-only'>Error:</span>"+
		"It must be atleast 5 character long</div>";
		document.getElementById("name_err").innerHTML = msg;
		name.focus();  
		return false;
	}
	else if (name_len>20) {
		var msg = "<div style='padding:2px; display:inline-block;' class='alert alert-danger' role='alert'>"+
		"<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span><span class='sr-only'>Error:</span>"+
		"It cannot be more than 20 character long</div>";
		document.getElementById("name_err").innerHTML = msg;
		name.focus();  
		return false;
	}
	else{
		var msg = "";
		document.getElementById("name_err").innerHTML = msg;
		return true;
	}
}

function checkpass(){
	var pass = document.getElementById('psw');
	var pass_len = pass.value.length;
	if (pass_len<8) {
		var msg = "<div style='padding:2px; display:inline-block;' class='alert alert-danger' role='alert'>"+
		"<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span><span class='sr-only'>Error:</span>"+
		"It must be atleast 8 character long</div>";
		document.getElementById("pass_err").innerHTML = msg;
		pass.focus();
		return false;
	}
	else{
		var msg = "";
		document.getElementById("pass_err").innerHTML = msg;
		return true;
	}
}


function checkpass2(){
	var pass2 = document.getElementById('psw_match');
	var pass = document.getElementById('psw');
	var pass2_val = pass2.value;
	var pass_val = pass.value; 
	if (pass_val!=pass2_val) {
		var msg = "<div style='padding:2px; display:inline-block;' class='alert alert-danger' role='alert'>"+
		"<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span><span class='sr-only'>Error:</span>"+
		"Password mismatch</div>";
		document.getElementById("pass2_err").innerHTML = msg;
		return false;
	}
	else{
		var msg = "";
		document.getElementById("pass2_err").innerHTML = msg;
		return true;
	}	
}


function checkphone(){
	var phone = document.getElementById('phone');
	var phone_len = phone.value.length;
	if (phone_len == 12) {
		var msg = "";
		document.getElementById('phone_err').innerHTML = msg;
		return true;
	}
	else{
		var msg = "<div style='padding:2px; display:inline-block;' class='alert alert-danger' role='alert'>"+
		"<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span><span class='sr-only'>Error:</span>"+
		"10 digit Numeric Only</div>";
		document.getElementById('phone_err').innerHTML = msg;
		phone.focus();
		return false;
	}
}