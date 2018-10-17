<?php
$servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "security";
    
    $parent = $_GET['cat'];
    
    // Create connection
    $conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    if ($conn->connect_error) {
      die("Connection failed: " . $conn->connect_error);
    } 
    $conn->set_charset("utf8");

  $query="SELECT Name FROM user where id_no =(select id_no from tables where tables_name =(select table_name from accesses where role_name ='".$parent."'))";
  $result = $conn->query($query);

 	echo"<select name='sselect1' class='e1'><option value='0'>--Select--</option>";
  // printing the list box select command
  while($catinfo=$result->fetch_assoc()){//Array or records stored in $nt

      echo "<option value='".$catinfo['Name']."'>".$catinfo['Name']."</option>";

  }

 echo"</select>";

?>