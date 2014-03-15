package sk.sochuliak.barabasi.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class TaskTimeCounter {
	
	private static Logger LOGGER = Logger.getLogger(TaskTimeCounter.class);
	
	public static TaskTimeCounter getInstance() {
		if (me == null) {
			me = new TaskTimeCounter();
		}
		return me;
	}
	
	private static TaskTimeCounter me = null;

	private Map<String, Date> taskStarts;
	
	private TaskTimeCounter() {
		this.taskStarts = new HashMap<String, Date>();
	}
	
	public void startTask(String taskName) {
		this.taskStarts.put(taskName, new Date());
	}
	
	public void endTask(String taskName) {
		Date endTime = new Date();
		Date startTime = this.taskStarts.get(taskName);
		this.taskStarts.remove(taskName);
		if (startTime == null) {
			LOGGER.error(String.format("Task %s has never been started!", taskName));
			return;
		}
		long estimatedTime = endTime.getTime() - startTime.getTime();
		this.logMiliseconds(taskName, (double)estimatedTime, estimatedTime);
	}
	
	private void logMiliseconds(String taskName, double estimatedTime, long estimatedTimeInMs) {
		if (estimatedTime < 1000) {
			this.logEstimatedTime(taskName, estimatedTime, "miliseconds", estimatedTimeInMs);
			return;
		}
		this.logSeconds(taskName, estimatedTime / 1000d, estimatedTimeInMs);
	}
	
	private void logSeconds(String taskName, double estimatedTime, long estimatedTimeInMs) {
		if (estimatedTime <= 60) {
			this.logEstimatedTime(taskName, estimatedTime, "seconds", estimatedTimeInMs);
			return;
		}
		this.logMinutes(taskName, estimatedTime / 60d, estimatedTimeInMs);
	}
	
	private void logMinutes(String taskName, double estimatedTime, long estimatedTimeInMs) {
		if (estimatedTime <= 60) {
			this.logEstimatedTime(taskName, estimatedTime, "minutes", estimatedTimeInMs);
			return;
		}
		this.logHours(taskName, estimatedTime / 60d, estimatedTimeInMs);
	}
	
	private void logHours(String taskName, double estimatedTime, long estimatedTimeInMs) {
		if (estimatedTime <= 24) {
			this.logEstimatedTime(taskName, estimatedTime, "hours", estimatedTimeInMs);
			return;
		}
		this.logDays(taskName, estimatedTime / 24d, estimatedTimeInMs);
	}
	
	private void logDays(String taskName, double estimatedTime, long estimatedTimeInMs) {
		this.logEstimatedTime(taskName, estimatedTime, "days", estimatedTimeInMs);
	}
	
	private void logEstimatedTime(String taskName, double estimatedTime, String timeUnit, long estimatedTimeInMs) {
		LOGGER.info(String.format("Task %s took %.2f %s (%d miliseconds).", taskName, estimatedTime, timeUnit, estimatedTimeInMs));
	}
}
