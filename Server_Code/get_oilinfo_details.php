


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
 * Following code will get single product details
 * A product is identified by product id (pid)
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

    // get a product from products table
    $result = mysql_query("SELECT * FROM oilinfo  WHERE carid = $carid ORDER BY oiltime DESC");

    
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);
            $response["oilinfos"] = array();
            $oilinfo = array();
           
            $oilinfo["carid"] = $result["carid"];
            $oilinfo["oiltime"] = $result["oiltime"];
            $oilinfo["oiltype"] = $result["oiltype"];
            $oilinfo["oilcount"] = $result["oilcount"];
            $oilinfo["oilstation"] = $result["oilstation"];
            // success
            $response["success"] = 1;

            // user node
           

            array_push($response["oilinfos"], $oilinfo);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No oilinfo found";

            // echo no users JSON
            echo json_encode($response);
        }
     // else {
        // no product found
      //  $response["success"] = 0;
       // $response["message"] = "No product found";

        // echo no users JSON
      //  echo json_encode($response);
   // }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>