package puup.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class prop {

    //pim
    private String pimBdurl;
    private String pimPort;
    private String pimServicename;
    private String pimUser;
    private String pimPass;
    //ufos
    //private String ufosBdurl;
    // private String ufosPort;
    // private String ufosServicename;
    private String ufosUser;
    private String ufosPass;
    private String ufosTns;
    //soap
    private String dict_type;
    private String state;
    private String event;
    private String eh_adress;

    //global settings
    private String sendPimConf;
    private int timeout;
    private int timelag;


    public prop() throws Exception {
        //   FileInputStream fileInputStream;
        //инициализируем специальный объект Properties
        //типа Hashtable для удобной работы с данными

        try {//(InputStream is = prop.class.getClassLoader().getResourceAsStream("config.properties")) {
            File file = new File("config.properties");
            Properties prop = new Properties();
            prop.load(new FileReader(file));
            //загружаем настройки подключеняи к бд пим из конфиг.пропертис
            this.pimBdurl = prop.getProperty("pimBdurl");
            this.pimPort = prop.getProperty("pimport");
            this.pimServicename = prop.getProperty("pimservicename");
            this.pimUser = prop.getProperty("pimuser");
            this.pimPass = prop.getProperty("pimpassword");
//для уфоса
            // this.ufosBdurl = prop.getProperty("ufosBdurl");
            //   this.ufosPort = prop.getProperty("ufosport");
            //   this.ufosServicename = prop.getProperty("ufosservicename");
            this.ufosUser = prop.getProperty("ufosuser");
            this.ufosPass = prop.getProperty("ufospassword");
            this.ufosTns = prop.getProperty("ufosTns");

            //soap

            this.dict_type = prop.getProperty("dict_type");
            this.state = prop.getProperty("state");
            this.event = prop.getProperty("event");
            this.eh_adress = prop.getProperty("eh_url");

            //global
            this.sendPimConf = prop.getProperty("sendPimConf");
            this.timeout = Integer.parseInt(prop.getProperty("timeout"));
            this.timelag = Integer.parseInt(prop.getProperty("timelag"));

        } catch (IOException e) {
            System.out.println("Ошибка в программе: ошибка чтения config.properties");
            e.printStackTrace();
        }

    }

    public int getTimelag() {
        return timelag;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getSendPimConf() {
        return sendPimConf;
    }

    public String getUfosTns() {
        return ufosTns;
    }

    public String getPimBdurl() {
        return pimBdurl;
    }

    public String getPimPort() {
        return pimPort;
    }

    public String getPimServicename() {
        return pimServicename;
    }

    public String getPimUser() {
        return pimUser;
    }

    public String getPimPass() {
        return pimPass;
    }

//    public String getUfosBdurl() {
//        return ufosBdurl;
//    }
//
//    public String getUfosPort() {
//        return ufosPort;
//    }
//
//    public String getUfosServicename() {
//        return ufosServicename;
//    }

    public String getUfosUser() {
        return ufosUser;
    }

    public String getUfosPass() {
        return ufosPass;
    }

    public String getDict_type() {
        return dict_type;
    }

    public String getState() {
        return state;
    }

    public String getEvent() {
        return event;
    }

    public String getEh_adress() {
        return eh_adress;
    }
}
