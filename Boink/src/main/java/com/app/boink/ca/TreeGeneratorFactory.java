package com.app.boink.ca;

public interface TreeGeneratorFactory {
    abstract TreeGenerator getGenerator(String path) throws ConfigProperties.ConfigLoadingException;
}
