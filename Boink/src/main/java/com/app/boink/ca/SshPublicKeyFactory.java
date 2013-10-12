package com.app.boink.ca;

import org.jetbrains.annotations.NotNull;

public interface SshPublicKeyFactory {
    @NotNull
    SshPublicKey get(String user) throws SshPublicKey.SshPublicKeyLoadingException, ConfigProperties.ConfigLoadingException;
}
