echo client/server
===

"echo client/server" is an exercise in Java sockets and client/server software and an exercise in collaboration.

This project consists primarily of two objects a client (an instance of EchoClient) and a server
(an instance of EchoServer).

The client sends messages to the server which are lines of text to be typed in by the user.  The server
merely echoes the messages back to the client.  When the client sends the message "Bye", the server
sends it back and then quits.

The server and the client are actually implemented with more than one class to separate the user interface
from the logic of the programs.

This first version of echo, the server is a daemon with no user interface and the client is has a console interface.

In future versions, the server could have a console interface or a GUI interface and the client could have
a GUI interface or a console interface.
