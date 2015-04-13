<?
//BASE DE DATOS BEGIN
// Conectando, seleccionando la base de datos
$link = mysql_connect('localhost', 'root', '')
    or die('No se pudo conectar: ' . mysql_error());

mysql_select_db('ventas') or die('No se pudo seleccionar la base de datos');

//Agrarra los elementos que vienen por post



// Realizar una consulta MySQL
$query = "SELECT * FROM usuarios where login ='" . $_POST["username"] . "' and password = '" .  $_POST["pass"] . "'";
$result = mysql_query($query) or die('Consulta fallida: ' . mysql_error());

$fila = mysql_fetch_assoc($result);

//echo var_dump($fila);
// Liberar resultados
mysql_free_result($result);

// Cerrar la conexión
mysql_close($link);
//BASE DE DATOS END
$pregunta = new stdClass();
if($fila){
$pregunta->mensaje = array("Estado"=>"OK","login"=>"login","codigo"=>1);
}
else{
$pregunta->mensaje = array("Estado"=>"ERROR","Mensaje"=>"Usuario Invalido");

}




$json = json_encode($pregunta);

echo $json;
?>
