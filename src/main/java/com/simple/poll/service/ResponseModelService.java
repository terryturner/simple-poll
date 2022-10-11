package com.simple.poll.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.simple.poll.model.output.ApiResponseBody;

@Service
public class ResponseModelService {
    @Autowired
    private HttpServletRequest request;
    
    public <T> ResponseEntity<ApiResponseBody<T>> getSuccessResult(T resultObject) {
        ApiResponseBody<T> response = new ApiResponseBody<>();
        response.setStatus(1);
        response.setUrl(request.getRequestURI());
        response.setResultBody(resultObject);
        ResponseEntity<ApiResponseBody<T>> result= new ResponseEntity<>(response, HttpStatus.OK);
        return result;
    }
    
    public <T> ResponseEntity<ApiResponseBody<T>> getCreateSuccessResult(T resultObject) {
        ApiResponseBody<T> response = new ApiResponseBody<>();
        response.setStatus(1);
        response.setUrl(request.getRequestURI());
        response.setResultBody(resultObject);
        ResponseEntity<ApiResponseBody<T>> result= new ResponseEntity<>(response, HttpStatus.CREATED);
        return result;
    }

    public ResponseEntity<ApiResponseBody<Object>> getDefaultSuccessResult() {
        ApiResponseBody<Object> response = new ApiResponseBody<>();
        response.setStatus(1);
        response.setUrl(request.getRequestURI());
        ResponseEntity<ApiResponseBody<Object>> result = new ResponseEntity<>(response, HttpStatus.OK);
        return result;
    }

    public ResponseEntity<ApiResponseBody<Object>> getDefaultCreateSuccessResult() {
        ApiResponseBody<Object> response = new ApiResponseBody<>();
        response.setStatus(1);
        response.setUrl(request.getRequestURI());
        ResponseEntity<ApiResponseBody<Object>> result = new ResponseEntity<>(response, HttpStatus.CREATED);
        return result;
    }
}
