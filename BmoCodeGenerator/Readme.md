Ce générateur de code lit l'input file nommé input.txt situé dans le projet java actuel ( ce fichier contient la description textuelle de notre diagramme de classe)
NB:les classes Pari simple , Pari Avance , Bookmaker , et Parieur n'ont pas d'ID parcequ'on ils vont l'hériter de leurs parents Utilisateur et Paris 
Il suffit de tourner ce projet java dans éclipse et tous les fichiers des classes vont etre générés automatiquement dans la racine du projet 
Chaque fichier contient la définition du classe avec toutes les importations nécessaires , ses attributs et meme la déclaration de ces méthodes.On a meme travaillé à 
gérer le cas des classes parents qui ont été générés comme abstract ,les classes filles à leur tour ont été aussi déclaré avec extends , on an meme généré les associations 
entre les tables.
Le principe de fonctionnement de ce générateur se base sur une lecture du fichier ligne par ligne et la répartition des différents données extraites sur des classes notamment
 une classe pour chacun des elements suivants : la squelete de la classe, ses attributs , ses méthodes , ses associations. 
