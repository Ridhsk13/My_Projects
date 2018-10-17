<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<?php
	require_once("includes/config.php");
	include(ROOT_PATH . "includes/footer.php");
	session_start();
?>
<html> 
<head><title>Elementary - A Database Project</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/project4/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
Welcome, <?php // print a username here ?>

    <ul class="nav navbar-nav navbar-right" style="padding-top:5px;padding-right:20px;">
    <?php 
    	if(array_search("INSERT", $_SESSION['privileges'])){
    ?>
      <li>&nbsp;<a href = "NewRole.php"><input type="submit" class="btn btn-danger btn-lg" name="Role" value="Create Role"></a></li>
      <li>&nbsp;<a href = "NewPrivilege.php"><input type="submit" class="btn btn-danger btn-lg" name="Privilege" value="Create Privilege"></a></li>
      <li>&nbsp;<a href = "NewTable.php"><input type="submit" class="btn btn-danger btn-lg" name="Table" value="Create Table"></a></li>
      <li>&nbsp;<a href = "RoleMgmt.php"><input type="submit" class="btn btn-danger btn-lg" name="Rolemgmt" value="Role Management"></a></li>
      <?php
      }
      	if(array_search("SELECT", $_SESSION['privileges'])){
      ?>

      <li>&nbsp;<a href = "PrivMgmt.php"><input type="submit" class="btn btn-danger btn-lg" name="Privmgmt" value="Privilege Management"></a></li>
      <?php
      	}
      ?>
    </ul>


</body>
</html>
