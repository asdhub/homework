package com.homework.dao;

import java.util.List;

import com.homework.jpa.Nurse;
import com.homework.jpa.Site;

public interface NurseDao {
	public Nurse find(int id);
	public Nurse update(Nurse obj);
	public List<Site> findSites(int id);
	public void delete(Nurse obj);
	public List<Nurse> findAll();
	
	public List<Nurse> findByName(String name ,boolean mix);
	public List<Nurse> findByNumber(String number ,boolean mix);
}
