# IK1203 Networks and Communication - Project: Socket Programming
<!--This is a repository for a project in the course IK1203 Networks and Communcation spring term 2023. The repository should contain all tasks for the project and will be updated during the course of the project. This repository is made as way to keep all tasks and their source code collected in one place.

This project was made by Roy L.-->

This is a project made during the course IK1203 Networks and Communication, where a concurrent server was made using Java's Socket and ServerSocket classes.  
This project consists of
 - task 1: creating TCPClient, which is a TCP socket that sends and receives data.
 - task 2: extending TCPClient, so that it can recognize parameters such as `shutdown` (shuts TCP connection after sending data), `timeout` (shuts down the connection if the server does not respond within the time limit) and `limit` (shuts down the connection after receiving a set amount of bytes).
 - task 3: creating HTTPAsk, which is the server of this project. It uses task 1 and 2 and reads the HTTP GET request in order to send data to other websites, receive the data and display it on the web browser.
 - task 4: creating ConcHTTPAsk, which is an extension of task 3 where support for concurrency is added.
