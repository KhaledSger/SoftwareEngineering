package il.cshaifasweng.OCSFMediatorExample.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private static SimpleServer server;
    public static void main( String[] args ) throws IOException , UnknownHostException
    {
        server = new SimpleServer(3000);
        server.listen();

    }
}
