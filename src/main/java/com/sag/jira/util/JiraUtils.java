package com.sag.jira.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraUtils {
  protected final static Logger log = LoggerFactory.getLogger(JiraUtils.class);

  public static double convertSecondsToHours(final Integer seconds) {
    if ((null != seconds) && (seconds > 0)) {
      return seconds / 3600;
    } else {
      return 0;
    }
  }

  public static Date convertStringToDate(final String dateInString) {
    return convertStringToDate(dateInString, "yyyy-MM-dd");
  }

  public static Date convertStringToDate(final String dateInString, final String dateFormat) {
    final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
    Date date = null;
    try {
      date = formatter.parse(dateInString);
      formatter.format(date);
    } catch (final ParseException e) {
    }
    return date;
  }

  public static String getCurrentTime() {
    final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    final Calendar cal = Calendar.getInstance();
    return dateFormat.format(cal.getTime());
  }

  public static boolean isDateMatched(final String date1, final String date2) {
    final Date modDate1 = convertStringToDate(date1);
    final Date modDate2 = convertStringToDate(date2);
    final int compareTo = modDate1.compareTo(modDate2);
    if (compareTo == 0) {
      return true;
    }
    return false;
  }

  public static boolean isValidDateFormate(final String date) {
    if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
      log.info("date format did not mathced! Please use format as YYYY-MM-DD, eg 2017-06-07");
      return false;
    }
    return true;
  }

  public static boolean isWorkStartedForTheItrac(final String status) {
    if (status.equalsIgnoreCase("open") || status.equalsIgnoreCase("implementing")) {
      return true;
    }
    return false;
  }
}
