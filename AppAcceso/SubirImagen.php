<?php

    $file_path = "";
    $file_path = $file_path . basename( $_FILES['imagen']['name']);
    if(move_uploaded_file($_FILES['imagen']['tmp_name'], $file_path)) {
        echo "success";
    } else{
        echo "fail";
    }

?>