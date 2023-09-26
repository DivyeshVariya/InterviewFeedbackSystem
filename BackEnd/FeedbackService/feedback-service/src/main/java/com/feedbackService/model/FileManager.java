package com.feedbackService.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@ToString
@Document("FileManager")
public class FileManager extends BaseEntity{

	@Id
	private String fileId;
	private List<String> formsFromFile;
	private String interviewerName;
	private String fileName;
}
