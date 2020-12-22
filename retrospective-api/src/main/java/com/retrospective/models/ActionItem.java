package com.retrospective.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;

//adding  data annotation which removes the need for getter/setter, toString etc boilerplate code
@Data
@EqualsAndHashCode(callSuper = true)
//no args constructor removes the need to add a constructor with no arguments
//and similar for all args constructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
//mapping actionItems to custom SQL table using JPA
@Table(name = "actionItems")
//class inherits characteristics from BaseEntity
public class ActionItem extends BaseEntity {
	//setting columns in actionItems table and constraints for entries
	@Column(name = "description", nullable = false)
	@Size(min = 1, max = 300, message = "Action item max length is 300 characters")
	private String description;
	
	//setting many to one relationship type
	//joining table to retro table by retroId column
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "retro_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Retro retro;
	
	
}

