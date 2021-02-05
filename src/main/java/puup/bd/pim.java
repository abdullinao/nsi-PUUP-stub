package puup.bd;

import puup.soap.soap_generator;
import puup.utils.prop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class pim {
    private static puup.utils.prop prop1;

    static {
        try {
            prop1 = new prop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SQLexecute(ArrayList<String> guidsArray) throws SQLException {
        if (prop1.getSendPimConf().equalsIgnoreCase("y")) {
            Connection pimCon = null;
            try {
                pimCon = bdConn.getPimConn();
                Statement pimStatement = pimCon.createStatement();
                try {
                    for (int i = 0; i < guidsArray.size(); ++i) {

                        pimStatement.execute(puup.utils.utils.pimSqlReq(guidsArray.get(i)));//отправляем скл запрос,запрос записан
                        //в утилсе, в утилс передается гуид из массива
                        // System.out.print(" pim - ok: " + guidsArray.get(i) + ";");
                    }
                    //   System.out.print(" из пим: "+guidsArray.size()+";");
                } catch (NullPointerException np) {
                 //   System.out.print(" нет гуидов для отправки из пим;");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            pimCon.close();
        } else System.out.println(" отправка из пим отключена.");
    }



    public static ArrayList<String> SverkaUfosPim() {

        Connection pimCon = null;
        ArrayList<String> guidsSverArray = new ArrayList<String>();
        //получение соединения с бд и делаем свои бд приколы
        try {
            pimCon = bdConn.getPimConn();
            Statement pimSverStatement = pimCon.createStatement();
            ResultSet PimSverResult = pimSverStatement.executeQuery(puup.utils.utils.UfosPimStatSver());//выполнение запроса
            while (PimSverResult.next()) {
                guidsSverArray.add(PimSverResult.getString(1));//заполнение массива гуидами из запроса
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//рвем коннект.
        try {
            pimCon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.print(" сверка по статусам: " + guidsSverArray.size()+";");
        return guidsSverArray;
    }





    public static ArrayList<String> ChangedPimd() {

        Connection pimCon = null;
        ArrayList<String> guidChangedInPim = new ArrayList<String>();

        try {
            pimCon = bdConn.getPimConn();
            Statement pimChangedStatement = pimCon.createStatement();
            ResultSet PimSverResult = pimChangedStatement.executeQuery(puup.utils.utils.UpdatedPim());//выполнение запроса
            while (PimSverResult.next()) {
                guidChangedInPim.add(PimSverResult.getString(1));//заполнение массива гуидами из запроса
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//рвем коннект.
        try {
            pimCon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.print(" сверка по статусам: " + guidsSverArray.size()+";");
        return guidChangedInPim;
    }
}
