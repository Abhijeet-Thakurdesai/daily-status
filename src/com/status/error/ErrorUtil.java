package com.status.error;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorUtil {
	
	@Autowired
	private  Properties errMessages;
		
	public String getError(StatusErrorCode error) {
		return errMessages.getProperty(error.getCode());
	}

	public String getError(StatusErrorCode error, String params) {
		return errMessages.getProperty(error.getCode(), params);
	}
}
