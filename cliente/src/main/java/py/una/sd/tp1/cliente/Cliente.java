package py.una.sd.tp1.cliente;
//Para el grafico
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import py.una.sd.tp1.entidad.ErrorCliente;

//Creados por el usuario
import py.una.sd.tp1.entidad.MensajeCliente;

import py.una.sd.tp1.entidad.PaqueteCLiente;


//Para el sockets
import java.net.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class Cliente {
	/*Método main*/
	public static void main(String[] args) throws UnknownHostException {
		
		//Muestro la ventana Principal de mi programa
		MarcoPrincipal mimarco=new MarcoPrincipal();
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


/*Clase que muestra el menú principal*/
class MarcoPrincipal extends JFrame{
	public MarcoPrincipal() {
		setBounds(40,100,100,200);	
		LaminaMarcoPrincipal milamina = new LaminaMarcoPrincipal();
		add(milamina);
		setVisible(true);
	}
	
}

/*MENU que se encarga de realizar el proceso del chat*/
class MarcoCliente extends JFrame{
	public MarcoCliente(){
		setBounds(600,300,280,350);	
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		add(milamina);
		setVisible(true);
			
			//Envio la señal
			//addWindowListener(new EnvioOnline());
	}	
}
//-----------------------------------------------ENVIO DE SEÑAL ONLINE---------------------------------------------
//Esta clase servirá para ejecutarse al abrirse el frame, su función será avisarle al servidor quien es
class EnvioOnline extends WindowAdapter{
	public void windowOpened(WindowEvent e) {
		try {
			String ip_servidor = "127.0.0.1";
			int puerto_servidor=9876;
			Socket misocket = new Socket(ip_servidor,puerto_servidor);
			PaqueteCLiente datos = new PaqueteCLiente();
			//datos.setMensaje("online");
			ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());
			paquete_datos.writeObject(datos);
			misocket.close();
		}catch(Exception e2) {
			
		}
	}
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	public LaminaMarcoCliente(){
		String nick_usuario=JOptionPane.showInputDialog("Nick : ");
		JLabel n_nick = new JLabel("Nick : ");
		nick=new JLabel();
		nick.setText(nick_usuario);
		add(nick);
		JLabel texto=new JLabel("Online : ");
		add(texto);
		ip=new JComboBox();
		
		//En vez de "Usuario x" poner la ip de la persona con quien chatear 
		ip.addItem("127.0.0.1");
		ip.addItem("127.0.0.1");
		ip.addItem("127.0.0.1");
		ip.addItem("127.0.0.1");
		add(ip);
		campochat=new JTextArea(12,20);
		add(campochat);
		mensaje=new JTextField(20);
		add(mensaje);	
		
		//Boton para enviar texto al servidor
		btnEnviar=new JButton("Enviar");
		EnviaTexto mievento2 = new EnviaTexto();
		btnEnviar.addActionListener(mievento2);
		add(btnEnviar);	
		
		
		Thread mihilo = new Thread(this);
		mihilo.start();
	}
	
	/*Me va a permitir mostrar todos los clientes conectados
	 * actualmente en el sistema, una vez que presione el botón
	 * "Ver Persona"
	 */
	
	/*
	 * Esta clase se acciona al presionar el botón "Enviar"
	 * Se encarga de enviar un mensaje con los parámetros correspondientes
	 * - consulta_servidor: contiene un indicador de la acción que solicita
	 * - Información del usuario que quiere escribir con otra persona
	 * 		- Nombre del emisor
	 * 		- Ip del Receptor (Mapear con un nombre)
	 * 		- Mensaje 
	 * 		 
	 * */
	private class EnviaTexto implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			campochat.append("\n"+nick.getText() +" : "+ mensaje.getText());
			
			/******INICIO-ACCION********PARA ENVIAR UN MENSAJE DE EMISOR A RECEPTOR AL SERVIDOR******************/
			DataOutputStream os;
			MensajeCliente sms = new MensajeCliente();
			try {
				//Creación de cliente
				InetAddress ip_servidor  = InetAddress.getByName("127.0.0.1");
				int puerto_servidor = 9876;
				Socket cliente = new Socket(ip_servidor, puerto_servidor);
			
				//Envia el json al servidor
				os = new DataOutputStream(cliente.getOutputStream());
				PrintWriter pw = new PrintWriter(os);
				sms.setNick(nick.getText());
				sms.setIp(ip.getSelectedItem().toString()); //A quien va dirigido
				sms.setMensaje(mensaje.getText());
				PaqueteCLiente envio = new PaqueteCLiente();
				envio.setSolicitud("chat"); 					       //Sirve para indicar que el proceso va a ser "chat" 
				String conversion = envio.objetoStringChat(sms, null); //Proceso conversion de mensaje y lista de ip(null porque no es necesario) a String
				pw.println(conversion);
				System.out.println("ENVIA --> "+conversion);
				pw.flush();			
				cliente.close();
			}catch(UnknownHostException e1) {
				e1.printStackTrace();
			}catch(IOException e1) {
				System.out.println(e1.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/****************************FIN-ACCION************************************/
	}
	
	private class VerPersona implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			System.out.println("HOLA MUNDO");
			//Envia una consulta al servidor para ver si le responde
			
			
			//Dependiendo de lo que le responde existen dos casos
			
			
			//llego bien, muestro una ventana independiente mostrando 
			//las personas que estan en linea
			
			
			
			//En caso contrario, muestro un combobox con mensaje de error
			
		}
	}
	private JTextField mensaje;
	private JButton btnEnviar;
	private JLabel nick;
	private JTextArea campochat;
	private JComboBox ip;
	
	public void run() {
		try {
			System.out.println("********************");
			System.out.println("********************");
			System.out.println("******CLIENTE*******");
			System.out.println("********************");
			System.out.println("********************");
			ServerSocket servidor_cliente = new ServerSocket(9090);
			Socket cliente;
			PaqueteCLiente paqueteRecibido;
			MensajeCliente sms;
			BufferedReader in = null; 
			while(true) {
				//Que acepte las conecciones que le viene del exterior
				cliente = servidor_cliente.accept();
			    if(cliente.isConnected()) {
	//--------------------------------DETECTA ONLINE------------------------------------------------------//
					InetAddress localizacion = cliente.getInetAddress();
					String ipremota=localizacion.getHostAddress();
					System.out.println("IP QUE LLLEGA : "+ipremota);
					in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
					String entrada = in.readLine().toString(); //Lo que llega del emisor pasando por el servidor y retomando al receptor
					System.out.println("RECIBE --->"+entrada);
					if(entrada != null) {
						PaqueteCLiente recibido = new PaqueteCLiente();
						recibido.stringObjetoChat(entrada); //Pasa de StringJson a Objeto
						if(recibido.getSolicitud().equals("chat") && recibido.getSms().getMensaje_error().equals("ok")) {
							sms = recibido.getSms();
							campochat.append("\n"+sms.getNick() +" : "+ sms.getMensaje());
							//cliente.close();
							/*System.out.println("Emisor     : "+sms.getNick());
							System.out.println("Ip Destino : "+sms.getIp());
							System.out.println("Mensaje    : "+sms.getMensaje());
							//System.out.println("Receptor   : "+sms.getDestino());
							System.out.println("estado     : "+sms.getEstado());
							System.out.println("sms error  : "+sms.getMensaje_error());
							campochat.append("\n"+sms.getNick() +" : "+ sms.getMensaje());
							cliente.close();*/
						}else if(!recibido.getSms().getMensaje_error().equals("ok")) {
							
						}
					}	
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

class LaminaMarcoPrincipal extends JPanel {
	public LaminaMarcoPrincipal(){
		//Boton para visualizar personas
		btnVerConect=new JButton("Ver Personas");
		VerPersona mievento1 = new VerPersona();
		btnVerConect.addActionListener(mievento1);
		add(btnVerConect);
		
		//Boton para enviar texto al servidor
		btnChat=new JButton("Chat");
		EnviaTexto mievento2 = new EnviaTexto();
		btnChat.addActionListener(mievento2);
		add(btnChat);	
	}
	
	/*Me va a permitir mostrar todos los clientes conectados
	 * actualmente en el sistema, una vez que presione el botón
	 * "Ver Persona"
	 */
	private class VerPersona implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("HOLA MUNDO");
			//Envia una consulta al servidor para ver si le responde
			
			
			//Dependiendo de lo que le responde existen dos casos
			
			
			//llego bien, muestro una ventana independiente mostrando 
			//las personas que estan en linea
			
			
			
			//En caso contrario, muestro un combobox con mensaje de error
			
		}
	}
	

	/*
	 * Esta clase se acciona al presionar el botón "Enviar"
	 * Se encarga de enviar un mensaje con los parámetros correspondientes
	 * - consulta_servidor: contiene un indicador de la acción que solicita
	 * - Información del usuario que quiere escribir con otra persona
	 * 		- Nombre del emisor
	 * 		- Ip del Receptor (Mapear con un nombre)
	 * 		- Mensaje 
	 * 		 
	 * */
	private class EnviaTexto implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//Muestro la ventana Principal de mi programa
			MarcoCliente mimarco=new MarcoCliente();
			mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	private JButton btnVerConect; //Boton para ver personas conectadas
	private JTextField mensaje;
	private JButton btnChat;      //Boton para chatear con otra persona
	private JLabel nick;
	private JTextArea campochat;
	private JComboBox ip;
	
}