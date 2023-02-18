// IK1203 Networks and Communication, Project Task 1
// Author: Roy L.

package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
    public TCPClient() {
    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {
    	// 1 byte i taget verkar funka bra!
    	byte[] data = new byte[1];
    	ByteArrayOutputStream incomingData = new ByteArrayOutputStream();
    	Socket socket = new Socket(hostname, port);
    	socket.getOutputStream().write(toServerBytes, 0, toServerBytes.length);
    	
    	while(socket.getInputStream().read(data) != -1) {
        	incomingData.write(data);
    	}

        socket.close();
    	
        return incomingData.toByteArray();
    }
    
    public byte[] askServer(String hostname, int port) throws IOException {
    	byte[] data = new byte[1];
    	ByteArrayOutputStream incomingData = new ByteArrayOutputStream();
    	Socket socket = new Socket(hostname, port);
    	
    	while(socket.getInputStream().read(data) != -1) {
    		incomingData.write(data);
    	}

    	socket.close();
    	
        return incomingData.toByteArray();
    }
}
