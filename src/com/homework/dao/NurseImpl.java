package com.homework.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.homework.jpa.Nurse;
import com.homework.jpa.Site;

@Repository
public class NurseImpl implements NurseDao,Serializable {

	 @PersistenceContext
	 private EntityManager em;
	
	@Override
	public Nurse find(int id) {
		return em.find(Nurse.class,id);
	}

	@Override
	public Nurse update(Nurse obj) {		
		if(find(obj.getId())==null){
			List<Site> sites=obj.getSites();
			obj.setSites(new ArrayList<Site>());
 			em.persist(obj);
 			for(Site s:sites){
 				Site tmp=em.find(Site.class, s.getId());
 				tmp.getNurses().add(obj);
 				obj.getSites().add(tmp);
 				em.merge(tmp);
 			}
			return obj;
		}else{
			List<Site> originalSite=find(obj.getId()).getSites();
			List<Site> checkSite= obj.getSites();
			for(Site s:checkSite){ //add
				if(!originalSite.contains(s)){
					Site newSite=em.find(Site.class,s.getId());
					newSite.getNurses().add(obj);
					em.merge(newSite);
				}
			}
			for(Site s:originalSite){ //remove
				if(!checkSite.contains(s)){
					s.getNurses().remove(obj);
					em.merge(s);
				}
			}
			return em.merge(obj);
		}		
	}

	@Override
	public List<Site> findSites(int id) {
		Nurse n=em.createQuery("select n from Nurse n LEFT JOIN FETCH n.sites where n.id=:ID", Nurse.class)
				.setParameter("ID", id)
				.getSingleResult();
		return n.getSites();			
	}

	@Override
	public void delete(Nurse obj) {	
		Nurse tmpNurse=find(obj.getId());
		List<Site> tmpSite=tmpNurse.getSites();
		for(Site s :tmpSite){
			s.getNurses().remove(tmpNurse);
			em.merge(s);
		}
		em.remove(tmpNurse);
	}

	@Override
	public List<Nurse> findAll() {
		return em.createNamedQuery("Nurse.findAll", Nurse.class).getResultList();
	}

	@Override
	public List<Nurse> findByName(String name, boolean mix) {
		if(mix){
			return em.createQuery("select n from Nurse n where n.name like :NAME", Nurse.class).setParameter("NAME", name+'%').getResultList();
		}else{
			return em.createQuery("select n from Nurse n where n.name=:NAME", Nurse.class).setParameter("NAME", name).getResultList();			
		}			
	}

	@Override
	public List<Nurse> findByNumber(String number, boolean mix) {
		if(mix){
			return em.createQuery("select n from Nurse n where n.number like :NUMBER", Nurse.class).setParameter("NUMBER", number+'%').getResultList();		
		}else{
			return em.createQuery("select n from Nurse n where n.number=:NUMBER", Nurse.class).setParameter("NUMBER", number).getResultList();
		}
	}

}
