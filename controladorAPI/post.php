<?php
include "config.php";
include "utils.php";


$dbConn =  connect($db);


if ($_SERVER['REQUEST_METHOD'] == 'GET')
{
      if (isset($_GET['idventa']))
    {
      $sql = $dbConn->prepare("SELECT nomcliente as nombre_cliente, doccliente as doc_cliente, tpaquetes.nombrepaq as nombre_paquete, cantidaddias as cantidad_dias, total, tusuarios.nombre as nombre_usuario, fecha FROM tusuarios 
      INNER JOIN tventas
      ON tusuarios.idusuario = tventas.idusuario INNER JOIN tpaquetes
      ON tpaquetes.idpaquete = tventas.idpaquete where idventa=:idventa");
      $sql->bindValue(':idventa', $_GET['idventa']);
      $sql->execute();
      header("HTTP/1.1 200 OK");
      echo json_encode(  $sql->fetch(PDO::FETCH_ASSOC)  );
      exit();
	  }
    else {
      $sql = $dbConn->prepare("SELECT nomcliente as nombre_cliente, doccliente as doc_cliente, tpaquetes.nombrepaq as nombre_paquete, cantidaddias as cantidad_dias, total, tusuarios.nombre as nombre_usuario, fecha FROM tusuarios 
      INNER JOIN tventas
      ON tusuarios.idusuario = tventas.idusuario INNER JOIN tpaquetes
      ON tpaquetes.idpaquete = tventas.idpaquete");
      $sql->execute();
      $sql->setFetchMode(PDO::FETCH_ASSOC);
      header("HTTP/1.1 200 OK");
      echo json_encode( $sql->fetchAll()  );
      exit();
    }
}

if ($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $input = $_POST;
    $sql = "INSERT INTO tventas
          (nomcliente, doccliente, idpaquete, cantidaddias, total, idusuario, fecha)
          VALUES
          (:nomcliente, :doccliente, :idpaquete, :cantidaddias, :total, :idusuario, :fecha)";
    $statement = $dbConn->prepare($sql);
    bindAllValues($statement, $input);
    $statement->execute();
    $postId = $dbConn->lastInsertId();
    if($postId)
    {
      $input['idventa'] = $postId;
      header("HTTP/1.1 200 OK");
      echo json_encode($input);
      exit();
	 }
}

?>