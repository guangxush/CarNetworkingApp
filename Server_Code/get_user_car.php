<?php

/*
 * Following code will list all the users
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();
if (isset($_POST["userid"])) 
{
// get all users from users table\
   $userid = $_POST['userid'];
   $result = mysql_query("SELECT * FROM carinfo WHERE userid=$userid") or die(mysql_error());
// check for empty result
  if (mysql_num_rows($result) > 0) 
  {
    // looping through all results
    // users node
   $response["carinfos"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $carinfo = array();
        //$carinfo["carinfoid"] = $row["carinfoid"];
        $carinfo["carid"] = $row["carid"];
        $carinfo["userid"] = $row["userid"];
        $carinfo["carbrand"] = $row["carbrand"];
        $carinfo["carlogo"] = $row["carlogo"];
        $carinfo["carlevel"] = $row["carlevel"];
        $carinfo["cartype"] = $row["cartype"];
        $carinfo["motortype"] = $row["motortype"];
        $carinfo["mileage"] = $row["mileage"];
        $carinfo["fueleconomy"] = $row["dfueleconomy"];
        $carinfo["motorperform"] = $row["motorperform"];
        $carinfo["transperform"] = $row["transperform"];
        $carinfo["heanligthperform"] = $row["heanligthperform"];
        // push single carinfo into final response array
        array_push($response["carinfos"], $carinfo);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
  } else {
    // no users found
    $response["success"] = 0;
    $response["message"] = "No users's car found";

    // echo no users JSON
    echo json_encode($response);
  }
}
else
{
     $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
