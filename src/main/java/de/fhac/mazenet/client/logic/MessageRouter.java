package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.generated.MazeCom;

import java.net.Socket;

/**
 * Created by Lukas Szimtenings on 6/25/2020.
 */
public class MessageRouter
{
    private Socket socket;
    
    public MessageRouter(Socket connectionToServer)
    {
        this.socket = connectionToServer;
    }
    
    public void route(MazeCom message){
    
    }
}
