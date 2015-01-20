package com.homework.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.homework.jpa.Nurse;
import com.homework.jpa.Site;

@Repository
public class SiteImpl implements SiteDao,Serializable {

	 @PersistenceContext(unitName="HomeWork")
	 private EntityManager em;
	
	@Override
	public Site find(int id) {
		return em.find(Site.class, id);		
	}

	@Override
	public List<Site> findAll() {
		return em.createNamedQuery("Site.findAll", Site.class).getResultList();		
	}

	@Override
	public Site update(Site obj) {
		if(find(obj.getId())==null){
			em.persist(obj);
			return obj;
		}else{
			return em.merge(obj);
		}	
	}

	@Override
	public List<Nurse> findNurses(int id) {		
		
		Site s=em.createQuery("select s from Site s LEFT JOIN FETCH s.nurses where s.id=:ID", Site.class)
		.setParameter("ID", id).getSingleResult();
		return s.getNurses();		
	}

	@Override
	public void delete(Site obj) {		
		em.remove(find(obj.getId()));		
	}
	

}
