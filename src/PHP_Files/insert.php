<?php 
include "conn.php";
$response=array();
if(isset($_POST["email"], $_POST["name"],$_POST["gender"], $_POST["phoneno"],$_POST["bio"],$_POST["dp"], $_POST["password"])) {
    $email = $_POST["email"];
    $name = $_POST["name"];
    $gender = $_POST["gender"];
    $phoneno = $_POST["phoneno"];
    $bio = $_POST["bio"];
    $dp = $_POST["dp"];
    $password = $_POST["password"];
    $target_dir = "upload";

    if(!file_exists($target_dir)) {
        mkdir($target_dir, 0777, true);
    }

    $target_dir = $target_dir ."/". rand() . "_" . time() . ".jpeg";
    
    file_put_contents($target_dir, base64_decode($dp));

    $query = "INSERT INTO `profile`(`email`, `name`, `gender`, `phoneno`, `bio`, `dp`, `password`) VALUES ('$email','$name','$gender','$phoneno','$bio','$target_dir','$password')";
    $result = mysqli_query($con,$query);
    if($result) {
        $response['id'] = mysqli_insert_id($con);
        $response['reqmsg'] = "Contact Inserted!";
        $response['reqcode'] = "1";   
        $response['dp'] = $target_dir;
    }
    else {
        $response['id'] = "NA";
        $response['reqmsg'] = "Error Inserting Contact!";
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