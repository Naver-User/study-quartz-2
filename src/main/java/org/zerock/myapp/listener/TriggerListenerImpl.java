package org.zerock.myapp.listener;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;


//@Log4j2
@Slf4j
@NoArgsConstructor
public class TriggerListenerImpl implements TriggerListener {

    @Override
    public String getName() {
        log.trace("getName() invoked.");

        return "TriggerListener";
    } // getName

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.trace("triggerFired({}, {}) invoked.", trigger, context);
    } // triggerFired

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        log.trace("vetoJobExecution({}, {}) invoked.", trigger, context);
        return false;
    } // vetoJobExecution

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.trace("triggerMisfired({}) invoked.", trigger);
    } // triggerMisfired

    @Override
    public void triggerComplete(
        Trigger trigger,
        JobExecutionContext context,
        Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        log.trace("triggerComplete({}, {}, {}) invoked.", trigger, context, triggerInstructionCode);
    } // triggerComplete

} // end class
