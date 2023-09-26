package com.userService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document("IdManager")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IdManager {

	@Id
	private String id;
	private int index;
}
