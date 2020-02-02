# Players

Players application allowing to send messages between the two players

# Prerequisites

In order to build and run the application you need to have Java 1.8 and Maven installed

# Installing

Hit the following command in order to build and install the application:

```
mvn clean install
```

# Running locally

## Run the following script for sending messages locally (1 process) with limit of 10 messages

```
./run_local_10.sh
```

# Running remotely

## Run the following script to start a remote player listening to messages 

```
./run_remote.sh
```

## Run the following script in a new console tab to start an initiator player for sending messages to the remote player with limit of 10 messages

```
./run_remote_initiator_10.sh
```
