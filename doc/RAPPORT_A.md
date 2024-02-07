# Rapport équipe A

## 1. Point d'avancement

### Fonctionnalités réalisées

- Implémentation des règles de citadelles première édition avec un jeu de 67 cartes
- Implémentation de 4 bots joueurs :
    - Bot aléatoire : joue aléatoirement
    - Bot économe : achète les quartiers les plus chers
    - Bot dépensier : achète les quartiers le plus rapidement possible
    - Bot monarchiste : achète les quartiers nobles en priorité et choisit le rôle de roi

### Fonctionnalités non réalisées

- Pas de prise en charge d'un septième joueur

### Logs

Utilisation de log4j pour les logs. Les logs sont écrits seulement si l'argument --demo est passé au programme ou si
aucun argument n'est donné. Si l'argument --2thousands, aucun log n'est écrit. Les logs détaillent la partie comprenant
la phase de sélection des personnages, les mises à jour de la main, cité et argent des bots ainsi que l'utilisation des
pouvoirs liés aux personnages et quartiers merveilles.

En fin de partie, les logs détaillent le score des bots.

### Csv

Utilisation d'open csv pour la sauvegarde des statistiques des bots.
Chaque ligne représente les statistiques d'un bot.

### Bot Richard

// TODO

## 2. Architecture et qualité

### Architecture

L'architecture se décompose principalement en 3 packages :

- Le package cards contient principalement les classes représentant les cartes du jeu et leur pouvoir : District,
  Character, City et Hand
- Le package engine contient principalement les classes représentant le moteur du jeu : Game, Score, Statistic et
  Display
- Le package players contient principalement les classes représentant les joueurs : Player, Memory, Actions ainsi que
  les bots

Des sous packages sont également présents dans chacun de ces packages pour une meilleure organisation.
Nous avons choisi ce découpage pour une meilleure lisibilité du code et pour une meilleure séparation des
résponsabilités après avoir essayé plusieurs autres découpages. Notamment avec Player qui contenait initialement Memory
et Actions.

### JavaDoc

La JavaDoc est présente sur l'ensemble des classes et méthodes du projet. Elle est complète et à jour.
Vous la trouverez dans le dossier [doc](JavaDoc) en ouvrant l'index.html.

### Qualité du code

// TODO

## 3. Processus de développement

### Assignement des tâches

//TODO

### Gestion d'équipe

Branching strategy :

- Nous avons principalement utilisé une branche principale master et une branche de développement develop.
- Chaque fonctionnalité a été développée dans une branche dédiée à cette fonctionnalité nommée feature/featureName.
- Les refactorings et les corrections de bugs ont été développés dans des branches dédiées à ces tâches préfixé par le
  type de la tache.
- Les branches de fonctionnalités ont été merge dans develop une fois la fonctionnalité terminée.
- La branche develop a été merge dans master une fois la version définitive prête.

Github Actions :

- Nous avons utilisé Github Actions pour automatiser les tests dans le cadre de pull requests.

Chaque membre de l'équipe a eu l'occasion de valider les pull requests des autres membres de l'équipe.








