package Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    public static long getMilliseconds(String date) {
        Date result = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try { result = format.parse(date); }
        catch (ParseException e) { e.printStackTrace(); }
        return result.getTime();
    }
}
