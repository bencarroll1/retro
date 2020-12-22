package com.retrospective.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

//adding  data annotation which removes the need for getter/setter, toString etc boilerplate code
@Data
@EqualsAndHashCode(callSuper = true)
//no args constructor removes the need to add a constructor with no arguments
//and similar for all args constructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
//mapping items to custom SQL table using JPA
@Table(name = "items")
//class inherits characteristics from BaseEntity
public class Item extends BaseEntity {
	
	//setting columns in items table
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "type", nullable = false)
	private ItemType type;
	
	@Column(name = "item_flag", nullable = false)
	private boolean itemFlag;
	
	@Column(name = "item_votes", nullable = false)
	private int itemVotes;
	
	//setting many to one relationship type
	//joining table to retro table by retroId column
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "retro_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Retro retro;
	
	
}

