# PW6-PARSONS

## Introduction

Le projet est composé de deux parties principales : 
- Partie backend basé sur Symfony 5
- Partie frontend basé sur Angular 9

## Requirements

Pour lancer correctement le projet, il est nécessaire d'installer les modules suivants :
```sh
$ sudo apt install php7.2-cli
$ sudo apt-get install composer
$ wget https://get.symfony.com/cli/installer -O - | bash
$ sudo apt-get install nodejs #v12.16
```

### Config

Il est aussi nécessaire de modifier l’accès à la base de données dans le fichier ```.env``` de l’API.
Il est important que la partie API soit démarré sur le port 8000, dans le cas contraire, il faudrait modifier le fichier d'environnement d'Angular ```environment.ts```.

Le projet a été testé sur les machines de l'UFR et la partie serveur du projet semble fonctionner normalement.

### Install

Le projet comporte de diverses dépendances qui peuvent être installée grâce à composer et npm.
Les commandes suivantes permettent d'installer les modules nécessaires.

```sh
$ make require
```

Il faut répondre "yes" à toutes les demandes du terminal, cette commande sert à peupler la base de données.
```sh
$ make install
```

### Launch
Il faudra ensuite lancer 2 serveurs en local.

Dans un premier terminal, effectuez :

```sh
$ make server
```

Dans un second terminal, effectuez :
```sh
$ make ui
```

## General

Voici les identifiants d'exemple pour la demo.

| Email               | Password |
|:-------------------:|:--------:|
| teacher@parsons.com | 123456   |
| student@parsons.com | 123456   |

## Development

Les resources principales utilisées pour la réalisation du projet ont été les documentations des technologies présente dans le projet, mais également des forum tel que Symfony Casts ou Angular University.

La principale raison pour laquelle nous avons fait le choix d'utiliser Angular est qu'il s'agit d'une technologie très moderne et populaire.

### Serveur
On a utilisé :
- les entités ainsi que les migrations
- les fixtures pour charger la base de données avec des données fictives
de la librairie API Plateforme, pour générer les contrôleurs automatiquement à partir des entités
- les data persister pour intervenir sur les évènements des contrôleurs générés
- le JWT pour gérer l'authentification avec la partie frontend
- le logiciel Postman pour tester les routes avec différentes méthodes (GET, POST …)

### Client
On a utilisé :
- les composants Material
- le drag and drop fournir par défaut 
- les ```Observable``` pour récupérer sous format JSON les données grâce aux requêtes http
- le système de ```guard``` pour gérer les rôles

### Features

Il est possible de s'inscrire et de se connecter en tant qu'étudiant où enseignant.

Les enseignants ont la possibilité de créer, éditer, supprimer des cours et des exercices. De plus, il est possible de voir la progression et le nombre des tentatives par étudiant.

Les étudiants ont la possibilité de s'inscrire à des cours et de faire les exercices de type parson.


### Difficulties

Il n'aura pas été facile de mettre en place une API REST. Nous avons eu affaire avec des nouvelles notions ainsi qu'à de nouvelles technologies. Le temps de s'adapter et de bien comprendre comment structurer le projet aura été une des parties qui nous a pris le plus de temps.

Une première difficulté a été de réfléchir à comment relier le backend avec le frontend. De plus, lors de sa mise en place, JWT nous a posé quelques problèmes de configuration. Nous avons bien eu le temps de découvrir l'ensemble des fichiers ```.yaml``` de Symfony.

Deuxièmement, la partie parson de l'application nous a fait beaucoup réfléchir. Nous avons trouvé la librairie ```js-parsons``` et avons regardé son mode d'emploi, ce qui nous a donné quelques pistes sur la route à suivre pour cette partie.

### External code
Les fichiers dont nous ne sommes PAS autheur sont :
- ```SwaggerDecorator.php``` pour avoir une interface pour les routes.
- ```auth.service.ts``` comporte des références externes, mais nous l'avons adapté à notre situation.

### Possible extensions
Avec l'architecture actuelle, il serait facile d'ajouter des extensions comme : 
- système des commentaires
- ajout des nouveaux types d'exercices