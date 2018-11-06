
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
if ( isset($_POST['carid']) && isset($_POST['userid'])) { 
    $carid = $_POST['carid'];
    $userid = $_POST['userid'];
    $carbrand = $_POST['carbrand'];
    $carlogo= $_POST['carlogo'];
    $carlevel = $_POST['carlevel'];
    $cartype= $_POST['cartype'];
    $motortype= $_POST['motortype'];
    $mileage = $_POST['mileage'];
    $fueleconomy = $_POST['fueleconomy'];
    $motorperform = $_POST['motorperform'];
    $transperform = $_POST['transperform'];
    $heanligthperform = $_POST['heanligthperform'];
    $response["message"] = "条件成立";
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
    // connecting to db
    $db = new DB_CONNECT();
    
    $str = "INSERT INTO 'carinfo' ('carid',  'userid', 'carbrand', 'carlogo', 'carlevel', 'cartype', 'motortype', 'mileage', 'fueleconomy', 'motorperform', 'transperform', 'heanligthperform') VALUES ( "+$carid+", "+$userid+", "+$carbrand+", "+$carlogo+", "+$carlevel+", "+$cartype+", "+$motortype+", "+$mileage+", "+$fueleconomy+", "+$motorperform+", "+$transperform+", "+$heanligthperform+")";
    $result = mysql_query("INSERT INTO `carnet`.`carinfo` (`carid`, `userid`, `carbrand`, `carlogo`, `carlevel`, `cartype`, `motortype`, `mileage`, `fueleconomy`, `motorperform`, `transperform`, `heanligthperform`) VALUES ( $carid, $userid, $carbrand, $carlogo, $carlevel, $cartype, $motortype, $mileage, $fueleconomy, $motorperform, $transperform, $heanligthperform)");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = $str;
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