/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.functionClass.socket.AIO.demo2;

import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author bence
 */
public class Nio2TestClient {
    
    private static final String DEFAULT_HOST = "127.0.0.1";
    
    private static final int port = 9999;
    
    private static int okBytes = 0;
    
    private static long nextSerial = 0;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        String host = args.length > 0 ? args[0] : DEFAULT_HOST;
        byte[] buffer = new byte[1024 * 1024];
        Socket s = new Socket(host, port);
        InputStream is = s.getInputStream();
        
        int dataLength = 0;
        do {
            int numBytes = is.read(buffer, dataLength, buffer.length - dataLength);
            if (numBytes == -1) {
                break;
            }
            dataLength += numBytes;

            do {
                int bytesProcessed = processPacket(buffer, 0, dataLength);
                dataLength -= bytesProcessed;

                if (bytesProcessed > 0) {
                    System.arraycopy(buffer, bytesProcessed, buffer, 0, dataLength);
                } else {
                    break;
                }
            } while (true);
            
        } while (true);
        
    }

    public static int processPacket(byte[] buffer, int offset, int length) {
        if (length < 16) {
            return 0;
        }
        
        ByteBuffer bb = ByteBuffer.wrap(buffer, offset, length);
        
        long packetLength = bb.getLong();
        if (packetLength > length) {
            return 0;
        }
        
        long serial = bb.getLong();
        if (serial != nextSerial) {
            
            //System.err.println("\n" + okBytes + " (" + (okBytes / length) + ")");
            okBytes = 0;
            
            int diff = (int)(serial - nextSerial);
            if (diff < 0) {
                System.err.println("\nDiscontiunity. Expected " + nextSerial + " got: " + serial + ".");
            } else {
                char[] errorDisplay = new char[diff];
                Arrays.fill(errorDisplay, '!');
                System.err.print(errorDisplay);
                System.err.println(".");
            }
        } else {
            System.err.print(".");
        }
        okBytes += length;
        nextSerial = serial + 1;
        
        return (int)packetLength;
    }
    
}
