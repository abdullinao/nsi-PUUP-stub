package puup;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import puup.utils.prop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class main {
    private static puup.utils.prop prop1;
    private static final Logger logger = LogManager.getLogger();

    static {
        try {
            prop1 = new prop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            logger.info("запуск");

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
        Runnable resendUfosPim = new Runnable() {
            ArrayList<String> guidsToSend = new ArrayList<String>();
            ArrayList<String> guidsToSendPrevSend = new ArrayList<String>();
            ArrayList<String> guidsToSver = new ArrayList<String>();
            ArrayList<String> guidsOrgcodesChangedInPim = new ArrayList<String>();
            ArrayList<String> guidsChangedInPim = new ArrayList<String>();
            ArrayList<String> OrgcodesChangedInPim = new ArrayList<String>();
            int Resendcoun = 0;
            int ResendcounArchive = 0;

            public void run() {

                logger.info("запуск прохода");

                puup.utils.utils.printTime();
                System.out.println("=================");
                try {
                    guidsToSendPrevSend = guidsToSend;
                    //блок получения изменных в пим
                    if (prop1.getSendAllChangedFromPim().equalsIgnoreCase("y")) {
                        puup.utils.utils.printTime();
                        System.out.println("получаю измененные в пим");
                        guidsOrgcodesChangedInPim = puup.bd.pim.ChangedPim();//получаем список гуидов;оргкодов из пим измененных
                    } else {
                        System.out.println("отправка измененных в пим отключена");
                    }

                    //блок получения измененных в уфосе
                    if (prop1.getSendPimUfosAll().equalsIgnoreCase("y")) {

                        puup.utils.utils.printTime();
                        System.out.println("получаю измененные из уфоса для переотправки в пим");
                        guidsToSend = puup.bd.ufos.getChangedGuidsFromUfos();//получаем список гуидов из уфоса измененных
                    }


                    //блок получения сверки статусов между уфос и пим

                    if (prop1.getStatusnayaSverka().equalsIgnoreCase("y")) {
                        puup.utils.utils.printTime();
                        System.out.println("получаю статусную сверку");
                        guidsToSver = puup.bd.pim.SverkaUfosPim();//получаем список гуидов из уфоса измененных
                    } else {
                        System.out.println("статсуная сверка отключена");
                    }
                    // System.out.println("\nвсего связок гуид+оргкод: " + guidsOrgcodesChangedInPim.size());

                    guidsChangedInPim.clear();
                    OrgcodesChangedInPim.clear();
                    //разделение guid;orgcode на разные массивы
                    try {
                        for (int i = 0; i < guidsOrgcodesChangedInPim.size(); ++i) {
                            try {
                                // System.out.println(i);
                                String[] split = guidsOrgcodesChangedInPim.get(i).split(";");
                                guidsChangedInPim.add(split[0]);
                                OrgcodesChangedInPim.add(split[1]);
                            } catch (Exception e) {
                                System.out.println("Error message :");
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

//dbg
//                    System.out.println("/////////");
//                    for (int i = 0; i < OrgcodesChangedInPim.size(); ++i) {
//                        try {
//                            System.out.println(i);
//                            System.out.println(OrgcodesChangedInPim.get(i));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    System.out.println("/////////");
                    //переотправка  соапом по переотправке
                    if (prop1.getSendPimUfosAll().equalsIgnoreCase("y")) {

                        guidsToSend = removeSendedInLastRun(guidsToSend, guidsToSendPrevSend);
                        puup.soap.soap_initialize.eh_initialize(guidsToSend);//переотправляем их соапом
                        System.out.println("Соапом по переотправке: " + guidsToSend.size());

                    } else {
                        System.out.println("переотправка всех записей из уфос в пим отключена");
                    }

                    //переотправка по сверке

                    if (prop1.getStatusnayaSverka().equalsIgnoreCase("y")) {

                        puup.soap.soap_initialize.eh_initialize(guidsToSver);//переотправляем их соапом
                        System.out.println("Соапом по статусной сверке: " + guidsToSver.size());
                        guidsToSver.clear();
                    } else {
                        System.out.println("статусная сверка отключена");
                    }

                    //распространение архивных
                    if (prop1.getSendArchiveFromPin().equalsIgnoreCase("y")) {
                        OrgcodesChangedInPim = new ArrayList<String>(removeDuplicates(OrgcodesChangedInPim)); //очистка от дублей

                        puup.bd.pim.SQLexecuteForOrgcodes(OrgcodesChangedInPim);
                        System.out.println("Архивных в пим к отправке: " + OrgcodesChangedInPim.size());
                        ResendcounArchive = ResendcounArchive + OrgcodesChangedInPim.size();
                        System.out.println("Всего переотправил архивных: " + ResendcounArchive);
                    } else {
                        System.out.println("переотправка архивных из пим отключена");
                    }


                    //распространение измененных

                    if (prop1.getSendAllChangedFromPim().equalsIgnoreCase("y")) {


                        puup.bd.pim.SQLexecute(guidsChangedInPim);
                        System.out.println("Изменных в пим к отправке: " + guidsChangedInPim.size());
                        Resendcoun = Resendcoun + guidsChangedInPim.size();
                        System.out.println("Всего переотправил активных: " + Resendcoun);
                    } else {
                        System.out.println("переотправка измененных в пим отключена");
                    }

                    //повторная отправка в exp/tse
                    if (prop1.getSendAddrExpTse().equalsIgnoreCase("y")) {

                        System.out.println("повторная отправка активных отдельно адресно");
                        puup.bd.pim.SendAddr(guidsChangedInPim);
                        System.out.println("В этом проходе: " + guidsChangedInPim.size());
                    } else {
                        System.out.println("повторная отправка активных отдельно адресно пим отключена");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(resendUfosPim, 0,
                prop1.getTimeout() - prop1.getTimelag(), TimeUnit.SECONDS);// минус Timelag   сек задается в конфиге
        //на лаги, лучше отправить запись дважды чем не отправить))


    }

    private static <T> HashSet<T> removeDuplicates(Collection<T> collection) {
        return new HashSet<T>(collection);
    }

    private static ArrayList<String> removeSendedInLastRun(ArrayList<String> current, ArrayList<String> sendedLastTime) {
        current.removeIf(sendedLastTime::contains);
        return current;
    }
}
