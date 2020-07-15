package com.atguigu.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    private int[] values;

    public void initialize(ListValue constraintAnnotation) {
        this.values = constraintAnnotation.values();
    }

    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (values == null) {
            return true;
        }
        for (int i : values) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

}
