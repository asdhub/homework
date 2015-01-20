package com.homework.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homework.dao.NurseDao;
import com.homework.dao.SiteDao;
import com.homework.jpa.Nurse;
import com.homework.jpa.Site;

@Service
@Transactional
public class ManageImpl implements ManageService,Serializable {

	@Autowired
	private NurseDao nurseDao;
	@Autowired
	private SiteDao siteDao;
	
	@Override
	public List<Site> findAllSites() {
		return siteDao.findAll();		
	}

	public List<Nurse> findAllNurse(){
		
		return nurseDao.findAll();
	}
	
	@Override
	public Site findSite(int id) {		
		return siteDao.find(id);
	}

	@Override
	public Nurse findNurse(int id) {
		
		return nurseDao.find(id);
	}

	@Override
	public void saveSite(Site obj) {
		siteDao.update(obj);		
	}

	@Override
	public void saveNurse(Nurse obj) {		
		nurseDao.update(obj);
	}

	@Override
	public void delSite(Site obj) {
		siteDao.delete(obj);
	}

	@Override
	public void delNurse(Nurse obj) {		
		nurseDao.delete(obj);
	}

	@Override
	public List<Nurse> findNurseBySite(Site site) {		
		return siteDao.findNurses(site.getId());
	}

	@Override
	public List<Site> findSiteByNurse(Nurse nurse) {
		return nurseDao.findSites(nurse.getId());
	}

	@Override
	public List<Nurse> autoFindNurse(String str) {
		List<Nurse> returnValue=new ArrayList<Nurse>();
		returnValue.addAll(nurseDao.findByName(str, true));
		List<Nurse> chkTmp=nurseDao.findByNumber(str, true);
		for(Nurse n:chkTmp){
			if(!returnValue.contains(n)){
				returnValue.add(n);
			}
		}		
		return returnValue;
	}

	@Override
	public Nurse findFullNurse(int id) {
		Nurse n=nurseDao.find(id);
		n.getSites();
		return n;
	}

}
