<?php
  session_start();
  if (!isset($_SESSION['loginSuccess'])) {
    $_SESSION['loginSuccess'] = false;
  }
 ?>

<head>
  <title>Login</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
</head>
<body class="bg-light text-dark">
  <br />
  <center>
    <h1 class="display-3">Welcome to the Message Board</h1>
  </center>
  <br />
  <div class="container-fluid">
    <div class="row justify-content-lg-center">
      <div class="col-lg-4">
        <div class="card bg-dark text-white rounded">
          <div class="card-body">
            <h1 class="card-title">Login</h1>
            <div class="card-text">
              <form action="login.php" method="get">
                <div class="form-group">
                  <label for="username">User Name :</label>
                  <input type="text" name="username" class="form-control" id="username"/>
                </div>
                <div class="form-group">
                  <label for="password">password :</label>
                  <input type="password" class="form-control" name="password" id="password"/>
                </div>
                <button type="submit" class="btn btn-primary" name="login">Login</button>
              </form>
            </div>
          </div>
        </div>
        <?php
          if (isset($_GET['login'])) {
            if (empty($_GET['username'])) {
              ?>
              <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Error!</strong> Username cannot be empty.
              </div>
              <?php
            } elseif (empty($_GET['password'])) {
              ?>
              <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Error!</strong> Password cannot be empty.
              </div>
              <?php
            } else {
              # code...
              try {
                $dbh = new PDO("mysql:host=127.0.0.1:3306;dbname=board","root","ridhs7474",array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
                $dbh->beginTransaction();
                $stmt = $dbh->prepare('select password from users where username="'.$_GET['username'].'"');
                $stmt->execute();
                $row = $stmt->fetch();
                  if ($row['password'] == md5($_GET['password'])) {
                    ?>
                    <div class="alert alert-success alert-dismissable">
                      <button type="button" class="close" data-dismiss="alert">&times;</button>
                      <strong>Login Successful!</strong> You have successfully login.
                    </div>
                    <?php
                    $_SESSION['loginSuccess'] = true;
                    $_SESSION['username'] = $_GET['username'];
                  }else {
                    ?>
                    <div class="alert alert-danger alert-dismissable">
                      <button type="button" class="close" data-dismiss="alert">&times;</button>
                      <strong>Login unsuccessful!</strong> Password dosen't match. Try again.
                    </div>
                    <?php
                    $_SESSION['loginSuccess'] = false;
                  }
              } catch (PDOException $e) {
                print "Error!: " . $e->getMessage() . "<br/>";
                die();
              }
            }
          }

          if ($_SESSION['loginSuccess']) {
            sleep(2);
              header("Location:board.php");
              exit;
          }
        ?>
      </div>
      <div class="col-lg-4">
        <div class="card bg-dark text-white rounded">
          <div class="card-body">
            <h1 class="card-title">Register</h1>
            <div class="card-text">
              <form action="login.php" method="get">
                <div class="form-group">
                  <lable for="username">Username :</lable>
                  <input type="text" name="username" class="form-control" id="username">
                </div>
                <div class="form-group">
                  <lable for="password">Password :</lable>
                  <input type="password" class="form-control" id="password" name="password">
                </div>
                <div class="form-group">
                  <lable for="name">Full Name :</lable>
                  <input type="text" class="form-control" id="name" name="name">
                </div>
                <div class="form-group">
                  <lable for="email">Email :</lable>
                  <input type="email" class="form-control" id="email" name="email">
                </div>
                <button type="Submit" class="btn btn-primary" name="register">Register</button>
              </form>
            </div>
          </div>
        </div>
        <?php
          if (isset($_GET['register'])) {
            if (empty($_GET['username']) || empty($_GET['password']) || empty($_GET['name']) || empty($_GET['email'])) {
              ?>
              <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Error!</strong> All fields must be filled.
              </div>
              <?php
            }else {
              try {
                $dbh = new PDO("mysql:host=127.0.0.1:3306;dbname=board","root","ridhs7474",array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
                //print_r($dbh);
                $dbh->beginTransaction();
                $user = $_GET['username'];
                $pwd = md5($_GET['password']);
                $name = $_GET['name'];
                $email = $_GET['email'];
                $sql = 'insert into users values("' . $user . '","' . $pwd . '","' . $name . '","' . $email . '")';
                $dbh->exec($sql) or die(print_r($dbh->errorInfo(), true));
                $dbh->commit();
                ?>
                <div class="alert alert-success alert-dismissable">
                  <button type="button" class="close" data-dismiss="alert">&times;</button>
                  <strong>Congratulation!</strong> You have registered successfully. Now login with the same credentials.
                </div>
                <?php
              } catch (PDOException $e) {
                print "Error!: " . $e->getMessage() . "<br/>";
                die();
              }
            }
          }
        ?>
      </div>
    </div>
  </div>

</body>
</html>
