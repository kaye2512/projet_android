<?php

header("Content-Type: application/json");

include_once '../config/dbconnect.php';
$json = json_decode(file_get_contents('php://input'), true);

if(isset($json['nom']) && isset($json['prenom']) && isset($json['email']) && isset($json['mdp']) && isset($json['dateN'])){
    $nom = htmlspecialchars($json['nom']);
    $prenom = htmlspecialchars($json['prenom']);
    $mail = htmlspecialchars($json['email']);
    $mdp = htmlspecialchars($json['mdp']);
    $passHashed = password_hash($mdp, PASSWORD_DEFAULT);
    $date = htmlspecialchars($json['dateN']);

    $success = "";

    if($nom == "" or $prenom == "" or $mail == "" or $mdp == "" or $date == ""){
        $result["success"] = false;
        $result["error"] = "les champs sont vides";
    }else{
        $verifsilogexist = $bdd->prepare('SELECT * FROM user where email = ?');
        $verifsilogexist ->execute(array($mail));

        if($verifsilogexist->rowCount() > 0){
            $result["success"] = false;
            $result["error"] = "cet email exist déja";
        }else {
            try {
                $add = $bdd->prepare('INSERT INTO user (id, nom, prenom, email, mdp, dateNaiss) VALUES (null, ?, ?, ?, ?, ?);');
                $add->execute(array($nom, $prenom, $mail, $passHashed, $date));
                $result["success"] = true;
            }catch (Exception $e){
                $result["success"] = false;
                $result["error"] = "erreur lors de la création du compte...";
            }
        }
    }
}else {
    $result["success"] = false;
    $result["error"] = "veuillez completer tous les champs...";
}

echo json_encode($result);