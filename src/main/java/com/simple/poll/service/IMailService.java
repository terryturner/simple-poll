package com.simple.poll.service;

import com.simple.poll.model.output.ElectionSimpleStatics;

public interface IMailService {

    void sendReportToAllUser(ElectionSimpleStatics statics) throws Exception;
}
