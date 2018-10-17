<html> 
<head><title>Elementary - A Database Project</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/project4/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<?php

	if(isset($_POST['Profile'])){
        header('Location: Profile.php');
      }
    if(isset($_POST['add'])){
			$tablename = $_POST['tablename'];
			$tableid = $_POST['tableid'];
			
			$servername = "localhost";
    		$username = "root";
    		$password = "";
    		$dbname = "security";
    		
    		$conn = new mysqli($servername, $username, $password, $dbname);
    		if ($conn->connect_error) {
    			die("Connection failed: " . $conn->connect_error);
    		}
    		$conn->set_charset('utf8');
    		$sql1 = "SELECT * FROM TABLES where tables_name= '$tablename'";
    		$result = $conn->query($sql1);
    		if($result->num_rows > 0){
    			echo "<div class='alert alert-danger alert-dismissible'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>Tablename already Exists</div>";
    		}else{
    		$sql = "INSERT INTO TABLES (tables_name,id_no) VALUES ('$tablename',$tableid)";
    		$conn->query($sql);
 			$conn->close();
    		header('Location: Profile.php');
			}

		}
?>

	<CENTER><h2>Create Table</h2></CENTER>
	<form method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>">
	<table>
		<tr>
			<td>Table Name</td>
			<td><input type='text' name='tablename' /></td>
		</tr>
		<tr>
			<td>Id Number</td>
			<td><input type='text' name='tableid' /></td>
		</tr>
		<tr>
			
			<td><input type="submit" class="btn btn-danger btn-lg" name="Profile" value="Back to Home"></td>
			<td><input type='submit' class="btn btn-danger btn-lg" name='add' value='Add Table' /></td>
		</tr>
	</table>
	</form>
</body>
</html>