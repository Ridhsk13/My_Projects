<?php
  session_start();
  require_once('config.php');
 ?>
 <?php
 if (isset($_POST['delete'])) {
   $id_array = $_POST['myCheckBox'];
   //print_r($ssn_array);
   foreach ($ssn_array as $id) {
     $sql = "DELETE FROM items WHERE item_id=$id";
     if ($conn->query($sql) === TRUE) {
         //
         echo "<meta http-equiv='refresh' content='0'>";
     } else {
         //echo "Error: " . $sql . "<br>" . $conn->error;
     }
   }
 }
 if (isset($_GET['update_data'])){
    $item_id = $_GET['item_id'];
    $item_name = $_GET['item_name'];
    $subject_type = $_GET['subject_type'];
    $description = $_GET['description'];
    $category = $_GET['category'];
    $price = $_GET['price'];
    $quantity = $_GET['quantity'];


    //echo $ssn,$fname;
    $sql = "UPDATE items
            SET item_id='$item_id',item_name='$item_name',subject_type='$subject_type',description='$description',category='$category',price=$price,quantity='$quantity'
            WHERE item_id=$item_id";

            if ($conn->query($sql) === TRUE) {
                echo "<meta http-equiv='refresh' content='0'>";
            } else {
                echo "Error: " . $sql . "<br>" . $conn->error;
            }

 }
 if (isset($_GET['add_new_data'])){
    //$item_id = $_GET['item_id'];
    $item_name = $_GET['item_name'];
    $subject_type = $_GET['subject_type'];
    $description = $_GET['description'];
    $category = $_GET['category'];
    $price = $_GET['price'];
    $quantity = $_GET['quantity'];

    $sql2 = "SELECT * FROM items ORDER BY item_id DESC LIMIT 1";
    $result = $conn->query($sql2);
    $row = $result->fetch_assoc();
    $last_id = $row['item_id'];
    $item_id = $last_id + 1;
    //echo $item_id;
    //echo $ssn,$fname;
    $sql = "INSERT INTO items VALUES ($item_id,'$item_name','$subject_type','$description','','$category',$price,$quantity)";
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

  <h2>Something will write here</h2>

  <div class="line"></div>
  <form action="items.php" method="get">
  <div class="inline-block">
    <button type="submit" name="delete" id="button_delete" class="btn float-right ml-3 mb-3 delete" disabled>Delete</button>
    <a href="items.php" data-toggle="modal" data-target="#modifyRow">
      <button name="modify" id="button_modify" class="btn float-right ml-3 mb-3 modify" disabled>Modify</button>
    </a>
    <a href="items.php" data-toggle="modal" data-target="#addRow">
      <button name="add_new" id="add_new" class="btn float-right ml-3 mb-3 btn-success">Add New</button>
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
          <th scope="col">Id</th>
          <th scope="col">Item Name</th>
          <th scope="col">Subject Type</th>
          <th scope="col">Description</th>
          <th scope="col">category</th>
          <th scope="col">price</th>
          <th scope="col">quantity</th>
        </tr>
      </thead>
      <tbody>
        <?php

        $sql = "SELECT * FROM items";
        $result = $conn->query($sql);
        $count = 1;
        if ($result->num_rows >0) {
          while ($row = $result->fetch_assoc()) {
            ?>
            <tr class="table_text" >
              <td style="padding-bottom: 32px; padding-left: 24px;">
                <input id="myCheckBox" name="myCheckBox[]" type="checkbox" value="<?php echo $row['item_id'] ?>" class="form-check-input myCheckBox"/>
              </td>

              <td>
                <?php echo $row['item_id'] ?>
              </td>
              <td>
                <?php echo $row['item_name'] ?>
              </td>
              <td>
                <?php echo $row['subject_type'] ?>
              </td>
              <td>
                <?php echo $row['description'] ?>
              </td>
              <td>
                <?php echo $row['category'] ?>
              </td>
              <td>
                <?php echo $row['price'] ?>
              </td>
              <td>
                <?php echo $row['quantity'] ?>
              </td>
            </tr>
            <?php
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
          <form class="form" action="items.php" method="get">
            <div class="container-fluid">
              <div class="row">
                <div class="col-4"><p> Id:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="item_id" id="item_id" disabled/></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Item Name:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="item_name" id="item_name" /></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Subject Type:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="subject_type" id="subject_type" /></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Description:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="description" id="description"/></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Category:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="category" id="category" /></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Price:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="price" id="price" /></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Quantity:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="quantity" id="quantity" /></div>
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






  <!-- Modal -->
  <div class="modal" id="addRow">
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
          <form class="form" action="items.php" method="get">
            <div class="container-fluid">
              <div class="row">
                <div class="col-4"><p> Id:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="new_item_id" id="new_item_id" disabled/></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Item Name:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="new_item_name" id="new_item_name" /></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Subject Type:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="new_subject_type" id="new_subject_type" /></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Description:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="new_description" id="new_description"/></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Category:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="new_category" id="new_category" /></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Price:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="new_price" id="new_price" /></div>
              </div>
              <div class="row">
                <div class="col-4"><p> Quantity:</p></div>
                <div class="col-8"><input class="pl-2" type="text" name="new_quantity" id="new_quantity" /></div>
              </div>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn delete" data-dismiss="modal" onclick="javascript:window.location.reload()">Close</button>
          <button type="button" id="add_new" class="btn modify" onclick="submitNew()">Add Item</button>
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
    var item_id = $('#item_id').val();
    var item_name = $('#item_name').val();
    var subject_type = $('#subject_type').val();
    var description = $('#description').val();
    var category = $('#category').val();
    var price = $('#price').val();
    var quantity = $('#quantity').val();

    //document.getElementById("demo").innerHTML = ssn;
    $.ajax({
      type:'GET',
      url:'items.php',
      data:'update_data=1&item_id='+item_id+'&item_name='+item_name+'&subject_type='+subject_type+'&description='+description+'&category='+category+'&price='+price+'&quantity='+quantity,
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
    var item_id = $(".myCheckBox:checked").val();
    //var fname = $(".fname").val();
    //$(e.currentTarge).find('input[name="form_ssn"]').val(ssn);

    //var ssn = $(e.relatedTarget).data('ssn');
    //document.getElementById("demo").innerHTML = ssn;

    document.getElementById("item_id").value = item_id;
  });

    function submitNew(){
      //var item_id = $('#new_item_id').val();
      var item_name = $('#new_item_name').val();
      var subject_type = $('#new_subject_type').val();
      var description = $('#new_description').val();
      var category = $('#new_category').val();
      var price = $('#new_price').val();
      var quantity = $('#new_quantity').val();

      //document.getElementById("demo").innerHTML = item_name;
      $.ajax({
        type:'GET',
        url:'items.php',
        data:'add_new_data=1&item_name='+item_name+'&subject_type='+subject_type+'&description='+description+'&category='+category+'&price='+price+'&quantity='+quantity,
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

    $("#addRow").on('show.bs.modal', function(e) {
      var item_id = $(".myCheckBox:checked").val();
      //var fname = $(".fname").val();
      //$(e.currentTarge).find('input[name="form_ssn"]').val(ssn);

      //var ssn = $(e.relatedTarget).data('ssn');
      //document.getElementById("demo").innerHTML = ssn;

      document.getElementById("new_item_id").value = item_id;
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
