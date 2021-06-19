# LPC Local Procedure Call

## Choix d'implémentation

Pour l'implémentation du projet, on a utilisé:
- la mémoire partagé
- la projection de mémoire
- les mutexes
- les conditions
- les signaux
- les sockets

## Serveur simple

Le serveur simple utilise  **les mutexes** et **les conditions** pour communiquer avec le client. Pour la synchronisation entre les deux, cela se passe d’une par pour le client dans la fonction **lpc_call** et pour le serveur dans la boucle principale.

## Serveur distribué

On a introduit le **sockets** pour le serveur distribué. Cela permet de garder au maximum la même base que le serveur simple. La seul différence c’est qu’on ne fournit pas un **shm_name** générique, mais un **shm_name** + **pid du client**.

La partie avec les sockets est faite uniquement pour créer la mémoire partager sur lequel le client doit faire ses appels (c’est le processus père du serveur qu’en charge). Dès qu’une connection est effectué, le client envoie son **pid** et le processus père du crée un processus fils qui va prendre en charge les appels du client en question.

Dès que le client appel **lpc_close** Le client envoie un signal comme quoi le processus fils du serveur doit terminer également. Le processus père du serveur fait le nettoyage nécessaire pour supprimer les processus zombies de temps en temps.

## Client

Le client fait une première demande au serveur distribué pour obtenir une **shm_name** + **pid du client**. Dès que la mémoire partagé est crée, il peut effectuer des appel avec **lpc_call**.

On a 2 fonctions auxiliaires: 
- l’initialisation des arguments dans la mémoire partagé
- la mise à jour des arguments dans la mémoire partagé

Cette partie est sensible car il faut tenir compte de la taille des paramètres. Ainsi, on tient compte pour chaque paramètre, sa taille. 
Pour **lpc_string** cela se fait en 2 parties:
- **int** qui correspond à **str->slen**
- **lcp_string** qui correspond à l'objet en question


