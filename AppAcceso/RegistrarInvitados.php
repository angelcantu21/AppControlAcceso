<?PHP
$hostname_localhost ="localhost";
$database_localhost ="AppAcceso";
$port_localhost = "3306";
$username_localhost ="root";
$password_localhost ="";

$json=array();

	if(isset($_GET["Nombre"]) && isset($_GET["Caducidad"]) && isset($_GET["FotoID"]) && isset($_GET["Selfie"]) && isset($_GET["Vehiculo"]) && isset($_GET["Placas"]) && isset($_GET["Empresa"]) && isset($_GET["GeneradorQR"]) && isset($_GET["FkResidente"])  && isset($_GET["check"])){
        $nombre=$_GET["Nombre"];
        $caducidad=$_GET["Caducidad"];
        $foto=$_GET["FotoID"];
        $selfie=$_GET["Selfie"];
        $vehiculo=$_GET["Vehiculo"];
        $placas=$_GET["Placas"];
        $empresa=$_GET["Empresa"];
        $generador=$_GET["GeneradorQR"];
		$residente=$_GET["FkResidente"];
		$check = $_GET["check"];

        
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost,$port_localhost);
		
		if($check == "true"){
			$insert="INSERT INTO Invitados(Nombre, Fecha, Caducidad, FotoID, Selfie, Vehiculo, Placas, Empresa, GeneradorQR, FkResidente) VALUES ('{$nombre}',CURDATE(),'{$caducidad}','{$foto}','{$selfie}','{$vehiculo}','{$placas}','{$empresa}','{$generador}',{$residente})";
		}else{
			$insert="INSERT INTO Invitados(Nombre, Fecha, Caducidad, FotoID, Selfie, GeneradorQR, FkResidente) VALUES ('{$nombre}',CURDATE(),'{$caducidad}','{$foto}','{$selfie}','{$generador}',{$residente})";
		}

		$resultado_insert=mysqli_query($conexion,$insert);
		
		if($resultado_insert){
			$consulta="SELECT * FROM Invitados WHERE Nombre = '{$nombre}'";
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


