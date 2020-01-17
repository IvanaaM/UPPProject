package com.ftn.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity(name="UserCustom")
public class UserCustom {
	 
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		@Column(name="FirstName")
	    private String firstName;

		@Column(name="LastName")
	    private String LastName;
		
		@Column(name="City")
	    private String city;

		@Column(name="State")
	    private String state;
		
		@Column(name="Title")
	    private String title;
		
		@Column(name="Email")
	    private String email;
		
		@Column(name="Username")
	    private String username;
		
		@Column(name="Password")
	    private String password;
		
		@Column(name="Active")
	    private boolean active;
		
		@ManyToMany(fetch = FetchType.EAGER)
		private Set<ScientificArea> areas = new HashSet<ScientificArea>();
		
		@ManyToMany(fetch = FetchType.EAGER)
		@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
		private Set<Role> roles;

	    
		public UserCustom() {
			this.roles = new HashSet<Role>();
		}

	
		public UserCustom(String firstName, String lastName, String city, String state, String title, String email,
				String username, String password, boolean active, Set<Role> roles) {
			super();
			this.firstName = firstName;
			LastName = lastName;
			this.city = city;
			this.state = state;
			this.title = title;
			this.email = email;
			this.username = username;
			this.password = password;
			this.active = active;
			this.roles = roles;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getFirstName() {
			return firstName;
		}


		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}


		public String getLastName() {
			return LastName;
		}


		public void setLastName(String lastName) {
			LastName = lastName;
		}


		public String getCity() {
			return city;
		}


		public void setCity(String city) {
			this.city = city;
		}


		public String getState() {
			return state;
		}


		public void setState(String state) {
			this.state = state;
		}


		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public String getUsername() {
			return username;
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		
		public boolean isActive() {
			return active;
		}


		public void setActive(boolean active) {
			this.active = active;
		}


		public Set<Role> getRoles() {
			return roles;
		}


		public void setRoles(Set<Role> roles) {
			this.roles = roles;
		}


		public void addRole(Role role) {
			this.roles.add(role);
		}


		public Set<ScientificArea> getAreas() {
			return areas;
		}

		public void setAreas(Set<ScientificArea> areas) {
			this.areas = areas;
		}
		
		public void addArea(ScientificArea area) {
			this.areas.add(area);
		}
		
		public boolean chechIfAreaExists(String name) {

			for (ScientificArea sa : this.areas) {
				if(sa.getNaziv().equals(name)) {
					System.out.println("Ima");
					return true;
				}
			}
			
			return false;
		}
		
}
