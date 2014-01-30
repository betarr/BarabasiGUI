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
		this.logMiliseconds(taskName, (double)estimatedTime);
	}
	
	private void logMiliseconds(String taskName, double estimatedTime) {
		if (estimatedTime < 1000) {
			this.logEstimatedTime(taskName, estimatedTime, "miliseconds");
			return;
		}
		this.logSeconds(taskName, estimatedTime / 1000d);
	}
	
	private void logSeconds(String taskName, double estimatedTime) {
		if (estimatedTime <= 60) {
			this.logEstimatedTime(taskName, estimatedTime, "seconds");
			return;
		}
		this.logMinutes(taskName, estimatedTime / 60d);
	}
	
	private void logMinutes(String taskName, double estimatedTime) {
		if (estimatedTime <= 60) {
			this.logEstimatedTime(taskName, estimatedTime, "minutes");
			return;
		}
		this.logHours(taskName, estimatedTime / 60d);
	}
	
	private void logHours(String taskName, double estimatedTime) {
		if (estimatedTime <= 24) {
			this.logEstimatedTime(taskName, estimatedTime, "hours");
			return;
		}
		this.logDays(taskName, estimatedTime / 24d);
	}
	
	private void logDays(String taskName, double estimatedTime) {
		this.logEstimatedTime(taskName, estimatedTime, "days");
	}
	
	private void logEstimatedTime(String taskName, double estimatedTime, String timeUnit) {
		LOGGER.info(String.format("Task %s took %.2f %s.", taskName, estimatedTime, timeUnit));
	}
}
