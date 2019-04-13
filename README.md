

1.Integrantes:
Alvaro Alvarez
Juan Cañiza
Pablo Escobar
Walter Jara
Sebastián Rojas

2.Requerimientos de instalación para modo desarrollador:
Registrarse en gitlab.com
Clonar el proyecto (git clone https://gitlab.com/peed96/sdtp1.git)
Instalar y ejecutar Eclipse IDE.
Abrir menú File y submenú Import.
Elegir Existing Maven Project y seleccionar Next.
Seleccionar la carpeta del proyecto sdtp1.
Marcar los proyecto de Cliente y Servidor y seleccionar Finish.
Realizar click derecho sobre el proyecto, luego submenú Maven y Update Project.
Marcar la opción Force Update y luego ok.

3.la estructura de Base de datos.


4.Cómo poblar los datos iniciales necesarios de Base de datos. 


5.Modo de compilar y ejecutar los componentes de servidor y cliente
Abrir menú File y submenú Export.
Elegir JAR File y seleccionar Next.
Seleccionar la carpeta del proyecto sdtp1.
Marcar los proyecto de Cliente y seleccionar Finish.
Repetir los pasos marcando el proyecto de Cliente.
Ejecutar los archivos .jar creados.

6.Documentación de un API de servicios ofrecidos
- Forma de invocación para Comunicarse y enviar información al Servidor 
		- Data OutputStream: Crea una nueva secuencia de salida de datos para escribir datos en la secuencia de salida subyacente especificada.
			DataOutputStream os;
		
		- MensajeCliente: Clase creada que contiene los siguientes atributos que serviran para completarse y enviarse.
			nick: Viene a ser el emisor
			ip  : Viene a ser la ip del receptor.
			mensaje : Viene a ser el mensaje que envia el emisor a receptor
			mensaje_error: Viene a ser la salida del mensaje de error
			estado : Viene a ser el estado de la transaccion
			MensajeCliente sms = new MensajeCliente(); 

		- InetAddress:Esta clase representa una dirección de Protocolo de Internet (IP).
		Una dirección IP es un número sin signo de 32 bits o 128 bits utilizado por IP, un protocolo de nivel inferior en el que se crean 			protocolos como UDP y TCP.
			InetAddress ip_servidor  = InetAddress.getByName("LA IP DEL EQUIPO A CONECTARSE EN FORMATO STRING");

		- int puerto_servidor = 9876; //Declaración de un puerto en especifico para la comunicación con el Servidor

		- Socket: Esta clase implementa sockets de cliente (también llamados simplemente "sockets"). Un zócalo es un punto final para la 			comunicación entre dos máquinas.
		
		reata un socket de flujo y lo conecta al número de puerto especificado en la dirección IP especificada.
		Si la aplicación ha especificado una fábrica de socket, se llama al método createSocketImpl de esa fábrica para crear la 			implementación del socket real. De lo contrario se crea un socket "plano".

		Parámetros:
		dirección - la dirección IP.
		puerto - el número de puerto.

			Socket cliente = new Socket(direccion, puerto);
			
		- Proceso de Envio  del json al servidor
			- Una vez creado la conección con el servidor, se crea el objeto "os" instanciando la clase DataOutputStream("OutputStream 				out"), que recibe como parametro el socket creado invocando a su metodo getOutputStream() que retorna un flujo de salida para 				este socket.
				os = new DataOutputStream(cliente.getOutputStream());

			-PrintWriter: Imprime representaciones formateadas de objetos en un flujo de salida de texto. Esta clase implementa todos los 				métodos de impresión encontrados en PrintStream. No contiene métodos para escribir bytes sin procesar, para los cuales un 				programa debe usar flujos de bytes no codificados. A diferencia de la clase PrintStream, si se habilita el vaciado automático, 				se realizará solo cuando se invoque uno de los métodos println, printf o format, en lugar de cada vez que salga un carácter de 				nueva línea. Estos métodos utilizan la propia noción de separador de línea de la plataforma en lugar del carácter de nueva 				línea.

			Crea un nuevo PrintWriter, sin lavado de línea automático, desde un OutputStream existente. Este práctico constructor crea el 				OutputStreamWriter intermedio necesario, que convertirá los caracteres en bytes utilizando la codificación de caracteres 				predeterminada.
				Parámetros:
					out - Un flujo de salida
		
				PrintWriter pw = new PrintWriter(os);

			- Se completa el objeto de la clase MensajeCliente() - (Completar todos los atributos)
				sms.setNick("texto");
				sms.setIp("texto"); //A quien va dirigido
				sms.setMensaje("texto");
				
			- Se crea un objeto de la clase PaqueteCliente()
					PaqueteCLiente envio = new PaqueteCLiente();

				- contiene el objeto sms de la clase MensajeCliente()
				- contiene un atributo llamado "solicitud" de tipo String, sirve para realizar la operación correspondiente dentro del 					servidor.
				
					El servidor suporta las siguiente solicitudes.
						- conectados
						- chat
				- Tiene además dos métodos para trabajar con Json
					- El metodo objetoStringChat() recibe dos parametros, y su funcion es la de convertir de objeto a String con 						formato json, para luego retornarlo.
						Parámetros:
							sms     - El combo completo de mensaje a enviarse.
							listaip - Lista de los equipos conectados.	

						public String objetoStringChat(MensajeCliente sms, ArrayList<String> listaip)

					- El metodo stringObjetoChat() recibe un parametro de tipo cadena y su funcion es la convertir de String a 						objeto pero completando los atributos de la clase PaqueteCliente sin la intención de poder retonarla 	
						public void stringObjetoChat(String str) 


			- Si se desea implementar para un chat en linea entonces completar con la especificacion de la solicitud
				envio.setSolicitud("chat"); //Donde envio es de la clase PaqueteCliente

			- Debe generar un string con el formato json de los datos a enviarse
				String conversion = envio.objetoStringChat(sms, null); //conversion de mensaje y lista de ip(null porque no es 																	necesario)
			- Imprime una cadena y luego termina la línea. Este método se comporta como si invocara print (String) y luego println ().
				Parámetros:
					conversion - el valor de la cadena a imprimir
				pw.println(conversion);


			- Limpia la comunicacion
				pw.flush();

			
			- Cierra el cliente socket
				cliente.close();




- Forma de invocación para Comunicarse y recibir información al Servidor 
		- Socket: Esta clase implementa sockets de cliente (también llamados simplemente "sockets"). Un zócalo es un punto final para la 			comunicación entre dos máquinas.
		Socket cliente;
		
		- Se crea un objeto de la clase PaqueteCliente(), su funcion será la de recepcionar el json que llegue
			PaqueteCLiente paqueteRecibido = new PaqueteCLiente();

			- contiene el objeto sms de la clase MensajeCliente()
			- contiene un atributo llamado "solicitud" de tipo String, sirve para realizar la operación correspondiente dentro del 				servidor.
				
				El servidor suporta las siguiente solicitudes.
					- conectados
					- chat
				- Tiene además dos métodos para trabajar con Json
					- El metodo objetoStringChat() recibe dos parametros, y su funcion es la de convertir de objeto a String con 						formato json, para luego retornarlo.
						Parámetros:
							sms     - El combo completo de mensaje a enviarse.
							listaip - Lista de los equipos conectados.	

						public String objetoStringChat(MensajeCliente sms, ArrayList<String> listaip)

					- El metodo stringObjetoChat() recibe un parametro de tipo cadena y su funcion es la convertir de String a 						objeto pero completando los atributos de la clase PaqueteCliente sin la intención de poder retonarla 	
						public void stringObjetoChat(String str) 
			
		- MensajeCliente: Clase creada que contiene los siguientes atributos que serviran para completarse una vez que se recepcionen los 			datos traidos del servidor, previamente casteados(llega en formato json y se debe convertir a objeto).
			nick: Viene a ser el emisor
			ip  : Viene a ser la ip del receptor.
			mensaje : Viene a ser el mensaje que envia el emisor a receptor
			mensaje_error: Viene a ser la salida del mensaje de error
			estado : Viene a ser el estado de la transaccion
			MensajeCliente sms = new MensajeCliente(); 

		- BufferedReader: Lee el texto de un flujo de entrada de caracteres, y almacena en búfer los caracteres para proporcionar una lectura 			eficiente de los caracteres, matrices y líneas.
		Se puede especificar el tamaño del búfer o se puede usar el tamaño predeterminado. El valor predeterminado es lo suficientemente 			grande para la mayoría de los propósitos.

		En general, cada solicitud de lectura hecha de un Reader hace que se realice una solicitud de lectura correspondiente del carácter 			subyacente o del flujo de bytes. Por lo tanto, es recomendable envolver un BufferedReader alrededor de cualquier Reader cuyas 			operaciones de lectura () puedan ser costosas, como FileReaders y InputStreamReaders.

			BufferedReader in = null; 

		- ServerSocket: Crea un socket de servidor, enlazado al puerto especificado.
			ServerSocket servidor_cliente = new ServerSocket(9090);


		- Escucha una conexión a este socket y la acepta. El método bloquea hasta que se realiza una conexión.
			Retorna un nuevo socket
				cliente = servidor_cliente.accept();

		- creación del objeto "in", Crea una secuencia de entrada de caracteres de búfer que utiliza un búfer de entrada de tamaño    			predeterminado.
			InputStreamReader: Crea un InputStreamReader que usa el conjunto de caracteres predeterminado.
				in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

		- readLine() metodo de la clase BufferedReader: Lee una línea de texto. Una línea se considera terminada por cualquiera de un salto de 				línea ('\ n'), un retorno de carro ('\ r'), o un retorno de carro seguido inmediatamente por un salto de línea.
			Devoluciones:
				Una cadena que contiene el contenido de la línea, sin incluir los caracteres de terminación de línea, o nula si se ha 					llegado al final de la secuencia
			String entrada = in.readLine()
			
		
		- Una vez que ya tengo mi entrada de datos, proceso a convertir de Json a Objeto
			Partes:
				recibido: es un objeto de la clase PaqueteCliente()
				stringObjetoChat() : Es el metodo que convierte de stringJson a objeto siguiendo el formato del Chat
				entrada : Es el json en formato String 
			recibido.stringObjetoChat(entrada); 
		
		- Consultar si el paque recibido llego en condiciones optimas y la operacion es "chat"
			if(recibido.getSolicitud().equals("chat") && recibido.getSms().getMensaje_error().equals("ok"))
		
		- Si el caso es exitoso imprimir lo que llega del servidor

		- Si fallo la operacion te retornará un error y mensaje indicando el problema
























