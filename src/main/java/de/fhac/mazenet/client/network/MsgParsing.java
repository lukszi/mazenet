package de.fhac.mazenet.client.network;

import de.fhac.mazenet.server.generated.MazeCom;
import de.fhac.mazenet.server.generated.MazeComMessagetype;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/** by Marc-Oliver Grunert**/

public class MsgParsing {
    private JAXBContext jc;
    private Marshaller marshaller;
    private final Unmarshaller unmarshaller;
    private String message;

    public MsgParsing() throws JAXBException
    {
        this.jc = JAXBContext.newInstance(MazeCom.class, MazeComMessagetype.class);
        //this.marshaller = this.jc.createMarshaller();
        this.unmarshaller = this.jc.createUnmarshaller();
    }

    public MazeCom parseMessage(String message) throws JAXBException
    {
        return (MazeCom) this.unmarshaller.unmarshal(new StringReader(message));
    }

}
