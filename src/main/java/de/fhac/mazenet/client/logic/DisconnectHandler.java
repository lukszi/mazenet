package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.generated.MazeCom;

/**
 * Created by Lukas Szimtenings on 6/25/2020.
 */
public class DisconnectHandler extends MessageHandler
{
    @Override
    public void handle(MazeCom message)
    {
        System.out.print("Disconnected with code: ");
        System.out.println(message.getDisconnectMessage().getErrortypeCode());
    }
}
