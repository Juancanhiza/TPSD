package py.una.sd.tp1.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bd {	/*Clase que representa la conexion con la base de datos*/

    private static final String url = "jdbc:postgresql://localhost/bdsd";
    private static final String user = "rol";
    private static final String password = "admin";
 
    /**
     * @return objeto Connection 
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
