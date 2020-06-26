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
        int id = loginMessage.getLoginReplyMessage().getNewID();
        System.out.println("Succesfully logged in, now working with ID: " + id);
        STATE.setId(id);
    }
}
