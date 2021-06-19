# PW6-MemoColor

## Deposit

Le dépôt se trouve sur [gaufre.](https://gaufre.informatique.univ-paris-diderot.fr/petic/pw6-memocolor) Si vous souhaitez voir le rythme du développement, je vous invite à jeter un coup d'oeil.

## Config

La configuration est définie dans le fichier: ```config/config.json``` et est préconfiguré avec le base de donnée de l’UFR.

## Install
L'installation des packages:
```sh
npm install
```
L'installation de la base de donnée et l'importation des données(la bd a déjà était chargé sur lampe):
```sh
make install-database-ufr
```
Normalement il ne doit pas y avoir de problème pour l’installation.

Il serait possible d'installer directement avec  ```make install``` mais la commande globale ```npx sequelize``` n’est pas disponible sur les machines de l’UFR.

## Launch 

Lancement du serveur: 
```sh
make run 
```

## Documentation

### General
Durant le développement du site web, je me suis beaucoup inspiré  des mes connaissances du framework **Laravel** que j’avais utilisé pour des projets personnels. Ainsi j’ai mis en place les notions suivantes: 

- les migrations (bien pratique pour éviter les commandes sql)
- les seeders (pour charger la base de donnée avec des données)
- l'architecture MVC grâce à ```express```
- les contrôleurs (pour l'organisation du code)
- les middlewares (pour les questions d'accès)
- utilisation d'un ORM (Object-relational mapping)

### Users
Vous pouvez vous connecter avec ces comptes, ou en créer d'autres.

| username | password|
|:--------:|:-------:|
| Alex     | psw     |
| lulu     | psw     |

### Features

Toutes les fonctionnalités demandé dans le sujet ont étaient développée à par la mise à jour des droits (de lecture & écriture) et le partage après création du mémo.

J'ai mis en place un petit système de couleur, ou l'utilisateur peut attribuer une couleur à un mémo.

Le partage et possible lors de la création du mémo en définissant les droits (1 pour lecture & 2 pour lecture et écriture) et la personne qui a accès.


### Packages

J'ai utilisé les packages suivants pour la base du projet:
- ```body-parser```
- ```express```
- ```ejs```

Pour la validation des données pour l'inscription j'ai eu recours à ```express-validator```. Mais également ```js-md5``` pour crypter les mots de passe.

Pour l'utilisation des variables de session: ```express-session```.

Pour changer la base de donnée avec des données: ```lorem-ipsum``` mais on aurait pu opter pour ```faker```.

Pour pouvoir nommer les routes : ```named-routes```.

L'ORM: ```sequelize``` qui a besoin de: ```mysql``` et ```mysql2```.

J'ai utilisé le framework ```bootstrap``` pour la vue. Cependant j'ai utilisé la technologie ```sass``` pour générer du  ```css```. Cela a été particulièrement utile pour éviter les répétitions du code, mais aussi pour les opérateurs comme if for etc...  

```nodemon``` pour avoir le serveur qui tourne en continue.


### Difficulties

La partie la plus dure cela a été la compréhension de la documentation des packages.
Spécialement pour l’ORM ```sequelize``` que j’ai utilisé. Cela m’a demandé beaucoup de temps  pour le faire fonctionner correctement malgré le fait que j’avais l’idée de comment ça marchait. 


### Extern code

Le code qui ne m'appartient pas, c'est le fichier: ```app/models/index.js``` qui fait la connexion à la base de donné et fait qui initialise les modèles.

### Generated code

```public/css/style.css``` puisque c'est du code généré à partir du code sass ```public/scss/style.scss``` 


### Possible functionalities to add

D'autres pistes de développement seraient:
- accès en lecture (tout public) via un lien partagé en utilisant un token
- personnalisation des couleurs, de sorte à faire des catégories 


### Final word
J'espère que vous avez apprécié les fonctionnalité de MemoColor !
En espérant vous voir de nouveau sur notre plateforme. Bye!


