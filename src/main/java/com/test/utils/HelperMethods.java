package com.test.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@Component
public class HelperMethods {
    private static final long DIFF_NET_JAVA_FOR_DATE_AND_TIMES=1164473600000L;
    private static final long DIFF_NET_JAVA_FOR_DATES=1164473600000L+24*60*60*1000;
    public String convertDateToString(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if(null==date){
            return df.format(new Date(0));
        }
        else {
            return df.format(date);
        }
    }
    public Date getDateTimeFrom(String adDateStr){
        long adDate = Long.parseLong(adDateStr);
        long milliSeconds=(adDate/10000)-DIFF_NET_JAVA_FOR_DATES;
        Date date = new Date(milliSeconds);
        return date;
    }
    public Date truncTimeFrom(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return  cal.getTime();
    }
    public  Date addDaysToDate(int daysToAdd,Date date) {
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE,daysToAdd );
    	return cal.getTime();
    }
}