package com.grochu.library.DAL;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data //automatic getters and setters
@Entity
@Table(name = "Borrows")
public class Borrow 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name = "copy")
	private Copy copy;
	@OneToOne
	private User user;
	private LocalDate since;
	private LocalDate until;

	@PrePersist
	private void borrow() {
		since = LocalDate.now();
	}
}
