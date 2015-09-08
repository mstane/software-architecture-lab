package org.sm.lab.mybooks.shared;


public class AppConsts {

    public static final String DISPATCH_PATH = "/mybooks/dispatch";
    
    //Session Management
	public static final String READER = "reader";
	public static final String COOKIE_NAME = "sid";
	
	public static final int SECOND = 1000;
	public static final int DURATION_OF_HOUR_SECONDS = 60 * 60;
	public static final int SESSION_DUARTION_COMMON_SECONDS = 1 * DURATION_OF_HOUR_SECONDS;
	public static final int SESSION_DUARTION_EXTENDED_SECONDS = 14 * 24 * DURATION_OF_HOUR_SECONDS;    //2 weeks
    
    
    private AppConsts() {
    }


}
