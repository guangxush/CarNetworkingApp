<?php

/*
 * Following code will list all the userstyleinfos
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all userstyleinfos from userstyleinfos table
$result = mysql_query("SELECT * FROM userstyleinfo") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // userstyleinfos node
    $response["userstyleinfos"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $userstyleinfo = array();
        $userstyleinfo["userid"] = $row["userid"];
        $userstyleinfo["usersex"] = $row["usersex"];
        $userstyleinfo["usernick"] = $row["usernick"];
        $userstyleinfo["usercity"] = $row["usercity"];
        $userstyleinfo["userstyle"] = $row["userstyle"];
        $userstyleinfo["userpay"] = $row["userpay"];
        $userstyleinfo["userwrong"] = $row["userwrong"];



        // push single userstyleinfo into final response array
        array_push($response["userstyleinfos"], $userstyleinfo);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no userstyleinfos found
    $response["success"] = 0;
    $response["message"] = "No userstyleinfos found";

    // echo no users JSON
    echo json_encode($response);
}
?>
