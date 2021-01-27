package puup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

public class prop {


    private String pimBdurl;
    private String pimPort;
    private String pimServicename;
    private String pimUser;
    private String pimPass;

    private String ufosBdurl;
    private String ufosPort;
    private String ufosServicename;
    private String ufosUser;
    private String ufosPass;
    private String ufosTns;

    private String dict_type;
    private String state;
    private String event;
    private String eh_adress;


    public prop() throws Exception {
        //   FileInputStream fileInputStream;
        //инициализируем специальный объект Properties
        //типа Hashtable для удобной работы с данными

        try (InputStream is = prop.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(is);
            //загружаем настройки подключеняи к бд пим из конфиг.пропертис
            this.pimBdurl = prop.getProperty("pimBdurl");
            this.pimPort = prop.getProperty("pimport");
            this.pimServicename = prop.getProperty("pimservicename");
            this.pimUser = prop.getProperty("pimuser");
            this.pimPass = prop.getProperty("pimpassword");
//для уфоса
            this.ufosBdurl = prop.getProperty("ufosBdurl");
            this.ufosPort = prop.getProperty("ufosport");
            this.ufosServicename = prop.getProperty("ufosservicename");
            this.ufosUser = prop.getProperty("ufosuser");
            this.ufosPass = prop.getProperty("ufospassword");
            this.ufosTns = prop.getProperty("ufosTns");

            //soap

            this.dict_type = prop.getProperty("dict_type");
            this.state = prop.getProperty("state");
            this.event = prop.getProperty("event");
            this.eh_adress = prop.getProperty("eh_url");

        } catch (IOException e) {
            System.out.println("Ошибка в программе: ошибка чтения config.properties");
            e.printStackTrace();
        }

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

    public String getUfosBdurl() {
        return ufosBdurl;
    }

    public String getUfosPort() {
        return ufosPort;
    }

    public String getUfosServicename() {
        return ufosServicename;
    }

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
