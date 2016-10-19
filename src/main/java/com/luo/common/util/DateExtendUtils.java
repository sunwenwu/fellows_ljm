package com.luo.common.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.luo.common.utils.DateUtil;

/**
 * 扩展Rock-core的DateUtil提供部分扩展功能
 *
 * @author LiuJian
 */
public class DateExtendUtils extends DateUtil {


	private static final String FORMAT_yyyyMMdd = "yyyyMMdd";
	private static final String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
	private static final String FORMAT_yyyy_MM_dd_p = "yyyy/MM/dd";

	private static final String[] patterns = { FORMAT_yyyyMMdd, FORMAT_yyyy_MM_dd, FORMAT_yyyy_MM_dd_p };

	private static final long SECOND = 1000;

	private static final long MINUTE = 1000 * 60;

	private static final long HOUR = 1000 * 60;

	private static final long DAY = 1000 * 60 * 60 * 24;

	/**
	 * DATE_FORMAT="yyyyMMdd"
	 *
	 * @param date
	 *            待输入
	 * @return 字符格式化
	 */
	public static String formatDate(Date date) {
		return DateFormatUtils.format(date, FORMAT_yyyyMMdd);
	}

	/**
	 * DATE_FORMAT="yyyyMMdd"
	 *
	 * @param date
	 *            待输入
	 * @return 字符格式化
	 */
	public static Date parseDate(String date) {
		try {
			return DateUtils.parseDate(date, patterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 判断当前距离上次更新还款日期是否已过6个月
	 *
	 * @param lastDueDay
	 * @return
	 */
	public static boolean after6Mounth(Date lastDueDay) {
		long l = diffHours(lastDueDay, new Date());
		return l >= 6;
	}

	/**
	 * 判断更新日期是否是还款日期
	 *
	 * @param dueDate
	 * @return
	 */
	public static boolean currentDay(Date dueDate) {
		long l = diffDays(dueDate, new Date());
		return l == 0;
	}

	

	/**
	 * 计算两个时间差了多少秒
	 *
	 * @param first
	 * @param last
	 * @return
	 */
	public static long diffSeconds(Date first, Date last) {
		long diff = first.getTime() - last.getTime();
		return Math.abs(diff / SECOND);
	}

	/**
	 * 计算两个时间差了多少分
	 *
	 * @param first
	 * @param last
	 * @return
	 */
	public static long diffMinutes(Date first, Date last) {
		long diff = first.getTime() - last.getTime();
		return Math.abs(diff / MINUTE);
	}

	/**
	 * 计算两个时间差了多少小时
	 *
	 * @param first
	 * @param last
	 * @return
	 */
	public static long diffHours(Date first, Date last) {
		long diff = first.getTime() - last.getTime();
		return Math.abs(diff / HOUR);
	}

	/**
	 * 计算两个时间差了多少天
	 *
	 * @param first
	 * @param last
	 * @return
	 */
	public static long diffDays(Date first, Date last) {
		long diff = first.getTime() - last.getTime();
		return Math.abs(diff / DAY);
	}

	/**
	 * 计算两个时间差了多少天
	 *
	 * @param first
	 * @param last
	 * @return
	 */
	public static long diffMonth(Date first, Date last) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(first);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(last);
		long diff = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
		int _diff;
		if (c1.after(c2)) {
			_diff = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		} else {
			_diff = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		}
		diff = (diff * 12) + _diff;
		return Math.abs(diff);
	}

	/**
	 * 计算两个时间差了多少天
	 *
	 * @param first
	 * @param last
	 * @return
	 */
	public static long diffYear(Date first, Date last) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(first);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(last);
		long diff = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
		return Math.abs(diff);
	}

	/**
	 * 获取日期的中的日
	 *
	 * @param date
	 * @return
	 */
	public static int dateInDay(String date) {
		Calendar c = Calendar.getInstance();// 获得一个日历的实例
		Date d = parseDate(date);
		if (d == null)
			return 0;
		c.setTime(d);// 设置日历时间
		return c.get(Calendar.DATE);
	}

	// 获取日期的中的月
	public static int dateInMonth(String date) {
		Calendar c = Calendar.getInstance();// 获得一个日历的实例
		Date d = parseDate(date);
		c.setTime(d);// 设置日历时间
		return c.get(Calendar.MONTH) + 1;
	}

	// 获取日期的中的年
	public static int dateInYear(String date) {
		Calendar c = Calendar.getInstance();// 获得一个日历的实例
		Date d = parseDate(date);
		if (d == null)
			return 0;
		c.setTime(d);// 设置日历时间
		return c.get(Calendar.YEAR);
	}

}
