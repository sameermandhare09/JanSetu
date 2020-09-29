package cssl.dialstar.representative_util;

/**
 * Created by catseye on 14/12/17.
 */

public class Config {


    public static String representativeremoteurl = "http://205.147.109.242:9988/";
    public static String representativeremoteurl1 = "http://205.147.109.242:9090/";

//    public static String representativeremoteurl = "http://192.168.122.1:8099/";
//    public static String representativeremoteurl1 = "http://192.168.122.1:9090/";
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";

}