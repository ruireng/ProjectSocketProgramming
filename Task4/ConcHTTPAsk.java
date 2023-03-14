// IK1203 Networks and Communication, Project Task 4
// Author: Roy L.

import java.net.*;
import java.io.*;

public class ConcHTTPAsk {
	public static void main( String[] args) throws Exception {
		boolean theFloorIsMadeOfFloor = true;
		
		try {
			int portNumber = Integer.parseInt(args[0]);
			ServerSocket serverSoc = new ServerSocket(portNumber);
			while(theFloorIsMadeOfFloor) {
				Socket incomingSocket = serverSoc.accept();
				MyRunnable run = new MyRunnable(incomingSocket);
				Thread oneThread = new Thread(run);
				oneThread.start();
			}
		}
		catch(Exception e) {
			System.out.println("maybe the floor wasn't made of floor...");
		}
	}
}