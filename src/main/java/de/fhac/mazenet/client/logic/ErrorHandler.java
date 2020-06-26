package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.generated.MazeCom;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Lukas Szimtenings on 6/25/2020.
 */
public class ErrorHandler extends MessageHandler
{
    private Socket serverSocket;

    public ErrorHandler(Socket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void handle(MazeCom message)
    {
        System.err.println("The messageType " + message.getMessagetype().name() + " is not handled");
        System.err.println("Leaving Server");
        closeConnection();
        System.err.println("Shutting down");
        shutdown();
    }
    
    private void shutdown(){
        System.exit(-1);
    }

    private void closeConnection(){
        try{
            this.serverSocket.close();
        } catch (IOException ex) {
            System.err.println("Error closing Server connection");
            ex.printStackTrace();
        }
    }
}
