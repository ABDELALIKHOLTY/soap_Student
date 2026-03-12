package com.example.demo.Exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM,customFaultCode = "{http://kholty.com/Student}001_not found")
public class StudentException extends RuntimeException {
    public  StudentException(String message){
        super(message);
    }
}
