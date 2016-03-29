# README #

Ce projet contient les sources du plugin EverAPI.

### Installation Git ###

1. `git clone git@github.com:EverCraft/EverAPI.git`
3. `cd EverAPI`

### Installation Gradle ###

1. `gradle`
2. `gradle --refresh-dependencies`
3. `gradle install build`
4. `gradle eclipse`

### Installation Eclipse ###
1. File > Import > Existing Projects into Workspace
2. Select root directory : sélectionner le répertoire du dossier
3. Finish
4. Clique droit sur le nom du projet > Configure > Convert to Gradle
5. Clique droit sur le nom du projet > Run As > Gradle Build...
6. Ajouter dans Type tacks : install build
7. Cliquer sur Apply 
8. Cliquer sur Run

### Mettre à jour les dépendances ###
1. `gradle --refresh-dependencies`

ou

1. Clique droit sur le nom du projet > Gradle > Refresh All

### Télécharger les derniers modifications ###
1. `git pull origin master`

ou

1. Clique droit sur le nom du projet > Team > Synchronize Workspace
2. Clique sur un élément > Pull 

### Envoyer les derniers modifications ###
1. `git add *`
2. `git commit -m "<message>"`

ou

1. Clique droit sur le nom du projet > Team > Commit
2. Mettre un message dans "Commit message"
3. Sélectionner les fichiers
4. Cliquer sur "Commit and push"

### Compiler le plugin ###
1. `gradle build`

ou

1. Clique droit sur le nom du projet > Run As > Gradle Build
