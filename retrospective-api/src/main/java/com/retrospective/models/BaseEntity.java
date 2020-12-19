package com.retrospective.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.OffsetDateTime;

@Data
@MappedSuperclass
public abstract class BaseEntity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private OffsetDateTime created;
	
	@PrePersist
	public void prePersist() {
		created = OffsetDateTime.now();
	}
	
}
