


<?php

/*<html>
<head>
<title>ceshi</title></head>
<body>
<form id="login" name="login" method ="get"  action="">  
  
        <p>pid<input id="pid" name="pid" type="text" /></p>  <!--ÓÃ»§ÃûÎÄ±¾¿ò-->  
       <!--  <p>price<input id="price" name="price" type="text" /></p>                     ÃÜÂëÎÄ±¾¿ò  
        <p>description<input id="description" name="description" type="text" /></p>   -->      
        <p><input id="sub"  name ="sub" type="submit" value="queding" /></p><!--Ìá½»°´Å¥-->  
          
</form>
 * Following code will get single user details
 * A user is identified by user id (pid)
 */

// array for JSON response
$response = array();
// include db connect class
require_once __DIR__ . '/db_connect.php';
// connecting to db
$db = new DB_CONNECT();
// check for post data
if (isset($_POST["userid"])&&isset($_POST["userpwd"])) {
    $userid = $_POST['userid'];
    $userpwd = $_POST['userpwd'];
    // get a user from users table
    $result = mysql_query("SELECT  * FROM user WHERE userid = $userid AND userpwd=$userpwd");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

          
            $response["success"] = 1;

           
            echo json_encode($response);
        } else {
            // no user found
            $response["success"] = 0;
            $response["message"] = "No user found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no user found
        $response["success"] = 0;
        $response["message"] = "No user found";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>