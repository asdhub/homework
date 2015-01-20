package com.homework.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;



/**
 * The persistent class for the site database table.
 * 
 */
@Entity
@Table(name="site")
@NamedQuery(name="Site.findAll", query="SELECT s FROM Site s")
public class Site implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;

	//bi-directional many-to-many association to Nurse
	@ManyToMany
	@JoinTable(
		name="site_nurse"
		, joinColumns={
			@JoinColumn(name="siteId", referencedColumnName="id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="nurseId", referencedColumnName="id")
			}
		)
	private List<Nurse> nurses;

	public Site() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Nurse> getNurses() {
		return this.nurses;
	}

	public void setNurses(List<Nurse> nurses) {
		this.nurses = nurses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Site other = (Site) obj;
		if (id != other.id)
			return false;
		return true;
	}

}