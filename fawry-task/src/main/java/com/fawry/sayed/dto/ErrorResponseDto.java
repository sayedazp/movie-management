package com.fawry.sayed.dto;

import java.time.LocalDateTime;

public class ErrorResponseDto {

    private int status;
    private String message;
    private String error;
    private LocalDateTime timestamp;
    
    
    
	private ErrorResponseDto(ErrorBuilder errorBuilder) {
		this.status = errorBuilder.status;
		this.message = errorBuilder.message;
		this.error = errorBuilder.error;
		this.timestamp = errorBuilder.timestamp;
	}
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public String getError() {
		return error;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public static class ErrorBuilder{
		private int status;
	    private String message;
	    private String error;
	    private LocalDateTime timestamp;
	    
	    public ErrorBuilder(int status, String message) {
            this.status = status;
            this.message = message;
        }

		public ErrorBuilder setStatus(int status) {
			this.status = status;
			return this;
		}

		public ErrorBuilder setMessage(String message) {
			this.message = message;
			return this;
		}

		public ErrorBuilder setError(String error) {
			this.error = error;
			return this;
		}

		public ErrorBuilder setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
			return this;
		}
		public ErrorResponseDto build() {
			return new ErrorResponseDto(this);
		}
	    		
	}

	
    
}