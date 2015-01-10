package TCPSpelling;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.SortedSet;
import java.util.StringTokenizer;
import TCPSpelling.WordList;

/* The class TCPSpellingServer takes input from the user such as name of the database file where 
 * the word is to be searched and a port number for the server to receive connections This class will 
 * search the database for the entered word if it finds the word then the server sends a message 
 * to the server indicating that the word is present. If the word is not found then the server searches 
 * for the closest words and returns them and if it can't find any close word then the server will
 * return a message saying that the word is not found and also there are no closest matches  */

public class TCPSpellingServer {
	public static void main(String args[]) throws Exception {

		System.out.println("             ********************************");
		System.out
				.println("*************WelCome to WordList_database*****************");
		System.out.println("             ********************************");
		/* creating a ServerSocket object and initializing it to null*/
		ServerSocket serverSocket = null;
		String fName = "";
		/* Entering a file nameExample:- "/Users/sneha/Downloads/WordList_database.txt"*/
		System.out
				.println("Please Enter The File name with Complete filePath  \n\n");
		/* checks whether the user entered file name is valid and repeats the loop until a valid name is entered*/
		while (true) {
			try {
				BufferedReader fileName = new BufferedReader(
						new InputStreamReader(System.in));
				/*Read the entered file name and store it in a string variable "fname"*/
				fName = fileName.readLine().trim();
				File f = new File(fName);
				/* Check if the file exists and is valid else send a message to enter a valid file name.
				 * If the file name given is invalid then, the server repeatedly asks to enter the valid file */
				if (f.exists()) {
					// fName = fileName.readLine();
					System.out.println("fName=" + fName);
					break;
				} else {
					System.out
							.println("Please Enter Valid  File name with full path example [C:\\work\\afdb\\k2s\\heroines.txt] \n ");

				}
			} catch (Exception e) {
				System.out
						.println("Please Enter Valid  File name with full path example [C:\\work\\afdb\\k2s\\heroines.txt] \n ");
			}

		}
		System.out
				.println("Please Enter The Port Number to Start the Server\n");
		/* Check repeatedly if the port number is a valid 4 digit number greater than 1023 */
		while (true) {
			try {

				BufferedReader serverPort = new BufferedReader(
						new InputStreamReader(System.in));

				int PortNum = Integer.parseInt(serverPort.readLine().trim());
				serverSocket = new ServerSocket(PortNum);

				break;
			} catch (Exception e) {
				System.out
						.println("Please Enter Valid  Port Number with 4 or less digits ONLY \n");
			}

		}

		System.out
				.println("WordList Database Server is Ready to Serve Requests from Clients");

		byte[] receiveData ;
		byte[] sendData ;
		/* call the WordList constructor with the filename as the parameter*/
		WordList WL = new WordList(fName);
		//Socket socket = serverSocket.accept();
		while (true) {
			/* Accept the connections from the client*/
			Socket socket = serverSocket.accept();
			receiveData = new byte[1024];
			sendData = new byte[1024];
			String responseString = new String();

			try {
				/* Accept the word sent by the client and stores it in the string variable "sentence"*/
				BufferedReader input = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));

				String sentence = input.readLine();
				System.out.println("REQUEST RECEIVED: " + sentence);
				/* dividing the string into two tokens where the first token is a number and the second 
				 * token is the query word entered by the user. If user doesn't enter a query word preceded by an integer
				 * greater then '0' then a message to enter a valid query word is placed in the response string and sent to the client  */
				StringTokenizer st = new StringTokenizer(sentence.trim(), " ");
				while (st.hasMoreTokens()) {
					
					try {
						String number = st.nextToken();
						String word = st.nextToken();
						if (Integer.parseInt(number) > 0) {
							/* calling the WordList constructor which checks whether the word is present in the database 
							 * if the word entered is found then the message found is returned in the response string*/
							if (WL.isInList(word)) {
								responseString = responseString
										+ "The word ["
										+ word
										+ "] you  Entered is Found in the Database\n";
							} 
							/* calling the getCloseWords method from the WordList class as the exact word is not found then,
							 *  check for the closest word list if it is empty then, the word not found message is returned in the response string */
							else {

								SortedSet<String> closeWords = WL
										.getCloseWords(word);
								if (closeWords.isEmpty()) {
									responseString = responseString
											+ "I could not find The word ["
											+ word
											+ "] and  has no closes match too  Pl try again!!!:(";
								} 
								/* If exact match to the word is not found then if getcloseWords method gives a non-empty 
								 * word list then the closest word list is returned to the response string*/
								else {
									responseString = responseString
											+ "I could not find The word ["
											+ word
											+ "] and These are the colsest words i found for you.\n";
									for (int i = 0; i < closeWords.size(); i++) {
										responseString = responseString
												+ closeWords.first() + "\n";
										closeWords.remove(word);

									}
								}

							}
						} 
						/* If user doesn't enter a word preceded by an integer greater than '0' then the message 
						 * enter a valid string is sent in the response string*/
						else{
							responseString = " INVALID \n Every word should preceed with an Integer Greater than zero";
							break ;
						}

					} catch (Exception e) {
						responseString = " INVALID \n Every word should preceed with an Integer Greater than zero";
						break ;
					}
				}

				System.out.println(responseString);
				/* Getting the current time of the server and sending it to the client along with the responseString */
				String capitalizedSentence = System.currentTimeMillis()
						+ responseString + "\nGOODBYE\n";
				PrintWriter out = new PrintWriter(socket.getOutputStream(),
						true);
				out.println(capitalizedSentence);

			} catch(Exception e) {}finally {
				/* Close the socket after serving the client requests. The word Quit will end the operation of 
				 * serving the client*/
				 socket.close();
			}
		}
	}
	
}


