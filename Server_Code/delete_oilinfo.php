

<?php

/*<html>
<head>
<title>ceshi</title></head>
<body>
<form id="login" name="login" method ="post"  action="">  
  
        <p>pid<input id="pid" name="pid" type="text" /></p>  <!--ÓÃ»§ÃûÎÄ±¾¿ò-->  
       <!--  <p>price<input id="price" name="price" type="text" /></p>                     ÃÜÂëÎÄ±¾¿ò  
        <p>description<input id="description" name="description" type="text" /></p>   -->      
        <p><input id="sub"  name ="sub" type="submit" value="queding" /></p><!--Ìá½»°´Å¥-->  
          
</form>
 * Following code will delete a product from table
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['oilinfoid'])) {
    $oilinfoid = $_POST['oilinfoid'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysql_query("DELETE FROM oilinfo WHERE oilinfoid= $oilinfoid");
    
    // check if row deleted or not
    if (mysql_affected_rows() > 0) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully deleted";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";

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