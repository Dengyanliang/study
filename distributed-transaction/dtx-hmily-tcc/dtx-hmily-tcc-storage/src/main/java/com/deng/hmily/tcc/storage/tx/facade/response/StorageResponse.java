package com.deng.hmily.tcc.storage.tx.facade.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class StorageResponse implements Serializable {
    private int code;
    private String message;
}
