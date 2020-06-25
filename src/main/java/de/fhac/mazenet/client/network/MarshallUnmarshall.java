package de.fhac.mazenet.client.network;

import de.fhac.mazenet.server.generated.*;

import javax.lang.model.type.ErrorType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * by Marc-Oliver Grunert
 **/

public class MarshallUnmarshall
{
    private JAXBContext jc;
    private javax.xml.bind.Marshaller marshaller;
    private final Unmarshaller unmarshaller;
    private String message;
    
    public MarshallUnmarshall() throws JAXBException
    {
        this.jc = JAXBContext.newInstance(AcceptMessageData.class, AwaitMoveMessageData.class, BoardData.class, CardData.class
                , ControlServerData.class, DisconnectMessageData.class, GameStatusData.class, LoginMessageData.class,
                LoginReplyMessageData.class, MazeCom.class, MoveInfoData.class, MoveMessageData.class,
                ObjectFactory.class, PositionData.class, StatisticData.class, TreasuresToGoData.class, WinMessageData.class);
        this.marshaller = this.jc.createMarshaller();
        this.unmarshaller = this.jc.createUnmarshaller();
    }
    
    public MazeCom unmarshall(String message) throws JAXBException
    {
        return (MazeCom) this.unmarshaller.unmarshal(new StringReader(message));
    }
    
    public String marshall(MazeCom message) throws JAXBException
    {
        StringWriter stringWriter = new StringWriter();
        this.marshaller.marshal(message, stringWriter);
        return stringWriter.toString();
    }
    
}
