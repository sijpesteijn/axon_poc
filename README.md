# Axon poc

* Axon server and elasticsearch is running in docker. use 
``./docker/docker-compose up``

* Start the springboot app. use
``mvn clean spring-boot:run``

* Use the ``./command-requests.http`` to send commands to app
* Use the ``./query-requests.http`` to query data from app