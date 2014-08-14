/**
 * 
 */
package com.uimirror.util;

import org.springframework.util.StopWatch;

/**
 * <p>Utility for the stop watch operations</p>
 * @author Jayaram
 */
public class StopWatchUtil {
	
	/**
	 * <p>
	 * This will format the stopwatch summary to be shown in the log
	 * 
	 * @param msg
	 * @param stopWatch
	 * @return
	 */
	public static String formatStopSwatchSummery(String msg, StopWatch stopWatch) {

		StringBuilder sb = new StringBuilder(100);

		sb.append("\n" + msg + "\n");
		sb.append("\n****************************************************************\n");
		sb.append(stopWatch.prettyPrint());
		sb.append("\n***************************************************************\n");
		sb.append(stopWatch.shortSummary());
		sb.append("\n*************************************************************\n");
		sb.append("Time Taken In second: "
				+ stopWatch.getLastTaskInfo().getTimeSeconds());
		sb.append("\n*************************************************************\n");

		return sb.toString();
	}

}
