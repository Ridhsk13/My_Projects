<?php
  session_start();
  require_once('config.php');
 ?>
 <?php
 if (isset($_GET['delete_order'])) {
   $id_array = $_GET['myCheckBox'];
   //print_r($ssn_array);
   foreach ($id_array as $item) {
     $sql = "DELETE FROM `order` WHERE order_id=$item";
     if ($conn->query($sql) === TRUE) {
         //
         header( "refresh:5;" );
     } else {
         echo "Error: " . $sql . "<br>" . $conn->error;
     }
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
  <form action="orders.php" method="get">
  <div class="inline-block">
    <button type="submit" name="delete_order" id="delete_order" class="btn float-right ml-3 mb-3 delete" disabled>Delete</button>
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
          <th scope="col">Date</th>
          <th scope="col">Quantity</th>
          <th scope="col">Tracking Number</th>
          <th scope="col">Shipping Address</th>

        </tr>
      </thead>
      <tbody>
        <?php

        $sql = "SELECT * FROM `order` ";
        $result = $conn->query($sql);
        $count = 1;
        if ($result->num_rows >0) {
          while ($row = $result->fetch_assoc()) {
            ?>
            <tr class="table_text" >
              <td style="padding-bottom: 32px; padding-left: 24px;">
                <input id="myCheckBox" name="myCheckBox[]" type="checkbox" value="<?php echo $row['order_id'] ?>" class="form-check-input myCheckBox"/>
              </td>
              <th scope="row"><?php echo $count ?></th>
              <td>
                <?php echo $row['order_id'] ?>
              </td>
              <td>
                <?php echo $row['order_date'] ?>
              </td>
              <td>
                <?php echo $row['quantity'] ?>
              </td>
              <td>
                <?php echo $row['tracking_no'] ?>
              </td>
              <td>
                <?php
                  $shipping_address = $row['ship_address1']."<br />".$row['ship_address2']."<br />".$row['ship_city']."<br />".$row['ship_state']."<br />".$row['ship_zip'];
                echo $shipping_address ?>
              </td>
              <td>
                <?php
                  //$sql = "SELECT item_id FROM track_order WHERE tracking_id='.$row['tracking_id'].'";
                  //$result =
                //echo $row['Sex'] ?>
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
  <script>



    var boxes = $('.myCheckBox');
    boxes.on('change', function() {
      //document.getElementById("demo").innerHTML = boxes.filter(':checked').length;
      var length = boxes.filter(':checked').length;
      $('#delete_order').prop('disabled', !boxes.filter(':checked').length);
      if (length == 1) {
          $('#button_modify').prop('disabled', !boxes.filter(':checked').length);
      }else {
        $('#button_modify').prop('disabled', true);
      }

    }).trigger('change');

    var boxesall = $('.checkbox_all');
    boxesall.on('change', function() {
      //document.getElementById("demo").innerHTML = boxesall.filter(':checked').length;
      $('#delete_order').prop('disabled', !boxesall.filter(':checked').length);
      $('#button_modify').prop('disabled', !boxesall.filter(':checked').length);
    }).trigger('change');

    $(document).ready(function(){
   // Check or Uncheck All checkboxes
    $("#checkbox_all").change(function(){
     var checked = $(this).is(':checked');
     if(checked){
       $(".myCheckBox").each(function(){
         $(this).prop("checked",true);
         document.getElementById('delete_order').enabled = true;
         document.getElementById('button_modify').disabled = true;
       });
     }else{
       $(".myCheckBox").each(function(){
         $(this).prop("checked",false);
         document.getElementById('delete_order').disabled = true;
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
    var ssn = $('#ssn').val();
    var fname = $('#fname').val();
    var lname = $('#lname').val();
    var email = $('#email').val();
    var sex = $('#sex').val();
    var dob = $('#dob').val();
    var address = $('#address').val();
    var ph_no = $('#ph_no').val();

    //document.getElementById("demo").innerHTML = ssn;
    $.ajax({
      type:'GET',
      url:'orders.php',
      data:'update_data=1&ssn='+ssn+'&fname='+fname+'&lname='+lname+'&email='+email+'&sex='+sex+'&dob='+dob+'&address='+address+'&ph_no='+ph_no,
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
    var ssn = $(".myCheckBox:checked").val();
    //var fname = $(".fname").val();
    //$(e.currentTarge).find('input[name="form_ssn"]').val(ssn);

    //var ssn = $(e.relatedTarget).data('ssn');
    //document.getElementById("demo").innerHTML = ssn;

    document.getElementById("ssn").value = ssn;
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
