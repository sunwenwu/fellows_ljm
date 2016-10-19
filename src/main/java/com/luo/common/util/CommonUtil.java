package com.luo.common.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;


/**
 * Created by Jacobow on 2016/7/6.
 */
public class CommonUtil {

	// /**
	// * 0 2位精度文本
	// */
	// public static final String ZERO_AMOUNT_SCALE_2 = "0.00";
	// /**
	// * 0 2位精度 数字
	// */
	// public static final BigDecimal ZERO_SCALE_2 = new
	// BigDecimal(ZERO_AMOUNT_SCALE_2);

	/**
	 * 获取文件名称
	 *
	 * @param oldName
	 *            旧的文件名称
	 * @return
	 */
	public static String getSystemFileName(String oldName) {
		if (oldName == null || "".equals(oldName))
			return UUID.randomUUID().toString();

		String suffix = oldName.contains(".") ? oldName.substring(oldName.lastIndexOf(".")) : "";
		return UUID.randomUUID().toString() + suffix;
	}

	/**
	 * 格式化输出
	 * 
	 * @param input
	 *            输入项
	 * @return 格式化后的输出项
	 */
	public static String formatOutput(Object input) {
		if (input == null) {
			return "";
		}
		return String.valueOf(input);
	}

	/**
	 * 保留2位小数格式化
	 * 
	 * @param input
	 * @return
	 */
	public static String formatAmountOutput(BigDecimal input) {
		if (input == null) {
			return "";
		}
		BigDecimal result = input.setScale(2);
		return String.valueOf(result);
	}

	/**
	 * 保留2位小数格式化
	 * 
	 * @param input
	 * @return
	 */
	public static String formatDefaultZeroAmountOutput(BigDecimal input) {
		if (input == null) {
			return LexicalConstant.ZERO_AMOUNT_SCALE_2;
		}
		BigDecimal result = input.setScale(2);
		return String.valueOf(result);
	}

	public static String formatRateMulti100Output(BigDecimal input) {
		// 当传入为空时，返回0.00作为费率
		if (input == null) {
			return LexicalConstant.ZERO_AMOUNT_SCALE_2;
		}
		BigDecimal result = input.multiply(new BigDecimal("100")).setScale(2);
		return String.valueOf(result);
	}

	/**
	 * 0.* 格式化输出 当输入为null时，默认返回0
	 * 
	 * @param input
	 *            输入项
	 * @return 格式化后的输出项
	 */
	public static String formatOutput(Integer input) {
		if (input == null) {
			return null;
		}
		return String.valueOf(input);
	}

	public static Integer convertToInt(String intValue) {
		if (StringUtils.isBlank(intValue)) {
			System.out.println("传入待转换数字为空");
			return null;
		}
		try {
			return Integer.parseInt(intValue);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据8位 20160808 格式日期返回 08 日 格式
	 * 
	 * @param dateInput
	 *            8位的日期格式字符串
	 * @return 还款日期
	 */
	public static String getDisplayRepayDate(String dateInput) {
		if (StringUtils.isBlank(dateInput)) {
			return "";
		} else if (dateInput.length() != 8) {
			return "";
		} else {
			// 还款日展示
			return "每月" + dateInput.substring(dateInput.length() - 2) + "日";
		}
	}

	/**
	 * 根据8位 20160808 格式日期返回 8 格式
	 * 
	 * @param dateInput
	 *            还款日期
	 * @return 还款日
	 */
	public static String getDisplayRepayConfirm(String dateInput) {
		if (StringUtils.isBlank(dateInput)) {
			return "";
		} else if (dateInput.length() != 8) {
			return "";
		} else {
			// 还款日展示
			return String.valueOf(Integer.valueOf(dateInput.substring(dateInput.length() - 2)));
		}
	}

	/**
	 * 长亮的日期格式化工具
	 */
	private final static DateFormat changliangDateFormat = new SimpleDateFormat("yyyyMMdd");
	private final static DateFormat repayDateFormat = new SimpleDateFormat("yyyy/MM/dd");

	public static String formatRepayDate(String srcDate) {
		if (StringUtils.isBlank(srcDate)) {
			System.out.println("日期为空 ");
			return "";
		}
		try {
			Date date = changliangDateFormat.parse(srcDate);

			return repayDateFormat.format(date);
		} catch (ParseException e) {
			return "";
		}
	}

	public static Integer calculateCurrentTerm(String loanCurrTerm) {
		Integer src = convertToInt(loanCurrTerm);
		// 默认返回第一期
		if (src == null) {
			return 1;
		} else {
			return src + 1;
		}
	}

	/**
	 * 判断更新日期是否是还款日期
	 *
	 * @param dueDate
	 * @return 判定给定日期是否为当前日
	 */
	public static boolean isToday(String date) {
		Date srcDate;
		try {
			srcDate = changliangDateFormat.parse(date);
			return isToday(srcDate);
		} catch (ParseException e) {
			System.out.println("日期解析异常:" + date);
			return true;
		}

	}

	/**
	 * 判断更新日期是否是还款日期
	 *
	 * @param dueDate
	 * @return
	 */
	public static boolean isToday(Date dueDate) {
		long l = DateExtendUtils.diffDays(dueDate, new Date());
		return l == 0;
	}
}
