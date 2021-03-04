package puup.bd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import puup.utils.prop;

import java.sql.*;
import java.util.ArrayList;

public class pim {
    private static puup.utils.prop prop1;
    private static final Logger logger = LogManager.getLogger();

    // logger.info("коннект с пим установлен");
//  logger.error("Ошибка подключения к бд пим", e);
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
                        logger.info("SQLexecute " + guidsArray.get(i));
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


    public static void SendAddr(ArrayList<String> guidsArray) throws SQLException {
        if (prop1.getSendPimConf().equalsIgnoreCase("y")) {
            Connection pimCon = null;
            try {
                pimCon = bdConn.getPimConn();
                Statement pimStatement = pimCon.createStatement();
                try {
                    for (int i = 0; i < guidsArray.size(); ++i) {

                        pimStatement.execute(puup.utils.utils.sendToExp(guidsArray.get(i)));//отправляем скл запрос,запрос записан
                        logger.info("SendAddr exp " + guidsArray.get(i));

                        pimStatement.execute(puup.utils.utils.sendToTSE(guidsArray.get(i)));//отправляем скл запрос,запрос записан
                        logger.info("SendAddr tse " + guidsArray.get(i));
                    }

                } catch (NullPointerException np) {

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            pimCon.close();
        } else System.out.println(" отправка из пим отключена.");
    }


    public static void SQLexecuteForOrgcodes(ArrayList<String> orgcodesArray) throws SQLException {
        if (prop1.getSendPimConf().equalsIgnoreCase("y")) {
            Connection pimCon = null;
            try {
                pimCon = bdConn.getPimConn();
                Statement pimStatement = pimCon.createStatement();
                try {
                    for (int i = 0; i < orgcodesArray.size(); ++i) {

                        pimStatement.execute(puup.utils.utils.SendArchiveFromOrgcodeToExp(orgcodesArray.get(i)));//отправляем скл запрос,запрос записан
                        logger.info("SQLexecuteForOrgcodes exp " + orgcodesArray.get(i));
                        pimStatement.execute(puup.utils.utils.SendArchiveFromOrgcodeToTSE(orgcodesArray.get(i)));
                        logger.info("SQLexecuteForOrgcodes tse " + orgcodesArray.get(i));
                        pimStatement.execute(puup.utils.utils.SendArchiveFromOrgcodeToALL(orgcodesArray.get(i)));//отправляем скл запрос,запрос записан
                        logger.info("SQLexecuteForOrgcodes to all " + orgcodesArray.get(i));
                    }
                } catch (NullPointerException np) {
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
                logger.info("SverkaUfosPim " + PimSverResult.getString(1));
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


    public static ArrayList<String> ChangedPim() {

        Connection pimCon = null;
        ArrayList<String> guidChangedInPim = new ArrayList<String>();

        try {
            pimCon = bdConn.getPimConn();
            Statement pimChangedStatement = pimCon.createStatement();
            ResultSet PimSverResult = pimChangedStatement.executeQuery(puup.utils.utils.UpdatedPim());//выполнение запроса

            while (PimSverResult.next()) {
                //guid;orgcode
                try {
                    guidChangedInPim.add(PimSverResult.getString(1) + ";" + PimSverResult.getString(2));//заполнение массива гуидами из запроса
                    logger.info("ChangedPim " + PimSverResult.getString(1) + ";" + PimSverResult.getString(2));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            PimSverResult.close();
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
