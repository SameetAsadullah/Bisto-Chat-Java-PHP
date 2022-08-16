<?php
    $con=mysqli_connect("localhost", "root", "", "bisto_chat") or die("I cannot connect to the database.".mysql_errno());

    mysqli_select_db($con,"bisto_chat");
    mysqli_query($con,"SET NAMES 'utf8'");

?>