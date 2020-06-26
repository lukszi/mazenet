package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.client.ApplicationState;
import de.fhac.mazenet.server.generated.MazeCom;


/**
 * Created by Lukas Szimtenings on 6/25/2020.
 */
public abstract class MessageHandler
{
    public static ApplicationState state;
    
    public abstract void handle(MazeCom message);
}
