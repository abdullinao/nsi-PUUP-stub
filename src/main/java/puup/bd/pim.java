package puup.bd;

import puup.soap.soap_generator;
import puup.utils.prop;

import java.sql.Connection;
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
                    System.out.print(" из пим: "+guidsArray.size()+";");
                } catch (NullPointerException np) {
                    System.out.print(" нет гуидов для отправки из пим;");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            pimCon.close();
        } else System.out.print(" отправка из пим отключена.");
    }
}
