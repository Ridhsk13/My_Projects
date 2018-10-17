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
			$privname = $_POST['privname'];
			$privtype = $_POST['privtype'];
			
			$servername = "localhost";
    		$username = "root";
    		$password = "";
    		$dbname = "security";
    		
    		$conn = new mysqli($servername, $username, $password, $dbname);
    		if ($conn->connect_error) {
    			die("Connection failed: " . $conn->connect_error);
    		}
    		$conn->set_charset('utf8');
    		$sql1 = "SELECT * FROM privileges where privilege_name= '$privname'";
    		$result = $conn->query($sql1);
    		if($result->num_rows > 0){
    			echo "<div class='alert alert-danger alert-dismissible'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>Privilege already Exists</div>";
    		}else{
    		$sql = "INSERT INTO PRIVILEGES (privilege_name,Type) VALUES ('$privname','$privtype')";
    		$conn->query($sql);
 			$conn->close();
    		header('Location: Profile.php');
			}

		}
?>

	<CENTER><h2>Create Privilege</h2></CENTER>
	<form method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>">
	<table>
		<tr>
			<td>Privilege Name</td>
			<td><input type='text' name='privname' /></td>
		</tr>
		<tr>
			<td>Privilege Type</td>
			<td><input type='text' name='privtype' /></td>
		</tr>
		<tr>
			
			<td><input type="submit" class="btn btn-danger btn-lg" name="Profile" value="Back to Home"></td>
			<td><input type='submit' class="btn btn-danger btn-lg" name='add' value='Add Privilege' /></td>
		</tr>
	</table>
	</form>
</body>
</html>