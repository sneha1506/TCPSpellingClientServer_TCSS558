package TCPSpelling;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
/* The class TCPSpellingClient takes input from the user such as server IP address or host name,
 *  port number and a word that has to be searched in the WordList database present at the 
 *  server.*/

public class TCPSpellingClient {
	public static void main(String args[]) throws Exception {

		String serverName;
		int portNum = 0;
		/* user should enter the server's IP address or hostname if we run both client and server on the 
		 * same machine then, the server and client would be the "localhost" and the IP address would be a 
		 * 32-bit number such as "192.168.1.8"*/
		System.out.println("Please Enter The Server's IP Address or hostname to Connect \n");

		/* serverIP and serverName variables are used to read the IP address or servername from user  */
		BufferedReader serverIP = new BufferedReader(new InputStreamReader(
				System.in));

		serverName = serverIP.readLine().trim();

		System.out.println("Please Enter The Port Number of the Server\n");

		/* Checks whether the entered portnumber is a valid(4 digit number greater than 1023) if not, it throws an exception */
		while (true) {
			try {

				BufferedReader serverPort = new BufferedReader(
						new InputStreamReader(System.in));

				portNum = Integer.parseInt(serverPort.readLine().trim());
				break;

			} catch (Exception e) {
				System.out
						.println("Please Enter Valid  Port Number with 4 or less digits ONLY \n");
			}

		}
		/* The client repeatedly accepts a word from the user and prints the message sent by the server
		 * which checks whether a given word is present in the database if not it returns the closest 
		 * words to the entered word*/
		while (true) {
			/*Creating a socket at client side of the type "Socket" which initiates a connection to the server 
			with a port number and serverName*/
			Socket socket = new Socket(serverName, portNum);
			System.out
					.println("\n\nPlease enter word(s) you wish or Enter[  Quit  ] to leave the server\n\n");
			/* Reads the user entered word and converts it into a string*/
			BufferedReader inFromUser = new BufferedReader(
					new InputStreamReader(System.in));
			String sentence = inFromUser.readLine();
			/* If user enters "Quit" then the client quits and the operation*/
			if (sentence.trim().equalsIgnoreCase("Quit"))
				break;
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(sentence + "\n");
			BufferedReader input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String line;
			//String response = "";
			System.out.println("From WordList Data Base:");
			/* Printing the response received from the server*/
			while ((line = input.readLine()) != null) {
				if (line.equals("GOODBYE"))
					break;
				System.out.println(line);

			}
			/* Close the created socket after completion of the operation*/
			socket.close();
		}

	}
}

