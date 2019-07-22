package com.taogen.hotcrawler.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 674487573480637090L;
    private static final Logger log = LoggerFactory.getLogger(DataNotFoundException.class);

    public DataNotFoundException() {}

    public DataNotFoundException(String errorMessage)
    {
        log.error(errorMessage);
    }

    public DataNotFoundException(String message, Throwable cause)
    {
        log.error(message, cause);
    }
}
