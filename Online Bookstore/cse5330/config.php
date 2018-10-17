<?php
  $servername = "localhost";
  $dbusername = "root";
  $dbpassword = "ridhs7474";
  $dbname = "bookstore";
  // Create connection
  $conn = new mysqli($servername, $dbusername, $dbpassword, $dbname);

  // Check connection
  if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
  }
  //echo "Connected successfully";
?>
