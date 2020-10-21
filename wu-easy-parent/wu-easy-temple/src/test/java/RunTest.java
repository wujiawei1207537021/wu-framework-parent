

import com.wu.framework.easy.stereotype.upsert.converter.SQLConverter;
import domain.PassengerTerminal;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2020/10/21 上午9:33
 */
public class RunTest {
    public static void main(String[] args) {
//        SQLConverter.createTableSQL(PassengerTerminal.class);
//
        SQLConverter.upsertSQL(PassengerTerminal.class);
//        SQLConverter.createSelectSQL(PassengerTerminal.class);
    }
}
