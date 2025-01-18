# Instructions pour exécuter les Jobs Hadoop

Ce guide explique comment configurer et exécuter les trois jobs Hadoop à partir de l'image Docker et du projet fourni. Toutes les commandes doivent être lancées depuis la racine du projet.

## Initialisation du conteneur Docker

1. **Construire l'image Docker** :
   ```bash
   docker build -t hadoop-tp3 ./hadoop-tp3-main/deploy
   ```

2. **Lancer le conteneur Docker** :
   ```bash
   docker run --rm \
     -p 8088:8088 \
     -p 9870:9870 \
     -p 9864:9864 \
     -v $(pwd)/hadoop-tp3-main/data:/data \
     -v $(pwd)/hadoop-tp3-main/jars:/jars \
     --name hadoop-container \
     hadoop-tp3
   ```

3. **Accéder au conteneur** :
   ```bash
   docker exec -it hadoop-container bash
   ```

## Compilation du projet

1. **Nettoyer et compiler le projet Maven** :
   ```bash
   mvn clean package
   ```

2. **Copier les fichiers JAR dans le conteneur Docker** :
   ```bash
   docker cp ./hadoop-tp3-main/p-collaborative-filtering-job-1/target/tpfinal-Clement_RONCOLATO_job1.jar hadoop-container:/jars/
   docker cp ./hadoop-tp3-main/p-collaborative-filtering-job-2/target/tpfinal-Clement_RONCOLATO_job2.jar hadoop-container:/jars/
   docker cp ./hadoop-tp3-main/p-collaborative-filtering-job-3/target/tpfinal-Clement_RONCOLATO_job3.jar hadoop-container:/jars/
   ```

## Exécution des Jobs Hadoop

### Job 1 : Liste de relations pour un utilisateur

1. **Charger les données d'entrée dans HDFS** :
   ```bash
   hdfs dfs -mkdir -p /input/job1
   hdfs dfs -put /data/relationships/data.txt /input/job1/
   ```

2. **Exécuter le Job 1** :
   ```bash
   hadoop jar /jars/tpfinal-Clement_RONCOLATO_job1.jar /input/job1/data.txt /output/job1
   ```

3. **Vérifier les résultats du premier et du deuxième reducer** :
   ```bash
   hdfs dfs -cat /output/job1/part-r-00000
   hdfs dfs -cat /output/job1/part-r-00001
   ```

### Job 2 : Nombre de relations communes

1. **Exécuter le Job 2** :
   ```bash
   hadoop jar /jars/tpfinal-Clement_RONCOLATO_job2.jar /output/job1 /output/job2
   ```

2. **Vérifier les résultats** :
   ```bash
   hdfs dfs -cat /output/job2/part-r-00000
   ```

### Job 3 : Recommandations

1. **Exécuter le Job 3** :
   ```bash
   hadoop jar /jars/tpfinal-Clement_RONCOLATO_job3.jar /output/job2 /output/job3
   ```

2. **Vérifier les résultats** :
   ```bash
   hdfs dfs -cat /output/job3/part-r-00000
   ```

