package com.sam.iptiq.exception;

import com.sam.iptiq.logic.ProviderPool;

public class ProviderCapacityException extends RuntimeException {

    private static final long serialVersionUID = -1041264753815342911L;

    public ProviderCapacityException(String provider) {
        super(String.format("Capacity of %s is reached, cannot add %s",
                ProviderPool.capacity, provider));
    }

}
