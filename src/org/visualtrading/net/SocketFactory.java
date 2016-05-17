/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.net;

import java.io.IOException;
import java.net.Socket;

public class SocketFactory {

    /**
     * Use remoteHost and remotePort in combination with predifined configuration to create a socket
     *
     * @param remoteHost
     * @param remotePort
     *
     * @return
     *
     * @throws IOException see getSocket(Hashtable)
     */
    public Socket getSocket(String remoteHost, int remotePort)
            throws IOException {
        return new Socket(remoteHost, remotePort);
    }

}
