package com.serveme.service.notification.external.dto.output;

import java.io.Serializable;

/**
 * Created by DavidChains on 8/1/15.
 */
public class GcmResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private int success;

    @Override
    public String toString() {
        return "GcmResponseDTO [success=" + success + "]";
    }

    public boolean isSuccessful() {
        return success > 0;
    }
}
