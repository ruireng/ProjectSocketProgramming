// IK1203 Networks and Communication, Project Task 3
// Author: Roy L.

import java.net.*;
import java.io.*;

public class HTTPAsk {
    public static void main( String[] args) throws Exception {
        // Your code here
    	int portNumber = Integer.parseInt(args[0]);
    	boolean theFloorIsMadeOfFloor = true;
    	while(theFloorIsMadeOfFloor) {
    		try {
    			while(theFloorIsMadeOfFloor) {
    				// initiating sockets
    				ServerSocket serverSoc = new ServerSocket(portNumber);
        	    	//if(theFloorIsMadeOfFloor)
        	    		//System.out.println("running");
        			Socket incomingSocket = serverSoc.accept();
        			//System.out.println("whoa, found connection");
        			InputStream fromConnection = incomingSocket.getInputStream();
        			OutputStream theOutput = incomingSocket.getOutputStream();
        			
        			// parses the data from the connecting socket into digestible sections
        			ByteArrayOutputStream incomingData = new ByteArrayOutputStream();
        			int data = 0;
        			data = incomingSocket.getInputStream().read();
        			incomingData.write(data);
        			//System.out.println(data);
        			while(data != 13) {
        				data = incomingSocket.getInputStream().read();
        				incomingData.write(data);
        				//System.out.println(incomingData);
        			}
        			String request = incomingData.toString("UTF-8");
        			//System.out.println(request);
        			String[] parameters = request.split("[?/& ]");
        			//for(String aParameter : parameters)
        				//System.out.println(aParameter);
        			
        			// using the parsed data to make adjustments
        			String outputData = null;
        			boolean get = false;
        			boolean ask = false;
        			boolean http = false;
        			boolean oneDotOne = false;
        			for(String aParameter : parameters) {
        				if(get && ask && http & oneDotOne)
        					break;
        				else if(aParameter.equals("GET"))
        					get = true;
        				else if(aParameter.equals("ask"))
        					ask = true;
        				else if(aParameter.equals("HTTP"))
        					http = true;
        				else if(aParameter.startsWith("1.1"))
        					oneDotOne = true;
        			}
        			//System.out.println(get);
        			//System.out.println(ask);
        			//System.out.println(http);
        			//System.out.println(oneDotOne);
        			if(get && ask && http && oneDotOne) {
        				//System.out.println("kinda working??");
        				boolean shutdown = false;
        				Integer timeout = null;
        				Integer limit = null;
        				String hostname = null;
        				Integer port = null;
        				byte[] toHostname = new byte[0];
        				for(String aParameter : parameters) {
        					if(aParameter.startsWith("shutdown")) {
        						shutdown = true;
        						//System.out.println("shutdown works");
        						//System.out.println(shutdown);
        					}
        					else if(aParameter.startsWith("timeout")) {
        						String[] timeoutParams = aParameter.split("=");
        						timeout = Integer.parseInt(timeoutParams[1]);
        						//System.out.println("timeout works");
        						//System.out.println(timeout);
        					}
        					else if(aParameter.startsWith("limit")) {
        						String[] limitParams = aParameter.split("=");
        						limit = Integer.parseInt(limitParams[1]);
        						//System.out.println("limit works");
        						//System.out.println(limit);
        					}
        					else if(aParameter.startsWith("hostname")) {
        						String[] hostnameParams = aParameter.split("=");
        						hostname = hostnameParams[1];
        						//System.out.println("hostname works");
        						//System.out.println(hostname);
        					}
        					else if(aParameter.startsWith("port")) {
        						String[] portParams = aParameter.split("=");
        						port = Integer.parseInt(portParams[1]);
        						//System.out.println("port works");
        						//System.out.println(port);
        					}
        					else if(aParameter.startsWith("string")) {
        						String[] dataParams = aParameter.split("=");
        						StringBuilder sb = new StringBuilder(dataParams[1]);
        						sb.append("\n");
        						toHostname = sb.toString().getBytes();
        						//System.out.println("data works");
        						//System.out.println(toHostname);
        						//System.out.println(dataParams[1]);
        					}
        				}
        				
        				// constructing response
        				if((hostname == null) || (port == null)) {
        					//System.out.println("oaj");
        					outputData = "HTTP/1.1 400 Bad Request\r\n\r\n";
        				}
        				else {
        					try {
        						//System.out.println("woaw");
            					TCPClient external = new TCPClient(shutdown, timeout, limit);
            					byte[] externalBytes = external.askServer(hostname, port, toHostname);
            					String externalData = new String(externalBytes);
            					//System.out.println(externalData);
            					StringBuilder sb = new StringBuilder("HTTP/1.1 200 OK\r\nAccept: text\\plain\r\nAccept-Charset: utf8\r\n\r\n");
            					sb.append(externalData);
            					outputData = sb.toString();
            					//System.out.println(sb.toString());
        					}
        					catch(Exception e) {
        						outputData = "HTTP/1.1 404 Not Found\r\n";
        					}
        				}
        			}
        			else {
        				//System.out.println("fail");
        				outputData = "HTTP/1.1 400 Bad Request\r\n\r\n";
        			}
        			
        			// send the response and close
        			theOutput.write(outputData.getBytes());
        			theOutput.close();
        			serverSoc.close();
    			}
    			
    		}
    		catch(IOException e) {
    			System.out.println("e");
    		}
    	}
    }
}

