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
	            //1.����һ����������Socket����ServerSocket��ָ���󶨵Ķ˿ڣ��������˶˿�
	            ServerSocket serverSocket=new ServerSocket(8899);
	            System.out.println(serverSocket.getInetAddress().getLocalHost());
	            
	            Socket socket=null;
	            //��¼�ͻ��˵�����
	            int count=0;
	            System.out.println("***�����������������ȴ��ͻ��˵�����***");
	            //ѭ�������ȴ��ͻ��˵�����
	            while(true){
	                //����accept()������ʼ�������ȴ��ͻ��˵�����
	                socket=serverSocket.accept();
	                ServerThread serverThread = new ServerThread(socket);
	                serverThread.start();
	                count++;//ͳ�ƿͻ��˵�����
	                System.out.println("�ͻ��˵�������"+count);
	                InetAddress address=socket.getInetAddress();
	                System.out.println("��ǰ�ͻ��˵�IP��"+address.getHostAddress());
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}

class ServerThread extends Thread {
    // �ͱ��߳���ص�Socket
    Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    private byte[] mByteBuff = new byte[1024*8];
    //�߳�ִ�еĲ�������Ӧ�ͻ��˵�����
    public void run(){
    	InputStream is=null;
        InputStreamReader isr=null;
        BufferedReader br=null;
        OutputStream os=null;
        PrintWriter pw=null;
        try {
            //��ȡ������������ȡ�ͻ�����Ϣ
            is = socket.getInputStream();
            isr = new InputStreamReader(is,"utf-8");
            br = new BufferedReader(isr);
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            String info=null;
            while((info=br.readLine())!=null){//ѭ����ȡ�ͻ��˵���Ϣ
                System.out.println("���Ƿ��������ͻ���˵��"+info);
              //��ȡ���������Ӧ�ͻ��˵�����
                pw.write("��������������������\r\n");
                pw.println();
                pw.flush();//����flush()�������������
            }
            socket.shutdownInput();//�ر�������
            //��ȡ���������Ӧ�ͻ��˵�����
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("��ӭ����");
            pw.flush();//����flush()�������������
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //�ر���Դ
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
