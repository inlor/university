# Visualisation d'algorithme

Le projet permet de visualiser les algorithmes de tri classiques à l'aide de la bibliothèque JavaFx.

## Fonctionnalités
L'application permet de voir les comportements des tris qui peuvent être effectuées étape par étape, mais aussi animé par une animation.
Si on prend un tableau de dix d'entiers, on peut remarquer leur deplacement qui est intuitif car il y a deux moyens pour l'utilisateur de comparer les blocs: par taille et couleur.

Le projet suit une architecture MVC:


└───src
    ├───configuration
    ├───controller
    ├───model
    │   └───sort
    ├───resources
    ├───style
    ├───util
    └───view
        └───component
            

## Prise en main
Les fonctionnalités de l'application sont assez intuitives. Des messages au survol sont indiqués.

### Prérequis

Sachant que la version 11 de java ne fournit pas par défaut JavaFx il faut l'installer par soit même en suivant ce [lien](https://openjfx.io/openjfx-docs). Lors du développement du projet, cela a été la version 10 de Java qui a été utilisé. Ceci importe par défaut la bibliothèque JavaFx.


### Installation

1. Il faut clonner le dépôt depuis ce [lien](https://gogs.script.univ-paris-diderot.fr/hhamil29/PI4).

2. Se rendre dans le répertoire racine et accéder le fichier ```src```du projet et executer la commande suivante: 
```javac -d . Main.java ; java Main```

3. En cas de modification du code source il est conseillé de supprimer récursivement tous les fichier ```.class``` du projet. Cela évite des potentiels bugs. 

### Authors

* **Hind HAMILA** - *Création et implémentation des classes de tri.*
* **Joris IDJELLIDAINE** - *Logique de la visualisation et fonctionnalitées.*
* **Lucian PETIC** - *Interface graphique JavaFX, liaison entre le modèle et la vue.*
