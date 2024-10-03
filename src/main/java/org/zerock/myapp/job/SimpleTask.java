package org.zerock.myapp.job;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


//@Slf4j
@Log4j2
@NoArgsConstructor
public class SimpleTask implements Job {
    private static int instanceCount;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.trace("execute(context) invoked.");

        try {
            log.info("\t>>> SimpleTask");

            /**
            JobDataMap map = context.getJobDetail().getJobDataMap();
            log.info("\t+ map: {}, isMapType: {}", map, map instanceof Map);

//            String greeting = map.getString("greeting");
            log.info("\t+ KEY_1({}), KEY_2({}), KEY_3({})",
                    map.get("KEY_1"), map.get("KEY_2"), map.get("KEY_3"));
            */

            // Quartz Scheduler 가 Task를 구동시킬 때마다, 새로운 task 객체를 생성 및 구동
            log.info("\t+ instanceCount: {}, this: {}", ++instanceCount, this);
        } catch(Exception _original) {
            _original.printStackTrace();
            throw new JobExecutionException(_original);
        } // try-catch
    } // execute

} // end class

