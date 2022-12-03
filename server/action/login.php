<?php

header("Content-Type: application/json");

include_once '../config/dbconnect.php';
$json = json_decode(file_get_contents('php://input'), true);

if(isset($json['email']) && isset($json['mdp'])){
    
    $mail = htmlspecialchars($json['email']);
    $mdp = htmlspecialchars($json['mdp']);
    
    $getUser = $bdd->prepare("SELECT * FROM user WHERE email = ?");
    $getUser -> execute(array($mail));
    
    if($getUser->rowCount() > 0) {
        $user = $getUser->fetch();

        if(password_verify($mdp, $user['mdp'])){
            $result["success"] = true;
        }else {
            $result["success"] = false;
            $result["error"] = "mot de passe incorrect";
        }
    }else {
        $result["success"] = false;
        $result["error"] = "ce nom d'utilisateur n'existe pas";
    }
    
}else {
    $result["success"] = false;
    $result["error"] = "veuillez completer tous les champs...";
}

echo json_encode($result);