package com.retrospective.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

// adding  data annotation which removes the need for getter/setter, toString etc boilerplate code
@Data
@EqualsAndHashCode(callSuper = true)
// no args constructor removes the need to add a constructor with no arguments
// and similar for all args constructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
// mapping items to custom SQL table using JPA
@Table(name = "sentimentAnalysis")
public class SentimentAnalysis extends BaseEntity {
	//setting columns in sentiment analysis table
	@Column(name = "retroItems", nullable = false)
	private String retroItems;
	
	@Column(name = "score", nullable = false)
	private float score;
	
	//setting many to one relationship type
	//joining table to retro table by retroId column
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "retro_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Retro retro;
}
