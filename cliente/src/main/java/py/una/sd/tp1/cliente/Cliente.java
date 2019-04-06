package py.una.sd.tp1.cliente;

//Para el grafico
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.swing.*;

import py.una.sd.tp1.entidad.Mensaje;
import py.una.sd.tp1.entidad.PaqueteEnvio;

//Para el sockets
import java.net.*; 

public class Cliente {
	public static void main(String[] args) {
		MarcoCliente mimarco=new MarcoCliente();
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


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
			PaqueteEnvio datos = new PaqueteEnvio();
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
		ip.addItem("Usuario 1");
		ip.addItem("Usuario 2");
		ip.addItem("Usuario 3");
		ip.addItem("Usuario 4");
		add(ip);
		campochat=new JTextArea(12,20);
		add(campochat);
		mensaje=new JTextField(20);
		add(mensaje);		
		miboton=new JButton("Enviar");
		EnviaTexto mievento = new EnviaTexto();
		miboton.addActionListener(mievento);
		add(miboton);	
		Thread mihilo = new Thread(this);
		mihilo.start();
	}
	

	private class EnviaTexto implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//System.out.println(mensaje.getText() );}
			campochat.append("\n"+nick.getText() +" : "+ mensaje.getText());
			DataOutputStream os;
			Mensaje sms = new Mensaje();
			try {
				//Creación de cliente
				Socket cliente = new Socket(InetAddress.getByName("127.0.0.1"), 9876);
				
				//Envia el json al servidor
				os = new DataOutputStream(cliente.getOutputStream());
				PrintWriter pw = new PrintWriter(os);
				sms.setNick(nick.getText());
				sms.setIp(ip.getSelectedItem().toString()); //A quien va dirigido
				sms.setMensaje(mensaje.getText());
				PaqueteEnvio envio = new PaqueteEnvio();
				pw.println(envio.objetoString(sms));
				pw.flush();
				cliente.close();
				
			}catch(UnknownHostException e1) {
				e1.printStackTrace();
			}catch(IOException e1) {
				System.out.println(e1.getMessage());
			}
		}
	}
		
	private JTextField mensaje;
	private JButton miboton;
	private JLabel nick;
	private JTextArea campochat;
	private JComboBox ip;
	
	public void run() {
		try {
			ServerSocket servidor_cliente = new ServerSocket(9090);
			Socket cliente;
			PaqueteEnvio paqueteRecibido;
			Mensaje sms;
			BufferedReader in = null; 
			while(true) {
				//Que acepte las conecciones que le viene del exterior
				cliente = servidor_cliente.accept();
//--------------------------------DETECTA ONLINE------------------------------------------------------//
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
				campochat.append("\n"+sms.getNick() +" : "+ sms.getMensaje());
				//Imprimimos en el frame
				//areatexto.append("\n"+sms.getNick()+" : "+ sms.getMensaje() +" para "+sms.getIp());
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}