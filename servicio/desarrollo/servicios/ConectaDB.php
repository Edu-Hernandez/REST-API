<?php

$hostname="localhost";
$username="root";
$password="";
$database="negocio";
$port="3306";

$cnx = mysqli_connect($hostname, $username, $password, $database, $port);

if (!$cnx){
    echo "ddsd";
}