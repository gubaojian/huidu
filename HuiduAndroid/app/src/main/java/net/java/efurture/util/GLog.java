package net.java.efurture.util;

import android.util.Log;

public class GLog {
	/**
	 * 日志输出的Level
	 * */
	public static int LEVEL = Log.ERROR;
	
    public static int v(String tag, String msg) {
    	   if (LEVEL <= Log.VERBOSE) {
	         return Log.v(tag, msg);
       }else {
			return 0;
	    }
    }

    public static int v(String tag, String msg, Throwable tr) {
      	if (LEVEL <= Log.VERBOSE) {
	         return Log.v(tag, msg, tr);
        }else {
			return 0;
		 }
    }

  
    public static int d(String tag, String msg) {
      	 if (LEVEL <= Log.DEBUG) {
	         return Log.d(tag, msg);
         }else {
			return 0;
		 }
    }


    
    public static int d(String tag, String msg, Throwable tr) {
    	   if (LEVEL <= Log.DEBUG) {
	         return Log.d(tag, msg, tr);
       }else {
			return 0;
		}
    }

 
    public static int i(String tag, String msg) {
      if (LEVEL <= Log.INFO) {
	         return Log.i(tag, msg);
       }else {
			return 0;
		}
    }

 
    public static int i(String tag, String msg, Throwable tr) {
 	   if (LEVEL <= Log.INFO) {
 	         return Log.i(tag, msg, tr);
        }else {
			return 0;
		}
     }

    public static int w(String tag, String msg) {
    	   if (LEVEL <= Log.WARN) {
   	         return Log.w(tag, msg);
         }else {
			return 0;
		}
    }

  
    public static int w(String tag, String msg, Throwable tr) {
    	     if (LEVEL <= Log.WARN) {
   	         return Log.w(tag, msg, tr);
         }else {
			return 0;
		}
     }

   
    public static int w(String tag, Throwable tr) {
    	     if (LEVEL <= Log.WARN) {
   	         return Log.w(tag, tr);
         }else {
			return 0;
		}
     }

  
    public static int e(String tag, String msg) {
    	    if (LEVEL <= Log.ERROR) {
   	         return Log.e(tag, msg);
         }else {
			return 0;
		}
    }

    
    public static int e(String tag, String msg, Throwable tr) {
    	      if (LEVEL <= Log.ERROR) {
    	    	      return Log.e(tag, msg, tr);
		   }else {
				return 0;
		  }
     }
}
