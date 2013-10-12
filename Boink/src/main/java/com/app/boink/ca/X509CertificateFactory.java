package com.app.boink.ca;

import com.app.boink.exception.XCISException;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.security.cert.X509Certificate;
import java.util.HashMap;

public interface X509CertificateFactory {
    @NotNull
    X509Certificate get(@NonNull HashMap<String, String> infos) throws XCISException;
}
