package com.achilles.wild.server.model.response.code;

import java.io.Serializable;

public enum BaseResultCode implements Serializable {

    SUCCESS("1", "success"),
    FAIL("0", "fail"),

    EXCEPTION("-100", "server exception, msg:%s"),
    ILLEGAL_PARAM("-101", "illegal parameter: %s"),
    ERROR("-102", "unsuccessful call, msg:%s"),
    MISSING_PARAMETER("-103", "missing parameter: %s"),
    SERVICE_UNAVAILABLE("-105", "service unavailable: %s"),
    DATA_NOT_EXISTS("-106", "data not exists: %s"),
    DATA_HAS_EXISTS("-107", "data has exists: %s"),

    NOT_LOGIN("-300", "NOT LOGIN ~"),


    EXCEPTION_TO_CLIENT("-1000", "the server is too busy ~ : %s"),
    ERROR_TO_CLIENT("-1001", "the server is asleep ~ : %s"),
    TOO_MANY_REQUESTS("-2001", "too many requests ~ : %s"),
    REQUESTS_TOO_FREQUENT("-2002", "requests are too frequent ~ : %s"),
    TRACE_ID_NECESSARY("-2003", "traceId is necessary"),
    TRACE_ID_LENGTH_ILLEGAL("-2004", "traceId length is 20-64"),
    TRACE_ID_CONTENT_ILLEGAL("-2005", "traceId must contain letter and number both ~"),
    TRACE_ID_CONTENT_EXPIRED("-2006", "traceId expired "),
    TRACE_ID_CONTENT_EXCEED_CURRENT("-2006", "traceId exceed current time "),
    TRACE_ID_PREFIX_ILLEGAL("-2007", "traceId prefix format illegal ");


    public final String code;
    public final String message;

    BaseResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static BaseResultCode codeOf(String code, BaseResultCode r) {
        for (BaseResultCode rc : BaseResultCode.values()) {
            if (rc.code.equals(code)) {
                return rc;
            }
        }
        return r;
    }
}
