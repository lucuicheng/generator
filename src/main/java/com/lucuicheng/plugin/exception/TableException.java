package com.lucuicheng.plugin.exception;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * Created by lucc1 on 2016/6/29.
 */
public class TableException extends MojoExecutionException {

    public TableException(Object source, String shortMessage, String longMessage) {
        super(source, shortMessage, longMessage);
    }

    public TableException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableException(String message, Exception cause) {
        super(message, cause);
    }

    public TableException(String message) {
        super(message);
    }
}
