package com.retrospective.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "retros")
public class Retro extends BaseEntity {
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "archive_retro_flag", nullable = false)
	private boolean archiveRetroFlag;
}


