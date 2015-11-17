<?php

$servername = "localhost";
$username = "youthcyb_160s1g3";
$password = "Lionstastegood";
$dbname = "youthcyb_cs160s1g3";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
?>