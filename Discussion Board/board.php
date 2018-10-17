<?php
  session_start();
 ?>
<html>
<head><title>Message Board</title></head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
<body style="padding-bottom:90px;padding-top:90px;">
  <nav class="navbar bg-dark fixed-top">
    <h5 class="display-4 text-light">Board of the Messages</h5>
    <ul class="navbar-nav ml-auto">
      <li class="nav-item">
        <form action="board.php" method="get">
          <button class="btn btn-primary" type="submit" name="logout">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Logout &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </button>
        </form>
      </li>
    </ul>
  </nav>
<?php
error_reporting(E_ALL);
ini_set('display_errors','On');
if (isset($_GET['postMessage'])) {
  if(!empty($_GET['message'])){
    try {
      $dbh = new PDO("mysql:host=127.0.0.1:3306;dbname=board","root","ridhs7474",array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
      $dbh->beginTransaction();
      $id = uniqid();
      $postedBy = $_SESSION['username'];
      $msg = $_GET['message'];
      $sql = 'insert into posts (id,postedby,message) values("' . $id . '","' . $postedBy . '","' . $msg .'")';
      $dbh->exec($sql)or die(print_r($dbh->errorInfo(), true));
      $dbh->commit();
      header("Refresh:0; url=board.php");
    } catch (PDOException $e) {
      print "Error!: " . $e->getMessage() . "<br/>";
      die();
    }
  }else{
    ?>
    <div class="alert alert-danger alert-dismissable">
      <button type="button" class="close" data-dismiss="alert">&times;</button>
      <strong>Error!</strong> Do write the Message First.
    </div>
    <?php
  }

}
if (isset($_GET['replyMessage'])) {
  if(!empty($_GET['message'])){
    try {
      $dbh = new PDO("mysql:host=127.0.0.1:3306;dbname=board","root","ridhs7474",array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
      $dbh->beginTransaction();
      $id = uniqid();
      $replyid = $_GET['replyMessage'];
      $postedBy = $_SESSION['username'];
      $msg = $_GET['message'];
      $sql = 'insert into posts (id,replyto,postedby,message) values("' . $id . '","' . $replyid . '","' . $postedBy . '","' . $msg .'")';
      $dbh->exec($sql)or die(print_r($dbh->errorInfo(), true));
      $dbh->commit();
      header("Refresh:0; url=board.php");
    } catch (PDOException $e) {
      print "Error!: " . $e->getMessage() . "<br/>";
      die();
    }
  }else {
    ?>
    <div class="alert alert-danger alert-dismissable">
      <button type="button" class="close" data-dismiss="alert">&times;</button>
      <strong>Error!</strong> Do write the Message First.
    </div>
    <?php
  }
}
if (isset($_GET['logout'])) {
  sleep(2);
  unset($_SESSION['loginSuccess']);
  unset($_SESSION['username']);
  header("Location:login.php");
  exit;
}
  ?>

  <form action="board.php" method="get">
  <?php
    try {
      $dbh = new PDO("mysql:host=127.0.0.1:3306;dbname=board","root","ridhs7474",array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
      $dbh->beginTransaction();
      $stmt = $dbh->prepare("select * from posts order by datetime DESC");
      $stmt->execute();
      while ($row = $stmt->fetch()) {
        if (empty($row['replyto'])) {
          $stmt = $dbh->prepare('select fullname from users where username="'.$_row['postedby'].'"');
          $stmt->execute();
          $name = $stmt->fetch;
          ?>
          <div class="container-fluid">
            <div class="col-lg-9">
              <br />
              <div class="card">
                  <div class="card-header">
                    <p class="card-title font-weight-bold">
                      From: <?php echo $_SESSION['fullname'] ?> (<?php echo $row['postedby'].":". $row['id']; ?>)
                    </p>
                    <p class="font-italic small"><?php echo $row['datetime']; ?></p>
                  </div>
                  <div class="card-body">
                    <p class="font-italic text-justify">
                      "<?php echo $row['message'] ?>"
                    </p>
                  <v>
                  <div class="card-footer">
                    <button class="btn btn-success btn-block" name="replyMessage" type="submit" value="<?php echo $row['id'] ?>"> Reply this Message</button>
                  </div>
              </div>
              <br />
            </div>
          </div>
          <?php
        }else{
          ?>

          <div class="container-fluid">
            <div class="col-lg-9 ml-auto">
              <br />
              <div class="card">
                  <div class="card-header">
                    <p class="card-title font-weight-bold">
                      Reply to the Message: <?php echo $row['replyto'] ?>
                    </p>
                    <p class="card-title font-weight-bold">
                      From: <?php echo $row['postedby']; ?> (ID:<?php echo $row['id']; ?>)
                    </p>
                    <p class="font-italic small"><?php echo $row['datetime']; ?></p>
                  </div>
                  <div class="card-body">
                    <p class="font-italic text-justify">
                      "<?php echo $row['message'] ?>"
                    </p>
                  </div>
                  <div class="card-footer">
                    <button class="btn btn-success btn-block" name="replyMessage" type="submit" value="<?php echo $row['id'] ?>"> Reply this Message</button>
                  </div>
              </div>
              <br />
            </div>
          </div>
          <?php
        }
      }
    } catch (PDOException $e) {
      print "Error!: " . $e->getMessage() . "<br/>";
      die();
    }
  ?>

  <nav class="navbar bg-dark fixed-bottom">
    <div class="container-fluid">
      <h6 class="text-white">&nbsp;&nbsp;&nbsp;&nbsp;Note: <small>To reply a message, write your message here and click the reply this message button of the respective message.</small></h6>
      <div class="col-lg-9">
        <textarea class="form-control" placeholder="Enter Your Message Here... " name="message"></textarea>
      </div>
      <div class="col-lg-3">
        <button class="btn btn-primary btn-block align-bottom" type="submit" name="postMessage">
          New Post
        </button>
      </div>

    </div>
  </nav>


  </form>
</body>
</html>
