package org.sheinbergon.aac.jna.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FdkAACVersion {
    V_1_6("3.3.22"),
    V_2_0("4.0.0");
    private final String aacEncoderModuleVersion;
}