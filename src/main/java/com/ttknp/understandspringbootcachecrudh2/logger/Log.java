package com.ttknp.understandspringbootcachecrudh2.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    public Logger application;

    public Log(Class<?> className) {
        this.application = LoggerFactory.getLogger(className);
    }
}
