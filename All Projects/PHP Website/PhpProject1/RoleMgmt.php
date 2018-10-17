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
    $sqluser = "select name from user where id_no in (select id_no from tables where tables_name in (SELECT table_name from accesses where role_name='farji'))";
    $result1 = $conn->query($sqluser);
    while($row = $result1->fetch_assoc()){
      echo $row["name"];
    }
    $result = $conn->query($sql);
    
?>

<div class="container">
<!-- TO add or delete role-user form -->
<form class="login card form-group" method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>">
<fieldset>
    <legend class="lg_legend">Role Management</legend>
    <div class="row">
  <div class="imgcontainer col-md-4">
    <img src="images/login_avatar.png" alt="Avatar" class="avatar">
  </div>
  <div class="container  col-md-8">
    <label><b>Roles</b></label><br/>
    <select name="roles" class="form-control" id="cat">
    	<option> --Select--</option>
    	<?php
        while($row = $result->fetch_assoc()){
          echo "<option value='".$row['NAME']."'>".$row['NAME']."</option>";
        }
    	?>
	    </select>

    <br/>
    <label><b>Users</b></label><br/>
    <select name="users" class="form-control" id="subcat">
      <option>--Select--</option>

    </select>
<br/>       
    <input type="text" name="userrole"  class="form-control" />
    <br/>
    <button type="submit" name="add" class="rgn_btn" value="Add">Add</button>
    <button type="submit" class="lgn_btn" name="delete" style="vertical-align:middle"><span>Delete</span></button>
    </div>
  </div>
  </fieldset>
  </form>
  </div>
</div>
<?php
  
  if(isset($_POST['add'])){
        if($_POST['roles'] == "--Select--" || !isset($_POST['userrole'])){
          echo "role is not selected or username is not specified";
        }else{
          $sqlgettable = "SELECT tables_name from tables where id_no = (select id_no from user where name='".$_POST["userrole"]."')";
          $resulttab = $conn-> query($sqlgettable);
          if($resulttab->num_rows == 0){
            echo "No table_name found for this user";
          }else{
          $rowtab = $resulttab->fetch_assoc();
          $sqladduserrole = "INSERT INTO accesses(role_name,table_name) VALUES ('".$_POST['roles']."','".$rowtab["tables_name"]."')";
          $conn -> query($sqladduserrole);
          header('Location: index.php');
          } 
        }
      }

      if(isset($_POST['delete'])){
    
      if($_POST['roles'] == "--Select--" || $_POST['users']== 0){
          echo "Please select both role and user";
        }else{
          $sqldeleteuser = "DELETE FROM ACCESSES where role_name='".$_POST["roles"]."' and table_name in (select tables_name from tables where id_no = (select id_no from user where name = '".$_POST["users"]."'))";
          if ($conn->query($sqldeleteuser) === TRUE) {
          echo "Record deleted successfully";
          header('Location: index.php');
} else {
    echo "Error deleting record";
}

        }
      }

?>
<!-- TO add or delete role-account_privilege form -->
<form class="login card form-group" method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>">
<fieldset>
    <legend class="lg_legend">Role Management</legend>
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
          echo $row['NAME'];
          echo "<option value='".$row['NAME']."'>".$row['NAME']."</option>";
        }
      ?>
      </select>

    <br/>
    <label><b>Account Privileges</b></label><br/>
    <select name="accountpriv" class="form-control" id="subcat1">
      <option>--Select--</option>

    </select>
<br/>       
    <input type="text" name="accountrole"  class="form-control" />
    <br/>
    <button type="submit" name="addAccount" class="rgn_btn">Add</button>
    <button type="submit" class="lgn_btn" name="deleteAccount" style="vertical-align:middle"><span>Delete</span></button>
    </div>
  </div>
  </fieldset>
  </form>
  <?php
  
  if(isset($_POST['addAccount'])){
        if($_POST['rolesaccount'] == "--Select--" || !isset($_POST['accountrole'])){
          echo "role is not selected or username is not specified";
        }else{
          $sqlgettable = "SELECT privilege_name,type from privileges where privilege_name= '".$_POST['accountrole']."'";
          $resulttab = $conn-> query($sqlgettable);
          $rowtab = $resulttab->fetch_assoc();
          $account_priv_type = $rowtab['type'];
          $account_priv_name = $rowtab['privilege_name'];
          if($resulttab->num_rows == 0){
            echo "No Such Privelege found ";
          }else if($account_priv_type !="ACCOUNT"){
            echo "It is not an Account Privilege";
          }
          else{
          $rowtab = $resulttab->fetch_assoc();
          $sqladduserrole = "INSERT INTO role_desc(role_name,privilege_name) VALUES ('".$_POST['rolesaccount']."','".$account_priv_name."')";
          $conn -> query($sqladduserrole);
          header('Location: index.php');
          } 
        }
      }

      if(isset($_POST['deleteAccount'])){
      echo $_POST['rolesaccount'];
      echo $_POST['accountpriv'];
      if($_POST['rolesaccount'] == "--Select--" || $_POST['accountpriv'] == 0){
          echo "Please select both role and user";
        }else{
          $sqldeleteuser = "DELETE FROM role_desc where role_name='".$_POST["rolesaccount"]."' and privilege_name='".$_POST["accountpriv"]."'";
          if ($conn->query($sqldeleteuser) === TRUE) {
          echo "Record deleted successfully";
          header('Location: index.php');
} else {
    echo "Error deleting record";
}

        }
      }

?>

<!-- TO add or delete role-relation_privilege-tables form -->
<form class="login card form-group" method="POST" action="<?php echo $_SERVER['PHP_SELF']; ?>">
<fieldset>
    <legend class="lg_legend">Role-Relation_Priv-Table Management</legend>
    <div class="row">
  <div class="imgcontainer col-md-4">
    <img src="images/login_avatar.png" alt="Avatar" class="avatar">
  </div>
  <div class="container  col-md-8">
    <label><b>Roles</b></label><br/>
    <select name="rolesrelation" class="form-control" id="cat2">
      <option> --Select--</option>
      <?php
      $sql = "SELECT NAME FROM ROLES";
      $result = $conn->query($sql);
        while($row = $result->fetch_assoc()){
          echo $row['NAME'];
          echo "<option value='".$row['NAME']."'>".$row['NAME']."</option>";
        }
      ?>
      </select>

    <br/>
    <label><b>User Tables</b></label><br/>
    <select name="reltables" class="form-control" id="subcat2">
      <option>--Select--</option>

    </select>
<br/>
    <label><b>Relation Privileges</b></label><br/>
    <select name="relprivs" class="form-control" id="subcat3">
      <option>--Select--</option>

    </select>
<br/>       
    <input type="text" name="relationrole"  class="form-control" />
    <br/>
    <button type="submit" name="addRelation" class="rgn_btn">Add</button>
    <button type="submit" class="lgn_btn" name="deleteRelation" style="vertical-align:middle"><span>Delete</span></button>
    </div>
  </div>
  </fieldset>
  </form>
  <?php
  
  if(isset($_POST['addRelation'])){
        if($_POST['rolesrelation'] == "--Select--" || $_POST['reltables'] == "--Select--" || !isset($_POST['relationrole'])){
          echo "role is not selected or user_table is not selected or privilege_name is not specified";
        }else{
          $sqlgettable = "SELECT privilege_name,type from privileges where privilege_name= '".$_POST['relationrole']."'";
          $resulttab = $conn-> query($sqlgettable);
          $rowtab = $resulttab->fetch_assoc();
          $account_priv_type = $rowtab['type'];
          $account_priv_name = $rowtab['privilege_name'];
          if($resulttab->num_rows == 0){
            echo "No Such Privelege found ";
          }else if($account_priv_type !="RELATION"){
            echo "It is not a Relation Privilege";
          }
          else{
          $rowtab = $resulttab->fetch_assoc();
          $sqladduserrole = "INSERT INTO role_desc(role_name,privilege_name) VALUES ('".$_POST["rolesrelation"]."','".$account_priv_name."')";
          if($conn -> query($sqladduserrole) == TRUE){
            echo "data inserted successfully";
          }else{
            echo "error:".$conn->error;
          }
          $temptab = "SELECT tables_name from tables where id_no = (select id_no from user where name = '".$_POST["reltables"]."')";
          $temprest = $conn->query($temptab);
          $temprow = $temprest -> fetch_assoc();
          $sqladduserpriv = "INSERT INTO accesses(role_name,privilege_name,table_name) VALUES ('".$_POST["rolesrelation"]."','".$account_priv_name."','".$temprow["tables_name"]."')";
          if($conn -> query($sqladduserpriv)){
            echo "<br/>data2 inserted successfully";
          }else{
           echo "error:".$conn->error; 
          }
          header('Location: index.php');
          } 
        }
      }

      if(isset($_POST['deleteRelation'])){
      if($_POST['rolesrelation'] == "--Select--" || $_POST['relprivs'] == 0 || $_POST['reltables'] == 0){
          echo "Please select role, user and Relation Privilege";
        }else{
          $temptab = "SELECT tables_name from tables where id_no = (select id_no from user where name = '".$_POST["reltables"]."')";
          $temprest = $conn->query($temptab);
          $temprow = $temprest -> fetch_assoc();
          $sqldeleteuser = "DELETE FROM accesses where role_name='".$_POST["rolesaccount"]."' and privilege_name='".$_POST["accountpriv"]."' and table_name='".$temprow["tables_name"]."'";
          if ($conn->query($sqldeleteuser) === TRUE) {
          echo "Record deleted successfully";
          header('Location: index.php');
} else {
    echo "Error deleting record";
}

        }
      }

?>


</body>
<script>

    $(document).ready(function() {

   // listen to events on the category dropdown
   $('#cat').change(function(){

       // don't do anything if use selects "Select Cat"
       if($(this).val() !== "--Select--") {
           // subcat.php would return the list of option elements 
           // based on the category provided, if you have spaces in 
           // your values you will need to escape the values
           $.get('subcat.php?cat='+ $(this).val(),{cat:$(this).val()}, function(result){
               $('#subcat').html(result);
           });

       }

   });

   $('#cat1').change(function(){

       // don't do anything if use selects "Select Cat"
       if($(this).val() !== "--Select--") {
           // subcat.php would return the list of option elements 
           // based on the category provided, if you have spaces in 
           // your values you will need to escape the values
           $.get('roleaccountpriv.php?cat='+ $(this).val(),{cat1:$(this).val()}, function(result){
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
           $.get('relationaccountpriv1.php?cat='+ $(this).val(),{cat2:$(this).val()}, function(result){
               $('#subcat2').html(result);
           });

       }

   });

   $('#subcat2').change(function(){

       // don't do anything if use selects "Select Cat"
       var j = document.getElementById("cat2").value;
       if($(this).val() !== "--Select--") {
           // subcat.php would return the list of option elements 
           // based on the category provided, if you have spaces in 
           // your values you will need to escape the values
           $.get('relationaccountpriv2.php?cat='+ $(this).val(),{cat3:$(this).val(),cat2:j}, function(result){
               $('#subcat3').html(result);
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
