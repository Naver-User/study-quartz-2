package org.zerock.myapp.job;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


//@Slf4j
@Log4j2
@NoArgsConstructor
public class CTask implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.trace("execute(context) invoked.");

        try {
            log.info("\t>>> CTask");

        } catch(Exception _original) {
            throw new JobExecutionException(_original);
        } // try-catch
    } // execute

} // end class


