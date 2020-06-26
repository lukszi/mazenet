package de.fhac.mazenet.client;

import de.fhac.mazenet.client.logic.ErrorHandler;
import de.fhac.mazenet.client.logic.MessageHandler;
import de.fhac.mazenet.server.generated.MazeCom;
import de.fhac.mazenet.server.generated.MazeComMessagetype;
import de.fhac.mazenet.server.networking.XmlOutputStream;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lukas Szimtenings on 6/25/2020.
 */
public class MessageDispatcher
{
    private final Map<MazeComMessagetype, MessageHandler> handlerRegistry;
    private final ErrorHandler errorHandler;

    public MessageDispatcher(ErrorHandler errorHandler)
    {
        this.handlerRegistry = new HashMap<>();
        this.errorHandler = errorHandler;
    }
    
    public void register(MazeComMessagetype messagetype, MessageHandler handler){
        this.handlerRegistry.put(messagetype, handler);
    }
    
    public void dispatch(MazeCom message){
        handlerRegistry.getOrDefault(message.getMessagetype(), errorHandler).handle(message);
    }
}
