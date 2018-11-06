

<?php

/*<html>
<head>
<title>ceshi</title></head>
<body>
<form id="login" name="login" method ="post"  action="">  
  
        <p>name<input id="name" name="name" type="text" /></p>  <!--ÓÃ»§ÃûÎÄ±¾¿ò-->  
        <p>price<input id="price" name="price" type="text" /></p>                     <!--ÃÜÂëÎÄ±¾¿ò-->  
        <p>description<input id="description" name="description" type="text" /></p>        
        <p><input id="sub"  name ="sub" type="submit" value="queding" /></p><!--Ìá½»°´Å¥-->  
          
</form>  
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['carid'])   &&isset($_POST['oiltime']) && isset($_POST['oiltype']) && isset($_POST['oilcount']) && isset($_POST['oilstation']))  {
    

    $carid = $_POST['carid'];
    $oiltime = $_POST['oiltime'];
    $oiltype = $_POST['oiltype'];
    $oilcount = $_POST['oilcount'];
    $oilstation = $_POST['oilstation'];
    $response["message"] = "条件成立";

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new rowINSERT INTO 'oilinfo'('carid', 'oiltime', 'oiltype', 'oilcount', 'oilstation') VALUES ($carid, $oiltime, $oiltype, $oilcount, $oilstation)
    $result = mysql_query("INSERT INTO `oilinfo`(`carid`, `oiltime`, `oiltype`, `oilcount`, `oilstation`) VALUES ($carid, $oiltime, $oiltype, $oilcount, $oilstation)");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Oil successfully Insert.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    //$response["message"] = "Required field(s) is missing";
    $response["message"] = "buchengli";

    // echoing JSON response
    echo json_encode($response);
}
?>