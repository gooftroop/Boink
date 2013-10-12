package com.app.boink.ca;

import org.jetbrains.annotations.NotNull;

public interface ConfigPropertiesFactory {
    @NotNull ConfigProperties get(ConfigProperties.Domain domain) throws ConfigProperties.ConfigLoadingException;
}
