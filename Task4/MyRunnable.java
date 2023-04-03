// IK1203 Networks and Communication, Project Task 4
// Author: Roy L.

import java.net.*;
import java.io.*;

public class MyRunnable implements Runnable {
	private Socket incomingSocket;
	
	public MyRunnable(Socket oneClient) {
		this.incomingSocket = oneClient;
	}
	
	public void run() {
		// server code here
		try {
			InputStream fromConnection = this.incomingSocket.getInputStream();
			OutputStream theOutput = this.incomingSocket.getOutputStream();
			
			// parses the data from the connecting socket into digestible sections
			ByteArrayOutputStream incomingData = new ByteArrayOutputStream();
			int data = 0;
			data = this.incomingSocket.getInputStream().read();
			incomingData.write(data);
		
			while(data != 13) {
				data = this.incomingSocket.getInputStream().read();
				incomingData.write(data);
			}
			String request = incomingData.toString("UTF-8");
			String[] parameters = request.split(" ");
			
			// using the parsed data to make adjustments
			String outputData = null;
			boolean get = false;
			boolean ask = false;
			boolean http = false;
			
			if(parameters[0].equals("GET"))
				get = true;
			if(parameters[1].startsWith("/ask?"))
				ask = true;
			if(parameters[2].contains("HTTP/1.1"))
				http = true;
			
			if(get && ask && http) {
				parameters = request.split("[?/& ]");
				boolean shutdown = false;
				Integer timeout = null;
				Integer limit = null;
				String hostname = null;
				Integer port = null;
				byte[] toHostname = new byte[0];
				for(String aParameter : parameters) {
					if(aParameter.startsWith("shutdown")) {
						shutdown = true;
					}
					else if(aParameter.startsWith("timeout")) {
						String[] timeoutParams = aParameter.split("=");
						timeout = Integer.parseInt(timeoutParams[1]);
					}
					else if(aParameter.startsWith("limit")) {
						String[] limitParams = aParameter.split("=");
						limit = Integer.parseInt(limitParams[1]);
					}
					else if(aParameter.startsWith("hostname")) {
						String[] hostnameParams = aParameter.split("=");
						hostname = hostnameParams[1];
					}
					else if(aParameter.startsWith("port")) {
						String[] portParams = aParameter.split("=");
						port = Integer.parseInt(portParams[1]);
					}
					else if(aParameter.startsWith("string")) {
						String[] dataParams = aParameter.split("=");
						StringBuilder sb = new StringBuilder(dataParams[1]);
						sb.append("\n");
						toHostname = sb.toString().getBytes();
					}
				}
				
				// constructing response
				if((hostname == null) || (port == null)) {
					outputData = "HTTP/1.1 400 Bad Request\r\n\r\n";
				}
				else {
					try {
						TCPClient external = new TCPClient(shutdown, timeout, limit);
						byte[] externalBytes = external.askServer(hostname, port, toHostname);
						String externalData = new String(externalBytes);
						StringBuilder sb = new StringBuilder("HTTP/1.1 200 OK\r\nAccept: text\\plain\r\nAccept-Charset: utf8\r\n\r\n");
						sb.append(externalData);
						outputData = sb.toString();
					}
					catch(Exception e) {
						outputData = "HTTP/1.1 404 Not Found\r\n\r\n";
					}
				}
			}
			else {
				if(!get || !http)
					outputData = "HTTP/1.1 400 Bad Request\r\n\r\n";
				else
					outputData = "HTTP/1.1 404 Not Found\r\n\r\n";
			}
			
			// send the response and close
			theOutput.write(outputData.getBytes());
			theOutput.close();
		}
		
		catch(Exception e) {
			System.out.println("e");
		}
	}
}
