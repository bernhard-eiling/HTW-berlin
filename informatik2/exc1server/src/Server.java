import java.io.*;
import java.net.*;

public class Server {

	public static void main(String[] args) {
		
		boolean write = true;

		try {
			
			// ServerSocket for revieving and Sending Data
			// waits for a client to connect
			ServerSocket serverSocket = new ServerSocket(7);
			// after connection server proceeds
			Socket server = serverSocket.accept();
			System.out.println("Connected to "
					+ server.getRemoteSocketAddress());
			// setting up out and inputs for sending and recieving data
			DataInputStream in = new DataInputStream(server.getInputStream());
			DataOutputStream out = new DataOutputStream(
					server.getOutputStream());
			System.out.println("incoming: " + in.readUTF());
			out.writeUTF("Hello from Server");
			System.out.println("Please start chatting:");
			String inputString = null;
			// Buffer for getting user input
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));

			//main loop for sending and recieving data
			while (write) {
				
				try {
					// reading input from bufer to string
					inputString = input.readLine();
					// checking for keyword "exit" to stop the loop
					if (inputString.equals("exit")) {
						write = false;
						
						System.exit(1);
					}
					// writing the user input to the output of the socket
					out.writeUTF("Server: " + inputString);
					out.flush();
					// printing the input of the socket
					System.out.println(in.readUTF());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		} catch (IOException e) {
			System.err.println(e.toString());
			System.exit(1);
		}

	}

}
