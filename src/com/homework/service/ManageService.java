package com.homework.service;

import java.util.List;

import com.homework.jpa.Nurse;
import com.homework.jpa.Site;

public interface ManageService {
	public List<Site> findAllSites();
	public List<Nurse> findAllNurse();
	public Site findSite(int id);
	public Nurse findNurse(int id);
	public void saveSite(Site obj);
	public void saveNurse(Nurse obj);
	public void delSite(Site obj);
	public void delNurse(Nurse obj);
	public List<Nurse> findNurseBySite(Site site);
	public List<Site> findSiteByNurse(Nurse nurse);	
	public List<Nurse> autoFindNurse(String str);	
	public Nurse findFullNurse(int id);
}
