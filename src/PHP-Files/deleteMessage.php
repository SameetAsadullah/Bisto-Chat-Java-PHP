<?php 
include "conn.php";
$response=array();
if(isset($_GET["text"])) {
    $text = $_GET["text"];
   
    $query = "DELETE FROM `messages` WHERE `text` = '$text'";
    $result = mysqli_query($con,$query);
    if($result) {
        $response['id'] = mysqli_insert_id($con);
        $response['reqmsg'] = "Message Deleted!";
        $response['reqcode'] = "1";   
    }
    else {
        $response['id'] = "NA";
        $response['reqmsg'] = "Error Deleting Message!";
        $response['reqcode'] = "0";
    }
}
else {
    $response['id'] = "NA";
    $response['reqmsg'] = "Incomplete Request!";
    $response['reqcode'] = "0";
}

$x = json_encode($response);
echo $x;

?>