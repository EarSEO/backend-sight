package com.earseo.sight.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Theme {
    NA("자연", "Nature"),
    CU("문화", "Culture"),
    AR("예술", "Art"),
    HI("역사", "History"),
    LS("레포츠", "Leisure/Sports"),
    SH("쇼핑", "Shopping"),
    ;

    private final String koName;
    private final String enName;
}
