<?php

require_once '../servicios/ConectaDB.php';
$codigo = $_REQUEST["codigo"];
$cadSQL = "SELECT codigo,nombre,precio,cantidad FROM producto "
        . " WHERE codigo = $codigo";

$registro = mysqli_query($cnx, $cadSQL);
$producto=[];
while ($fila = mysqli_fetch_assoc($registro)) {
    $producto[]  = $fila;
}
mysqli_close($cnx);
echo json_encode($producto);