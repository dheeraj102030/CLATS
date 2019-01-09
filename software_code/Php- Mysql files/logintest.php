<?php
require "info.php";

$Roll_Number = $_POST["Roll_Number"];
$Password = $_POST["Password"];

$sql = "select Present_location,Time,Past_location_1,Past_location_2 from test_table where Roll_Number like '".$Roll_Number."' and
Password like '".$Password."';";

$result = mysqli_query($con, $sql);
$response = array();

if(mysqli_num_rows($result)>0)
{       
        $row =mysqli_fetch_row($result);
        $Present_location = $row[0];
        $Time = $row[1];
        $Past_location_1 = $row[2];
        $Past_location_2 = $row[3];
        $code = "Login_Success";
        array_push($response, array("code" =>$code,"Present_location" =>$Present_location,"Time" =>$time,"Past_location_1" =>$Past_location_1,"Past_location_2" =>$Past_location_2));
        echo json_encode($response);
}

else{
        $code = "login_failed";
        $message = "User not found try again";
        array_push($response , array("code" =>$code, "message" =>$message)); 
        echo json_encode($response);
}
mysqli_close($con);
?>
