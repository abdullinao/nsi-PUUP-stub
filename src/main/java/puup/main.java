package puup;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class main {


    public static void main(String[] args) {
        long lastLaunch = 0;//время последнего запуска

        try {
            //  while (true) {
            mainJob();
            lastLaunch = System.currentTimeMillis();
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * вызов метода каждые сколько-то секунд
     */
    public static void mainJob() {
        //        try {
//            puup.soap.soap_initialize.eh_initialize(guidsToSend);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Runnable FUCKRUN = new Runnable() {
            public void run() {
                try {
                    ArrayList<String> guidsToSend = getChangedGuidsFromUfos();//получаем список гуидов из уфоса измененных
                    puup.soap.soap_initialize.eh_initialize(guidsToSend);//переотправляем их соапом
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(FUCKRUN, 0, 290, TimeUnit.SECONDS);


    }


    public static ArrayList<String> getChangedGuidsFromUfos() {

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
            // pimCon.close();
            UfosCon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("В уфосе изменилось: " + guidsArray.size());
        return guidsArray;
    }

}
