package org.zerock.myapp.listener;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;


@Log4j2
@NoArgsConstructor
public class JobListenerImpl implements JobListener {


    @Override
    public String getName() {   // Listener 의 이름을 반환
        log.trace("getName() invoked.");
        return "CustomJobListener";
    } // getName

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.trace("jobToBeExecuted({}) invoked.", context);
    } // jobToBeExecuted

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.trace("jobExecutionVetoed({}) invoked.", context);
    } // jobExecutionVetoed

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.trace("jobWasExecuted({}, {}) invoked.", context, jobException);
    } // jobWasExecuted

} // end class
