package com.earseo.sight.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubTheme {
    //자연
    NA01("자연관광지", "Natural Sites", Theme.NA),
    NA02("관광자원", "Natural Resources", Theme.NA),

    //문화
    CU01("휴양관광지", "Recreational Sites", Theme.CU),
    CU02("체험관광지", "Experience Programs", Theme.CU),
    CU03("산업관광지", "Industrial Sites", Theme.CU),
    CU04("문화시설", "Cultural Facilities", Theme.CU),

    //예술
    AR01("축제", "Festivals", Theme.AR),
    AR02("공연/행사", "Events/Performances", Theme.AR),

    //역사
    HI01("역사관광지", "Historical Sites", Theme.HI),
    HI02("건축/조형물", "Architectural Sights", Theme.HI),

    //레포츠
    LS01("레포츠소개", "Introduction", Theme.LS),
    LS02("육상 레포츠", "Leisure/Sports (Land)", Theme.LS),
    LS03("수상 레포츠", "Leisure/Sports (Water)", Theme.LS),
    LS04("항공 레포츠", "Leisure/Sports (Sky)", Theme.LS),
    LS05("복합 레포츠", "Leisure/Sports (Others)", Theme.LS),

    //쇼핑
    SH01("쇼핑", "Shopping", Theme.SH),
    ;

    private final String koName;
    private final String enName;
    private final Theme theme;

}
