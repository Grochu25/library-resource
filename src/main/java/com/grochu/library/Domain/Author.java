package com.grochu.library.Domain;

import jakarta.persistence.*;
import lombok.Data;

@Data //automatic getters and setters
@Entity
@Table(name = "Authors")
public class Author {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer birthYear;
	private String description;
}
