package com.party.admin.socket;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wei.li
 *
 * @date 2017/5/5 0005
 * @time 10:25
 */
public class SocketTest {

    @Test
    public void serviceTest() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10086);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String a = null;
        while ( (a = bufferedReader.readLine()) != null){
            System.out.println("服务" + a);
        }

        socket.shutdownInput();
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
    }

    @Test
    public void  clienTest()throws IOException{
        Socket client = new Socket("127.0.0.1", 10086);
        Writer writer = new OutputStreamWriter(client.getOutputStream());
        writer.write("Hello From Client");
        writer.flush();
        writer.close();
        client.close();
    }
}
