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
        Runnable resendUfosPim = new Runnable() {
            ArrayList<String> guidsToSend = new ArrayList<String>();
            ArrayList<String> guidsToSver = new ArrayList<String>();
            ArrayList<String> guidsOrgcodesChangedInPim = new ArrayList<String>();
            ArrayList<String> guidsChangedInPim = new ArrayList<String>();
            ArrayList<String> OrgcodesChangedInPim = new ArrayList<String>();
            int Resendcoun = 0;
            int ResendcounArchive = 0;

            public void run() {


                puup.utils.utils.printTime();
                System.out.println("=================");
                try {


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
                    //блок получения измененных в уфосе
                    puup.utils.utils.printTime();
                    System.out.println("получаю измененные из уфоса для переотправки");
                    guidsToSend = puup.bd.ufos.getChangedGuidsFromUfos();//получаем список гуидов из уфоса измененных

                    //блок получения изменных в пим
                    puup.utils.utils.printTime();
                    System.out.println("получаю измененные в пим");
                    guidsOrgcodesChangedInPim = puup.bd.pim.ChangedPim();//получаем список гуидов;оргкодов из пим измененных
                    //блок получения сверки статусов между уфос и пим
                    puup.utils.utils.printTime();
                    System.out.println("получаю сверку");
                    guidsToSver = puup.bd.pim.SverkaUfosPim();//получаем список гуидов из уфоса измененных

                    System.out.println("\nвсего связок гуид+оргкод: " + guidsOrgcodesChangedInPim.size());

                    guidsChangedInPim.clear();
                    OrgcodesChangedInPim.clear();
                    //разделение guid;orgcode на разные массивы
                    try {
                        for (int i = 0; i < guidsOrgcodesChangedInPim.size(); ++i) {
                            try {
                                System.out.println(i);
                                String[] split = guidsOrgcodesChangedInPim.get(i).split(";");
                                guidsChangedInPim.add(split[0]);
                                OrgcodesChangedInPim.add(split[1]);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //переотправка по переотправке
                    puup.soap.soap_initialize.eh_initialize(guidsToSend);//переотправляем их соапом
                    System.out.println("Соапом по переотправке: " + guidsToSend.size());

                    //переотправка по сверке
                    puup.soap.soap_initialize.eh_initialize(guidsToSver);//переотправляем их соапом
                    System.out.println("Соапом по сверке: " + guidsToSver.size());


                    //распространение архивных

                    puup.bd.pim.SQLexecuteForOrgcodes(OrgcodesChangedInPim);
                    System.out.println("Архивных в пим к отправке: " + OrgcodesChangedInPim.size());
                    ResendcounArchive = ResendcounArchive + OrgcodesChangedInPim.size();
                    System.out.println("Всего переотправил архивных: " + ResendcounArchive);


                    //распространение измененных
                    puup.bd.pim.SQLexecute(guidsChangedInPim);
                    System.out.println("Изменных в пим к отправке: " + guidsChangedInPim.size());
                    Resendcoun = Resendcoun + guidsChangedInPim.size();
                    System.out.println("Всего переотправил активных: " + Resendcoun);


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
}
