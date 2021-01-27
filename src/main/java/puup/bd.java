package puup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bd {
    private static prop prop;

    public static Connection getPimConn() {
        test();

        Connection pimCon = null;
        try {
            pimCon = DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + prop.getPimBdurl()
                            + ":" + prop.getPimPort() + "/" + prop.getPimServicename() + " "
                    , prop.getPimPort(), prop.getPimPass());
        } catch (SQLException e) {

            System.out.println("Ошибка подключения к бд пим!");
            e.printStackTrace();


        }
        return pimCon;
    }


    public static Connection getUfosConn() {
        test();
        Connection ufosCon = null;
        try {

            ufosCon = DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + prop.getUfosBdurl()
                            + ":" + prop.getUfosPort() + "/" + prop.getUfosServicename() + " "
                    , prop.getUfosUser(), prop.getUfosPass());

        } catch (SQLException e) {

            System.out.println("Ошибка подключения к бд пим!");
            e.printStackTrace();


        }
        return ufosCon;
    }

    private static void test() {
        System.out.println("-------- Попытка подключения к бд... ------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Не нашел драйвер!");
            e.printStackTrace();

        }

        System.out.println("Драйвер найден! Подключаемся...");

    }
}
