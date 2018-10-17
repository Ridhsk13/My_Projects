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

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
     
     <script src="assets/bootstrap/js/bootstrap.min.js"></script>


      <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
     
      <link href="assets/css/style.css" rel="stylesheet" type="text/css">
      <link rel="icon" type="image/png" href="<?php echo BASE_URL; ?>images/icon.png">
      <title>Elementary</title>

    </head>
<body class="texture">
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
    
    
    // Create connection
    $conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    if ($conn->connect_error) {
      die("Connection failed: " . $conn->connect_error);
    } 
    $conn->set_charset("utf8");
    $sql = "SELECT NAME FROM ROLES";
    $result = $conn->query($sql);
    
?>

<div class="container">
<form class="login card form-group" method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>">
<fieldset>
    <legend class="lg_legend">Privilege-Role View</legend>
    <div class="row">
  <div class="imgcontainer col-md-4">
    <img src="images/login_avatar.png" alt="Avatar" class="avatar">
  </div>
  <div class="container  col-md-8">
    <label><b>Roles</b></label><br/>
    <select name="rolesaccount" class="form-control" id="cat1">
      <option> --Select--</option>
      <?php
      $sql = "SELECT NAME FROM ROLES";
      $result = $conn->query($sql);
        while($row = $result->fetch_assoc()){
          echo "<option value='".$row['NAME']."'>".$row['NAME']."</option>";
        }
      ?>
      </select>

    <br/>
    <label><b>Privileges</b></label><br/>
    <select name="accountpriv" class="form-control" id="subcat1">
      <option>--Select--</option>

    </select>
<br/>       
 </div>
  </div>
  </fieldset>
  </form>

  <form class="login card form-group" method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>">
<fieldset>
    <legend class="lg_legend">Privilege-User View</legend>
    <div class="row">
  <div class="imgcontainer col-md-4">
    <img src="images/login_avatar.png" alt="Avatar" class="avatar">
  </div>
  <div class="container  col-md-8">
    <label><b>Users</b></label><br/>
    <select name="rolesaccount" class="form-control" id="cat2">
      <option> --Select--</option>
      <?php
      $sql = "SELECT NAME FROM USER";
      $result = $conn->query($sql);
        while($row = $result->fetch_assoc()){
          echo "<option value='".$row['NAME']."'>".$row['NAME']."</option>";
        }
      ?>
      </select>

    <br/>
    <label><b>Privileges</b></label><br/>
    <select name="accountpriv" class="form-control" id="subcat2">
      <option>--Select--</option>

    </select>
<br/>       
 </div>
  </div>
  </fieldset>
  </form>

</div>
</body>
  <script>

    $(document).ready(function() {

   $('#cat1').change(function(){
       // don't do anything if use selects "Select Cat"
       if($(this).val() !== "--Select--") {
           // subcat.php would return the list of option elements 
           // based on the category provided, if you have spaces in 
           // your values you will need to escape the values
           $.get('privRole.php?cat='+ $(this).val(),{cat1:$(this).val()}, function(result){
               $('#subcat1').html(result);
           });

       }

   });

   $('#cat2').change(function(){
       // don't do anything if use selects "Select Cat"
       if($(this).val() !== "--Select--") {
           // subcat.php would return the list of option elements 
           // based on the category provided, if you have spaces in 
           // your values you will need to escape the values
           $.get('privRole2.php?cat='+ $(this).val(),{cat2:$(this).val()}, function(result){
               $('#subcat2').html(result);
           });

       }

   });
 });

</script>
</html>
<?php
$conn->close();
include(ROOT_PATH . "includes/footer.php");
?>
