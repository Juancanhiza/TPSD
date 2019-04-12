package py.una.sd.tp1.server;

/* Hará 2 tareas
* 1)En primer plano tendrá que recibir texto en su frame
* 2)En segundo plano tendrá que estar a la escucha constantemente y tener el puerto abierto 
* */
import javax.swing.*;
import org.json.simple.JSONObject;

import py.una.sd.tp1.entidad.ErrorServidor;
import py.una.sd.tp1.entidad.MensajeServidor;
import py.una.sd.tp1.entidad.PaqueteServidor;
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
import java.util.ArrayList;

public class Servidor  {
	public static void main(String[] args) {
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
	
	class BusquedaAlgoritmo {
		public int buscar( String[] arreglo, String dato) {
		 int inicio = 0;
		 int fin = arreglo.length - 1;
		 int pos; //Retorna la posicion si encuentra
		 while (inicio <= fin) {
		     pos = (inicio+fin) / 2;
		     if ( arreglo[pos].equals(dato))
		       return pos;
		     else if ( arreglo[pos].compareTo(dato) > 0) {
		    	 inicio = pos+1;
		     } else if (arreglo[pos].compareTo(dato) < 0){
		    	 fin = pos-1;
		     }
		 }
		 return -1;
	    }
	}
	
	
	
	
	
	
	
	
	private	JTextArea areatexto;
	ArrayList <String> listaip; 
	private String[] ips;
	
	public void run() {
		//El codigo que tiene que estar a la escucha
		System.out.println("Estoy en la escucha");
		System.out.println("********************");
		System.out.println("********************");
		System.out.println("******SERVIDOR******");
		System.out.println("********************");
		System.out.println("********************");
		
		try {
			//Creacion de un servidor a la escucha del puerto 9876
			ServerSocket servidor = new ServerSocket(9876);
			listaip = new ArrayList<String>();
			BusquedaAlgoritmo ba = new BusquedaAlgoritmo();
			//ErrorServidor error = new ErrorServidor();
			while(true) {
				PaqueteServidor paquete_recibido = null;
				MensajeServidor sms = null ;
				BufferedReader in = null; 
//--------------------------------DETECTA ONLINE------------------------------------------------------//
				Socket cliente = servidor.accept();
			    if(cliente.isConnected()) {
					//Para obtener la direccion ip del cliente que se acaba de conectar
					InetAddress localizacion = cliente.getInetAddress();
					String ip_cliente_conectado = localizacion.getHostAddress();
					//Para agregar la ip del cliente que se conecta al arraylist
					
	//--------------------------------RECIBE INFO DEL CLIENTE---------------------------------------------//
					in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
					String entrada = in.readLine(); //Lo que llega del cliente
					System.out.println("RECIBE DEL EMISOR --->"+entrada);
					//Validacion de lo que ingresa
					if(entrada != null) {  //EN CASO DE QUE EXISTA ENTRADA INGRESA EN ESTA SECCION
						
						/*if(listaip.size()>0) {
							ips = (String[]) listaip.toArray(); //Array que contiene la lista de ip's de los equipos conectados
							int tamanio = ips.length();
							//Realizo busqueda binaria para ver si el equipo ya está conectado, en caso contrario se le agrega a la lista
							int salida = ba.buscar(ips, ip_cliente_conectado);
							if(salida == -1) {//No encontró y se agrega a la lista
								//Agrega a la lista el ip del equipo que envia informacion
								listaip.add(ip_cliente_conectado);
							}
						}else {
							//Agrega a la lista el ip del equipo que envia informacion
							listaip.add(ip_cliente_conectado);
						}
						
						//Se imprime el arraylist de ips conectadas al servidor
						System.out.print("[ ");
						for(String z:listaip) {
							System.out.print(", "+z);
						}
						System.out.print(" ]\n");*/
						
						
						
						//Imprime el mensaje recibido en formato json
						System.out.println(entrada.toString());
						PaqueteServidor recibido = new PaqueteServidor();
						recibido.stringObjetoChat(entrada);
						//System.out.println("solicitud  : "+recibido.getSolicitud());
						//****Ya tengo el objeto a reenviar [sms]****
						recibido.setListaip(listaip); //Almaceno la lista de los ips conectados

			/***************CONSULTO LO QUE ME LLEGA DEL CLIENTE PARA REALIZAR UNA ACCION ********************/
						if(recibido.getSolicitud().equals("chat")) {
							//Mensaje recibido
							sms = recibido.getSms();
							/*System.out.println("MENSAJE RECIBIDO DEL EMISOR");
							System.out.println("Emisor     : "+sms.getNick());
							System.out.println("Ip Destino : "+sms.getIp());
							System.out.println("Mensaje    : "+sms.getMensaje());
							System.out.println("Receptor   : "+sms.getDestino());*/
							sms.setEstado(0);             //Operacion exitosa
							sms.setMensaje_error("ok");	  //Mensaje de la operacion
							//Imprimimos en el frame
							System.out.println("\n"+sms.getNick()+" : "+ sms.getMensaje() +" para "+sms.getIp());
							areatexto.append("\n"+sms.getNick()+" : "+ sms.getMensaje() +" para "+sms.getIp());
					//------------------------ENVIA INFO AL CLIENTE QUE FUNCIONA COMO SERVIDOR-----------------------------//
							System.out.println("REENVIO DE SMS AL RECEPTOR CON IP: "+sms.getIp());
							Socket enviaDestinatario = new Socket(InetAddress.getByName(sms.getIp()), 9090);
							//Envia el json al servidor
							DataOutputStream os = new DataOutputStream(enviaDestinatario.getOutputStream());
							PrintWriter pw = new PrintWriter(os);
							PaqueteServidor envio = new PaqueteServidor();
							envio.setSolicitud("chat"); 
							String reenvio = envio.objetoStringChat(sms,listaip);
							System.out.println("ENVIA AL RECEPTOR --->   : "+reenvio);
							pw.println(reenvio);
							pw.flush();
							//enviaDestinatario.close();
						}
					System.out.println("\n\n\n");
					}else {
						System.out.println("NO LLEGÓ NADA");
					}		
				}
					
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
