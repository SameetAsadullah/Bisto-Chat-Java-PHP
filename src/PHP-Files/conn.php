<?php
    $con=mysqli_connect("localhost", "root", "", "assignment4") or die("I cannot connect to the database.".mysql_errno());

    mysqli_select_db($con,"assignment4");
    mysqli_query($con,"SET NAMES 'utf8'");

?>