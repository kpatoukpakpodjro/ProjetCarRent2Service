# Partie 1
 - - - - 
- Coder une mini application dans le langage que vous voulez
   Programme Java pour la gestion de location de voiture.
   Nous avions 2 entités en MCD(Modèle Conceptuel de Données) : User, Car de relation ManyToMany.
  Un client peut louer plusieurs voitures et une voiture peut être louée plusieurs clients
  ( à différentes dates)
   Ce qui nous fait 3 classes au passage en MLD : User, Car, Rent
   Les données (iniatiles) sont stockées dans des fichiers json ( chemins relatifs)
   La classe JsonDataService permet de gérer la relation entre les entités et d'enregistrer
    les modifications apportées.
    # Fonctionnalités développées
        - consulter les voitures disponibles : /cars/available
        - trier les voitures disponibles par prix ( Attribut price) : /cars/available?sort=price
        - louer une voiture : /rents/rent
        - consulter les voitures qu'il a louées : myrents?userId=2
                                        : celles retournées : myrents?userId=2&returned=true
                                        : celles non encore retournées : myrents?userId=2&returned=false
        - rendre une voiture : /rents/return

   
- Créer une image Docker => faire un Dockerfile
   
- Publier l’image Docker sur le Docker Hub
   Se placer dans le dosiier Backend, puis :
  `docker login`

   `docker build -t kkpodjro/backend:last .`

   `docker tag kkpodjro/backend:last kkpodjro/backend:last`

   `docker push kkpodjro/backend:last`

- Créer un déploiement Kubernetes
   Le deploiement est créé dans le fichier deploiement.yml
- Créer un service Kubernetes 
   `kubectl create deployment backend --image=kkpodjro/backend:last`

   `kubectl get pods`

   `kubectl scale --replicas=2 deployment/backend`

   `kubectl delete pod backend-7898b8fb86-5sqtr`

   `kubectl get pods`

   `kubectl expose deployment backend --type=LoadBalancer --port=8080 --target-port=8080`

# Partie ajout du gateway
Commandes utilsées :
   `minikube delete`

   `minikube start`

   `kubectl apply -f backend-deployment.yml`

   `kubectl apply -f backend-service.yml`

   `minikube addons enable ingress`

   `kubectl get pods -n kube-system`

   `kubectl apply -f ingress.yml`

   `kubectl get ingress`

   Ouvrir c:\windows\system32\drivers\etc\hosts en tant qu'administrateur puis ajouter `127.0.0.1 backend.info`

   `minikube addons enable ingress-dns`

   `minikube tunnel`

   Ouvrir `http://backend.info/cars/available` dans le navigateur