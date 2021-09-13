<?php

$table = "users";
//Connecting to the database
$conn = mysqli_connect("http://localhost:8080/phpmyadmin/", "", "", "bt_sync_db");
//checking the successful connection
if($conn === false) {
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

    //if there is a post request move ahead 
    if($_SERVER['REQUEST_METHOD']=='POST'){
    
        //$data = array();
      // $jsonData = $_POST['data'];
        $password = $_POST['password'];

          $sql = "SELECT * FROM $table";
      
          $result = $conn->query($sql);

          if ($result->num_rows > 0) {
          // output data of each row
          while($row = $result->fetch_assoc()) {
            
            if($row['pwd'] == $password){
              $response['data'] = 'Login Successfully';
              $response['status'] = '200';                       
                                      
            }else {
              $response['data'] = 'Invalid User, please try again';
              $response['status'] = '400';
            }

         }

         echo json_encode($response);
     }

  }
 
?>

 
