package puup.bd;

import puup.utils.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class ufos {

    private static final Logger logger = LogManager.getLogger();
    // logger.info("коннект с пим установлен");
//  logger.error("Ошибка подключения к бд пим", e);

    public static ArrayList<String> getChangedGuidsFromUfos() {

        Connection UfosCon = null;
        ArrayList<String> guidsArray = new ArrayList<String>();


        //получение соединения с бд и делаем свои бд приколы
        try {

          //  System.out.println("пробую получить коннект с бд уфос");
            //pimCon = bdConn.getPimConn();
            UfosCon = bdConn.getUfosConn();


            Statement UfosStatement = UfosCon.createStatement();
            ResultSet UfosResult = UfosStatement.executeQuery(utils.ChangedInUfosPersaccounts()); // вставить скл запрос

            while (UfosResult.next()) {
                // UfosResult.next();
                guidsArray.add(UfosResult.getString(1));
                logger.info("getChangedGuidsFromUfos " + UfosResult.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            for (int i = 0; i < guidsArray.size(); ++i) {
//
//                System.out.println(guidsArray.get(i));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//рвем коннект.
        try {
            UfosCon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.print(" В уфосе изменилось: " + guidsArray.size()+";");
        return guidsArray;
    }
}
