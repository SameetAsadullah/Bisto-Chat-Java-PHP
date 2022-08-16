<?php 

include "conn.php";
$result=array();
$result['data'] = array();
$select = "SELECT * from `profile`";
$response = mysqli_query($con, $select);

while($row = mysqli_fetch_assoc($response)) {
    $index["id"] = $row['id'];
    $index["email"] = $row['email'];
    $index["name"] = $row['name'];
    $index["password"] = $row['password'];
    $index["gender"] = $row['gender'];
    $index["phoneno"] = $row['phoneno'];
    $index["bio"] = $row['bio'];
    $index["dp"] = $row['dp'];

    array_push($result['data'], $index);    
    }

    $result["success"] = "1";
    echo json_encode($result['data']);
    
?>