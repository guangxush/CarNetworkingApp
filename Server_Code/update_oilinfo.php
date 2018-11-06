

<?php

/*<html>
<head>
<title>ceshi</title></head>
<body>
<form id="login" name="login" method ="post"  action="">  
       <p>pid<input id="pid" name="pid" type="text" /></p> 
        <p>name<input id="name" name="name" type="text" /></p>  <!--ÓÃ»§ÃûÎÄ±¾¿ò-->  
        <p>price<input id="price" name="price" type="text" /></p>                     <!--ÃÜÂëÎÄ±¾¿ò-->  
        <p>description<input id="description" name="description" type="text" /></p>        
        <p><input id="sub"  name ="sub" type="submit" value="queding" /></p><!--Ìá½»°´Å¥-->  
          
</form> 
 * Following code will update a product information
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['carid']) &&isset($_POST['oilinfoid']) &&isset($_POST['carinfoid']) &&isset($_POST['oiltime']) && isset($_POST['oiltype']) && isset($_POST['oilcount']) && isset($_POST['oilmoney'])) {
    
    $oilinfoid = $_POST['oilinfoid'];
    $carinfoid = $_POST['carinfoid'];
    $carid = $_POST['carid'];
    $oiltime = $_POST['oiltime'];
    $oiltype = $_POST['oiltype'];
    $oilcount = $_POST['oilcount'];
    $oilmoney = $_POST['oilmoney'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysql_query("UPDATE `oilinfo` SET 'carinfoid`=$carinfoid,`carid`=$carid,`oiltime`=$oiltime,`oiltype`=$oiltype,`oilcount`=$oilcount,`oilmoney`=$oilmoney WHERE oilinfoid=$oilinfoid");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
        
        // echoing JSON response
        echo json_encode($response);
    } else {
        
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
