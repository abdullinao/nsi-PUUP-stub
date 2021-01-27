package puup;

public class utils {

    public static String UfosSqlReq (){

        return "select  ubp.GUID from ufos_ref.dc_ref_ubpandnubp ubp\n" +
                "join ufos_ref.dict D on ubp.docid=D.dictid where  D.LASTMODIFYDATE >=SYSDATE  - INTERVAL '5' MINUTE ";

    }

}
