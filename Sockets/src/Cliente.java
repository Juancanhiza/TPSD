
//Para el grafico
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
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
		}	
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	public LaminaMarcoCliente(){
		nick=new JTextField(5);
		add(nick);
		JLabel texto=new JLabel("--CHAT--");
		add(texto);
		ip=new JTextField(5);
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

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//System.out.println(mensaje.getText() );}
			try {
				//Creación de cliente
				Socket cliente = new Socket("127.0.0.1",9876);
				PaqueteEnvio datos = new PaqueteEnvio();
				//Creo un objeto datos con los parametros completados en el frame
				datos.setNick(nick.getText());
				datos.setIp(ip.getText());
				datos.setMensaje(mensaje.getText());
				//Para crear un flujo de datos para enviar un objeto
				ObjectOutputStream paquete_datos=new ObjectOutputStream(cliente.getOutputStream());
				paquete_datos.writeObject(datos);
				cliente.close();
			
				
				
				/*FORMA 1
				//Creación de cliente
				Socket cliente = new Socket("127.0.0.1",9876);
				//COnstruccion de flujo de datos de salida y va a circular por el socket cliente
				DataOutputStream flujo_salida = new DataOutputStream(cliente.getOutputStream());
				//En el flujo de datos se escribirá lo que le pasamos como parametro que viajará por el socket
				flujo_salida.writeUTF(campo1.getText());
				//Cerrar el flujo
				flujo_salida.close();
				*/
				
				
			}catch(UnknownHostException e1) {
				e1.printStackTrace();
			}catch(IOException e1) {
				System.out.println(e1.getMessage());
			}
		}
	}
		
		
	private JTextField mensaje;
	private JButton miboton;
	
	private JTextField nick;
	private JTextField ip;
	private JTextArea campochat;
	
	@Override
	public void run() {
		try {
			ServerSocket servidor_cliente = new ServerSocket(9090);
			Socket cliente;
			PaqueteEnvio paqueteRecibido;
			while(true) {
				cliente = servidor_cliente.accept();
				ObjectInputStream flujoentrada=new ObjectInputStream(cliente.getInputStream());
				paqueteRecibido=(PaqueteEnvio)flujoentrada.readObject();
				campochat.append("\n"+ paqueteRecibido.getNick()+" : "+paqueteRecibido.getMensaje());
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}