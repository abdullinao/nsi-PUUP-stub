package puup;

import puup.utils.prop;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class main {
    private static puup.utils.prop prop1;

    static {
        try {
            prop1 = new prop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            mainJob();
        } catch (Exception e) {
            e.printStackTrace();
            mainJob();
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
            ArrayList<String> guidsToSend;

            public void run() {

                puup.utils.utils.printTime();
                try {
                    puup.bd.pim.SQLexecute(guidsToSend);
                    guidsToSend = puup.bd.ufos.getChangedGuidsFromUfos();//получаем список гуидов из уфоса измененных
                    puup.soap.soap_initialize.eh_initialize(guidsToSend);//переотправляем их соапом
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(FUCKRUN, 0, prop1.getTimeout() - prop1.getTimelag(), TimeUnit.SECONDS);//минус 10 сек
        //на лаги, лучше отправить запись дважды чем не отправить))


    }


}
