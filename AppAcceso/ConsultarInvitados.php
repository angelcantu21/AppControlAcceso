<?PHP
$hostname_localhost ="localhost";
$database_localhost ="AppAcceso";
$port_localhost = "3306";
$username_localhost ="root";
$password_localhost ="";

$json=array();

if(isset($_GET["id"])){
	$identificador=$_GET["id"];
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost,$port_localhost);

		$consulta="SELECT * FROM Invitados WHERE FkResidente=".$identificador;
		$resultado=mysqli_query($conexion,$consulta);
		
		while($registro=mysqli_fetch_array($resultado)){
			$json['Invitados'][]=$registro;
		}
		mysqli_close($conexion);
		echo json_encode($json);
	}else{
		echo "Error identificador no seteado";
	}
	//echo date("Y-m-d");
?>