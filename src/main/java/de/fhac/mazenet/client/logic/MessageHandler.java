package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.generated.MazeCom;

import java.net.Socket;

/**
 * Created by Lukas Szimtenings on 6/25/2020.
 */
public abstract class MessageHandler
{
    public abstract void handle(MazeCom message);
}
