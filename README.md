# Lecteur Multi

**Lecteur Multi** est une application Android simple permettant aux utilisateurs de lire des fichiers audio et vidéo depuis leur appareil. L'application prend en charge la gestion des playlists, la lecture aléatoire (shuffle), la répétition, et l'affichage des métadonnées des fichiers (titre, artiste).

## Fonctionnalités

- **Lecture de fichiers audio et vidéo** : Prise en charge des formats audio et vidéo, avec sélection de fichiers depuis le stockage local.
- **Gestion des playlists** : Ajoutez des fichiers à une playlist et parcourez facilement les éléments pour une lecture fluide.
- **Contrôles de lecture** : Play, pause, piste suivante, piste précédente, lecture en boucle et lecture aléatoire.
- **Affichage des métadonnées** : L'application affiche le titre et l'artiste (s'ils sont disponibles) pour chaque média joué.
- **Progression en temps réel** : Suivi de la progression de la lecture via une barre de progression mise à jour en temps réel.

## Prérequis

Avant de lancer le projet, assurez-vous d'avoir les éléments suivants installés :

- [Android Studio](https://developer.android.com/studio)
- Un appareil Android ou un émulateur avec **Android 5.0** (API 21) ou plus récent.

## Installation

1. Clonez ce dépôt sur votre machine locale :
   ```bash
   git clone https://github.com/votreutilisateur/LecteurMulti.git
   ```

2. Ouvrez le projet dans Android Studio :
   - Lancez Android Studio et sélectionnez **"Open an existing project"**.
   - Naviguez vers le dossier où vous avez cloné le projet et sélectionnez-le.
   - Synchronisez Gradle en cliquant sur **"Sync Now"** si nécessaire.

3. Connectez un appareil Android ou configurez un émulateur pour lancer l'application.

4. Cliquez sur le bouton **"Run"** dans Android Studio pour installer et exécuter l'application.

## Utilisation

1. **Lancer l'application** : Dès l'ouverture, l'application demandera l'autorisation d'accéder aux fichiers multimédias sur votre appareil.

2. **Sélectionner un média** : Utilisez le bouton **"Ajouter un fichier"** pour sélectionner des fichiers audio ou vidéo à partir de votre stockage.

3. **Contrôles de lecture** :
   - **Lecture/Pause** : Appuyez sur l'icône **Play/Pause** pour démarrer ou arrêter la lecture.
   - **Piste suivante** : Passez à la piste suivante avec le bouton **Forward**.
   - **Piste précédente** : Revenez à la piste précédente avec le bouton **Backward**.
   - **Lecture en boucle** : Activez/désactivez la répétition avec l'icône **Repeat**.
   - **Lecture aléatoire** : Mélangez les pistes avec l'icône **Shuffle**.

4. **Gérer la playlist** : Utilisez le bouton **"Playlist"** pour ouvrir une boîte de dialogue et choisir un fichier à lire dans la playlist.

## Permissions

L'application requiert l'autorisation d'accéder au stockage pour lire les fichiers multimédias. Si cette autorisation est refusée, l'application affichera un dialogue expliquant pourquoi elle est nécessaire et vous invitera à accorder l'autorisation à nouveau.

## Améliorations futures

- Ajout de la gestion des sous-titres pour les vidéos.
- Lecture en streaming à partir d'URL distantes.
- Support de plus de formats de fichiers multimédias.
- Amélioration de l'interface utilisateur avec des illustrations et des métadonnées avancées.

## Contribuer

Les contributions sont les bienvenues ! Veuillez suivre ces étapes pour contribuer :

1. Forker le dépôt.
2. Créer une branche pour vos modifications :
   ```bash
   git checkout -b feature-xyz
   ```
3. Effectuer vos changements et les valider :
   ```bash
   git commit -m "Ajouter la fonctionnalité xyz"
   ```
4. Pousser vos modifications :
   ```bash
   git push origin feature-xyz
   ```
5. Ouvrir une Pull Request dans le dépôt principal.

## Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.
