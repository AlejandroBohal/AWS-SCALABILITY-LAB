docker-compose down
echo Y | docker volume prune 
echo Y | docker system prune -a
docker-compose up -d --scale web=3
docker-compose push