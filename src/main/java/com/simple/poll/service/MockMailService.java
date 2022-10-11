package com.simple.poll.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.simple.poll.model.output.ElectionSimpleStatics;

@Service
public class MockMailService implements IMailService {

    @Async
    @Override
    public void sendReportToAllUser(ElectionSimpleStatics statics) throws Exception {
        //TODO: create the send mail of election closed task, and set the state of task as PROCESSING
        
        //TODO: find the user by page
        
        //TODO: send the static to every user's email each page
        
        //TODO: save the mail sent status related to this task
        
        //TODO: update the state of task as DONE
    }

}
