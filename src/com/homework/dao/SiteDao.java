package com.homework.dao;

import java.util.List;

import com.homework.jpa.Nurse;
import com.homework.jpa.Site;

public interface SiteDao {
	public Site find(int id);
	public List<Site> findAll();
	public Site update(Site obj);
	public List<Nurse> findNurses(int id);
	public void delete(Site obj);
}
