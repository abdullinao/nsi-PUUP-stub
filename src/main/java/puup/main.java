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
            ArrayList<String> guidsToSend;
            ArrayList<String> guidsToSver;
            ArrayList<String> guidsOrgcodesChangedInPim;
            ArrayList<String> guidsChangedInPim;
            ArrayList<String> OrgcodesChangedInPim;
            int Resendcoun = 0;
            int ResendcounArchive = 0;

            public void run() {


                puup.utils.utils.printTime();
                try {
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


                    guidsToSend = puup.bd.ufos.getChangedGuidsFromUfos();//получаем список гуидов из уфоса измененных
                    guidsToSver = puup.bd.pim.SverkaUfosPim();//получаем список гуидов из уфоса измененных
                    guidsOrgcodesChangedInPim = puup.bd.pim.ChangedPim();//получаем список гуидов;оргкодов из пим измененных
                    guidsChangedInPim = null;
                    OrgcodesChangedInPim = null;
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
                    } catch (NullPointerException np) {
                    }

//переотправка по переотправке
                    puup.soap.soap_initialize.eh_initialize(guidsToSend);//переотправляем их соапом
                    try {
                        System.out.println("Соапом по переотправке: " + guidsToSend.size());
                    } catch (NullPointerException n) {
                        System.out.println("Соапом по переотправке: 0");
                    }


                    //переотправка по сверке
                    puup.soap.soap_initialize.eh_initialize(guidsToSver);//переотправляем их соапом

                    try {
                        System.out.println("Соапом по сверке: " + guidsToSver.size());
                    } catch (NullPointerException n) {
                        System.out.println("Соапом по сверке: 0");
                    }

                    //распространение архивных
                    puup.bd.pim.SQLexecuteForOrgcodes(OrgcodesChangedInPim);
                    System.out.println("Архивных в пим к отправке: " + OrgcodesChangedInPim.size());
                    ResendcounArchive = ResendcounArchive + OrgcodesChangedInPim.size();
                    System.out.println("Total archive: " + ResendcounArchive);

//распространение измененных
                    puup.bd.pim.SQLexecute(guidsChangedInPim);
                    System.out.println("Изменных в пим к отправке: " + guidsChangedInPim.size());
                    Resendcoun = Resendcoun + guidsChangedInPim.size();
                    System.out.println("Total active: " + Resendcoun);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(resendUfosPim, 0,
                prop1.getTimeout() - prop1.getTimelag(), TimeUnit.SECONDS);//минус 10 сек
        //на лаги, лучше отправить запись дважды чем не отправить))


    }
}
