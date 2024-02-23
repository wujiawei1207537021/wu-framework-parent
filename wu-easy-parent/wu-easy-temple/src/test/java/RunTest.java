import java.text.MessageFormat;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2020/10/21 上午9:33
 */
public class RunTest {
    public static void main(String[] args) {
//        SQLConverter.createTableSQL(PassengerTerminal.class);
//
//        SQLAnalyze.upsertSQL(PassengerTerminal.class);
//        SQLConverter.createSelectSQL(PassengerTerminal.class);
        String str1 = "大秦 {0} {1} {2}";
        System.out.println(MessageFormat.format(str1, "将军", "梦回", "秦朝"));

        Object[] param = {"将军", "梦回"};
        String fromat = "select * from information_schema.tables where table_schema=''{0}'' and table_name=''{1}'' ";

        System.out.println(MessageFormat.format(fromat, "将军", "梦回"));
        x(param);
    }

    public static void x(Object[] param) {
        String fromat = "select * from information_schema.tables where table_schema=''{0}'' and table_name=''{1}'' ";

        System.out.println(MessageFormat.format(fromat, param));
    }
}
