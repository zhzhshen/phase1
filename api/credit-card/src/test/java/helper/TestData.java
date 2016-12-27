package helper;

import java.util.HashMap;
import java.util.Map;

public class TestData {
    public static final HashMap<String, Object> CARD = new HashMap<String, Object>() {
        {
            put("number", "1234567812345678");
            put("balance", 0);
        }
    };

    public static final Map<String, Object> STATEMENT = new HashMap<String, Object>() {
        {
            put("year", 2016);
            put("month", 12);
            put("total", 200);
        }
    };
    public static final Map<String, Object> REPAYMENT = new HashMap<String, Object>() {
        {
            put("statementId", "1");
            put("amount", 100);
        }
    };
}
