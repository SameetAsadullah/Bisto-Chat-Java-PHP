<?php

include 'conn.php';
$responce=array();
if(isset($_POST['senderId'],$_POST['receiverId'],$_POST['text']))
{
	$senderId=$_POST['senderId'];
	$receiverId=$_POST['receiverId'];
	$text=$_POST['text'];
	$timestamp=$_POST['timestamp'];
	

	if(isset($_POST['image'])){
		$image=$_POST['image'];
		$directory="message_images/";
		$image_name=rand()."-".$senderId."-".$receiverId.".jpeg";
		$target_directory=$directory."/".$image_name;
		file_put_contents($target_directory, base64_decode($image));
		

		$query="INSERT INTO `messages`(`senderId`, `receiverId`, `text`, `timestamp`, `image`) 
		VALUES ('$senderId','$receiverId','$text','$timestamp','$target_directory')";
		$res=mysqli_query($con,$query);
		if($res)
		{
			$responce["id"]=mysqli_insert_id($con);
			$responce["msg"]="Contact Inserted";
			$responce["responce"]="1";
		}
		else {
			$responce["id"]="NA";
			$responce["msg"]="Error Inserting Contact";
			$responce["responce"]="0";
		}
	}
	else{
		$query="INSERT INTO `messages`(`senderId`, `receiverId`, `text`, `timestamp`, `image`) 
		VALUES ('$senderId','$receiverId','$text','$timestamp','Not Available')";
		$res=mysqli_query($con,$query);
		if($res)
		{
			$responce["id"]=mysqli_insert_id($con);
			$responce["msg"]="Contact Inserted";
			$responce["responce"]="1";
		}
		else {
			$responce["id"]="NA";
			$responce["msg"]="Error Inserting Contact";
			$responce["responce"]="0";
		}
	}
	
}
else {
 $responce["id"]="NA";
 $responce["msg"]="Incomplete Request, Error Inserting";
 $responce["responce"]="0";
}


$x = json_encode(utf8ize($responce)); #Converts Array into JSON
echo $x;			## Prints the JSON

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