package de.fhac.mazenet.client;

import de.fhac.mazenet.client.logic.AwaitMoveHandler;
import de.fhac.mazenet.client.logic.LoginReplyHandler;
import de.fhac.mazenet.client.network.MarshallUnmarshall;
import de.fhac.mazenet.server.generated.ClientRole;
import de.fhac.mazenet.server.generated.LoginMessageData;
import de.fhac.mazenet.server.generated.MazeCom;
import de.fhac.mazenet.server.generated.MazeComMessagetype;
import de.fhac.mazenet.server.networking.MazeComMessageFactory;
import de.fhac.mazenet.server.networking.XmlInputStream;
import de.fhac.mazenet.server.networking.XmlOutputStream;
import org.apache.commons.cli.*;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.bind.JAXBException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Teammitglieder: Lukas Szimtenings, Felix Szimtenings, Marvin Schmidt, Nicklas Felden-Hilfer, Marco Grunert, Cedric Radtke
 * <p>
 */
public class Team2
{

    private static final String TEAMNAME = Team2.class.getSimpleName();
    private static final String OPTION_HOSTNAME = "host";
    private static final String OPTION_PORT = "port";
    private static final String OPTION_TRUSTSTORE_PATH = "truststore";
    private static final String OPTION_TRUSTSTORE_PASSWORD = "storepass";
    private static final String OPTION_HELP = "help";

    private static String hostname = "localhost";
    private static int port = 5123;
    private static int portSSL = 5432;
    private static String truststorePath = "";
    private static Options options = new Options();
    private static String truststorePassword = "";
    
    private static MessageDispatcher dispatcher;

    static {
        String descriptionHost = "Festlegen zu welchem Host verbunden werden soll";
        String descriptionPort = "Festlegen auf welchen Port auf dem Zielsystem verbunden werden soll (TLS- und unverschlüsselter Port)";
        String descriptionTruststore = "Bei Verwendung von SSL/TLS wird hier der Pfad zum Truststore angegeben";
        String descriptionHelp = "Anzeigen dieser Hilfe";
        String descriptionTruststorePassword = "Setzen des Passworts für den Truststore";

        options.addOption(
                Option.builder().longOpt(OPTION_HOSTNAME).desc(descriptionHost).hasArg().argName("hostname").build());
        options.addOption(Option.builder().longOpt(OPTION_PORT).desc(descriptionPort).hasArg().argName("port").build());
        options.addOption(Option.builder().longOpt(OPTION_TRUSTSTORE_PATH).desc(descriptionTruststore).hasArg()
                .argName("path").build());
        options.addOption(Option.builder().longOpt(OPTION_HELP).desc(descriptionHelp).build());
        options.addOption(Option.builder().longOpt(OPTION_TRUSTSTORE_PASSWORD).desc(descriptionTruststorePassword)
                .hasArg().argName("password").build());
    }

    private static void parseArgs(String[] args) {
        HelpFormatter formatter = new HelpFormatter();
        try {
            CommandLine commandLine = new DefaultParser().parse(options, args);
            // wenn Hilfe angezeigt wird, wird der Rest ignoriert
            if (commandLine.hasOption(OPTION_HELP)) {
                formatter.printHelp(TEAMNAME, options);
                System.exit(0);
            }
            if (commandLine.hasOption(OPTION_HOSTNAME))
                hostname = commandLine.getOptionValue(OPTION_HOSTNAME);
            if (commandLine.hasOption(OPTION_PORT)) {
                port = Integer.parseInt(commandLine.getOptionValue(OPTION_PORT));
                portSSL = Integer.parseInt(commandLine.getOptionValue(OPTION_PORT));
            }
            if (commandLine.hasOption(OPTION_TRUSTSTORE_PATH))
                truststorePath = commandLine.getOptionValue(OPTION_TRUSTSTORE_PATH);
            if (commandLine.hasOption(OPTION_TRUSTSTORE_PASSWORD))
                truststorePassword = commandLine.getOptionValue(OPTION_TRUSTSTORE_PASSWORD);
        } catch (ParseException e) {
            // sobald ein ungueltiger Parameter vorhanden ist
            System.err.println("Ungültige Parameter vorhanden -> Anzeigen der Hilfe");
            formatter.printHelp(TEAMNAME, options);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException
    {
        parseArgs(args);
        System.out.println("Team: " + TEAMNAME);
        System.out.println("Host: " + hostname);
        System.out.println("Port: " + port);
        System.out.println("Port: " + portSSL);
        System.out.println("Truststore-Path: " + truststorePath);
        System.out.println("Truststore-Password gesetzt: " + !truststorePassword.equals(""));

        Socket toServer = null;

        if (!truststorePath.equals("")) {
            System.setProperty("javax.net.ssl.trustStore", truststorePath);
            System.setProperty("javax.net.ssl.trustStorePassword", truststorePassword);
            try {
                SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                toServer = socketFactory.createSocket(hostname, port);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(-1);
            }
        } else {
            try {
                SocketFactory socketFactory = SocketFactory.getDefault();
                toServer = socketFactory.createSocket(hostname, port);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(-1);
            }

        }
        
        setupMessageRouter();
        XmlOutputStream out = new XmlOutputStream(toServer.getOutputStream());
        XmlInputStream in = new XmlInputStream(toServer.getInputStream());
        try
        {
            
            MarshallUnmarshall messageParser = new MarshallUnmarshall();
    
            System.out.println("Logging in");
            MazeCom login = MazeComMessageFactory.createLoginMessage("Team2");
            out.write(login);
            
            System.out.println("Waiting for messages");
            
            boolean conOpen = true;
            while(conOpen){
                MazeCom message = in.readMazeCom();
                dispatcher.dispatch(message);
            }
            
        } catch (IOException | JAXBException e)
        {
            e.printStackTrace();
        }
    }
    
    private static void setupMessageRouter()
    {
        dispatcher = new MessageDispatcher();
        dispatcher.register(MazeComMessagetype.LOGINREPLY, new LoginReplyHandler());
        dispatcher.register(MazeComMessagetype.AWAITMOVE, new AwaitMoveHandler());
    }
    
}
