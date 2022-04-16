package com.ehelpy.brihaspati4.comnmgr;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.*;
import java.util.function.Function;
import java.nio.charset.StandardCharsets;
public class Sender {
    public static void start(List<String> int_var, String destinationIP,String Port) throws IOException {
        try (Socket socket = new Socket(destinationIP, 4445)) {
            byte[] bytes = new byte[16 * 1024];
			//Stream stream = int_var.stream();
			//BufferedInputStream in = new BufferedInputStream(stream);
			//BufferedInputStream in = new BufferedInputStream(int_var);
			StringBuilder sb = new StringBuilder();
			for(String s :int_var)
			{
				sb.append(s);           
			}
            InputStream stream = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
			//InputStream stream = int_var.stream();
            BufferedInputStream in = new BufferedInputStream(stream);
			
			OutputStream out = socket.getOutputStream();
            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public static void start(File xmlFile, String IP) throws IOException {
        try (Socket socket = new Socket(IP, 4445)) {
            byte[] bytes = new byte[16 * 1024];
            InputStream in = new FileInputStream(xmlFile);
            OutputStream out = socket.getOutputStream();
            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(String xmlPath, String IP) throws IOException {
        Socket socket = null;
        Boolean flag = false;
        socket = new Socket(IP, 3333);

        File file = new File(xmlPath);
        // Get the size of the file
        long length = file.length();
        byte[] bytes = new byte[2 * 1024];

        InputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }
        out.close();
        in.close();
        socket.close();
    }
}