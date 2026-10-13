<?php
$host = getenv('BISTO_DB_HOST') ?: 'localhost';
$port = (int) (getenv('BISTO_DB_PORT') ?: 3306);
$database = getenv('BISTO_DB_NAME') ?: 'bisto_chat';
$username = getenv('BISTO_DB_USER') ?: 'root';
$password = getenv('BISTO_DB_PASSWORD') ?: '';

$con = mysqli_connect($host, $username, $password, $database, $port);
if (!$con) {
    http_response_code(500);
    die('Database connection failed. Check the BISTO_DB_* environment variables.');
}

mysqli_set_charset($con, 'utf8mb4');
?>
