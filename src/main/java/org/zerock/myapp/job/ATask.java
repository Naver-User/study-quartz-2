package org.zerock.myapp.job;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


//@Slf4j
@Log4j2
@NoArgsConstructor
public class ATask implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.trace("execute(context) invoked.");

        try {
            log.info("\t>>> ATask");

            Thread.sleep(1000L * 5);
            log.info("Waked Up. Done.");
        } catch(Exception original) {
            throw new JobExecutionException(original);
        } // try-catch
    } // execute

} // end class

