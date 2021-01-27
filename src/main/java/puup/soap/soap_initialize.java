package puup.soap;

import puup.prop;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class soap_initialize {

    private static prop prop1;

    static {
        try {
            prop1 = new prop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String endpoint = prop1.getEh_adress();
    private static String dict_typeString = prop1.getDict_type();
    private static String stateString = prop1.getState();
    private static String event = prop1.getEvent();
    private static final String soapAction = "dlc-action-event";

    public static String getDict_typeString() {
        return dict_typeString;
    }

    public static String getStateString() {
        return stateString;
    }

    public static String getEvent() {
        return event;
    }

    public static void eh_initialize(ArrayList<String> guidsArray) throws IOException {


        //выводим данные для дебуга.

        System.out.println("\nадрес эвентхендлера: " + prop1.getEh_adress());


        System.out.println("\ndocument-type(REF_UBPandNUBP, REF_PersAccount...): " + prop1.getDict_type());


        System.out.println("\ndocument-state (ACTIVE/ARCHIVE...): " + prop1.getState());


        System.out.println("\noperation-code(CreateRecord/toArchive...): " + prop1.getEvent());

        try {
            for (int i = 0; i < guidsArray.size(); ++i) {
                soap_generator.callSoapWebService(endpoint, soapAction, guidsArray.get(i)); //создаем соап запрос для этого гуида
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        while (guid != null) {
//            String guid =
//            System.out.println("\n\nработаю с гуидом " + guid);
//            soap_generator.callSoapWebService(endpoint, soapAction, guid); //создаем соап запрос для этого гуида
//            // считываем остальные строки в цикле
//            guid = reader.readLine();
//        }


    }


}
