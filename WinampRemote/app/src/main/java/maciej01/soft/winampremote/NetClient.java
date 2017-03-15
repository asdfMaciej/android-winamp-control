package maciej01.soft.winampremote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class NetClient {

    /**
     * Maximum size of buffer
     */
    public static final int BUFFER_SIZE = 1024;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    private String host = null;
    private String macAddress = null;
    private int port = 21337;


    /**
     * Constructor with Host, Port and MAC Address
     * @param host
     * @param port
     * @param macAddress
     */
    public NetClient(String host, int port, String macAddress) {
        this.host = host;
        this.port = port;
        this.macAddress = macAddress;
    }

    private void connectWithServer() {
        try {
            if (socket == null) {
               // socket = new Socket(this.host, this.port);
                socket = new Socket("192.168.1.109", 21337);
                out = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disConnectWithServer() {
        if (socket != null) {
            if (socket.isConnected()) {
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendDataWithString(String message) {
        if (message != null) {
            connectWithServer();
            out.write(message);
            //out.write("pause");
            out.flush();
            disConnectWithServer();
        }
    }

    public String receiveDataFromServer(String send) {
        try {
            connectWithServer();
            //out.write(message);
            out.write(send);
            String message = "";
            int charsRead = 0;
            char[] buffer = new char[BUFFER_SIZE];

            if (buffer != null) {
                while ((charsRead = in.read(buffer)) != -1) {
                    message += new String(buffer).substring(0, charsRead);
                }
            }
            disConnectWithServer(); // disconnect server
            return message;
        } catch (IOException e) {
            return "Error receiving response:  " + e.getMessage();
        }
    }


}