Bonjour 

mon projet android studio contient un login et un register

dans un premier temps j'ai crée la partie front sur android studio, insérer des dependances tel que volley pour appeler mon web service construite en php, j'ai autorisé aussi la connexion au web ce qui est important pour lire mon fichier

mon web service en php a été construite au format json.

NB: pour les test faudra changer le chemin d'accès de mon web service sur ma page login et register
*****************************************************************************************************************************************************************************************************************************************
la page register contient 5 edittext dans lequel l'utilisateur  insère les informations  pour s'enregistrer dans la base de donnée et d'un button. j'ai vérifié que les champs soit remplis si les champs ne le sont pas

j'ai renvoyé un message à l'utilisateur pour qu'il soit avertis, j'ai mis une condition pour laquelle le mot de passe de l'utilisateur dois etre > 6. 

dans ma partie server j'ai crée un passwordhash qui permet de hash le mot de passe dans la base de donnée et d'un password verify qui permet de le lire sur la page login.

la page acceuil j'ai pas reussi a faire le panier il me faudra travailler pour trouver des solutions a ce problème.
