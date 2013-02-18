import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws IOException {

		boolean read = true;

		try {
			/* the socket for recieving and sending data
			setting up a connection with a running server
			*/
			Socket clientSocket = new Socket("192.168.2.105", 7);	
			System.out.println("Connected to "
					+ clientSocket.getRemoteSocketAddress());

			// Objects to send and recieve data from
			DataInputStream in = new DataInputStream(
					clientSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(
					clientSocket.getOutputStream());
			

			out.writeUTF("Hello from Client");
			System.out.println(in.readUTF());

			System.out.println("Please start chatting:");
			String inputString = null;
			
			// the buffer to input text via the console
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			
			// the main loop for exchanging data
			while (read) {
				try {
					// the user input is read
					inputString = input.readLine();
					// by writing "exit" the loop for exchanging data is interrupted and the client closes
					if (inputString.equals("exit")) {			
						read = false;
						System.exit(1);
					}
					
					// data is send to the server
					out.writeUTF("Client: " + inputString);
					out.flush();
					// printing the sockets input
					System.out.println(in.readUTF());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			

		} catch (UnknownHostException e) {
			System.err.println("Theres no fokkn host.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("I/O Exception");
			System.err.println(e.toString());
			System.exit(1);
		}
		
	}
}
