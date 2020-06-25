package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.generated.MazeCom;

/**
 * Created by Lukas Szimtenings on 6/25/2020.
 */
public class ErrorHandler extends MessageHandler
{
    @Override
    public void handle(MazeCom message)
    {
        System.err.println("This should not appear, the messageType" + message.getMessagetype().name() + "is not handled");
        System.err.println("Shutting down");
    }
    
    public void shutdown(){
        //TODO: Implement proper shutdown routine
        // IE exiting from the server etc.
        System.exit(-1);
    }
}
