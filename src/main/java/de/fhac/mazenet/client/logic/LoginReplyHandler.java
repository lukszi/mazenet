package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.generated.LoginReplyMessageData;
import de.fhac.mazenet.server.generated.MazeCom;

/**
 * Created by Lukas Szimtenings on 6/25/2020.
 */
public class LoginReplyHandler extends MessageHandler
{
    @Override
    public void handle(MazeCom loginMessage)
    {
        LoginReplyMessageData loginReplyMessage = loginMessage.getLoginReplyMessage();
        
        // TODO: implement gameState object that contains our ID
        System.out.println(loginReplyMessage.getNewID());
    }
}
