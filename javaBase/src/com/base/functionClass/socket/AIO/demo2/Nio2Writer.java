/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.functionClass.socket.AIO.demo2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author bence
 */
public class Nio2Writer {
    
    private final AsynchronousByteChannel channel;
    
    private final Deque<ByteBuffer> buffers = new ArrayDeque<>();
    
    private boolean busy = false;


    public Nio2Writer(AsynchronousByteChannel channel) {
        this.channel = channel;
    }
    
    
    public void write(ByteBuffer buffer) {
        synchronized (buffers) {
            buffers.addLast(buffer);
            
            if (!busy) {
                writeNow();
            }
        }
    }

    private void writeNow() {
        
        final ByteBuffer currentBuffer;
        synchronized (buffers) {
            busy = true;
            currentBuffer = buffers.peekFirst();
        }
        
        final long time = System.nanoTime();
        channel.write(currentBuffer, null, new CompletionHandler<Integer, Void>() {

            @Override
            public void completed(Integer v, Void a) {
                
                if (currentBuffer.remaining() > 0) {
                    writeNow();
                }
                
                /*
                currentBuffer.position(0);
                long serial = currentBuffer.getLong(8);
                long timeElapsed = System.nanoTime() - time;
                System.err.println(timeElapsed + " " + serial);
                //System.err.println(packetCount);
                */
                
                synchronized (buffers) {
                    buffers.removeFirst();
                    busy = false;
                    if (buffers.size() > 0) {
                        writeNow();
                    }
                }
            }

            @Override
            public void failed(Throwable thrwbl, Void a) {
                System.err.println("Failed write.");
                
                try {
                    channel.close();
                } catch (IOException ex) {
                    System.err.println("Failure while closing.");
                }
            }
        });
    }
    
}
