<?php
  if(!isset($_SESSION))
  {
      session_start();
  }
  require_once('config.php');
  //$_SESSION['cart_items'] = array();
  //if (!isset($_SESSION['total'])) {
    $_SESSION['total'] = 0;
  //}
 ?>
 <?php
 if (isset($_POST['remove_item'])) {
   //echo "Remove item is set <br />";
   if (!empty($_SESSION['cart_item'])) {
     //echo "Session variable is not empty <br />";
     $id_array = $_POST['myCheckBox'];
     foreach ($id_array as $item_id) {
       //echo $item_id;
       foreach ($_SESSION['cart_item'] as $key => $value) {
         foreach ($value as $id => $data) {
           if ($item_id == $id) {
             unset($_SESSION['cart_item'][$key][$id]);
           }
         }
       }
     }
     $_SESSION['total'] = 0;
     echo "<meta http-equiv='refresh' content='0'>";

     //array_push($_SESSION['cart_items'],$id_array);
//   } else {
//      $id_array = $_GET['myCheckBox'];
//      $_SESSION['cart_items'] = $id_array;
    }
  }
  //print_r($_SESSION['cart_item']);
  if (isset($_POST['empty_cart'])) {
   unset($_SESSION['cart_item']);
   $_SESSION['total'] = 0;
   echo "<meta http-equiv='refresh' content='0'>";

  }

  if (isset($_POST['buy_more'])){
    sleep(2);
    header("Location:customermain.php");
  }
  if (isset($_POST['checkout'])) {
    sleep(2);
    header("Location:checkout.php");
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
  <form action="viewcart.php" method="post">
  <div class="inline-block">
    <button type="submit" name="remove_item" id="remove_item" class="btn float-right ml-3 mb-3 delete">Remove Item</button>
    <button type="submit" name="checkout" id="checkout" class="btn float-right ml-3 mb-3 modify">Checkout</button>
    <button type="submit" name="empty_cart" id="empty_cart" class="btn float-right ml-3 mb-3 delete">Empty cart</button>
    <button type="submit" name="buy_more" id="buy_more" class="btn float-right ml-3 mb-3 btn-success">Buy more</button>
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
          <th scope="col">Price</th>
        </tr>
      </thead>
      <tbody>
        <?php
        if (isset($_SESSION['cart_item']) && !empty($_SESSION['cart_item']))
        {
          foreach ($_SESSION['cart_item'] as $key => $value) {
            foreach ($value as $cart_id => $data) {
              $id = $cart_id;
              $name = $data['name'];
              $price = $data['price'];
              //echo $id." = ".$name." = ".$price."<br />";
              $_SESSION['total'] = $_SESSION['total'] + $price;

          ?>

            <tr class="table_text" >
              <td style="padding-bottom: 32px; padding-left: 24px;">
                <input id="myCheckBox" name="myCheckBox[]" type="checkbox" value="<?php echo $id ?>" class="form-check-input myCheckBox"/>
              </td>
              <!--<th scope="row"><?php //echo $count ?></th>-->
              <td>
                <?php echo $id ?>
              </td>
              <td>
                <?php echo $name ?>
              </td>
              <td>
                $ <?php echo $price ?>
              </td>
            </tr>
            <?php
          }
        }

      }
        ?>
      </tbody>
      <tfoot>
        <tr>
          <td></td>
          <td></td>
          <td>Total:</td>
          <td>$ <?php echo $_SESSION['total']?></td>
        </tr>
      </tfoot>
    </table>
  </div>
  </form>

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
