<?php 
include "conn.php";
$response=array();
if(isset($_GET["text"], $_GET["newText"])) {
    $text = $_GET["text"];
    $newText = $_GET["newText"];
   
    $query = "UPDATE `messages` SET `text`='$newText' WHERE `text` = '$text'";
    $result = mysqli_query($con,$query);
    if($result) {
        $response['id'] = mysqli_insert_id($con);
        $response['reqmsg'] = "Message Changed!";
        $response['reqcode'] = "1";   
    }
    else {
        $response['id'] = "NA";
        $response['reqmsg'] = "Error Changing Message!";
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