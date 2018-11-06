

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
if (isset($_POST['carid'])) {
    
    $carid=$_POST['carid'];
    $userid=$_POST['userid'];
    $carbrand=$_POST['carbrand'];
    $carlogo=$_POST['carlogo'];
    $carlevel=$_POST['carlevel'];
    $cartype=$_POST['carlevel'];
    $motortype= $_POST['motortype'];
    $mileage = $_POST['mileage'];
    $fueleconomy= $_POST['fueleconomy'];
    $motorperform = $_POST['motorperform'];
    $transperform = $_POST['transperform'];
    $heanligthperform = $_POST['heanligthperform'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysql_query("UPDATE carinfo SET `carbrand`=$carbrand , `carlogo`=$carlogo , `carlevel`=$carlevel , `cartype`=$cartype,`motortype`=$motortype,`mileage`=$mileage,`fueleconomy`=$fueleconomy,`motorperform`=$motorperform,`transperform`=$transperform,`heanligthperform`=$heanligthperform WHERE `carid`=$carid");

    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Carinfo successfully updated.";
        
        // echoing JSON response
        echo json_encode($response);
    } else {
         // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Update Failed!";
        
        // echoing JSON response
        echo json_encode($response);
        
    }
    // check if row inserted or not
    //if ($result) {
        // successfully updated
       // $response["success"] = 1;
        //$response["message"] = "Product successfully updated.";
        
        // echoing JSON response
       // echo json_encode($response);
    //} else {
        
   // }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
