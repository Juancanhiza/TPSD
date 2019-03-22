/*
 * Hará 2 tareas
 * 1)En primer plano tendrá que recibir texto en su frame
 * 2)En segundo plano tendrá que estar a la escucha constantemente y tener el puerto abierto 
 * */

import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		JLabel texto=new JLabel("--CHAT--"); 
		milamina.add(areatexto,BorderLayout.CENTER);
		add(milamina);
		setVisible(true);
		//Creo mi hilo ---> Ejecuta mi funcion run()
		Thread mihilo = new Thread(this);
		mihilo.start();
	}
	private	JTextArea areatexto;
	@Override
	public void run() {
		//El codigo que tiene que estar a la escucha
		System.out.println("Estoy en la escucha");
		try {
			//Creacion de un servidor a la escucha del puerto 9876
			ServerSocket servidor = new ServerSocket(9876);
			String nick,ip,mensaje;
			PaqueteEnvio paquete_recibido;
			while(true) {
				//Que acepte las conecciones que le viene del exterior
				Socket cliente = servidor.accept();
				//Proceso para recibir un objeto enviado por el cliente conectado
				ObjectInputStream paquete_datos=new ObjectInputStream(cliente.getInputStream());
				paquete_recibido=(PaqueteEnvio)paquete_datos.readObject();
				//Acceder al paquete recibido 
				nick=paquete_recibido.getNick();
				ip=paquete_recibido.getIp();
				mensaje=paquete_recibido.getMensaje();
				//Imprimimos en el frame
				areatexto.append("\n"+nick+" : "+ mensaje +" para "+ip);
				
				
				Socket enviaDestinatario = new Socket(ip,9090);
				ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
				paqueteReenvio.writeObject(paquete_recibido);
				enviaDestinatario.close();
				paqueteReenvio.close();
				
				/*
				//Crear un flujo de entrada que le llega e un cliente
				DataInputStream flujo_entrada = new DataInputStream(cliente.getInputStream());
				//Tengo el texto en formato String
				String mensaje = flujo_entrada.readUTF();
				//Para escribirlo en el area de texto
				areatexto.append("\nCliente : "+mensaje);
				*/
				cliente.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
