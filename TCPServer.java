import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPServer {
	public static void main(String[] args) {
		  try {
	            //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
	            ServerSocket serverSocket=new ServerSocket(8899);
	            System.out.println(serverSocket.getInetAddress().getLocalHost());
	            
	            Socket socket=null;
	            //记录客户端的数量
	            int count=0;
	            System.out.println("***服务器即将启动，等待客户端的连接***");
	            //循环监听等待客户端的连接
	            while(true){
	                //调用accept()方法开始监听，等待客户端的连接
	                socket=serverSocket.accept();
	                ServerThread serverThread = new ServerThread(socket);
	                serverThread.start();
	                count++;//统计客户端的数量
	                System.out.println("客户端的数量："+count);
	                InetAddress address=socket.getInetAddress();
	                System.out.println("当前客户端的IP："+address.getHostAddress());
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}

class ServerThread extends Thread {
    // 和本线程相关的Socket
    Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    private byte[] mByteBuff = new byte[1024*8];
    //线程执行的操作，响应客户端的请求
    public void run(){
    	InputStream is=null;
        InputStreamReader isr=null;
        BufferedReader br=null;
        OutputStream os=null;
        PrintWriter pw=null;
        try {
            //获取输入流，并读取客户端信息
            is = socket.getInputStream();
            isr = new InputStreamReader(is,"utf-8");
            br = new BufferedReader(isr);
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            String info=null;
            while((info=br.readLine())!=null){//循环读取客户端的信息
                System.out.println("我是服务器，客户端说："+info);
              //获取输出流，响应客户端的请求
                pw.write("哈哈哈啊哈哈哈哈！！\r\n");
                pw.println();
                pw.flush();//调用flush()方法将缓冲输出
            }
            socket.shutdownInput();//关闭输入流
            //获取输出流，响应客户端的请求
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("欢迎您！");
            pw.flush();//调用flush()方法将缓冲输出
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //关闭资源
            try {
                if(pw!=null)
                    pw.close();
                if(os!=null)
                    os.close();
                if(br!=null)
                    br.close();
                if(isr!=null)
                    isr.close();
                if(is!=null)
                    is.close();
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
