


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
 * Following code will get single userstyleinfo details
 * A userstyleinfo is identified by userstyleinfo id (pid)
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// check for post data
if (isset($_POST["userid"])) {
    $userid = $_POST['userid'];

    // get a userstyleinfo from userstyleinfos table
    //$result = mysql_query("SELECT * FROM userstyleinfo WHERE userid = $userid");
    $result = mysql_query("SELECT * FROM `userstyleinfo` WHERE userid = $userid");

    //if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);
            $response["userstyleinfos"] = array();
            $userstyleinfo = array();
            $userstyleinfo["userid"] = $result["userid"];
            $userstyleinfo["usersex"] = $result["usersex"];
            $userstyleinfo["usernick"] = $result["usernick"];
            $userstyleinfo["username"] = $result["username"];
            $userstyleinfo["usercity"] = $result["usercity"];
            $userstyleinfo["userpay"] = $result["userpay"];
            // success
            $response["success"] = 1;

            // user node
            array_push($response["userstyleinfos"], $userstyleinfo);
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no userstyleinfo found
            $response["success"] = 0;
            $response["message"] = "No userstyleinfo found";

            // echo no users JSON
            echo json_encode($response);
        }
    //} else {
        // no userstyleinfo found
       // $response["success"] = 0;
       // $response["message"] = "No userstyleinfo found";

        // echo no users JSON
      //  echo json_encode($response);
    //}
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>