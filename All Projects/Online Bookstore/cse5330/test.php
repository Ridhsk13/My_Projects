<?php
session_start();
$_SESSION['mycart2'] = array();

$count = 1;
if (isset($_GET['test'])) {
  $_test = array();
  array_push($_test,$_GET['test']);
  $_SESSION['mycart2'] = array_merge($_SESSION['mycart2'],$_test);

  $count += 1;
  print_r($_SESSION['mycart2']);
}
//array_push($_SESSION['mycart'],"2");
//print_r($_SESSION['mycart']);

//array_push($_SESSION['mycart'],"3");
//print_r($_SESSION['mycart']);

//array_push($_SESSION['mycart'],"4");


?>

<html>
<body>
  <form action="test.php" method="get">
    <button name="test" id="test" value="1" type="submit">click</button>
  </form>
</body>
</html>
