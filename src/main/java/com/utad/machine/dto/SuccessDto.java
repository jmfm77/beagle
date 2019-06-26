package com.utad.machine.dto;

public class SuccessDto {

    private boolean success;

    public SuccessDto(
            boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(
            boolean success) {
        this.success = success;
    }

}
