package puup;

import puup.utils.prop;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * todo
 * по лицевыс счетам перезаталкивание по отстувющим в пиме - сверка +
 * перезаталкивание по статусам между уфосом и пимом - по статсам слишком душно, т.к в пиме только актив архив переотправка всех измененных +
 * <p>
 * распространить только измененные в пиме по всем подпискам +
 */

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
        Runnable resendUfosPim = new Runnable() {
            ArrayList<String> guidsToSend = new ArrayList<String>();
            ArrayList<String> guidsToSendLastRun = new ArrayList<String>();
            ArrayList<String> guidsToSver = new ArrayList<String>();
            ArrayList<String> guidsOrgcodesChangedInPim = new ArrayList<String>();
            ArrayList<String> guidsChangedInPim = new ArrayList<String>();
            ArrayList<String> guidsNotInPim = new ArrayList<String>();
            ArrayList<String> OrgcodesChangedInPim = new ArrayList<String>();
            int Resendcoun = 0;
            int ResendcounArchive = 0;

            public void run() {


                puup.utils.utils.printTime();
                System.out.println("=================");
                try {
                    guidsToSendLastRun = guidsToSend;
                    //Тест распространения всех изменееых из в пур

                    // puup.bd.pim.sendToExp(guidsToSend);
                    // System.out.println("переотправил в пур: " + guidsToSend.size());


                    //блок для переотпрваки тех, кто сумел измениться в уфосе. но если в уфос изменилась в пим
                    //могла не меняться по-этому пришлось убрать
                    // puup.bd.pim.SQLexecute(guidsToSend);
                    //puup.bd.pim.SQLexecute(guidsToSver);

                    //    try {
                    //        System.out.println("К распространению по переотправке: " + guidsToSend.size());

                    //    } catch (NullPointerException nullPointerException) {
                    //        System.out.println("Нет гуидов для отправки из пим по переотправке");
                    //    }
                    //    try {
                    //        System.out.println("К распространению по сверке: " + guidsToSver.size());
                    //    } catch (NullPointerException nullPointerException) {
                    //        System.out.println("Нет гуидов для отправки из пим по сверке");
                    //    }

                    //блок получения измененных в уфосе +
                    puup.utils.utils.printTime();
                    System.out.println("получаю измененные из уфоса лс для перезаталкивания");
                    guidsToSend = puup.bd.ufos.getChangedGuidsFromUfos();//получаем список гуидов из уфоса измененных

                    //блок получения изменных в пим +
                    puup.utils.utils.printTime();
                    System.out.println("получаю измененные в пим");
                    guidsChangedInPim = puup.bd.pim.ChangedPim();//получаем список гуидов;оргкодов из пим измененных

                    //блок получения разницы между пимом и уфосом
                    puup.utils.utils.printTime();
                    System.out.println("получаю отсутствующие в пим");
                    guidsNotInPim = puup.bd.pim.SverPersAccNotInPim();//получаем список гуидов;оргкодов из пим измененных

                    //блок получения сверки статусов между уфос и пим
//                    puup.utils.utils.printTime();
//                    System.out.println("получаю сверку");
//                    guidsToSver = puup.bd.pim.SverkaUfosPim();//получаем список гуидов из уфоса измененных

                    // System.out.println("\nвсего связок гуид+оргкод: " + guidsOrgcodesChangedInPim.size());


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

                    //переотправка по переотправке
                    guidsToSend = removeSendedInLastRun(guidsToSend, guidsToSendLastRun);
                    puup.soap.soap_initialize.eh_initialize(guidsToSend);//переотправляем их соапом
                    System.out.println("Соапом по переотправке: " + guidsToSend.size());


                   // переотправка по отсутствию в пим
                    puup.soap.soap_initialize.eh_initialize(guidsNotInPim);//переотправляем их соапом
                    System.out.println("Соапом по отсуствующим в пиме: " + guidsNotInPim.size());
                    guidsNotInPim.clear();

                    //распространение архивных
//
//                    puup.bd.pim.SQLexecuteForOrgcodes(OrgcodesChangedInPim);
//                    System.out.println("Архивных в пим к отправке: " + OrgcodesChangedInPim.size());
//                    ResendcounArchive = ResendcounArchive + OrgcodesChangedInPim.size();
//                    System.out.println("Всего переотправил архивных: " + ResendcounArchive);
//

                    //распространение измененных
                    puup.bd.pim.SQLexecute(guidsChangedInPim);
                    System.out.println("Изменных в пим к отправке: " + guidsChangedInPim.size());
                    Resendcoun = Resendcoun + guidsChangedInPim.size();
                    System.out.println("Всего переотправил активных: " + Resendcoun);

//                    //повторная отправка в exp/tse
//                    System.out.println("---повторная отправка активных отдельно адресно");
//                    puup.bd.pim.SendAddr(guidsChangedInPim);
//                    System.out.println("В этом проходе: " + guidsChangedInPim.size());

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

    private static ArrayList<String> removeSendedInLastRun(ArrayList<String> current, ArrayList<String> sendedLastTime) {
        current.removeIf(sendedLastTime::contains);
        return current;
    }
}
