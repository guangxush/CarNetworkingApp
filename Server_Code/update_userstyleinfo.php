

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
if (isset($_POST['userid'])) {
    
    $userid = $_POST['userid'];
    $usersex = $_POST['usersex'];
    $usernick = $_POST['usernick'];
    $usercity = $_POST['usercity'];
    $username= $_POST['username'];
    $userpay = $_POST['userpay'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pidUPDATE `userstyleinfo` SET `userid`=[value-1],`usersex`=[value-2],`usernick`=[value-3],`usercity`=[value-4],`username`=[value-5],`userpay`=[value-6] WHERE 1
    //$result = mysql_query("UPDATE 'userstyleinfo' SET 'usersex'=$usersex,'usernick'=$usernick,'usercity'=$usercity,'userstyle'=$userstyle,'userpay'=$userpay WHERE userid=$userid");
     $result = mysql_query("UPDATE `userstyleinfo` SET `usersex`=$usersex,`usernick`=$usernick,`usercity`=$usercity,`username`=$username,`userpay`=$userpay WHERE `userid`=$userid");
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
        
        // echoing JSON response
        echo json_encode($response);
    } else {
         $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
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
