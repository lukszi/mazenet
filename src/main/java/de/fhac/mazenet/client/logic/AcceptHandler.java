package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.generated.AcceptMessageData;
import de.fhac.mazenet.server.generated.MazeCom;

/**
 * Created by Lukas Szimtenings on 6/26/2020.
 */
public class AcceptHandler extends MessageHandler
{
    
    @Override
    public void handle(MazeCom message)
    {
        AcceptMessageData payload = message.getAcceptMessage();
        if(!payload.isAccept()){
            // TODO: Write to the application state
            System.err.println("Server did not accept last move: " + payload.getErrortypeCode());
        }
        
    }
}
