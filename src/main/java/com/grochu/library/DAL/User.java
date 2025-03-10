package com.grochu.library.DAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Data;

@Data //automatic getters and setters
@Entity
@Table(name="Users")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String phoneNumber;
	private String addressStreet;
	private String addressCity;
	private String addressState;
	private String addressZip;
	
//	@ManyToMany(targetEntity=Book.class)
//	@JoinTable(name="History")
//	private List<Book> books;

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		switch (role.toLowerCase())
//		{
//			case "admin":
//				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//			case "user":
//				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//				break;
//		}
//		return authorities;
//	}
//
//	@Override
//	public String getUsername() {
//		return email;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
}
