<?php
if(!isset($_SESSION))
  {
      session_start();
  }
  require_once('config.php');
  $_SESSION['username'] = "Abc"
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
    <link href="css/login.css" rel="stylesheet">


</head>
<body class="bg-dark">
    <form method="get" action="register.php">
      <center>
        <div class="mt-5 w-50 bg-light">
          <span class="align-middle">
          <center>
            <h3 class="py-3 title">Registration</h1>
          </center>
          <div class="mx-4">
                <input class="textinput my-3" type="text" name="ssn" placeholder="Social number"/><br />
                <input class="textinput my-3" type="text" name="fname" placeholder="First name"/><br />
                <input class="textinput my-3" type="text" name="lname" placeholder="Last name"/><br />
                <input class="textinput my-3" type="text" name="email" placeholder="email"/><br />
                <div class="inline-block">
                  <input class="my-3 mx-3" type="radio" name="sex" value="male" checked>Male
                  <input class="my-3 mx-3" type="radio" name="sex" value="female">Female<br />
                </div>
                <input class="textinput my-3" type="date" name="dob" /><br />
                <textarea class="textinput my-3" rows="3" name="address" placeholder="Address"></textarea><br />
                <input class="textinput my-3" type="text" name="ph_no" placeholder="Phone number"/><br />
                <input class="textinput my-3" type="text" name="username" placeholder="Username"/><br />
                <input class="textinput my-3" type="password" name="password" placeholder="Password"/><br />
                <div class="inline-block">
                  <input class="my-3 mx-3" type="radio" name="logintype" value="customer" checked>Customer
                  <input class="my-3 mx-3" type="radio" name="logintype" value="staff">Staff<br />
                </div>
                <button class="btn btn-success my-3 mx-3" type="submit" name="register" value="register" />Register
                <button class="btn delete my-3 mx-3" type="submit" name="cancel" value="cancel" />Cancel
            </div>
          </span>
        </div>
      </center>
    </form>
    <?php
      if (isset($_GET['register'])) {
        $ssn = $_GET['ssn'];
        $fname = $_GET['fname'];
        $lname = $_GET['lname'];
        $email = $_GET['email'];
        $sex = $_GET['sex'];
        $dob = $_GET['dob'];
        $today = new datetime();
        $diff = $today->diff(new datetime($dob));
        $age = $diff->y;
        $address = $_GET['address'];
        $ph_no = $_GET['ph_no'];
        $user_name = $_GET['username'];
        $user_password = $_GET['password'];
        $logintype = $_GET['logintype'];

        $sql = "INSERT INTO person VALUES ($ssn,'$fname','$lname','$email','$sex','$dob',$age,'$address',$ph_no)";
          if ($conn->query($sql) === TRUE) {
            if ($logintype = "customer") {
              $customer_id = rand(10000,99999);
              $sql2 = "INSERT INTO customer VALUES ($customer_id,$ssn,'$user_name','$user_password',now())";
              if ($conn->query($sql2) === TRUE) {
                sleep(2);
                header("Location:index.php");
              } else {
                echo "Error: " . $sql . "<br>" . $conn->error;
              }
            }

          } else {
            echo "Error: " . $sql . "<br>" . $conn->error;
          }

      }
      if (isset($_GET['cancel'])) {
        sleep(2);
        header("Location:index.php");
      }

     ?>

    <!-- Font Awesome JScript !-->
    <script defer src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>

    <!-- Bootstrap JScript !-->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
