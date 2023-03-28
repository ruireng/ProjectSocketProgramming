# IK1203 Networks and Communication - Project: Socket Programming
<!--This is a repository for a project in the course IK1203 Networks and Communcation spring term 2023. The repository should contain all tasks for the project and will be updated during the course of the project. This repository is made as way to keep all tasks and their source code collected in one place.

This project was made by Roy L.-->

This is a project made during the course IK1203 Networks and Communication, where a concurrent server was made using Java's Socket and ServerSocket classes. The server takes inputted parameter data and sends these to another specified host. The received data from the other host will then be displayed on a web browser. 
  
This project consists of
 - task 1: creating TCPClient, which is a TCP socket that sends and receives data.
 - task 2: extending TCPClient, so that it can recognize parameters such as `shutdown` (shuts TCP connection only in one way (client to server) after sending data), `timeout` (shuts down the connection if the server does not respond within the time limit) and `limit` (shuts down the connection after receiving a set amount of bytes).
 - task 3: creating HTTPAsk, which is the server of this project. It uses task 1 and 2 and reads the HTTP GET request in order to send data to other websites, receive the data and display it on the web browser.
 - task 4: creating ConcHTTPAsk, which is an extension of task 3 where support for concurrency is added.

## Running the server
 1. Make sure you have Java installed on your machine.
 2. Download the files from the `Task4` folder in this repository.
 3. In your computer's command line, run `javac ConcHTTPAsk.java` to compile the code.
 4. Now, run the code with `java ConcHTTPAsk x`, where x is your computer's port number the server will be hosted from. Example: `java ConcHTTPAsk 7777`.
 5. You should now be able to access the server from your web browser. In the address bar, search `http://localhost:x` (where x is the port number chosen in the step above) to access the server.
 6. The server does not really do anything at this point. It only works when you input some extra parameters in the address bar. 
    - Mandatory parameters: `hostname` (the name of the host you want to access) and `port` (the port needed to reach the host). 
    - Optional parameters: `string` (a string that will be sent to the specified host), `shutdown` (if the TCP connection from this server to the specified host should shut down after sending data. If the parameter is added, `shutdown` becomes `true`. If not, it will be `false` as default) and `limit` (shuts the TCP connection both ways after receiving a specified amount of bytes from the specified host).
    An example: `http://localhost:7777/ask?hostname=whois.iis.se&port=43&timeout=300&string=kth.se`.
