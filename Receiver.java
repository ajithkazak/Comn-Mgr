package com.ehelpy.brihaspati4.comnmgr;

//import com.ehelpy.brihaspati4.DFS.dfs3Mgr.DFS3BufferMgr;
//import com.ehelpy.brihaspati4.DFS.dfs3xmlHandler.XMLReader;
//import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;
import java.util.stream.*;
import java.util.*;

public class Receiver 
{
    // public static DFS3BufferMgr dfsbuffer = DFS3BufferMgr.getInstance();
    //private static final Logger log = Logger.getLogger(Receiver.class.getName());
	//public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
    public static void start() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException 
	{
        //ServerSocket serverSocket = null;
        //try {
        ServerSocket serverSocket = new ServerSocket(4445);
        //log.debug("Server started");
		//} catch (IOException e) {
		//System.out.println(e);
        //log.debug("Can't setup server on this port number. ");
		//}
        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;
        //try {
        assert serverSocket != null;
        socket = serverSocket.accept();
		//} catch (SocketException e) {
        //log.debug("Can't accept client connection.");
		// }
		assert socket != null;
		while(true)
		{
			if(socket.isConnected())
			{
                try 
				{
                    in = socket.getInputStream();
                } catch (SocketException e) 
				{
                    //log.debug("Can't get socket input stream.");
                }
                /*String fileName = getName();
                File file = new File(fileName);
                file.createNewFile();
                try {
                    out = new FileOutputStream(fileName);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    log.debug("File not found.");
                }*/
				//try 
				//{
					//ObjectInputStream ois = new ObjectInputStream(in);
					//String message = (String) ois.readObject();
					//System.out.println("Message Received: " + message);
					// Convert Stream to ArrayList in Java
					Stream<String> lines = new BufferedReader(new InputStreamReader(in)).lines();
					ArrayList<String> arrayList = getArrayListFromStream(lines);
					//ArrayList arrayList = getArrayListFromStream(lines);
					String baflag=Communicator.ext_input_bufferadd(arrayList);
				//}catch(SocketException e)
				//{
					//System.out.println(e);
				//}
				//ext_input_buffer.add(message);
				/*byte[] bytes = new byte[16*1024];
                int count;
                while (true) {
                    assert in != null;
                    if (!((count = in.read(bytes)) > 0)) break;
                    assert out != null;
                    //out.write(bytes, 0, count);
					//write into the ext input buffer ??
                }*/
                assert out != null;
                out.close();
                in.close();
                //XMLReader.reader(file);
                //dfsbuffer.addToInputBuffer(new File(fileName));
                //XMLReader.reader(inputbuffer.fetchFromInputBuffer());
			}
            else
			{
				break;
			}
		}	
		/*public static <T> ArrayList<T>
		getArrayListFromStream(Stream<T> stream)
		{
			// Convert the Stream to List
			List<T>	list = stream.collect(Collectors.toList());
			// Create an ArrayList of the List
			ArrayList<T> arrayList = new ArrayList<T>(list);
			// Return the ArrayList
			return arrayList;
		}*/
	}  
	//socket.close();
    //serverSocket.close();
	/*public static String getName(){
    Path fileName = Paths.get(UUID.randomUUID() + ".xml");
    return String.valueOf(fileName);
	}*/
    public static <String> ArrayList<String> getArrayListFromStream(Stream<String> stream)
    {
    // Convert the Stream to List
    List<String> list = stream.collect(Collectors.toList());
    // Create an ArrayList of the List
    ArrayList<String> arrayList = new ArrayList<String>(list);
    // Return the ArrayList
    return arrayList;
	}
}