<?PHP
$hostname_localhost ="localhost";
$database_localhost ="AppAcceso";
$port_localhost = "3306";
$username_localhost ="root";
$password_localhost ="";

$json=array();

	if(isset($_GET["disponible"]) && isset($_GET["ausente"]) && isset($_GET["molestar"]) && isset($_GET["id"])){
        $disponible=$_GET["disponible"];
        $ausente=$_GET["ausente"];
        $molestar=$_GET["molestar"];
        $id=$_GET["id"];
        
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost,$port_localhost);
		
		$insert="UPDATE Residentes SET Disponible='{$disponible}', Ausente='{$ausente}', No_molestar='{$molestar}' WHERE idResidente={$id}";

		$resultado_insert=mysqli_query($conexion,$insert);
		
		if($resultado_insert){
			$consulta="SELECT * FROM Residentes WHERE idResidente={$id}";
			$resultado=mysqli_query($conexion,$consulta);
			
			if($registro=mysqli_fetch_array($resultado)){
				$json['Invitados'][]=$registro;
			}
			mysqli_close($conexion);
			echo json_encode($json);
		}
		else{
			echo 'No registra';
			//echo json_encode($json);
		}
		
	}
	else{
			echo 'No retorna';
			//echo json_encode($json);
		}
//http://localhost/appacceso/RegistrarResidentes.php?Nombre=Erick%20Rodriguez&Caducidad=2018-12-24%2000:00:00&FotoID=jnose&Selfie=asdad&Vehiculo=chevy&Placas=937sdbu&Empresa=Bitfarm&GeneradorQR=9373isjsue&FkResidente=1
?>


