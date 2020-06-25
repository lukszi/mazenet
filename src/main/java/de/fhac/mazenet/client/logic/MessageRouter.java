package de.fhac.mazenet.client.logic;

import de.fhac.mazenet.server.generated.MazeCom;
import de.fhac.mazenet.server.generated.MazeComMessagetype;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lukas Szimtenings on 6/25/2020.
 */
public class MessageRouter
{
    private Socket socket;
    private Map<MazeComMessagetype, MessageHandler> handlerRegistry;

    public MessageRouter(Socket connectionToServer)
    {
        this.socket = connectionToServer;
        handlerRegistry = new HashMap<MazeComMessagetype, MessageHandler>();
    }
    
    
    public void register(MazeComMessagetype messagetype, MessageHandler handler){
        this.handlerRegistry.put(messagetype, handler);
    }
    
    
    public void route(MazeCom message){
        switch(message.getMessagetype()){
            case LOGIN:
                break;
            case LOGINREPLY:
                break;
            case AWAITMOVE:
                break;
            case MOVE:
                break;
            case MOVEINFO:
                break;
            case GAMESTATUS:
                break;
            case CONTROLSERVER:
                break;
            case ACCEPT:
                break;
            case WIN:
                break;
            case DISCONNECT:
                break;
        }
    }
}
