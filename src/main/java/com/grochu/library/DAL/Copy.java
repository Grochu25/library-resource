package com.grochu.library.DAL;

import jakarta.persistence.*;
import lombok.Data;


@Data //automatic getters and setters
@Entity
@Table(name = "Copies")
public class Copy 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="book")
	private Book book;

	public Copy(Long id, Book book)
	{
		this.id = id;
		this.book = book;
	}

	public Copy()
	{
		this.id = null;
		this.book = null;
	}
}
