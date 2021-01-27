package puup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bdConn {
    private static prop prop1;

    static {
        try {
            prop1 = new prop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getPimConn() {
        test();

        Connection pimCon = null;
        try {
            pimCon = DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + prop1.getPimBdurl()
                            + ":" + prop1.getPimPort() + "/" + prop1.getPimServicename() + " "
                    , prop1.getPimPort(), prop1.getPimPass());
        } catch (SQLException e) {

            System.out.println("Ошибка подключения к бд пим!");
            e.printStackTrace();


        }
        return pimCon;
    }


    public static Connection getUfosConn() {
        test();
      //  System.out.println("завершил фазу тест");
        Connection ufosCon = null;
        try {

//            ufosCon = DriverManager.getConnection(
//                    "jdbc:oracle:thin:@" + prop.getUfosBdurl()
//                            + ":" + prop.getUfosPort() + "/" + prop.getUfosServicename() + " "
//                    , prop.getUfosUser(), prop.getUfosPass());
//
//
         //   System.out.println("загружеенные параметры:");
         //   System.out.println("тнс: " + prop1.getUfosTns());
          //  System.out.println("user: " + prop1.getUfosUser());
          //  System.out.println("pwd: " + prop1.getUfosPass());


            String dbUrl = "jdbc:oracle:thin:@" + prop1.getUfosTns();

          //  System.out.println(dbUrl);

           // System.out.println("начинаю уфоскон");
            ufosCon = DriverManager.getConnection(dbUrl, prop1.getUfosUser(), prop1.getUfosPass());

            //System.out.println("получил уфос кон");
        } catch (SQLException e) {

            System.out.println("Ошибка подключения к бд");
            e.printStackTrace();


        }
        return ufosCon;
    }

    private static void test() {
      //  System.out.println("-------- Попытка подключения к бд... ------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Не нашел драйвер!");
            e.printStackTrace();

        }

     //   System.out.println("Драйвер найден! Подключаемся...");

    }
}
