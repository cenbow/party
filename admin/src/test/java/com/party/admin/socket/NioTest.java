package com.party.admin.socket;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wei.li
 *
 * @date 2017/5/5 0005
 * @time 11:20
 */
public class NioTest {

    @Test
    public void test() throws IOException{
        RandomAccessFile file = new RandomAccessFile("G:\\test.txt", "rw");
        FileChannel fileChannel = file.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int bytesRead = fileChannel.read(byteBuffer);

        System.out.println(bytesRead);

        while (bytesRead != -1){
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()){
                System.out.print((char)byteBuffer.get());
                byteBuffer.compact();
                bytesRead = fileChannel.read(byteBuffer);

            }
        }

        file.close();

    }
}
