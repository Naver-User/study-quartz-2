package org.zerock.myapp;

import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.zerock.myapp.job.ATask;
import org.zerock.myapp.listener.JobListenerImpl;
import org.zerock.myapp.listener.SchedulerListenerImpl;
import org.zerock.myapp.listener.TriggerListenerImpl;

import java.util.Arrays;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.StdSchedulerFactory.getDefaultScheduler;


@Log4j2
public class SimpleJobExample2 {

    public static void main(String[] args) throws SchedulerException {
        log.trace("main({}) invoked.", Arrays.toString(args));

        JobDetail aTask =
            newJob(ATask.class)
                .withDescription("ATask")
                .withIdentity("ATask", "GROUP1")
                .build();

        Trigger aTaskTrigger =
            newTrigger()
                .withIdentity("ATaskTrigger", "GROUP1")
                .startNow()
                .withSchedule(
                    simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever()
                )
                .build();

        Scheduler scheduler = getDefaultScheduler();
        scheduler.scheduleJob(aTask, aTaskTrigger);

        ListenerManager manager = scheduler.getListenerManager();

        manager.addJobListener(new JobListenerImpl());
        manager.addSchedulerListener(new SchedulerListenerImpl());
        manager.addTriggerListener(new TriggerListenerImpl());

        scheduler.start();
    } // main

} // end class
