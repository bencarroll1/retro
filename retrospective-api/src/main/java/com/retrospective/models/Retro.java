package com.retrospective.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//adding  data annotation which removes the need for getter/setter, toString etc boilerplate code
@Data
@EqualsAndHashCode(callSuper = true)
//no args constructor removes the need to add a constructor with no arguments
//and similar for all args constructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
//mapping retros to custom SQL table using JPA
@Table(name = "retros")
//class inherits characteristics from BaseEntity
public class Retro extends BaseEntity {
	//setting columns in actionItems table
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "archive_retro_flag", nullable = false)
	private boolean archiveRetroFlag;
}


