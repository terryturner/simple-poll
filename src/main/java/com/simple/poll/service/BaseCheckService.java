package com.simple.poll.service;

import java.util.List;

import com.simple.poll.controller.ExceptionBusiness;
import com.simple.poll.controller.ExceptionResourceMissing;

public class BaseCheckService {
    protected void buildBusinessException(List<String> errors) throws ExceptionBusiness {
        StringBuilder sb = new StringBuilder();
        for (String errorMsg: errors) {
            sb.append(errorMsg);
            sb.append(System.getProperty("line.separator"));
        }
        if (errors.size() > 0) {
            throw new ExceptionBusiness(sb.toString());
        }
    }
    
    protected void buildResourceMissingException(List<String> errors) throws ExceptionResourceMissing {
        StringBuilder sb = new StringBuilder();
        for (String errorMsg: errors) {
            sb.append(errorMsg);
            sb.append(",");
        }
        if (errors.size() > 0) {
            throw new ExceptionResourceMissing(sb.toString());
        }
    }
}
