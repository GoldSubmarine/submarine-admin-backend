package org.javahub.submarine.common.exception;

import lombok.Data;

/**
 * Created by suikelei on 10/12/19.
 */
@Data
public class ErrorResponse {
    private String message;
    private String extCode;
}
