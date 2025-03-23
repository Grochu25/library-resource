package com.grochu.library.Domain;

import jakarta.persistence.*;
import lombok.Data;


@Data //automatic getters and setters
@Entity
@Table(name="Books")
public class Book 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;
	private int publishYear;
}
