package com.epam.esm.config;

import com.epam.esm.dto.reponse.BaseExceptionResponse;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.exception.UnknownDataBaseException;
import com.epam.esm.exception.gift_certificate.InvalidCertificateException;
import com.epam.esm.exception.tag.InvalidTagException;
import com.epam.esm.exception.tag.TagAlreadyExistException;
import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<BaseExceptionResponse> classNotFoundExceptionHandler(Exception e){
        return ResponseEntity.ok(
                new BaseExceptionResponse(500, e.getLocalizedMessage(), 10500)
        );
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<BaseExceptionResponse> psqlExceptionHandler(PSQLException e){
        return ResponseEntity.status(400).body(
                new BaseExceptionResponse(400, e.getLocalizedMessage(), 10500)
        );
    }

    @ExceptionHandler(TagAlreadyExistException.class)
    public ResponseEntity<BaseExceptionResponse> tagAlreadyExistExceptionHandler(TagAlreadyExistException e){
        return ResponseEntity.badRequest().body(
                new BaseExceptionResponse(400, e.getMessage(), 10400)
        );
    }

    @ExceptionHandler(InvalidTagException.class)
    public ResponseEntity<BaseExceptionResponse> invalidTagExceptionHandler(InvalidTagException e){
        return ResponseEntity.badRequest().body(
                new BaseExceptionResponse(400, e.getMessage(), 10400)
        );
    }

    @ExceptionHandler(InvalidCertificateException.class)
    public ResponseEntity<BaseExceptionResponse> invalidCertificateExceptionHandler(InvalidCertificateException e){
        return ResponseEntity.badRequest().body(
                new BaseExceptionResponse(400, e.getMessage(), 10400)
        );
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<BaseExceptionResponse> noDataFoundExceptionHandler(NoDataFoundException e){
        return ResponseEntity.status(400).body(
                new BaseExceptionResponse(400, e.getMessage(), 10204)
        );
    }

    @ExceptionHandler(UnknownDataBaseException.class)
    public ResponseEntity<BaseExceptionResponse> unknownDatabaseExceptionHandler(UnknownDataBaseException e){
        return ResponseEntity.status(500).body(
                new BaseExceptionResponse(500, e.getMessage(), 10500)
        );
    }

}
