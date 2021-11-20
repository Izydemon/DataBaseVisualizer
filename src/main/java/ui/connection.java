package ui;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class connection {

    static Connection cn;

    static boolean connect(String username, String password) {
        boolean res = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/diu?zeroDateTimeBehavior=CONVERT_TO_NULL", username, password);
            //cn = DriverManager.getConnection("jdbc:mysql://mozart.dis.ulpgc.es/DIU_BD?useSSL=true",username,password);
            dataBaseView dataBase = new dataBaseView();
            dataBase.setVisible(true);
            res = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Not Connected");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    static ResultSet readTables() throws SQLException {
        DatabaseMetaData md = cn.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = md.getTables(null, null, "%", types);

        return rs;
    }

    static List<String> getColums(List<String> tables) throws SQLException {
        List<String> columns = new ArrayList<>();
        DatabaseMetaData md = cn.getMetaData();
        for (String table : tables) {
            ResultSet rs = md.getColumns(null, null, table, null);
            while (rs.next()) {
                String nombreCampo = rs.getString("COLUMN_NAME");
                columns.add(table + "." + nombreCampo);
            }
        }
        return columns;
    }

    static void closeSession() throws SQLException {
        cn.close();
    }

}
