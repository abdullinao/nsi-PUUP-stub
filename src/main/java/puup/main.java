package puup;

import java.sql.*;
import java.util.ArrayList;

public class main {


    private static prop prop;

    public static void main(String[] args) {
        try {
            // while (true) {
            mainJob();
            //  }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void mainJob() {
        Connection pimCon = null;
        Connection UfosCon = null;
        ArrayList<String> guidsArray = new ArrayList<String>();


        //получение соединения с бд и делаем свои бд приколы
        try {

            System.out.println("пробую получить коннект с бд уфос");
            //pimCon = bdConn.getPimConn();
            UfosCon = bdConn.getUfosConn();


            Statement UfosStatement = UfosCon.createStatement();
            ResultSet UfosResult = UfosStatement.executeQuery(utils.UfosSqlReq()); // вставить скл запрос

            while (UfosResult.next()) {
                // UfosResult.next();
                guidsArray.add(UfosResult.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < guidsArray.size(); ++i) {

                System.out.println(guidsArray.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//рвем коннект.
        try {
            // pimCon.close();
            UfosCon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("конец");
    }

}
