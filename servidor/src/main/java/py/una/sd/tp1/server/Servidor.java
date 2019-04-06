package py.una.sd.tp1.server;

/* Hará 2 tareas
* 1)En primer plano tendrá que recibir texto en su frame
* 2)En segundo plano tendrá que estar a la escucha constantemente y tener el puerto abierto 
* */

import javax.swing.*;

import org.json.simple.JSONObject;

import py.una.sd.tp1.entidad.Mensaje;
import py.una.sd.tp1.entidad.PaqueteEnvio;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor  {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MarcoServidor mimarco=new MarcoServidor();
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}	
}

class MarcoServidor extends JFrame implements Runnable {
	public MarcoServidor(){
		setBounds(1200,300,280,350);				
		JPanel milamina= new JPanel();
		milamina.setLayout(new BorderLayout());
		areatexto=new JTextArea();
	 
		milamina.add(areatexto,BorderLayout.CENTER);
		add(milamina);
		setVisible(true);
		//Creo mi hilo ---> Ejecuta mi funcion run()
		Thread mihilo = new Thread(this);
		mihilo.start();
	}
	private	JTextArea areatexto;
	public void run() {
		//El codigo que tiene que estar a la escucha
		System.out.println("Estoy en la escucha");
		try {
			//Creacion de un servidor a la escucha del puerto 9876
			ServerSocket servidor = new ServerSocket(9876);
			PaqueteEnvio paquete_recibido;
			Mensaje sms ;
			BufferedReader in = null; 
			while(true) {
				
//--------------------------------DETECTA ONLINE------------------------------------------------------//
				Socket cliente = servidor.accept();
//--------------------------------RECIBE INFO DEL CLIENTE---------------------------------------------//
				InetAddress localizacion = cliente.getInetAddress();
				String ipremota=localizacion.getHostAddress();
				System.out.println("IP REMOTA : "+ipremota);
				in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				String entrada = in.readLine().toString(); //Lo que llega del cliente
				System.out.println(entrada);
				PaqueteEnvio recibido = new PaqueteEnvio();
				sms = recibido.stringObjeto(entrada);
				System.out.println("Emisor     : "+sms.getNick());
				System.out.println("Ip Destino : "+sms.getIp());
				System.out.println("Mensaje    : "+sms.getMensaje());
				System.out.println("Receptor   : "+sms.getDestino());	
				//****Ya tengo el objeto a reenviar [sms]****
				//Imprimimos en el frame
				areatexto.append("\n"+sms.getNick()+" : "+ sms.getMensaje() +" para "+sms.getIp());
//------------------------ENVIA INFO AL CLIENTE QUE FUNCIONA COMO SERVIDOR-----------------------------//
				//Imprimimos en el frame
				System.out.println("REENVIO A SMS");
				Socket enviaDestinatario = new Socket(InetAddress.getByName("127.0.0.1"), 9090);
				//Envia el json al servidor
				DataOutputStream os = new DataOutputStream(enviaDestinatario.getOutputStream());
				PrintWriter pw = new PrintWriter(os);
				PaqueteEnvio envio = new PaqueteEnvio();
				pw.println(envio.objetoString(sms));
				pw.flush();
				cliente.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
