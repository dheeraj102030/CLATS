<?php
require"info.php";

$Password = (isset($_POST["Password"]) ? $_POST['Password']:'');
$riding_check = (isset($_POST["riding_check"]) ? $_POST['riding_check']:'');

$response = array();

$sql = "UPDATE test_table SET riding_check ='".$riding_check."' where Password like '".$Password."';";

$result =mysqli_query($con,$sql);
$code = "Update Success";
array_push($response,array($code));
echo json_encode($response); 

mysqli_close($con);

?>
