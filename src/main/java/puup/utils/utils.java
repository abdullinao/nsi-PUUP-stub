package puup.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class utils {
    private static puup.utils.prop prop1;

    static {
        try {
            prop1 = new prop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String UfosSqlReq() {

        return "select  D.globaldocid from ufos_ref.dc_ref_ubpandnubp ubp\n" +
                "join ufos_ref.dict D on ubp.docid=D.dictid" +
                " where  D.LASTMODIFYDATE >=SYSDATE  - INTERVAL '" + prop1.getTimeout() + "' SECOND ";

    }


    public static String UpdatedPim() {

        return "select guid, orgcode\n" +
                " from APPS.OTR_REF_UBPandNUBP_0_V where  \n" +
                "   LAST_UPDATE_DATE >=SYSDATE  - INTERVAL '" + prop1.getTimeout() + "' SECOND ";


    }

    public static String UfosPimStatSver() {

        return " select v.guid from apps.OTR_ref_ubpandnubp_0_v v, ufos_ref.DC_ref_ubpandnubp@ufos_ref.otr.ru e " +
                "where upper(v.guid)= upper(e.guid)and " +
                " upper(v.status)!= upper(e.status)";

    }


    public static String pimSqlReq(String guid) {

        return "begin\n" +
                "  for l_lines in (select attribute2,\n" +
                "                      (select segment2\n" +
                "                            from inv.mtl_item_catalog_groups_b b\n" +
                "                           where a.item_catalog_group_id =\n" +
                "                                 b.item_catalog_group_id\n" +
                "                             and rownum < 2) as dict_name\n" +
                "             from inv.MTL_SYSTEM_ITEMS_B a\n" +
                "                 where UPPER(attribute2) in UPPER('" + guid + "')\n" +
                "                         ) loop\n" +
                "        OTR_MTL_JMS.SEND_MESS(p_guid              => l_lines.attribute2,\n" +
                "    p_class_intern_name => l_lines.dict_name,\n" +
                "    p_oper              => 'update');\n" +
                "    null;\n" +
                "  end loop;\n" +
                "commit;\n" +
                "end;";
    }


    public static String sendToExp(String guid) {

        return "begin\n" +
                "  for l_lines in (select attribute2,\n" +
                "                      (select segment2\n" +
                "                            from inv.mtl_item_catalog_groups_b b\n" +
                "                           where a.item_catalog_group_id =\n" +
                "                                 b.item_catalog_group_id\n" +
                "                             and rownum < 2) as dict_name\n" +
                "             from inv.MTL_SYSTEM_ITEMS_B a\n" +
                "                 where UPPER(attribute2) in UPPER('" + guid + "')\n" +
                "                         ) loop\n" +
                "        OTR_MTL_JMS.SEND_MESS(p_guid              => l_lines.attribute2,\n" +
                "    p_class_intern_name => l_lines.dict_name,\n" +
                "    p_oper              => 'toUFOS_EXP');\n" +
                "    null;\n" +
                "  end loop;\n" +
                "commit;\n" +
                "end;";

    }

    public static String sendToTSE(String guid) {

        return "begin\n" +
                "  for l_lines in (select attribute2,\n" +
                "                      (select segment2\n" +
                "                            from inv.mtl_item_catalog_groups_b b\n" +
                "                           where a.item_catalog_group_id =\n" +
                "                                 b.item_catalog_group_id\n" +
                "                             and rownum < 2) as dict_name\n" +
                "             from inv.MTL_SYSTEM_ITEMS_B a\n" +
                "                 where UPPER(attribute2) in UPPER('" + guid + "')\n" +
                "                         ) loop\n" +
                "        OTR_MTL_JMS.SEND_MESS(p_guid              => l_lines.attribute2,\n" +
                "    p_class_intern_name => l_lines.dict_name,\n" +
                "    p_oper              => 'toUFOS_TSE');\n" +
                "    null;\n" +
                "  end loop;\n" +
                "commit;\n" +
                "end;";

    }

    public static String SendArchiveFromOrgcodeToExp(String orgcode) {
        return "begin\n" +
                "  for l_lines in (select attribute2,\n" +
                "                      (select segment2\n" +
                "                            from inv.mtl_item_catalog_groups_b b\n" +
                "                           where a.item_catalog_group_id =\n" +
                "                                 b.item_catalog_group_id\n" +
                "                             and rownum < 2) as dict_name\n" +
                "             from inv.MTL_SYSTEM_ITEMS_B a\n" +
                "                 where UPPER(attribute2) in  \n" +
                "                         (  select upper(guid) from \n" +
                "  (select  * from apps.otr_ref_ubpandnubp_0_v where orgcode in ('" + orgcode + "') and status in ('ARCHIVE') order by last_update_date desc \n" +
                ") \n" +
                "where rownum<=2\n" +
                "                         )) loop\n" +
                "        OTR_MTL_JMS.SEND_MESS(p_guid              => l_lines.attribute2,\n" +
                "    p_class_intern_name => l_lines.dict_name,\n" +
                "    p_oper              => 'toUFOS_EXP');\n" +
                "    null;\n" +
                "  end loop;\n" +
                "commit;\n" +
                "end;";
    }


    public static String SendArchiveFromOrgcodeToTSE(String orgcode) {
        return "begin\n" +
                "  for l_lines in (select attribute2,\n" +
                "                      (select segment2\n" +
                "                            from inv.mtl_item_catalog_groups_b b\n" +
                "                           where a.item_catalog_group_id =\n" +
                "                                 b.item_catalog_group_id\n" +
                "                             and rownum < 2) as dict_name\n" +
                "             from inv.MTL_SYSTEM_ITEMS_B a\n" +
                "                 where UPPER(attribute2) in  \n" +
                "                         (  select upper(guid) from \n" +
                "  (select  * from apps.otr_ref_ubpandnubp_0_v where orgcode in ('" + orgcode + "') and status in ('ARCHIVE') order by last_update_date desc \n" +
                ") \n" +
                "where rownum<=2\n" +
                "                         )) loop\n" +
                "        OTR_MTL_JMS.SEND_MESS(p_guid              => l_lines.attribute2,\n" +
                "    p_class_intern_name => l_lines.dict_name,\n" +
                "    p_oper              => 'toUFOS_TSE');\n" +
                "    null;\n" +
                "  end loop;\n" +
                "commit;\n" +
                "end;";
    }



    public static String SendArchiveFromOrgcodeToALL(String orgcode) {
        return "begin\n" +
                "  for l_lines in (select attribute2,\n" +
                "                      (select segment2\n" +
                "                            from inv.mtl_item_catalog_groups_b b\n" +
                "                           where a.item_catalog_group_id =\n" +
                "                                 b.item_catalog_group_id\n" +
                "                             and rownum < 2) as dict_name\n" +
                "             from inv.MTL_SYSTEM_ITEMS_B a\n" +
                "                 where UPPER(attribute2) in  \n" +
                "                         (  select upper(guid) from \n" +
                "  (select  * from apps.otr_ref_ubpandnubp_0_v where orgcode in ('" + orgcode + "') and status in ('ARCHIVE') order by last_update_date desc \n" +
                ") \n" +
                "where rownum<=1\n" +
                "                         )) loop\n" +
                "        OTR_MTL_JMS.SEND_MESS(p_guid              => l_lines.attribute2,\n" +
                "    p_class_intern_name => l_lines.dict_name,\n" +
                "    p_oper              => 'update');\n" +
                "    null;\n" +
                "  end loop;\n" +
                "commit;\n" +
                "end;";
    }



    public static void printTime() {
        //System.out.println("\n");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(sdf.format(cal.getTime()));
    }


    public static void compare(ArrayList<String> guidsOld, ArrayList<String> guidsNew) {


    }

}
