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
    <form method="get" action="index.php">
      <center>
        <div class="mt-5 w-50 h-50 bg-light">
          <span class="align-middle">
          <center>
            <h3 class="py-3 title">Online Bookstore</h1>
          </center>
          <div class="mx-4">
                <input class="textinput my-3" type="text" name="username" placeholder="Username"/><br />
                <input class="textinput my-3" type="password" name="password" placeholder="Password"/><br />
                <div class="inline-block">
                  <input class="my-3 mx-3" type="radio" name="logintype" value="customer" checked>Customer
                  <input class="my-3 mx-3" type="radio" name="logintype" value="staff">Staff<br />
                </div>
                <div class="inline-block">
                  <button class="btn delete my-3 mx-3" type="submit" name="submit" value="Login" />Login
                  <button class="btn btn-success my-3 mx-3" type="submit" name="register" value="register" />Register
                </div>
            </div>
          </span>
        </div>
      </center>
    </form>
    <?php
      if (isset($_GET['register'])) {
        sleep(2);
        header("Location:register.php");
      }
      if (isset($_GET['submit'])) {
        //$staffid = 'S'.rand(100000,999999) ;

        if (empty($_GET['username'])) {
          echo "Must enter Username";
        } elseif (empty($_GET['password'])) {
          echo "Must enter Password";
        } else {
          $username = $_GET['username'];
          $password = $_GET['password'];
          if ($username == 'admin' && $password == 'admin') {
              sleep(2);
              header("Location:person.php");
          } elseif ($_GET['logintype'] == 'customer' && $username != 'admin') {
              $sql = "SELECT user_password FROM customer WHERE user_name='".$username."'";
              $result = $conn->query($sql);
              $row = $result->fetch_assoc();
              $pwd = $row['user_password'];
              if ($pwd != $password) {
                echo "Password does not match";
              } else {
                $_SESSION["username"] = $username;
                echo $_SESSION["username"];
                sleep(2);
                header("Location:customermain.php?username=".$username."");
              }
          } else {
              $sql = "SELECT user_password FROM staff WHERE user_name='".$username."'";
              $pwd = $conn->query($sql);
              if ($pwd != $password) {
                echo "Password does not match";
              } else {
                $_SESSION['username'] = $username;
                sleep(2);
                header("Location:staffmain.php");
              }
          }
        }
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
