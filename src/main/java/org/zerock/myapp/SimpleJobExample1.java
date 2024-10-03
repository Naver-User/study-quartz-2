package org.zerock.myapp;

import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.zerock.myapp.job.SimpleTask;

import java.util.Arrays;
import java.util.Date;

import static org.quartz.DateBuilder.futureDate;


//@Slf4j
@Log4j2
public class SimpleJobExample1 {


    // Simple Job 형태로 SimpleTask를 스케쥴링해서 구동시키는 예제
    public static void main(String... args) throws SchedulerException {
        log.trace("main({}) invoked.", Arrays.toString(args));

        // ---------------------
        // Step1. SimpleTask 에 대한 상세정보(설명, 이름, 그룹)를 가진 JobDetail 객체 생성.
        // ---------------------
        JobDetail simpleTaskJobDetail =
            JobBuilder.newJob(SimpleTask.class)
                    .withDescription("SimpleTask Description")      // 1. 설명

                    // 2. 이름과 그룹명
//                    .withIdentity("SimpleTask")
                    .withIdentity("SimpleTask", "SimpleJobGroup")
//                    .withIdentity(JobKey.jobKey("SimpleTask"))
//                    .withIdentity(JobKey.jobKey("SimpleTask", "SimpleJobGroup"))

                    // 3. Task 에 데이터 공급
//                    .usingJobData("greeting", "Welcome to")     // key = value(String)
//                    .usingJobData("greeting", true)             // key = value(Boolean)
                    .usingJobData("KEY_1", "VALUE_1")
                    .usingJobData("KEY_2", "VALUE_2")
                    .usingJobData("KEY_3", "VALUE_3")
                    .build();

        log.info("\t+ Step1. simpleTaskJobDetail: {}", simpleTaskJobDetail);


        // ---------------------
        // Step2. Step1 에서 생성한 JobDetail 객체를 기반으로 스케쥴링(Trigger 객체 생성)
        // ---------------------
        assert simpleTaskJobDetail != null;

        Trigger simpleTaskTrigger =
            TriggerBuilder.newTrigger()
//                    .startNow()             // 1st. test
                                        // interval, interval unit
                    .startAt(futureDate(3, DateBuilder.IntervalUnit.SECOND))    // 2nd. test
                    .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
//                            .withIntervalInMilliseconds( 200L )  // 1st. test
                            .withIntervalInSeconds(1)            // 2nd. test
//                            .withIntervalInMinutes(1)           // 3rd. test
//                            .withIntervalInHours(1)                 // 4rd. test

                            .withRepeatCount(10)    // 스케쥴링 최종 메소드1
//                            .repeatForever()        // 스케쥴링 최종 메소드2
                    )
                    .build();

        log.info("\t+ simpleTaskTrigger: {}", simpleTaskTrigger);


        // ---------------------
        // Step3. Step1 에서 생성한 JobDetail 과 Step2 에서 생성한 Trigger 를 기반으로
        //        Quartz Scheduler 에 등록
        // ---------------------

        // 1st. method
        Scheduler scheduler1 = StdSchedulerFactory.getDefaultScheduler();
        log.info("\t1st. method - scheduler1: {}", scheduler1);

        // 2nd. method
//        Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
//        log.info("\t2nd. method - scheduler2: {}", scheduler2);

        // 스케쥴러가 제공하는 스케쥴링 메소드를 이용해서, JobDetail / Trigger 객체를
        // 이용하여 등록합니다.
        Date scheduledDate =
            scheduler1.scheduleJob(simpleTaskJobDetail, simpleTaskTrigger);
        log.info("\t+ scheduledDate: {}", scheduledDate);


        // ---------------------
        // Step4. Start Quartz Scheduler
        // ---------------------
        scheduler1.start();
    } // main

} // end class
