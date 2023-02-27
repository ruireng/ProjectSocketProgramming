// IK1203 Networks and Communication, Project Task 3
// Author: Roy L.

import java.net.*;
import java.io.*;

public class TCPClient {
    private boolean shutdown;				// true if the client should be the one to shut down connection
    private Integer timeout;				// optional timeout limit in milliseconds
    private Integer limit;					// optional byte limit from server
	
    public TCPClient(boolean shutdown, Integer timeout, Integer limit) {
    	this.shutdown = shutdown;
    	this.timeout = timeout;
    	this.limit = limit;
    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {
    	byte[] data = new byte[1];
    	
    	int timer;
    	if(timeout == null)					// ... then no timeout
    		timer = 0;
    	else
    		timer = timeout;
    					// ... then there is a limit
    	int byteLimit;
    	if(limit != null) {
    		byteLimit = limit;
    	}
    	else {
    		byteLimit = -1;					// this should not be able to reach 0 since we decrement the limit every loop
    	}

    	ByteArrayOutputStream incomingData = new ByteArrayOutputStream();
    	Socket socket = new Socket(hostname, port);
    	socket.getOutputStream().write(toServerBytes, 0, toServerBytes.length);
    	// if shutdown is true, then we can shut down our own output stream to the server
    	if(this.shutdown) {
    		socket.shutdownOutput(); 
    	}
    	
    	socket.setSoTimeout(timer);
    	
    	try {
    		while((socket.getInputStream().read(data) != -1) && (byteLimit != 0)) {
    			incomingData.write(data);
    			byteLimit--;
    		}
    		socket.close();
    		
    		return incomingData.toByteArray();
    	}
    	// if timeout
    	catch(SocketTimeoutException exc) {
    		socket.close();

    		return incomingData.toByteArray();
    	}
    	// if shutdown parameter is true
    
    }
    
    public byte[] askServer(String hostname, int port) throws IOException {
    	byte[] data = new byte[1];
    	
    	int timer;
    	if(timeout == null)					// ... then no timeout
    		timer = 0;
    	else
    		timer = timeout;
    					// ... then there is a limit
    	int byteLimit;
    	if(limit != null) {
    		byteLimit = limit;
    	}
    	else {
    		byteLimit = -1;					// this should not be able to reach 0 since we decrement the limit every loop
    	}

    	ByteArrayOutputStream incomingData = new ByteArrayOutputStream();
    	Socket socket = new Socket(hostname, port);
    	socket.setSoTimeout(timer);
    	
    	// if shutdown is true, then we can shut down our own output to the server
    	if(this.shutdown) {
    		socket.shutdownOutput();
    	}
    		
    	try {
    		while((socket.getInputStream().read(data) != -1) && (byteLimit != 0)) {
    			incomingData.write(data);
    			byteLimit--;
    		}
    		socket.close();
    		
    		return incomingData.toByteArray();
    	}
    	// if timeout
    	catch(SocketTimeoutException exc) {
    		socket.close();

    		return incomingData.toByteArray();
    	}
    }
}
