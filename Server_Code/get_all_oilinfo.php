<?php

/*
 * Following code will list all the oilinfos
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all oilinfos from oilinfos table
$result = mysql_query("SELECT * FROM oilinfo") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // oilinfos node
    $response["oilinfo"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $oilinfo = array();
        $oilinfo["oilinfoid"] = $row["oilinfoid"];
        $oilinfo["carinfoid"] = $row["carinfoid"];
        $oilinfo["carid"] = $row["carid"];
        $oilinfo["oiltime"] = $row["oiltime"];
        $oilinfo["oiltype"] = $row["oiltype"];
        $oilinfo["oilcount"] = $row["oilcount"];
        $oilinfo["oilmoney"] = $row["oilmoney"];
    



        // push single oilinfo into final response array
        array_push($response["oilinfos"], $oilinfo);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no oilinfos found
    $response["success"] = 0;
    $response["message"] = "No oilinfos found";

    // echo no users JSON
    echo json_encode($response);
}
?>
