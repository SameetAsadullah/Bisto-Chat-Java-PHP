<?php
include 'conn.php';
$responce=array();


$senderId  = $_GET['senderId'];
$receiverId  = $_GET['receiverId'];
$sql="SELECT * FROM `messages` 	WHERE (senderId='$senderId' AND receiverId='$receiverId') OR (senderId='$receiverId' AND receiverId='$senderId')";
$res=$con->query($sql);

if ($res->num_rows > 0) {
  // output data of each row
  while($row = $res->fetch_assoc()) {

  		$temp=array();
    	$temp["senderId"]=$row["senderId"];
		$temp["receiverId"]=$row["receiverId"];
		$temp["text"]=$row["text"];
		$temp["image"]=$row["image"];
		$temp["timestamp"]=$row["timestamp"];
		array_push($responce,$temp);
  }
} else {
  echo "0 results";
}

$x = json_encode($responce); #Converts Array into JSON
echo $x;


function utf8ize($d) {
	if (is_array($d)) {
		foreach ($d as $k => $v) 
		{
			$d[$k] = utf8ize($v);
		}
	} 
	else if (is_string ($d)) {
		return utf8_encode($d);
	}
	return $d;
}


?>