package com.achilles.wild.server.enums;

/**
 * ͨ��״̬
 */
public enum StatusEnum implements DescedEnum, NumbericEnum{


    NORMAL(1,"NORMAL"),

    DELETED(0,"DELETED");


    private Integer value;
    private String desc;

    StatusEnum(Integer value, String desc) {
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
