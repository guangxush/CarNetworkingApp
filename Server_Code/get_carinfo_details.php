


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
 * Following code will get single carinfo details
 * A carinfo is identified by carinfo id (pid)
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// check for post data
if (isset($_POST["carid"])) {
    $carid = $_POST['carid'];

    // get a carinfo from carinfos table
    $result = mysql_query("SELECT * FROM carinfo WHERE carid= $carid");

   //// if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);
            $response["carinfos"] = array();
            $carinfo = array();
            //$carinfo["carinfoid"] = $result["carinfoid"];
            $carinfo["carid"] = $result["carid"];
            $carinfo["userid"] = $result["userid"];
            $carinfo["carbrand"] = $result["carbrand"];
            $carinfo["carlogo"] = $result["carlogo"];
            $carinfo["carlevel"] = $result["carlevel"];
            $carinfo["cartype"] = $result["cartype"];
            $carinfo["motortype"] = $result["motortype"];
            $carinfo["mileage"] = $result["mileage"];
            $carinfo["fueleconomy"] = $result["fueleconomy"];
            $carinfo["motorperform"] = $result["motorperform"];
            $carinfo["transperform"] = $result["transperform"];
            $carinfo["heanligthperform"] = $result["heanligthperform"];
            // success
            $response["success"] = 1;

            // user node
            array_push($response["carinfos"], $carinfo);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no carinfo found
            $response["success"] = 0;
            //$response["message"] = "No carinfo found1".$carid;
            $response["message"] = $carid;

            // echo no users JSON
            echo json_encode($response);
        }
 //   } else {
 //      // no carinfo found
 //       $response["success"] = 0;
  //      $response["message"] = "No carinfo found2";

        // echo no users JSON
  //      echo json_encode($response);
  //  }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>