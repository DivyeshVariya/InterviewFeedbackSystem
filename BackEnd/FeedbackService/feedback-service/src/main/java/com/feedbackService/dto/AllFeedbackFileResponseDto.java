package com.feedbackService.dto;

import java.util.List;

import com.feedbackService.model.FileManager;

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
public class AllFeedbackFileResponseDto extends ResponseDto{

	private List<FileManager> listOfFiles;
}
