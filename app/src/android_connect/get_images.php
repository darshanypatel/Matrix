<?php

/*
 * Following code will list all the event images
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all images from images table
$result = mysql_query("SELECT * FROM images") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results

    while ($row = mysql_fetch_array($result)) {
        $images = array();
        $images["event_id"] = $row["event_id"];
        $images["image_name"] = $row["image_name"];
        $images["image_src"] = $row["image_src"];

        // push single image into final response array
        array_push($response, $images);
    }

    // echoing JSON response
    echo json_encode($response);

}
else {
    // no image found
    /*$response["success"] = 0;
    $response["message"] = "No image found";
     */
    // echo no users JSON
    echo json_encode($response);
}
?>
