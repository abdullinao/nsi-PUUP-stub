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


    /**
     *начало блока с утилсами по лицевым счетам
     */

    public static String ChangedInUfosPersaccounts() {

        return "select  D.globaldocid from ufos_ref.dc_ref_persaccount ubp\n" +
                "join ufos_ref.dict D on ubp.docid=D.dictid" +
                " where  D.LASTMODIFYDATE >=SYSDATE  - INTERVAL '" + prop1.getTimeout() + "' SECOND and status in ('ACTIVE','ARCHIVE') ";

    }
    public static String UpdatedPersPim() {

        return "select guid\n" +
                " from APPS.OTR_ref_persaccount_0_V where  \n" +
                "   LAST_UPDATE_DATE >=SYSDATE  - INTERVAL '" + prop1.getTimeout() + "' SECOND ";


    }

    public static String SverPersNotInPim() {

        return "select   d.globaldocid from ufos_Ref.dc_ref_persaccount@ufos_ref.otr.ru ufos join ufos_ref.dict@ufos_ref.otr.ru d on d.dictid=ufos.docid\n" +
                " where ufos.status in ('ACTIVE','ARCHIVE') and d.globaldocid not in (select guid from apps.otr_ref_persaccount_0_v) and d.globaldocid not in ('133b23f3-7055-40a9-bf64-c286a9cae849','2817ed58-43b0-414f-a170-41e648801d3e','fa30c91b-433d-434d-9e90-3b2c2b92acf8','a0f2e9ca-6d5a-43e1-9ffd-3e0565f59df9','ed2cf085-8d70-4e6a-8e71-2b5f6e716b49','fd226cef-7963-4ac9-8101-8ee0d04c7fdf','19108364-40b6-4e9a-94a6-6ad45bd8cbe4','10b401c3-3d3f-40fe-8a36-ebd034f60cab','b3d0917d-d7ad-4267-a908-26bb3a75dda0','67328571-2353-499f-963f-0c27a82950c6','b5e175b2-c222-4c3a-b11e-6fc917a1dd21','fe93050e-b339-444e-9965-68f09f043570','03537055-09de-4fee-ba2d-c75b6aef2b32','15505ea0-3c3b-4465-b661-d4814ab0bbb3','9a983e78-55f1-4187-a876-70fe9f9d9eb1','7df365ac-8b9a-41fe-b6ab-e929e99d0b5d','5c0ccb47-d1be-460f-a5a6-9e9b1557a987','7ec7ff3d-53df-4ea7-9f3f-7cf296baee2c','6679d17b-7844-45aa-aedc-d32aa2ac9af5','b599b5ec-33e0-4a1c-a71a-be60fd24453e','a3216713-41fd-4afe-a968-1fcffee1788e','bc0aea14-c8fd-437f-95bf-0748184e6ed9','6bb0b624-c0e8-4424-8fc4-76c300b6ff1a','c26f8cb7-4821-4e69-987f-8bc0866a1e60','210b890f-ca6c-49d7-b3b4-e2c5c0910a57','221a13b0-3a1d-4d76-aba3-331c0f275b33','9617ae80-dbca-43d8-94b7-5fd45dd32db4','19409657-cd62-4b83-8aea-9d018f7835b9','6d93450b-7bf2-416c-ae87-43f605212962','a22644f0-8d4e-4b52-9bb3-244109fc5cfa','f6d23973-bd1e-45ce-8dba-d2ce1cc76c10','71cd039d-6ec1-442b-84cf-1d91be1474fc','5936884c-0921-4762-a49b-7b9565128248','2eb6dd39-c47e-4acf-91cd-20d41511a725','ce227652-e169-4de9-8d76-3c18ed1108b1','e3c9461e-57bc-410c-a9e4-a6ffbc6e659f','abb8c4e3-bebf-4bb5-935c-1b0dcdf28ff6','fa539aed-19a6-4358-87bb-7d3ad01fe93f','f59f9e16-a761-40bf-a455-3aac45e52a32','719ad419-5782-45aa-bc95-87694c59f33c','d7f56359-7bb6-472c-967e-d8655888d703','8499ed96-cdc4-4b1a-b74a-f650e6fc5dda','540816ab-18c6-436b-a34c-7e0a9819f2fe','95f81823-1c38-4f74-83eb-329986080511','67f18906-dfed-4c43-8455-2e527e3f167b','9dfa4c95-06fc-430b-97dc-12790b432dc4','27cafcc5-eab0-49c6-9080-5a60cf587e48','43a6e190-dcfc-48de-b4ad-549becdd156e','10f6bc1d-ed8d-4d05-8768-5285dba16ef9','37ad0458-839f-447e-93d6-9ec245019787','7c574527-19f0-4f07-aa8c-43819f6c19fb','035f011d-1f0b-4448-8fc6-545bc5c3a0f4','2601a51b-66ac-4074-94cc-756374587055','e1fe21f4-59ab-4059-91a1-d359ea5aadac','125b2a7a-5b2e-4d78-9bb1-b786c1d71a17','8679a203-2b38-402e-a51a-c240a1f9a4de','9caf79ae-3e17-40f1-8018-1f246c9f8ab0','9386f0df-6018-4a74-977a-56be0b7562fa','03b4f8e4-0765-4efa-be50-f55f55372846','908d3383-c8d1-4c04-bb0d-1283632abfbc','c7520def-e4af-43c6-936e-f31610cec5ag','563cf40f-174a-4329-8f53-f1fc5a9960db','3d9c6ce0-5c81-484e-b109-f7e2dcd0cacf','7f511066-5594-4315-8c87-dcfbbbb10f53','9e36cfab-3618-4732-ada3-f431fcf0852f','42f2c86c-017a-4ec9-a773-725db5ab5578','49799a89-a67d-4bba-86dc-f5db745af6ad','d667d5d5-2c76-45f5-81d0-e13e0490bd8g','79b1e2f9-5250-4cf9-939c-823f5a288ec7','daa1dfeb-9aad-486b-a312-faadd25f0859','15f00481-b065-47e6-be96-5b782cc98c95','39e435db-84e6-4bb1-8379-568da6066dc9','485dd362-2a2c-4fac-9c62-75fc1040bb28','e3de2dfe-6d55-4c6f-9d12-6e7a53ceabc5','67d1414c-6e38-4c70-b2d2-4fe02ee71eb9','22a74250-ceba-4669-af3b-e79fa2a9bb8a','7fcc6160-a058-4286-a5a8-09192f85457e','80ff23c9-1e91-4f67-9313-3d651385cb5b','42419760-e35f-4e9a-b072-22c514e1d66c','80146146-ea89-433b-8c09-2736f7474b79','9304805e-74b0-44f2-9a2a-2538931937d2','f18b4393-8ad8-45e0-a5b8-f2863a41d3de','eb9f86dd-9234-415b-9fb5-816fddc99d3e','8b4852c6-adda-4eb9-a208-acfd8b728576','c3f19f8c-3798-4c44-b323-0818c8c512fg','2667b689-9ac3-4904-b8ce-2cd69f68f0f3','d5843b22-1968-49e8-98d1-c1e5e9b9b2e9','f4135eff-8fb1-4215-a6eb-c2d25ff0e5e5','8d2c1375-3eb6-4aab-9837-63c262894a5c','f4a3d8e0-e540-4327-bd76-09c479917dd4','655768f3-c345-43a7-ba16-295db1a820bb','4baa02fe-ea07-4e22-a0f9-c4384081e2f3','81c6c0e1-7adc-4baa-964d-2f128547cc8f','d26d918b-568d-47a7-b097-87e96b14ea4f','2f79850a-e379-4dc4-b70b-a293f49596b8','475b9150-b305-4eac-8b0c-62c063225403','c7d401c9-7a0e-47f8-9f65-0f26f2a5ec5a','dab4d0ef-385d-4d52-a142-28f2de7ea2cb','516904c1-4bcf-484a-a4f6-28a5eface907','b521466b-b1f3-483e-b87d-ff98ecffd4a1','7ae7d136-deb4-40ac-8bde-46db895ac7ea','84b34ccd-4661-4cf5-bbf1-d49848a93184','fdef4752-97ad-4ae0-b427-29d042f53226','23aa38ea-48ae-46b5-b7ee-b32d5b16c9b7','886663b4-570c-4580-ad47-9feb5cadbe69','f1d8787d-7a37-4004-8bad-88af1c2cfeca','243f2176-0852-4b7c-9931-22510992650a','bd487af5-3830-41de-89c5-476d8771b0f5','5dd0cd73-6229-41fc-8fce-011ce4f88a7g','60640f7a-05cb-436a-a9cf-b9e159d20dfc')";

    }

    /**
     *конец блока с утилсами по лицевым счетам
     */

    //измененные в уфосе
    public static String UfosSqlReq() {

        return "select  D.globaldocid from ufos_ref.dc_ref_ubpandnubp ubp\n" +
                "join ufos_ref.dict D on ubp.docid=D.dictid" +
                " where  D.LASTMODIFYDATE >=SYSDATE  - INTERVAL '" + prop1.getTimeout() + "' SECOND ";

    }



//отсутсвтуют в пим


//обновленные в пим
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
                "where rownum=2\n" +
                "                         )) loop\n" +
                "        OTR_MTL_JMS.SEND_MESS(p_guid              => l_lines.attribute2,\n" +
                "    p_class_intern_name => l_lines.dict_name,\n" +
                "    p_oper              => 'toUFOS_TSE');\n" +
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
