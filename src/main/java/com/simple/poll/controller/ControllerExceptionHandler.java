package com.simple.poll.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.simple.poll.model.output.ApiResponseBody;
import com.simple.poll.model.output.ErrorObj;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private HttpServletRequest httpRequest;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("HttpMessageNotReadableException", ex);

        ApiResponseBody<Object> response = new ApiResponseBody<>();
        response.setStatus(0);
        response.setUrl(httpRequest.getRequestURI());
        response.setError(new ErrorObj(99990, ex.getMessage()));
        patchErrorList(response);
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("HttpRequestMethodNotSupportedException", ex);

        ApiResponseBody<Object> response = new ApiResponseBody<>();
        response.setStatus(0);
        response.setUrl(httpRequest.getRequestURI());
        response.setError(new ErrorObj(99991, ex.getMessage()));
        patchErrorList(response);
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("HttpMediaTypeNotSupportedException", ex);

        ApiResponseBody<Object> response = new ApiResponseBody<>();
        response.setStatus(0);
        response.setUrl(httpRequest.getRequestURI());
        response.setError(new ErrorObj(99992, ex.getMessage()));
        patchErrorList(response);
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("HttpMediaTypeNotAcceptableException", ex);

        ApiResponseBody<Object> response = new ApiResponseBody<>();
        response.setStatus(0);
        response.setUrl(httpRequest.getRequestURI());
        response.setError(new ErrorObj(99993, ex.getMessage()));
        patchErrorList(response);
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.error("MissingPathVariableException", ex);

        ApiResponseBody<Object> response = new ApiResponseBody<>();
        response.setStatus(0);
        response.setUrl(httpRequest.getRequestURI());
        response.setError(new ErrorObj(99994, ex.getMessage()));
        patchErrorList(response);
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("MissingServletRequestParameterException", ex);

        ApiResponseBody<Object> response = new ApiResponseBody<>();
        response.setStatus(0);
        response.setUrl(httpRequest.getRequestURI());
        response.setError(new ErrorObj(99995, ex.getMessage()));
        patchErrorList(response);
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ApiResponseBody<Object>> restClientExceptionHandler(Exception e, WebRequest request) {
        ApiResponseBody<Object> response = new ApiResponseBody<>();
        response.setStatus(0);
        response.setError(new ErrorObj(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        patchErrorList(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiResponseBody<Object>> httpClientExceptionHandler(Exception e, WebRequest request) {
        ApiResponseBody<Object> response = new ApiResponseBody<>();
        response.setStatus(0);
        response.setError(new ErrorObj(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        patchErrorList(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseBody<Object>> handleJDBCError(HttpServletRequest req, Exception ex) {
        ApiResponseBody<Object> response = new ApiResponseBody<>();

        response.setStatus(0);
        response.setUrl(httpRequest.getRequestURI());
        response.setError(new ErrorObj(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        patchErrorList(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponseBody<Object> response = new ApiResponseBody<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String messages = fieldErrors.stream()
                .map(x -> x.getField().concat(":").concat(x.getDefaultMessage()).concat(","))
                .reduce("", String::concat);
        response.setStatus(0);
        response.setUrl(httpRequest.getRequestURI());
        response.setError(
                new ErrorObj(HttpStatus.BAD_REQUEST.value(), messages));

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseBody<Object>> globalExceptionHandler(Exception e, WebRequest request) {
        ApiResponseBody<Object> response = new ApiResponseBody<>();
        // List<ErrorObj> errors = new ArrayList<>();
        response.setStatus(0);
        response.setUrl(httpRequest.getRequestURI());
        response.setError(new ErrorObj(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        patchErrorList(response);
        e.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseBody<Object>> httpAccessDeniedExceptionHandler(Exception e, WebRequest request) {
        ApiResponseBody<Object> response = new ApiResponseBody<>();
        response.setStatus(0);
        response.setError(new ErrorObj(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        patchErrorList(response);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExceptionBusiness.class)
    public ResponseEntity<ApiResponseBody<Object>> handleError(HttpServletRequest req, ExceptionBusiness ex) {
        ApiResponseBody<Object> response = new ApiResponseBody<>();

        List<ErrorObj> errors = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        response.setStatus(0);
        response.setUrl(httpRequest.getRequestURI());
        response.setError(new ErrorObj(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        //response.setErrorList(errors);

        response.setResultBody(map);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ExceptionResourceMissing.class)
    public ResponseEntity<ApiResponseBody<Object>> handleError(HttpServletRequest req, ExceptionResourceMissing ex) {
        ApiResponseBody<Object> response = new ApiResponseBody<>();

        List<ErrorObj> errors = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        response.setStatus(0);
        response.setUrl(httpRequest.getRequestURI());
        response.setError(new ErrorObj(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
        //response.setErrorList(errors);

        response.setResultBody(map);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private void patchErrorList(ApiResponseBody<Object> response) {
        List<ErrorObj> errors = new ArrayList<>();
        if (response.getError() != null) {
            errors.add(response.getError());
        }
        response.setErrorList(errors);
    }
}
