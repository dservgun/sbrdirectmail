package com.sbr.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DateUtil{
  public static long numberOfMillis = 24*60*60*1000;
  public static Date getDate(String aDateFormat, String aDate) throws ParseException{
    SimpleDateFormat dateFormat = getDateFormat(aDateFormat);
    return dateFormat.parse(aDate);
  }
  public static SimpleDateFormat getDateFormat(String aDateFormat){
    return new SimpleDateFormat(aDateFormat);
  }
  public static Date getStartOfDay(Date aDate){
    long millisSinceEpoch = aDate.getTime();
    long startOfDaySinceEpoch = millisSinceEpoch - (millisSinceEpoch % numberOfMillis);
    return new Date(startOfDaySinceEpoch);
  }
}