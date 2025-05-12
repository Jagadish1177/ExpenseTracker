package com.example.expense.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(
        description = "ErrorDetails DTO(Data Transfer Object) to transfer the error response data between client and server"

)
@Getter
@Setter
public class ErrorDetails {
    @Schema(description = "Error occurrd date time")
    private LocalDateTime timestamp;
    @Schema(description = "Error message")
    private String message;
    @Schema(description = "Error details")
    private String details;
    @Schema(description = "Error code")
    private String errorCode;
}
