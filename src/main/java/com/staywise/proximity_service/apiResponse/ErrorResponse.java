package com.staywise.proximity_service.apiResponse;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime localDateTimeStamp;
    private int status;
    private String error;
    private String message;
    private Map<String,String> fieldErrors;
}

