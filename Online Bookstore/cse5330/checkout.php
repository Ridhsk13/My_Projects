<?php
  if(!isset($_SESSION))
  {
      session_start();
  }
  require_once('config.php');

  if (isset($_GET['cancel'])) {
    sleep(2);
    header("Location:viewcart.php");
  }
  if (isset($_GET['pay'])) {
    if (!empty($_GET['address1']) && !empty($_GET['address2']) && !empty($_GET['city']) &&
        !empty($_GET['state']) && !empty($_GET['zip']) && !empty($_GET['card_type']) &&
        !empty($_GET['card_number']) && !empty($_GET['exp_date']) && !empty($_GET['cvv'])) {

        $payment_id = abs( crc32( uniqid() ) );
        $card_type = $_GET['card_type'];
        $card_number = $_GET['card_number'];
        $exp_date = $_GET['exp_date'];
        $total_price = $_SESSION['total'];

        $order_id = rand();
        //echo $order_id;
        //$order_date = new datetime();
        //print_r($order_date);
        $quantity = 1;
        $ship_address1 = $_GET['address1'];
        $ship_address2 = $_GET['address2'];
        $ship_city = $_GET['city'];
        $ship_state = $_GET['state'];
        $ship_zip = $_GET['zip'];
        $tracking_no = uniqid();

        $sql = "SELECT customer_id FROM customer WHERE user_name='".$_SESSION['username']."'";
        $result = $conn->query($sql);
        $row = $result->fetch_assoc();
        $customer_id = $row['customer_id'];

        $sql2 = "INSERT INTO payment VALUES ($payment_id,now(),'$card_type','$card_number','$exp_date',$total_price)";
        if ($conn->query($sql2) === TRUE) {
          //echo "record added";
          $sql3 =  "INSERT INTO `order` VALUES ($order_id,now(),$quantity,'$tracking_no',$customer_id,$payment_id,'$ship_address1','$ship_address2','$ship_city','$ship_state','$ship_zip')";
          if ($conn->query($sql3) === TRUE) {
            //echo "record added";
            foreach ($_SESSION['cart_item'] as $key => $value) {
              foreach ($value as $id => $data) {
                $item_id = $id;
                $sql4 = "INSERT INTO `track_order` VALUES ('$tracking_no',$order_id,$item_id)";
                if ($conn->query($sql4) === TRUE) {
                  //echo "record added";
                  sleep(2);
                  header("Location:myorder.php");
                } else {
                  echo "Error: ".$sql4."<br>". $conn->error;
                }
              }
            }
          } else {
            echo "Error: " . $sql3 . "<br>" . $conn->error;
          }
        } else {
          echo "Error: " . $sql2 . "<br>" . $conn->error;
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
    <?php include('customer_menu.php') ?>
  </div>
  <div class="content">

  <h2>Enjoy Shopping!</h2>

  <div class="line"></div>
  <form action="checkout.php" method="get">
  <div class="container">
    <div class="mt-2">
      <h3>Cart Items</h3>
      <table class="table table-small mt-2">
        <thead>
          <tr class="table_text">
            <!--<th scope="col" style="padding-bottom: 32px; padding-left: 24px;">
              <input id="checkbox_all" type="checkbox" class="form-check-input checkbox_all"/>
            </th>-->
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
                //$_SESSION['total'] = $_SESSION['total'] + $price;

            ?>

              <tr class="table_text" >
                <!--<td style="padding-bottom: 32px; padding-left: 24px;">
                  <input id="myCheckBox" name="myCheckBox[]" type="checkbox" value="<?php echo $id ?>" class="form-check-input myCheckBox"/>
                </td>-->
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
            <td>Total:</td>
            <td>$ <?php echo $_SESSION['total']?></td>
          </tr>
        </tfoot>
      </table>
    </div>
  </div>
  <div class="container">
      <div class="row mt-2">
        <div class="col-6">
          <h3 class="mb-3">Shipping Information</h3>
            <div class="inline-block">
              Address 1: <input class="form-control" type="text" name="address1" id="address1" />
            </div>
            <div class="inline-block">
              Address 2: <input class="form-control" type="text" name="address2" id="address2" />
            </div>
            <div class="inline-block">
              City: <input class="form-control" type="text" name="city" id="city" />
            </div>
            <div class="inline-block">
              State: <input class="form-control" type="text" name="state" id="state" />
            </div>
            <div class="inline-block">
              Zip code: <input class="form-control" type="text" name="zip" id="zip" />
            </div>
        </div>
        <div class="col-6">
          <h3 class="mb-3">Payment Information</h3>
          <div class="inline-block">
            <h5>Total Amount: $ <?php echo $_SESSION['total']?></h5>
          </div>
          <div class="inline-block">
            Card Type: <input class="form-control" type="text" name="card_type" id="card_type" />
          </div>
          <div class="inline-block">
            Card Number: <input class="form-control" type="text" name="card_number" id="card_number" />
          </div>
          <div class="inline-block">
            Expiration Date: <input class="form-control" type="date" name="exp_date" id="exp_date" />
          </div>
          <div class="inline-block">
            CVV: <input class="form-control" type="text" name="cvv" id="cvv" />
          </div>
        </div>
      </div>
  </div>
  <div class="container">
    <div class="mt-2">
      <div class="inline-block my-5 mx-3">
        <center>
          <button type="submit" name="cancel" id="cancel" class="btn ml-3 mb-3 delete">Cancel Payment</button>
          <button type="submit" name="pay" id="pay" class="btn ml-3 mb-3 modify">Make Payment</button>
        </center>
      </div>
    </div>
  </div>

  </form>
  <!--<script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>-->

  <!-- Font Awesome JScript !-->
  <script defer src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>

  <!-- Bootstrap JScript !-->
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</body>

</html>
