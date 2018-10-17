<?php
  session_start();
  error_reporting(E_ALL);
  ini_set('display_errors','On');

  if (!isset($_SESSION["cart_item"])) {
    $_SESSION["cart_item"] = [];
  }

  if (isset($_GET['buy'])) {
    $myproductId = $_GET['buy'];
    foreach ($_SESSION["search_item"] as $item) {
      if ($item["id"] == (String)$myproductId) {
        array_push($_SESSION["cart_item"],$item);
        //$_SESSION["total"] += $item["price"];
      }
    }
  }

  if (isset($_GET['remove'])) {
    $myproductId = $_GET['remove'];
    if(!empty($_SESSION["cart_item"])) {
      //$count = 0;
      foreach($_SESSION["cart_item"] as $k => $v) {
					if($v["id"] == $myproductId){
            $_SESSION["total"] -= $v["price"];
            unset($_SESSION["cart_item"][$k]);

          }

					if(empty($_SESSION["cart_item"]))
						unset($_SESSION["cart_item"]);
			}
		}
  }

  if (isset($_GET['clear'])) {
    if ($_GET['clear'] == 1) {
      unset($_SESSION["cart_item"]);
      $_SESSION["total"] = 0;
    }
  }
 ?>

<html>
<head><title>Buy Products</title></head>
<body>
  <p>
    Shopping Basket
  </p>
  <?php
    if (isset($_SESSION["cart_item"])) {
      $_SESSION["total"] = 0;
    }
  ?>
  <table id="cartItems" border="1">
      <?php
        $count = 0;
        if (!empty($_SESSION["cart_item"])) {
        foreach ($_SESSION["cart_item"] as $item) {
          $count += 1;
          ?>
            <tr>
              <td padding=10px><b><?php echo $count?></b></td>
              <td><image src="<?php echo $item['image']?>"></image></td>
              <td><?php echo $item['name']?></td>
              <td><b><?php echo $item['price']?></b></td>
              <td><a href="<?php echo "buy.php?remove=".$item['id'] ?>">Remove</a></b></td>
            </tr>
          <?php
          $_SESSION["total"] += $item['price'];
        }
      }
      ?>
  </table>
  <p>
    Total: $ <?php echo $_SESSION["total"]?>
  </p>
  <form action="buy.php" method="get">
    <input type="hidden" name="clear" value="1" />
    <input type="submit" value="Empty Basket"/>
  </form>

  <form action="buy.php" method="get">
    <fieldset>
      <legend>Find Products:</legend>
      <label>Category: </label>
      <select name="category">
      <?php
        error_reporting(E_ALL);
        ini_set('display_errors','On');
          $xmlstr = file_get_contents('http://sandbox.api.ebaycommercenetwork.com/publisher/3.0/rest/CategoryTree?apiKey=78b0db8a-0ee1-4939-a2f9-d3cd95ec0fcc&visitorUserAgent&visitorIPAddress&trackingId=7000610&categoryId=72&showAllDescendants=true');
        $xml = new SimpleXMLElement($xmlstr);
        //$categoriesTitles = $xml->category->categories->category[0]->name;
        //$categoriesTitles = [];
        ?>
        <option value="<?php echo $xml->category[0]->attributes()->id ?>"><?php echo $xml->category[0]->name ?></option>
        <?php
        foreach ($xml->category->categories->category as $c) {
          # code...
          $categoryTitles = $c->name;
          ?>
              <optgroup label="<?php echo $categoryTitles ?> :">
                <?php
                  foreach ($c->categories->category as $subc) {
                    $subCategoriesName = $subc->name;
                    $subCateforiesId = $subc->attributes()->id;
                    ?>
                    <option value="<?php echo $subCateforiesId ?>"><?php echo $subCategoriesName ?></option>
                    <?php
                    //print $subCategoriesName;
                  }
                ?>
              </optgroup>
          <?php
        }
        //print_r($categoriesTitles);
       ?>
      </select>
      <label>Search Keywords</label>
      <input type="text" name="search" />
      <input type="submit" value="search"/>
    </fieldset>
  </form>

<?php
if (isset($_GET["search"])) {
  //echo $_GET["search"];
  $searchkeyword = $_GET["search"];
  $category = $_GET["category"];
  $url = "http://sandbox.api.ebaycommercenetwork.com/publisher/3.0/rest/GeneralSearch?apiKey=78b0db8a-0ee1-4939-a2f9-d3cd95ec0fcc&trackingId=7000610&visitorUserAgent&visitorIPAddress&keywordcategoryId=".$category."&keyword=".$searchkeyword;
  $xmlstr = file_get_contents($url);
  $xml = new SimpleXMLElement($xmlstr);
  ?>
    <table border=1 >
      <tr>
  <?php
  $_SESSION["search_item"] = [];
  foreach ($xml->categories->category[0]->items[0]->product as $product) {
    $productId = $product->attributes()->id;
    $productName = $product->name;
    $productDesc = $product->fullDescription;
    $productImage = $product->images->image[0]->sourceURL;
    $productPrice = $product->maxPrice;
    //echo $productName."\n".$productDesc."\n".$productImage;
    $product = array('id' => (string)$productId, 'name' => (string)$productName , 'desc' => (string)$productDesc , 'image' => (string)$productImage, 'price' => (string)$productPrice);
    //if (!isset($_SESSION["search_item"])) {
      array_push($_SESSION["search_item"],$product);
    //}
    //else {
    //  $_SESSION["search_item"] = $product;
    //}
    //print_r($_SESSION["search_item"])
    ?>

          <td>
            <table>
            <tr>
              <td align="center">
                <a href="<?php echo "buy.php?buy=".$productId ?>"><image src="<?php echo $productImage ?>"/></a>
              </td>
            </tr>
            <tr>
              <td align="center">
                <b><?php echo $productName?></b>
              </td>
            </tr>
            <tr>
              <td align="center">
                <b><?php echo "$ ".$productPrice ?></b>
              </td>
            </tr>
            <tr>
              <td align="center">
                <?php echo $productDesc ?>
              </td>
            </tr>
            <!--<tr>
              <td align="center">
                  <input type="button" value="Add to Cart" name="buy" />
              </td>
            </tr>-->
            </table>
          </td>
    <?php
  }
  ?>
        </tr>
  </table>
  <?php
}
?>
</body>
</html>
