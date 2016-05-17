/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.net;

import org.visualtrading.util.Codecs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HTTPProxySocketFactory extends SocketFactory {

// ------------------------------ FIELDS ------------------------------

    public static final String HTTP_PROXY_USER = "https.proxyUser";
    public static final String HTTP_PROXY_PASSWORD = "https.proxyPassword";
    public static final String HTTP_PROXY_HOST = "https.proxyHost";
    public static final String HTTP_PROXY_PORT = "https.proxyPort";

    public static final String PASITIVE_REPLY_1_0 = "HTTP/1.0 200";
    public static final String PASITIVE_REPLY_1_1 = "HTTP/1.1 200";

    public static final int CR = 13;
    public static final int LF = 10;
    public static final int BUFFER_SIZE = 9086;
    private static final String CRLF = "\r\n";

    private static final String[] boolValues = {"true", "yes", "ok", "on"};

// --------------------------- CONSTRUCTORS ---------------------------

    public HTTPProxySocketFactory() {
    }

// -------------------------- OTHER METHODS --------------------------

    public Socket getSocket(String remoteHost, int remotePort) throws IOException {


        String httpProxyUser = System.getProperty(HTTP_PROXY_USER);
        String httpProxyPassowrd = System.getProperty(HTTP_PROXY_PASSWORD);
        String httpProxyHost = System.getProperty(HTTP_PROXY_HOST);
        String httpProxyPort = System.getProperty(HTTP_PROXY_PORT);

        if (httpProxyHost == null || httpProxyHost.length() == 0 || httpProxyPort == null|| httpProxyPort.length() == 0) {
            return super.getSocket(remoteHost, remotePort);
        }

        if (httpProxyUser == null || httpProxyPassowrd == null) {
            throw new IOException("Authentication information is incomplete.");
        }

        String authString = new StringBuffer().append(httpProxyUser)
                .append(":")
                .append(httpProxyPassowrd)
                .toString();


        if (remoteHost == null || remotePort < 0) {
            throw new IOException("Information about the remote host is incomplete.");
        }

        String url = new StringBuffer().append(remoteHost)
                .append(":")
                .append(remotePort)
                .toString();

        String auth = new StringBuffer().append("Basic ")
                .append(Codecs.base64Encode(authString))
                .toString();

        Socket socket = new Socket(httpProxyHost, Integer.parseInt(httpProxyPort));

        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        out.write((new StringBuffer().append("CONNECT ")
                   .append(url)
                   .append(" HTTP/1.1")
                   .append(CRLF)
                   .append("Host: ")
                   .append(url)
                   .append(CRLF)
                   .toString()).getBytes());

        out.write((new StringBuffer().append("Proxy-Authorization: ")
                   .append(auth)
                   .append(CRLF)
                   .toString()).getBytes());

//        out.write((new StringBuffer().append("Content-Length: 0")
//                   .append(CRLF)
//                   .append(CRLF)
//                   .toString()).getBytes());

//        byte[] buffer = new byte[1];
//        byte[] message = new byte[BUFFER_SIZE];
//        int count = 0;
//        int index = 0;
//        while (count < 2 && in.read(buffer) > 0) {
//            if (buffer[0] == LF || buffer[0] == CR) {
//                count++;
//            } else if (count > 0) {
//                count--;
//            }
//            for (int i = 0;
//                 i < buffer.length && index < message.length;
//                 i++, index++) {
//                message[index] = buffer[i];
//            }
//        }
//
//        String reply = new String(message, 0, index);
//        if (!(reply.startsWith(PASITIVE_REPLY_1_0) ||
//              reply.startsWith(PASITIVE_REPLY_1_1))) {
//            throw new IOException("Can't connect - " + reply);
//        }

        return socket;

    }

}

