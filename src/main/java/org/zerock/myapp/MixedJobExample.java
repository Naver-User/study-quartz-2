package org.zerock.myapp;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.zerock.myapp.job.ATask;
import org.zerock.myapp.job.BTask;
import org.zerock.myapp.listener.SchedulerListenerImpl;

import java.util.Arrays;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.StdSchedulerFactory.getDefaultScheduler;


@Slf4j
@Log4j2
public class MixedJobExample {


    public static void main(String[] args) throws SchedulerException {
        log.trace("main({}) invoked.", Arrays.toString(args));

        // Step1. ATask 와 BTask 각각에 대한 JobDetail 생성
        //        JobBuilder.newJob(Clazz<Task>).build()
        JobDetail aTaskDetail = newJob(ATask.class).build();
        JobDetail bTaskDetail = newJob(BTask.class).build();


        // Step2. Step1에서 생성한 JobDetail 과는 전혀 무관한, 재사용가능한
        //        Simple Schedule 생성
        Trigger simpleJobTrigger =
            newTrigger()
                .withSchedule(
                    simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever()
                )
                .startNow()
                .build();

        // Step3. Step1 에서 생성한 JobDetail 과는 전혀 무관한, 재사용가능한
        //        Cron Schedule 생성
        Trigger cronJobTrigger =
            newTrigger()
                .withSchedule(
                    cronSchedule("0/7 * * * * ?")
                )
                .startNow()
                .build();

        // Step4. Quartz Scheduler 생성
        Scheduler sched = getDefaultScheduler();
//        Scheduler sched = new StdSchedulerFactory().getScheduler();

        // Scheduler Listener 는 설정파일에서 등록할 수가 없기 때문에
        // 아래와 같이 직접 코드를 통해 등록해야 합니다.
        ListenerManager listerManager = sched.getListenerManager();
        listerManager.addSchedulerListener(new SchedulerListenerImpl());

        // Step5. ATask는 Simple Schedule 방식으로 등록,
        //        BTask는 Cron Schedule 방식으로 등록
        sched.scheduleJob(aTaskDetail, simpleJobTrigger);
        sched.scheduleJob(bTaskDetail, cronJobTrigger);

        // Step6. Quartz Scheduler 구동
        sched.start();
    } // main

} // end class
