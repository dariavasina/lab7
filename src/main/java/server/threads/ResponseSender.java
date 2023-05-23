package server.threads;

import common.networkStructures.Response;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ResponseSender extends Thread {
    private ObjectOutputStream out;
    private final Response response;
    //private Socket clientSocket;

    public ResponseSender(ObjectOutputStream out, Response response) {
        this.out = out;
        this.response = response;
    }

//    public ResponseSender(Socket clientSocket, Response response) {
//        this.clientSocket = clientSocket;
//        this.response = response;
//    }

    @Override
    public void run() {
        try {
            out.writeObject(response);
            out.flush();
        } catch (IOException e) {
            System.err.println("Error sending response: " + e.getMessage());
        }
    }
}