<?php
 

$table = "timing_points";
//Connecting to the database
$conn = mysqli_connect("http://localhost:8080/phpmyadmin/index.php", "", "", "bt_sync_db");

//checking the successful connection
if($conn === false) {
    die("ERROR: Could not connect. " . mysqli_connect_error());
}
  
//if there is a post request move ahead 
if($_SERVER['REQUEST_METHOD']=='POST'){
 
    //$data = array();
   // $jsonData = $_POST['data'];
    $data = $_POST['data'];


    if(!empty($data)){
        $jsonData = json_decode($data);
        if(!empty($jsonData)){
                $sql = "SELECT * FROM $table";
                $result = $conn->query($sql);

                if ($result->num_rows > 0) {
                // output data of each row
                while($row = $result->fetch_assoc()) {
                    foreach($jsonData as $jsonD){

                            if($row["rfid"] == $jsonD->rfid){
                                $serverId = $row["rfid"];
                                $mobileId = $jsonD->rfid;
                            
                                if(strcasecmp($jsonD->tp, "Timing Point 1") == 0){
                                    
                                    $sqlUpdate = "UPDATE $table SET tp1 = '$jsonD->time' WHERE rfid = '$mobileId' ";
                                    if(mysqli_query($conn, $sqlUpdate)){
                                        $response['data'] = 'Data Updated';
                                        $response['status'] = '200';
                                      } else {
                                        $response['data'] = 'Error While Update data';
                                        $response['status'] = '400';
                                      }
                                }
                                else if(strcasecmp($jsonD->tp, "Timing Point 2") == 0){
                                    $sqlUpdate = "UPDATE $table SET tp2 = '$jsonD->time' WHERE rfid = '$mobileId' ";
                                    if(mysqli_query($conn, $sqlUpdate)){
                                        $response['data'] = 'Data Updated';
                                        $response['status'] = '200';
                                      } else {
                                        $response['data'] = 'Error While Update data';
                                        $response['status'] = '400';
                                      }
                                }
                                else if(strcasecmp($jsonD->tp, "Timing Point 3") == 0){
                                    $sqlUpdate = "UPDATE $table SET tp3 = '$jsonD->time' WHERE rfid = '$mobileId' ";
                                    if(mysqli_query($conn, $sqlUpdate)){
                                        $response['data'] = 'Data Updated';
                                        $response['status'] = '200';
                                      } else {
                                        $response['data'] = 'Error While Update data';
                                        $response['status'] = '400';
                                      }
                                }
                                else if(strcasecmp($jsonD->tp, "Timing Point 4") == 0){
                                    $sqlUpdate = "UPDATE $table SET tp4 = '$jsonD->time' WHERE rfid = '$mobileId' ";
                                    if(mysqli_query($conn, $sqlUpdate)){
                                        $response['data'] = 'Data Updated';
                                        $response['status'] = '200';
                                      } else {
                                        $response['data'] = 'Error While Update data';
                                        $response['status'] = '400';
                                      }
                                }
                                else if(strcasecmp($jsonD->tp, "Timing Point 5") == 0){
                                    $sqlUpdate = "UPDATE $table SET tp5 = '$jsonD->time' WHERE rfid = '$mobileId' ";
                                    if(mysqli_query($conn, $sqlUpdate)){
                                        $response['data'] = 'Data Updated';
                                        $response['status'] = '200';
                                      } else {
                                        $response['data'] = 'Error While Update data';
                                        $response['status'] = '400';
                                      }
                                }
                                else if(strcasecmp($jsonD->tp, "Timing Point 6") == 0){
                                    $sqlUpdate = "UPDATE $table SET tp6 = '$jsonD->time' WHERE rfid = '$mobileId' ";
                                    if(mysqli_query($conn, $sqlUpdate)){
                                        $response['data'] = 'Data Updated';
                                        $response['status'] = '200';
                                      } else {
                                        $response['data'] = 'Error While Update data';
                                        $response['status'] = '400';
                                      }
                                }

                            }
                                  
                        }
                    }
                } else {
                    $response['data'] = 'No Record Matched';
                    $response['status'] = '400';
                }
            }
        }else {
            $response['data'] = 'Error While Update data';
            $response['status'] = '400';
        }

      echo json_encode($response);
    }
 
?>
