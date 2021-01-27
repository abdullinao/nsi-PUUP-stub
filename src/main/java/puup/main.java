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

            while (UfosResult != null) {
                UfosResult.next();
                guidsArray.add(UfosResult.getString(1));
            }


            /**
             *
             C:\Users\aabdullin\stub>java -Xmx32m -Duser.language=en -Duser.country=US -jar n
             si-PUUP-stub-1.0-SNAPSHOT.jar
             пробую получить коннект с бд уфос
             -------- Попытка подключения к бд... ------
             Драйвер найден! Подключаемся...
             завершил фазу тест
             загружеенные параметры:
             тнс: ebgz1
             user: otr_aabdullin
             pwd: as]lRahj
             jdbc:oracle:thin:@ebgz1
             начинаю уфоскон
             получил уфос кон
             java.sql.SQLException: Result set after last row
             at oracle.jdbc.driver.GeneratedScrollableResultSet.getString(GeneratedSc
             rollableResultSet.java:409)
             at puup.main.mainJob(main.java:40)
             at puup.main.main(main.java:14)
             2aba7b9e-f51b-4039-80f0-2f24ac0905ef
             f8a73610-8c60-4397-93a4-cb699427f991
             fdcb6563-0588-4cb1-9027-6aa2f80afc56
             51e8ac55-f13c-4e80-8d5c-28eca0193fd0
             5bb46dba-8f84-4699-bba8-218f21ff1a70
             aa5b3a00-7e25-4c28-a750-ff1e2a949ce2
             39df3362-60b0-4110-85f7-5984633cb4e0
             b39c761a-5943-4a4b-afa0-ca2dcbaf4116
             bb43feed-1735-4e8a-911f-bcfeda9579a1
             f3474a65-5c99-416c-ab2a-f5f13448c120
             java.lang.NullPointerException
             at puup.main.mainJob(main.java:56)
             at puup.main.main(main.java:14)

             C:\Users\aabdullin\stub>PAUSE
             Для продолжения нажмите любую клавишу . . .
             *
             *
             *
             */



        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < guidsArray.size(); ++i) {

            System.out.println(guidsArray.get(i));
        }

//рвем коннект.
        try {
            pimCon.close();
            UfosCon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
