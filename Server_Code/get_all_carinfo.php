<?php

/*
 * Following code will list all the carinfos
 */
// array for JSON response
$response = array();
// include db connect class
require_once __DIR__ . '/db_connect.php';
// connecting to db
$db = new DB_CONNECT();
// get all carinfos from carinfos table
$result = mysql_query("SELECT * FROM carinfo") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // carinfos node
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
    // no carinfos found
    $response["success"] = 0;
    $response["message"] = "No carinfos found";
    // echo no users JSON
    echo json_encode($response);
}
?>
