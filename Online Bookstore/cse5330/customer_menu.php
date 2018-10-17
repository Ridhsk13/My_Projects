<?php
if(!isset($_SESSION))
  {
      session_start();
  }
  require_once('config.php');
 ?>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Online Bookstore Login</title>
  <!-- Bootstrap CSS !-->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

  <!-- Font Awesome CSS !-->
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">

  <link href="css/admincss.css" rel="stylesheet">

  <!-- jQuery CDN -->
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>

</head>
<body>

  <!--<div class="container-fluid  bg-dark">
    <br />
    <center><p class="display-4 text-light">Admin Panel</p></center>
    <br />
  </div>-->
  <?php
    //$conn = $_SESSION['connection'];
    //$sql = "INSERT INTO publisher VALUES (1, '5478963215' ,'john', 'dallas')";

    //if ($conn->query($sql) === TRUE) {
    //    echo "New record created successfully";
    //} else {
    //    echo "Error: " . $sql . "<br>" . $conn->error;
    //}
    if (isset($_GET['submit'])) {
      $ssn = $_GET['ssn'];
      $fname = $_GET['fname'];
      $lname = $_GET['lname'];
      $email = $_GET['email'];
      $sex = $_GET['gender'];
      $dob = $_GET['dob'];
      $today = new datetime();
      $diff = $today->diff(new datetime($dob));
      $age = $diff->y;
      $address = $_GET['address'];
      $ph_no = $_GET['phone_number'];
      //echo $dob." / ".$age;

      //$sql = "INSERT INTO person VALUES('$ssn','$fname','$lname','$email','$sex','$dob',$age,'$address','$ph_no')";
      //if ($conn->query($sql) === TRUE) {
      //    echo "New record created successfully";
      //} else {
      //    echo "Error: " . $sql . "<br>" . $conn->error;
      //}
      }
  ?>
  <div class="wrapper">
  <nav id="sidebar" >
      <div class="sidebar-header">
          <h3>
            <center>
              <?php
                if (isset($_SESSION['username'])) {
                  echo $_SESSION['username'];
                }else {
                  echo "User";
                }
              ?>
            </center>
          </h3>
      </div>

      <ul class="list-unstyled components">
          <li class="active">
              <a href="customermain.php" id="link" ><i class="glyphicon glyphicon-home"></i>Items</a>
          </li>
          <li>
              <a href="viewcart.php"><i class="glyphicon glyphicon-home"></i>View Cart</a>
          </li>
          <li>
              <a href="#"><i class="glyphicon glyphicon-home"></i>My Orders</a>
          </li>
          <li>
              <a href="#"><i class="glyphicon glyphicon-home"></i>Logout</a>
          </li>
      </ul>
  </nav>


  <!--<form method="get" action="adminmain.php">
    SSN: <input type="text" name="ssn" /><br />
    First Name: <input type="text" name="fname" /><br />
    Last Name: <input type="text" name="lname" /><br />
    Email: <input type="email" name="email" /><br />
    Sex: <input type="radio" name="gender" value="male" checked/> Male <input type="radio" name="gender" value="female" /> Female
    Date of Birth: <input type="date" name="dob" /><br />
    Address: <textarea name="address" rows="4" cols="5"></textarea><br />
    Phone Number: <input type="text" name="phone_number" /><br />
    <input type="submit" name="submit" />
  </form>
-->

  <script>
  $("nav ul li").on("click", function() {
      $("nav ul li").removeClass("active");
      $(this).addClass("active");
    });
  </script>


  <!-- Font Awesome JScript !-->
  <script defer src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>

  <!-- Bootstrap JScript !-->
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


  </html>
</body>
