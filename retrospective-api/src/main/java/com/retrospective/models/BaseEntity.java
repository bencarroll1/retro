package com.retrospective.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.OffsetDateTime;

//adding  data annotation which removes the need for getter/setter, toString etc boilerplate code
@Data
@MappedSuperclass
//superclass that all other models inherit traits from
public abstract class BaseEntity {
	
	@Id
	//each model id is a generated long value
	@GeneratedValue
	private Long id;
	
	private OffsetDateTime created;
	
	@PrePersist
	public void prePersist() {
		created = OffsetDateTime.now();
	}
	
}
