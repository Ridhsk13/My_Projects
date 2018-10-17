<?php
  session_start();
  require_once('config.php');
 ?>
 <?php
 if (isset($_POST['delete'])) {
   $id_array = $_POST['myCheckBox'];
   //print_r($ssn_array);
   foreach ($ssn_array as $id) {
     $sql = "DELETE FROM customer WHERE customer_id=$id";
     if ($conn->query($sql) === TRUE) {
         //
         echo "<meta http-equiv='refresh' content='0'>";
     } else {
         //echo "Error: " . $sql . "<br>" . $conn->error;
     }
   }
 }
 if (isset($_GET['update_data'])){
    $customer_id = $_GET['customer_id'];
    //$customer_ssn = $_GET['customer_ssn'];
    //$user_name = $_GET['user_name'];
    $user_password = $_GET['user_password'];
    //$created_date = $_GET['created_date'];

    //echo $ssn,$fname;
    $sql = "UPDATE customer
            SET user_password='$user_password'
            WHERE customer_id=$customer_id";

            if ($conn->query($sql) === TRUE) {
                echo "<meta http-equiv='refresh' content='0'>";
            } else {
                echo "Error: " . $sql . "<br>" . $conn->error;
            }

 }

  ?>
<html>
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
  <link href="css/person.css" rel="stylesheet">

</head>
<body>
  <div id="menu">
    <?php include('adminmain.php') ?>
  </div>
  <div class="content">

  <h2>Add, modify or delete data</h2>

  <div class="line"></div>
  <form action="customerlist.php" method="post">
  <div class="inline-block">
    <button type="submit" name="delete" id="button_delete" class="btn float-right ml-3 mb-3 delete" disabled>Delete</button>
    <a href="customerlist.php" data-toggle="modal" data-target="#modifyRow">
      <button name="modify" id="button_modify" class="btn float-right ml-3 mb-3 modify" disabled>Modify</button>
    </a>
    <!--<button id="button_new" class="btn float-right ml-3 mb-3" enabled>Add New</button>-->
  </div>

  <div class="mt-2">
    <table class="table table-small mt-2">
      <thead>
        <tr class="table_text">
          <th scope="col" style="padding-bottom: 32px; padding-left: 24px;">
            <input id="checkbox_all" type="checkbox" class="form-check-input checkbox_all"/>
          </th>
          <th scope="col">#</th>
          <th scope="col">Id</th>
          <th scope="col">SSN</th>
          <th scope="col">Username</th>
          <th scope="col">Password</th>
          <th scope="col">Registration Date</th>
        </tr>
      </thead>
      <tbody>
        <?php

        $sql = "SELECT * FROM customer";
        $result = $conn->query($sql);
        $count = 1;
        if ($result->num_rows >0) {
          while ($row = $result->fetch_assoc()) {
            ?>
            <tr class="table_text" >
              <td style="padding-bottom: 32px; padding-left: 24px;">
                <input id="myCheckBox" name="myCheckBox[]" type="checkbox" value="<?php echo $row['customer_id'] ?>" class="form-check-input myCheckBox"/>
              </td>
              <th scope="row"><?php echo $count ?></th>
              <td>
                <?php echo $row['customer_id'] ?>
              </td>
              <td>
                <?php echo $row['customer_ssn'] ?>
              </td>
              <td>
                <?php echo $row['user_name'] ?>
              </td>
              <td>
                <?php echo $row['user_password'] ?>
              </td>
              <td>
                <?php echo $row['created_date'] ?>
              </td>
            </tr>
            <?php
            $count += 1;
          }
        }

        ?>
      </tbody>
    </table>
  </div>
  </form>

  <p id="demo">

  </p>
  <!-- Modal -->
  <div class="modal" id="modifyRow">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Edit data </h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <p class="statusMsg"></p>
          <form class="form" action="customerlist.php" method="post">
            <div class="container-fluid">
              <div class="row">
                <div class="col-4"><p> Customer Id:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="customer_id" id="customer_id" disabled/></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Customer SSN:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="customer_ssn" id="customer_ssn" disabled/></div>
              </div>
              <div class="row">
                <div class="col-4"><p> User Name:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="user_name" id="user_name" disabled/></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Password:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="user_password" id="user_password"/></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Registration Date:</p></div>
                <div class="col-8"><input class="pl-2" type="date" name="created_date" id="created_date" disabled/></div>
              </div>
            </div>

          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn delete" data-dismiss="modal" onclick="javascript:window.location.reload()">Close</button>
          <button type="button" id="update_data" class="btn modify" onclick="submitForm()">Update</button>
        </div>
      </div>
    </div>
  </div>
  <script>



    var boxes = $('.myCheckBox');
    boxes.on('change', function() {
      //document.getElementById("demo").innerHTML = boxes.filter(':checked').length;
      var length = boxes.filter(':checked').length;
      $('#button_delete').prop('disabled', !boxes.filter(':checked').length);
      if (length == 1) {
          $('#button_modify').prop('disabled', !boxes.filter(':checked').length);
      }else {
        $('#button_modify').prop('disabled', true);
      }

    }).trigger('change');

    var boxesall = $('.checkbox_all');
    boxesall.on('change', function() {
      //document.getElementById("demo").innerHTML = boxesall.filter(':checked').length;
      $('#button_delete').prop('disabled', !boxesall.filter(':checked').length);
      $('#button_modify').prop('disabled', !boxesall.filter(':checked').length);
    }).trigger('change');

    $(document).ready(function(){
   // Check or Uncheck All checkboxes
    $("#checkbox_all").change(function(){
     var checked = $(this).is(':checked');
     if(checked){
       $(".myCheckBox").each(function(){
         $(this).prop("checked",true);
         document.getElementById('button_delete').enabled = true;
         document.getElementById('button_modify').disabled = true;
       });
     }else{
       $(".myCheckBox").each(function(){
         $(this).prop("checked",false);
         document.getElementById('button_delete').disabled = true;
         document.getElementById('button_modify').disabled = true;
       });
     }
   });

  // Changing state of CheckAll checkbox
  $(".myCheckBox").click(function(){

    var $length = (".myCheckBox:checked").length;
    if (1 !== $length) {
      $('.modify').prop('enabled',true);
    } else {
      $('.modify').prop('disabled',true);
    }
    //document.getElementById("demo").innerHTML = counter;
    if($(".myCheckBox").length == $(".myCheckBox:checked").length) {
      $("#checkbox_all").prop("checked", true);
      document.getElementById('button_modify').disabled = true;
    } else {
      $("#checkbox_all").prop("checked", false);
    }

  });


});

  function submitForm(){
    var customer_id = $('#customer_id').val();
    //var customer_ssn = $('#customer_ssn').val();
    //var user_name = $('#user_name').val();
    var user_password = $('#user_password').val();
    //var created_date = $('#created_date').val();

    //document.getElementById("demo").innerHTML = ssn;
    $.ajax({
      type:'GET',
      url:'customerlist.php',
      data:'update_data=1&customer_id='+customer_id+'&user_password='+user_password,
      beforeSend: function(){
        $('#update_data').attr('disabled','disabled');
      },
      success:function(msg){
        //if (msg != 'ok') {
        //  $('.statusMsg').html(msg);
        //  $('#modifyRow.close').click();
        //} else {
          //$('.statusMsg').html('Some problem occured, please try again');
        //}
        //$('#update_data').removeAttr('disabled');
      }
    });
  }

  $("#modifyRow").on('show.bs.modal', function(e) {
    var customer_id = $(".myCheckBox:checked").val();
    //var fname = $(".fname").val();
    //$(e.currentTarge).find('input[name="form_ssn"]').val(ssn);

    //var ssn = $(e.relatedTarget).data('ssn');
    //document.getElementById("demo").innerHTML = ssn;

    document.getElementById("customer_id").value = customer_id;
  });

  /*$("#button_delete").click(function(){
    var checkedvalues = $('#myCheckBox:checked').map(function(){
      return this.value;
    }).get();
    document.getElementById("demo").innerHTML = checkedvalues;
  });*/


  </script>
  <!--<script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>-->

  <!-- Font Awesome JScript !-->
  <script defer src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>

  <!-- Bootstrap JScript !-->
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</body>

</html>
