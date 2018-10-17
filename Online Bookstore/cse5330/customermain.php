<?php
  if(!isset($_SESSION))
  {
      session_start();
  }
  require_once('config.php');
  
  //$_SESSION['user'] = $_GET['username'];
  //print_r($_SESSION);
 ?>
 <?php

    if (!isset($_SESSION["cart_item"])) {
      $_SESSION['cart_item'] = array();
    }

    if (isset($_GET['add_to_cart'])) {

      $id_array = $_GET['myCheckBox'];
      foreach ($id_array as $id) {
        $result = $conn->query("SELECT * FROM items WHERE item_id=".$id."");
        $product = $result->fetch_assoc();
        $itemArray = array($product['item_id'] => array('name'=>$product['item_name'], 'price'=>$product['price']) );

        //if (!empty($_SESSION['cart_item'])){
          array_push($_SESSION['cart_item'],$itemArray);
        //}else {
          //$_SESSION['cart_item'] = $itemArray;
        //}
    }

   print_r($_SESSION['cart_item']);
   //$id = abs( crc32( uniqid() ) );
   //$item_id = $product['item_id'];
   //$name = $product['item_name'];
   //$price = $product['price'];
   //$user = $_GET['username'];
   //echo $_SESSION["username"];
  // $sql = "INSERT INTO shopping_cart values ($id,$item_id,'$name',$price,'$user')";
   //if ($conn->query($sql) === TRUE) {
  //     echo "New record created successfully";
   //} else {
  //     echo "Error: " . $sql . "<br>" . $conn->error;
   //}

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
    <?php include('customer_menu.php') ?>
  </div>
  <div class="content">

  <h2>Enjoy Shopping!</h2>

  <div class="line"></div>
  <form action="customermain.php" method="get">
  <div class="inline-block">
    <!--<button type="submit" name="delete" id="button_delete" class="btn float-right ml-3 mb-3 delete">Delete</button>-->
    <button type="submit" name="add_to_cart" id="add_to_cart" class="btn float-right ml-3 mb-3 modify">Add to cart</button>
    <!--<button id="button_new" class="btn float-right ml-3 mb-3" enabled>Add New</button>-->
  </div>

  <div class="mt-2">
    <table class="table table-small mt-2">
      <thead>
        <tr class="table_text">
          <th scope="col" style="padding-bottom: 32px; padding-left: 24px;">
            <input id="checkbox_all" type="checkbox" class="form-check-input checkbox_all"/>
          </th>
          <!--<th scope="col">#</th>-->
          <th scope="col">Id</th>
          <th scope="col">Item Name</th>
          <th scope="col">subject_type</th>
          <th scope="col">Description</th>
          <th scope="col">Category</th>
          <th scope="col">Price</th>
          <th scope="col">Quantity</th>

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
              <!--<th scope="row"><?php //echo $count ?></th>-->
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
                $ <?php echo $row['price'] ?>
              </td>
              <td>
                <?php echo $row['quantity'] ?>
              </td>
              <!--<td>
                <button type="submit" name="add_to_cart"
                        id="add_to_cart" class="btn ml-1 mb-1 modify"
                        value="add_to_cart">Add to cart</button>
              </td>-->
            </tr>
            <?php
            $count = $count+1;

          }
        }
//print_r($_SESSION['cart_item']);
        ?>
      </tbody>
    </table>
  </div>
  </form>

  <p id="demo">

  </p>
  <script>

    $(document).ready(function(){
   // Check or Uncheck All checkboxes
    $("#checkbox_all").change(function(){
     var checked = $(this).is(':checked');
     if(checked){
       $(".myCheckBox").each(function(){
         $(this).prop("checked",true);
       });
     }else{
       $(".myCheckBox").each(function(){
         $(this).prop("checked",false);
       });
     }
   });

  // Changing state of CheckAll checkbox
  $(".myCheckBox").click(function(){

    if($(".myCheckBox").length == $(".myCheckBox:checked").length) {
      $("#checkbox_all").prop("checked", true);
    } else {
      $("#checkbox_all").prop("checked", false);
    }

  });


});

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
