<?php

/*
 * Following code will list all the events
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all events from event table
$result = mysql_query("SELECT * FROM event") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    //$response["event"] = array();

    while ($row = mysql_fetch_array($result)) {
        $event = array();
        //$event["event_id"] = $row["event_id"];
        $event["event_name"] = $row["event_name"];
        $event["time_start_day1"] = $row["time_start_day1"];
        $event["venue"] = $row["venue"];
        /*
        $response["event_id"] = $row["event_id"];
        $response["event_name"] = $row["event_name"];
        $response["time_start_day1"] = $row["time_start_day1"];
        $response["venue"] = $row["venue"];
        */

        $event["time_start_day2"] = $row["time_start_day2"];
        $event["duration_day2"] = $row["duration_day2"];

        //$event["description"] = $row["description"];
        //$event["max_participant"] = $row["max_participant"];
        //$event["contact"] = $row["contact"];
        //$event["instructions"] = $row["instructions"];
        //$event["rules"] = $row["rules"];
        //$event["prize_results"] = $row["prize_results"];

        // push single admin into final response array

        array_push($response/*["event"]*/, $event);

    }
    // success
    //$response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);


}
else {
    // no admin found
    //$response["success"] = 0;
    //$response["message"] = "No admin found";

    // echo no users JSON
    echo json_encode($response);

}
?>
