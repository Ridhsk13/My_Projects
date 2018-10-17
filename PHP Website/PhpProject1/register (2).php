  <?php
require_once("includes/config.php");
//now we can use our config file
?>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
     <script type="text/javascript" src="assets/js/register.js" ></script>
     <script src="assets/bootstrap/js/bootstrap.min.js"></script>

      <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
     
      <link href="assets/css/style.css" rel="stylesheet" type="text/css">
      <link rel="icon" type="image/png" href="<?php echo BASE_URL; ?>images/icon.png">
      <title>Elementary</title>    
      
    </head>
<body class="texture" onload="document.register.uname.focus();">
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">
        <img alt="Brand" src="images/icon.png" class="logo">
      </a>
    </div>
    <center><h1 class="text-muted">Welcome to Elementary</h1></center>
  </div>
</nav>
<?php
    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "security";
    $user = "";
    $phone = "";
    $pass = "";

    if(isset($_POST['register'])){
      if(isset($_POST['uname'])){
        $user=$_POST['uname'];
      }
      if(isset($_POST['phone'])){
        $phone = $_POST['phone'];
      }
      if(isset($_POST['psw'])){
        $pass = md5($_POST['psw']);
      }

      

    // Create connection
    $conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    if ($conn->connect_error) {
      die("Connection failed: " . $conn->connect_error);
    } 
    $conn->set_charset("utf8");
      $user = stripslashes($user);
      $phone = stripslashes($phone);
      $pass = stripslashes($pass);


      $user = mysqli_real_escape_string($conn, $user);
      $phone = mysqli_real_escape_string($conn, $phone);
      $pass = mysqli_real_escape_string($conn, $pass);

    $sql_u = "SELECT Name FROM user WHERE Name='$user'";
    $result_u = $conn->query($sql_u);

    if ($result_u->num_rows > 0) {
        echo "<br/><br/><br/><div style='text-align:center' class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span><span class='sr-only'>Error:</span>"."Username Already Taken"."</div>";
    }
    else
    {
        $sql = "INSERT INTO user (Name, Phone, password) VALUES ('$user', '$phone', '$pass')";
        $result = $conn->query($sql);
        if($result)
        {
            header("Location: login.php");
            exit;
        }
        else {
          echo "0 results";
        }
    }
    $conn->close();
  }
?>
  
<div class="container">
<form class="register card" onsubmit="return checkall();" name="register" method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>">
<fieldset>
    <legend class="rg_legend">Register</legend>
    <div class="row reg">
  <div class="imgcontainer col-md-4">
    <img src="images/register_avatar.png" alt="Avatar" class="avatar">
  </div>
  <div class="container  col-md-8">
  <div class="alert alert-info" role="alert" style="padding: 3px; text-align: center">All Fields are Mandatory</div>
    <label><b>Name</b></label>&nbsp;&nbsp;&nbsp;<span id="name_err"></span><br/>
    <input type="text" placeholder="Eg: J. Smith" name="uname" id="uname" onblur="checkname();">
<br/>
    <label><b>Password</b></label>&nbsp;&nbsp;&nbsp;<span id="pass_err"></span><br/>
    <input type="password" placeholder="Enter Password" name="psw" id="psw" onblur="checkpass();">
<br/>
    <label><b>Confirm Password</b></label>&nbsp;&nbsp;&nbsp;<span id="pass2_err"></span><br/>
    <input type="password" placeholder="Re-enter Password" name="psw_match" id="psw_match" onblur="checkpass2();">
<br/>
    <label><b>Mobile Number</b>&nbsp;&nbsp;&nbsp;<span id="phone_err"></span></label><br/>
    <input type="text" placeholder="Enter Mobile Number" name="phone" id="phone" onblur="checkphone();">
<br/>       
    <a href="login.php"><input type="button" name="login" class="lgn_btn1" value="Exsisting User?"></a>
    <button type="submit" class="rgn_btn1" name="register" style="vertical-align:middle"><span>Register</span></button>
    </div>
  </div>
  </fieldset>
  </form>
  </div>
</div>
</body>
<script>
        $("#phone").on("change keyup paste", function () {
          var output;
          var input = $("#phone").val();
          input = input.replace(/[^0-9]/g, '');
          var area = input.substr(0, 3);
          var pre = input.substr(3, 3);
          var tel = input.substr(6, 4);
          if (area.length < 3) {
              output = area;
          } else if (area.length == 3&& pre.length < 3) {
              output = area + "-" + pre;
          } else if (area.length == 3 && pre.length == 3) {
              output = area + "-" + pre + "-" + tel;
          }
          $("#phone").val(output);
        });
      </script>  
</html>

<?php
include(ROOT_PATH . "includes/footer.php");
?>
