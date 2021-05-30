package com.achilles.wild.server.enums.account;

import com.achilles.wild.server.enums.DescedEnum;
import com.achilles.wild.server.enums.NumbericEnum;

public enum ExceptionTypeEnum implements DescedEnum, NumbericEnum {

    OTHER_EXCEPTION(0,"other exception"),

    BIZ_EXCEPTION(1,"biz exception");


    private Integer value;
    private String desc;

    ExceptionTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public int toNumbericValue() {
        return value;
    }

    @Override
    public String desc() {
        return desc;
    }
}
