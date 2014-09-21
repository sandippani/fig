package com.sandip.fig.service.utils;

public class FigSystemUtils {

	private static final String LIMIT_MY_SQL_RESULT_SET_STRING = " LIMIT ?1, ?2";

	public static String getLimit(final int pageNum, final int pageSize) {
		int firstResult = 0;
		int maxResult = 0;
		if (pageNum > 0 && pageSize > 0) {
			firstResult = (pageNum - 1) * pageSize;
			maxResult = pageSize;
			String finalLimit = LIMIT_MY_SQL_RESULT_SET_STRING.replaceAll(
					"\\?1", String.valueOf(firstResult)).replaceAll("\\?2",
					String.valueOf(maxResult));
			return finalLimit;
		}
		return "";
	}

}
