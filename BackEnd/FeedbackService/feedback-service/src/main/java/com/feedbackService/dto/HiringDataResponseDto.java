package com.feedbackService.dto;

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
public class HiringDataResponseDto extends ResponseDto{

	private int totalApplications;
	private int pendingApplications;
	private int hiredApplications;
	private int notHiredApplications;
}
