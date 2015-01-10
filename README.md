# TCPSpellingClientServer_TCSS558
TCP communication between client and server

A simple client-server application that communicates using TCP sockets, using Java, implemented a spell-server that returns exact match or close match words.

Usage:- 
TCPSpellingClient.java
TCPSpellingClient takes input from the user such as server IP address or host name, port number and a word that has to be searched in the WordList database present at the server

For Ex:- javac TCPSpellingClient.java
 TCPSpellingClient.java 198.1.1.0 localhost 4440 SearchWord
 
Run the server program TCPSpellingServer.javawhen the client is running
 
The class TCPSpellingServer takes input from the user such as name of the database file where 
the word is to be searched and a port number for the server to receive connections This class will 
search the database for the entered word if it finds the word then the server sends a message 
to the server indicating that the word is present. If the word is not found then the server searches 
for the closest words and returns them and if it can't find any close word then the server will
return a message saying that the word is not found and also there are no closest matches 
 
TCPSpellingServer.java 4440 AbsolutePathOftheWordListDataFile

