package org.zerock.myapp;

import lombok.extern.log4j.Log4j2;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.zerock.myapp.job.BTask;

import java.util.Arrays;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;
import static org.quartz.impl.StdSchedulerFactory.getDefaultScheduler;


@Log4j2
public class CronJobExample {

    // Cron table 에 기반하여 스케쥴링 되는 Cron Job 예제 실습
    public static void main(String... args) throws SchedulerException {
        log.trace("main({}) invoked.", Arrays.toString(args));

        JobDetail bTask =
            newJob(BTask.class)
                .withDescription("Cron Job Style's BTask")
                .withIdentity(jobKey("BTask", "GROUP1"))
                .build();

        // Cron Table 방식의 Scheduling 수행해서 Trigger 를 생성 (차이점)
        Trigger cronTrigger =
            newTrigger()
                .withDescription("Cron Table Based Trigger")
                .withIdentity(triggerKey("BTaskTrigger", "GROUP1"))
                .startNow()
                .withSchedule(
                    /*
                     * ---------------------------------------------------------------------
                     *      Field		Mandatory	Allowed				Allowed
                     *      Name					Values				Special Characters
                     * ---------------------------------------------------------------------
                     * 1st. Seconds			YES		0-59				, - * /
                     * 2nd. Minutes			YES		0-59				, - * /
                     * 3rd. Hours			YES		0-23				, - * /
                     * 4th. Day of month	YES		1-31				, - * ? / L W
                     * 5th. Month			YES		1-12 or JAN-DEC	, 	- * /
                     * 6th. Day of week		YES		1-7 or SUN-SAT	, 	- * ? / L #
                     * 7th. Year			NO		empty, 1970-2099	, - * /
                     * ---------------------------------------------------------------------
                     * Note1: Support for specifying both a `day-of-week` AND a `day-of-month` parameter is NOT implemented.
                     * Note2: '?' can only be specified for Day-of-Month or Day-of-Week.
                     * Note3: '-' is an unexpected character in the year.
                     */
                    cronSchedule("*/3 * * * * ?")
                )
                .build();

        Scheduler sched = getDefaultScheduler();
        sched.scheduleJob(bTask, cronTrigger);

        sched.start();
    } // main

} // end class


