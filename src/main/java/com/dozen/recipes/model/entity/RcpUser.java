package com.dozen.recipes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rcp_user")
@Data
public class RcpUser extends AbstractEntity{

	@Column
	private String username;
	@Column
	@JsonIgnore
	private String password;

}