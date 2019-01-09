<?php
require"info.php";

$Name = (isset($_POST["Name"]) ? $_POST['Name']:'');
$Roll_Number = (isset($_POST["Roll_Number"]) ? $_POST['Roll_Number']:'');
$Phone_Number=(isset($_POST["Phone_Number"]) ? $_POST['Phone_Number']:'');
$Cycle_Number = (isset($_POST["Cycle_Number"]) ? $_POST['Cycle_Number']:'');
$Cycle_Description = (isset($_POST["Cycle_Description"]) ? $_POST['Cycle_Description']:'');
$Password = (isset($_POST["Password"]) ? $_POST['Password']:'');
$Past_location_1 = (isset($_POST["Past_location_1"]) ? $_POST['Past_location_1']:'');
$Past_location_2 = (isset($_POST["Past_location_2"]) ? $_POST['Past_location_2']:'');
$Past_location_3 = (isset($_POST["Past_location_3"]) ? $_POST['Past_location_3']:'');
$Past_location_4 = (isset($_POST["Past_location_4"]) ? $_POST['Past_location_4']:'');
$Present_location = (isset($_POST["Present_location"]) ? $_POST['Present_location']:'');
$Past_time_1 = (isset($_POST["Past_time_1"]) ? $_POST['Past_time_1']:'');
$Past_time_2 = (isset($_POST["Past_time_2"]) ? $_POST['Past_time_2']:'');
$Past_time_3 = (isset($_POST["Past_time_3"]) ? $_POST['Past_time_3']:'');
$Past_time_4 = (isset($_POST["Past_time_4"]) ? $_POST['Past_time_4']:'');
$Time = (isset($_POST["Time"]) ? $_POST['Time']:'');
$riding_check = (isset($_POST["riding_check"]) ? $_POST['riding_check']:'');

$sql = "select * from test_table where Cycle_Number like '".$Cycle_Number."';";
$response = array();
$result =mysqli_query($con,$sql);

if(mysqli_num_rows($result)>0){
	$code = "reg_failed";
	$message = "Cycle Number already registered";
	array_push($response,array($code,$message));
	echo json_encode($response);
}


else{
$sql = "insert into test_table values('".$Name."','".$Roll_Number."','".$Phone_Number."','".$Cycle_Number."','".$Cycle_Description."','".$Password."',
			'".$Present_location."','".$Past_location_1."','".$Past_location_2."','".$Past_location_3."','".$Past_location_3."','".$Time."',
				'".$Past_time_1."','".$Past_time_2."','".$Past_time_3."','".$Past_time_4."','".$riding_check."');";

$result =mysqli_query($con,$sql);		
$code = "registration success";
$message ="Thank you for registration.Now you can Login ";
array_push($response,array($code,$message));
echo json_encode($response);
} 

mysqli_close($con);

?>
