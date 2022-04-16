package com.ehelpy.brihaspati4.comnmgr;

// Glue Code = gc 
// Authentication Manager = am 
// Communication Manager = cm 
// Indexing Manager = im 
// Routing Manager = rm 
// Web Server Module = ws
// Web Module = web 
// DFS = dfs 
// UFS = ufs 
// Message = sms 
// Mail = mail 
// VoIP = voip 
// Address Book = adbk 
// Search = srch

import com.ehelpy.brihaspati4.authenticate.Config;
import com.ehelpy.brihaspati4.GC.GlueCode;
import java.io.FileReader;
import java.util.Properties;
import java.util.*;
import java.util.LinkedList;
import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
public class Communicator //extends Thread
{
		private static Communicator cm;
		private static Config conf;
		private static LinkedList cm_buffer_gc = new LinkedList();//i/p buffer  of cm
		private static LinkedList ext_input_buffer = new LinkedList();
		private static List var1 = new ArrayList();
		private static List var2 = new ArrayList();
		private static List int_var = new ArrayList();
		private static List nexthop = new ArrayList();
		//private  TLV res_var = new TLV();
		private static LinkedList comnmgr_process_queue = new LinkedList();
		private static LinkedList comnmgr_wait_queue = new LinkedList();
		private static String que_var;
		private static String que_or_res;
		private static String source_module;
		private static String res_var;
		private static String que_or_res1;
		private static String source_module1;
		private static String que_var1;
		private static String destination_module1;
		private static String arg_var1;
		private static String que_or_res2;
		private static String source_module2;
		private static String que_var2;
		private static String arg_var2;
		private static String wait_que_var;
		private static String wait_que_arg;
		private static int max_limit_of_buffer = 1024;
		private static String ipAddress;
		private static String portAddress;
		private static String transport;
		private static String NodeID;
		private static String destinationIp;
		private static String destinationNodeID;
		private static String destinationPort;
		private static String nexthopId;
		
	/*public static void main(String[] args) throws  IOException, ClassNotFoundException, InterruptedException
	{
		Communicator comn = Communicator.getInstance();
	}*/
		private Communicator()//if needed change it to public
		{
			/*try(FileReader reader = new FileReader("gc_config.txt")) 
			{ 
				Properties properties = new Properties(); 
				properties.load(reader); 
				max_limit_of_buffer = properties.getProperty("max_limit_of_buffer"); 
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}*/
			internalBufferThread();
		    Node_details_query();
			//internalBufferThread();
			externalBufferThread();
			this.NodeID=NodeID;
			this.ipAddress=ipAddress;
			this.portAddress=portAddress;
			this.transport=transport;
			
		}
		public static synchronized Communicator getInstance()
		{
			if( cm==null) 
			{
				cm = new Communicator();
			}
			return cm;
		}
		public static void Node_details_query()	
		{
			System.out.println("Reading Node_details_query ");
			GlueCode gc= GlueCode.getInstance();
			try
			{
			List abc = new ArrayList();
			abc.add("que");
			abc.add("cm");
			abc.add("rm");
			
			abc.add("getNodeID");
			abc.add("TLV Format of arguments 2CCD68");
			gc.addMessage_gc_buffer_cm(abc);
			
			abc.set(3,"getIPAddress");
			abc.set(4,"TLV Format of arguments 556CC");
			gc.addMessage_gc_buffer_cm(abc);
			
			abc.set(3,"getPortAddress");
			abc.set(4,"TLV Format of arguments 556CC");
			gc.addMessage_gc_buffer_cm(abc);
			
			abc.set(3,"getTransport");
			abc.set(4,"TLV Format of arguments 556CC");
			gc.addMessage_gc_buffer_cm(abc);
			} catch (Exception e) 
				{
				e.printStackTrace();
				}
		gc.display_buffer_status();	
		}
		
		public static void internalBufferThread()
		{
			Thread internalbufferThread = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						// This statement will request Routing manager to ascertain keys for which I am root node.
						try
						{
							while (true)
							{
								System.out.println("Reading Internal Input Buffer Thread running...");
								//check output buffer of Comn manager;
								//if (data found in the input buffer of Comn manager)
								if ((cm_buffer_gc.size())>0)
								{
									List var1 = new ArrayList();
									var1 = (ArrayList) cm_buffer_gc.get(0);
									cm_buffer_gc.pollFirst();
									que_or_res = (String) var1.get(0);
									source_module = (String) var1.get(1);
									que_var = (String) var1.get(3);
									res_var = (String) var1.get(5);//TLV
									if(var1!=null)
									{
										if ((que_or_res == "res") && (que_var == "getNodeID"))
										{
											NodeID="";//TLV parsing of var2
										}
										else if ((que_or_res == "res") && (que_var == "getIPAddress"))
										{
											ipAddress="";//TLV parsing of var2
										}
										else if ((que_or_res == "res") && (que_var == "getPortAddress"))
										{
											portAddress="";//TLV parsing of var2
										}
										else if ((que_or_res == "res") && (que_var == "getTransport"))
										{
											transport="";//TLV parsing of var2
										}
										else 
										{
											comnmgr_process_queue.add(var1);
											
											//else if (no response found in the  input buffer of Comn Manager)
											//else if (cm_buffer_gc.size()==0)
											
										}
									}
									else
											{
												try 
												{
													//System.out.println("Reading Thread going to sleep");
													Thread.sleep(6000);
												} catch (Exception e) 
												{
													e.printStackTrace();
												}
											}
								}
								else
											{
												try 
												{
													//System.out.println("Reading Thread going to sleep");
													Thread.sleep(6000);
												} catch (Exception e) 
												{
													e.printStackTrace();
												}
											}
							}//close while
						}catch (IndexOutOfBoundsException | NullPointerException e2) {
                    System.out.println("Exception Occurred: " + e2);
                    //log.error("Exception Occurred",e2);
                }

					}//close run
				});
				internalbufferThread.start();
		}
		
		public void internalProcessingThread()
		{
			Thread internalprocessingThread = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						// This statement will request Routing manager to ascertain keys for which I am root node.
						try
						{
							while (true)
							{
								System.out.println("Reading Internal Input Buffer Thread running...");
								//check output buffer of Comn manager;
								//if (data found in the output buffer of Comn manager)
								if ((comnmgr_process_queue.size())>0)
								{
									List<String> int_var = new ArrayList<String>();
									int_var = (ArrayList) comnmgr_process_queue.get(0);
									comnmgr_process_queue.pollFirst();
									if(int_var!= null)
									{
										que_or_res1= (String) int_var.get(0);
										source_module1= (String) int_var.get(1);
										destination_module1= (String) int_var.get(2);
										que_var1 = (String) int_var.get(3);
										arg_var1 = (String) int_var.get(4);//TLV	
										
										if (que_or_res1 == "que")
										
										{
											
											destinationIp= "";//TLV parsing of arg_var1;
											destinationNodeID="";//TLV parsing of arg_var1;
											
											if(destinationIp.equals(ipAddress))//compare ipAddress=destinationIp?
											{
												//System.out.println("check the Ip");
												//arg_var.addmessage(gc_buffer_cm);//int_var
												GlueCode gc= GlueCode.getInstance();
												gc.addMessage_gc_buffer_cm(int_var);
												
											}
											else//draft find next hop query
											{
												GlueCode gc= GlueCode.getInstance();
												List nexthop= new ArrayList();
												nexthop.set(0,"que");
												nexthop.set(1,"cm");
												nexthop.set(2,"rm");
												nexthop.set(3,"findNextHop()");
												nexthop.set(4,"TLV Format of arguments destinationNodeID,LayerId ");
												gc.addMessage_gc_buffer_cm(nexthop);
												List mergeintdataquery= new ArrayList();
												mergeintdataquery.add(int_var);
												mergeintdataquery.add(nexthop);											
												comnmgr_wait_queue.add(mergeintdataquery);// create link list to save the query with data to be sent
											}
										}
										else if (que_or_res1 == "res")
										{
											
											
											for( int ii=0;ii<comnmgr_wait_queue.size();ii++)
											{
												List wait_process_que= new ArrayList();
												wait_process_que=(ArrayList) comnmgr_wait_queue.get(ii);// get first element from com wait queue
												List wait_que=new ArrayList();
												wait_que=(ArrayList)wait_process_que.get(1);// get the value of 1 index or second element
												wait_que_var=(String) wait_que.get(3);// get the 3rd and 4th value
												wait_que_arg=(String) wait_que.get(4);
												if((que_var1==wait_que_var) && (arg_var1==wait_que_arg))// compare (query and argument) with stored query data and retrive data for process//
												{
													//res_var = (TLV) var1.get(5);//get the responce 5
													nexthopId="";//TLV parsing of res_var 
													destinationIp="";//TLV parsing of res_var
													destinationPort="";//TLV parsing of res_var
													transport="";//TLV parsing of res_var
													//wait_data=wait_process_que.get(0);//get the 0 index or first element (data)
													Sender.start((ArrayList)wait_process_que.get(0),destinationIp,destinationPort);//sender output buffer external
													comnmgr_wait_queue.remove(ii);//remove this element from wait queue 
													break;									    								
												}
											}	
										}
									}
								}	
								
							}			
							
						}catch (IndexOutOfBoundsException | NullPointerException | IOException e2) {
                    System.out.println("Exception Occurred: " + e2);
                    //log.error("Exception Occurred",e2);
                }

					}
				});
				internalprocessingThread.start();
		}	
		
		public static String ext_input_bufferadd(ArrayList message)
		{
			String wflag="";
		if((ext_input_buffer.size())< (max_limit_of_buffer))
		{
			ext_input_buffer.add(message);
			wflag="success";
			return wflag;
		}
		else
		{
			wflag="failed";
			return wflag;
		}
}
	public void addMessage_cm_buffer_gc(List gcMessage)
    {
        if ((cm_buffer_gc.size()) < max_limit_of_buffer)
        {
            cm_buffer_gc.add(gcMessage);
            System.out.println("List received by Com Mgr's input buffer.");
        }
        else
        {
            System.out.println("Com Mgr’s input buffer is Full.");
        }
        //sendResponse_gc_buffer_cm();
    }
	/*public void sendResponse_gc_buffer_cm()
    {
        GlueCode gc = GlueCode.getInstance();
        List pqr = new ArrayList();
        pqr = (ArrayList)cm_buffer_gc.get(0);
        cm_buffer_gc.pollFirst();
        System.out.println("\nTemp Var: " + pqr + "\n");
        //String pqr_02 = (String)pqr.get(0);
        pqr.set(0, "res");
        pqr.set(2, pqr.get(1));
        pqr.set(1, "cm");
        pqr.add("TLV Format of the Response of the query");
        System.out.println("\nTemp Var: " + pqr + "\n");
        //display_buffer_status();
        System.out.println("Response being sent to gc.");
        gc.addMessage_gc_buffer_cm(pqr);
        System.out.println("Response already received by gc.");
        //display_buffer_status();
    }*/
	public static void externalBufferThread()
	{
		Thread externalbufferThread = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					// this thread will start the processing of data in the ext input buffer.
					try
					{
						while (true)
						{
							System.out.println("Reading External Input Buffer Thread running...");
							//check output buffer of Comn manager;
							//if (data found in the output buffer of Comn manager)
							if ((ext_input_buffer.size())>0)
							{
								List var2 = new ArrayList();
								var2 = (ArrayList) ext_input_buffer.get(0);
								ext_input_buffer.pollFirst();
								que_or_res2 = (String) var2.get(0);
								source_module2 = (String) var2.get(1);
								que_var2 = (String) var2.get(3);
								arg_var2 = (String) var2.get(4);
								if(var2!=null)
								{
									if ((que_or_res2 == "que")||(que_or_res2 == "res"))
									{
										destinationIp=""; //TLV parsing of arg_var2;
										destinationNodeID="";//TLV parsing of arg_var2;
										
									}
									if(destinationIp.equals(ipAddress))//compare ipAddress=destinationIp?
									{
										
										//System.out.println("check the Ip");
										//arg_var.addmessage(gc_buffer_cm);//int_var
										GlueCode gc= GlueCode.getInstance();
										gc.addMessage_gc_buffer_cm(var2);
										
									}
									else//find next hop query
									{
										GlueCode gc= GlueCode.getInstance();
										List nexthop2= new ArrayList();
										nexthop2.set(0,"que");
										nexthop2.set(1,"cm");
										nexthop2.set(2,"rm");
										nexthop2.set(3,"findNextHop()");
										nexthop2.set(4,"TLV Format of arguments destinationNodeID,LayerId ");
										gc.addMessage_gc_buffer_cm(nexthop2);
										List mergeextdataquery = new ArrayList();
										mergeextdataquery.add(var2);
										mergeextdataquery.add(nexthop2);
										comnmgr_wait_queue.add(mergeextdataquery);// create link list to save the query with data to be sent
									}
									
									/*	else if (que_or_res2 == "res")
										
										{									
										
										for( int ii=0;ii<comnmgr_wait_queue.size;ii++)
										{
										wait_process_que=(Arraylist) comnmgr_wait_queue.get(ii);// get first element from com wait queue
										wait_que=wait_process_que.get(1);// get the value of 1 index or second element
										wait_que_var=wait_que.get(3);// get the 3rd and 4th value
										wait_que_arg=wait_que.get(4):
										if((que_var==wait_que_var) && (arg_var==wait_que_arg))// compare (query and argument) with stored query data and retrive data for process//
										{
										//res_var = (TLV) var1.get(5);//get the responce 5
										nexthopId="";//TLV parsing of res_var 
										destinationIp="";//TLV parsing of res_var
										destinationPort="";//TLV parsing of res_var
										wait_data=wait_process_que.get(0);//get the 0 index or first element (data)
										Sender.start(wait_process_que.get(0),destinationIp,destinationPort);//sender output buffer external
										comnmgr_wait_queue.poll(ii);//remove this element from wait queue 
										break;									    								
										}
										}	
									}*/								 	
								}//not null
							}//if size
							else
								{
									try 
									{
										//System.out.println("Reading Thread going to sleep");
										Thread.sleep(6000);
									} catch (InterruptedException e) 
									{
										e.printStackTrace();
									}
								}
						}//while	
					
						}catch (IndexOutOfBoundsException | NullPointerException e2) {
						System.out.println("Exception Occurred: " + e2);
						//log.error("Exception Occurred",e2);
						}
				}
					});
					externalbufferThread.start();
								
			}
		
}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		