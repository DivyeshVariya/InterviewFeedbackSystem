package com.feedbackService.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportDateRangeDto {

	@DateTimeFormat(pattern = "YYYY-MM-DD",iso = ISO.DATE)
	@PastOrPresent(message = "Date must be past or present...")
	private LocalDate to;
	
	@DateTimeFormat(pattern = "YYYY-MM-DD",iso = ISO.DATE)
	@PastOrPresent(message = "Date must be past or present...")
	private LocalDate from;
}
