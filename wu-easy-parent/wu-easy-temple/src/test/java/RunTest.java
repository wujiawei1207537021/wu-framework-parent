import com.wu.framework.easy.stereotype.upsert.converter.SQLConverter;
import domain.PassengerTerminal;

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
        SQLConverter.upsertSQL(PassengerTerminal.class);
//        SQLConverter.createSelectSQL(PassengerTerminal.class);
        String str1 = "大秦 {0} {1} {2}";
        System.out.println(MessageFormat.format(str1, "将军", "梦回", "秦朝"));
    }
}
