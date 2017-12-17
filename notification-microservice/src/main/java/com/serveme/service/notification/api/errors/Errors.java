package com.serveme.service.notification.api.errors;

import com.serveme.service.notification.util.api.error.ApiError;

/**
 * Created by DavidChains on 8/12/15.
 */
public enum Errors {

    //PUSH NOTIFICATION ERRORS
    PUSH_CANNOT_BE_SENT(new ApiError(101,null,null,"Push Notification Errors: Push notification cannot be sent to device")),

    //GENERIC ERRORS
    INTERNAL_SERVER_ERROR(new ApiError(1,null,null,"Internal Server Error")),
    EMAIL_VALIDATION(new ApiError(3,null,null,"Email Validation error")),
    GT_VALIDATION(new ApiError(4,null,null, "Error Validation: Value grater than")),
    LT_VALIDATION(new ApiError(5,null,null,"Error Validation: Value smaller than")),
    MAX_LENGTH_VALIDATION(new ApiError(6,null,null,"Error Validation: Length larger than")),
    MIN_LENGTH_VALIDATION(new ApiError(7,null,null,"Error Validation: Length smaller than")),
    NULL_VALIDATION(new ApiError(8,null,null,"Error Validation: Null value")),
    AUTHENTICATION(new ApiError(9,null,null,"Authentication Errors: service failed to authenticate"))
    ;


    private ApiError value;

    Errors(ApiError value){
        this.value = value;
    }

    public ApiError getError(){
        ApiError copy = new ApiError();
        copy.setCode(value.getCode());
        copy.setField(value.getField());
        copy.setValue(value.getValue());
        copy.setMessage(value.getMessage());
        return copy;
    }
}
