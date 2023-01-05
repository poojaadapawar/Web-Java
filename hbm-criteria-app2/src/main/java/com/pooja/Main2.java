package com.pooja;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Subquery;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.pooja.model.Employee;

public class Main2 {
	private static SessionFactory sf;
	public static void main(String[] args) {
		Configuration cfg = new Configuration();
		cfg.configure();
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
		ssrb.applySettings(cfg.getProperties());
		StandardServiceRegistry ssr = ssrb.build();
		sf = cfg.buildSessionFactory(ssr);
		
//		selectAll();
//		selectOrder();
//		selectWhere();
//		selectWhereOr();
//		selectWhereBetween();
//		selectWhereLike();
//		selectOneProp();
//		selectMoreProp();
//		selectDistinct();
//		selectGroupFun();
//		selectGroupBy();
		selectSubquery();

		sf.close();
		System.out.println("done");
	}

	private static void selectAll() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Criteria q = s.createCriteria(Employee.class);
		List<Employee> lst = q.list();
		lst.forEach(ele->System.out.println(ele)); 
		t.commit();
		s.close();
	}
	private static void selectOrder() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Criteria q = s.createCriteria(Employee.class);
		q.addOrder(Order.asc("salary"));
		List<Employee> lst = q.list();
		lst.forEach(ele->System.out.println(ele)); 
		t.commit();
		s.close();
	}
	
	private static void selectWhere() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Criteria q = s.createCriteria(Employee.class);
		q.add(Restrictions.eq("dept", "cs")); 
		List<Employee> lst = q.list();
		lst.forEach(ele->System.out.println(ele)); 
		t.commit();
		s.close();
	}
	
	private static void selectWhereOr() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Criteria q = s.createCriteria(Employee.class);
		q.add(Restrictions.or(Restrictions.eq("dept", "cs"),Restrictions.eq("dept", "it"))); 
		List<Employee> lst = q.list();
		lst.forEach(ele->System.out.println(ele)); 
		t.commit();
		s.close();
	}
	
	private static void selectWhereBetween() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Criteria q = s.createCriteria(Employee.class);
		q.add(Restrictions.between("salary", 9000.00f, 99000.60f));
		List<Employee> lst = q.list();
		lst.forEach(ele->System.out.println(ele)); 
		t.commit();
		s.close();
	}
	private static void selectWhereLike() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Criteria q = s.createCriteria(Employee.class);
		q.add(Restrictions.like("name", "r%")); 
		List<Employee> lst = q.list();
		lst.forEach(ele->System.out.println(ele)); 
		t.commit();
		s.close();
	}
	private static void selectOneProp() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Criteria q = s.createCriteria(Employee.class);
		q.setProjection(Projections.property("name"));
		
		List<String> lst = q.list();
		lst.forEach(ele->System.out.println(ele)); 
		t.commit();
		s.close();
	}
	
	private static void selectMoreProp() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Criteria q = s.createCriteria(Employee.class);
		
		ProjectionList plist = Projections.projectionList();
		plist.add(Projections.property("name"));
		plist.add(Projections.property("salary"));
		q.setProjection(plist);
		
		List<Object[]> lst = q.list(); 
		lst.forEach(ele->System.out.println(ele[0]+" "+ele[1])); 
		t.commit();
		s.close();
	}
	
	private static void selectDistinct() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Criteria q = s.createCriteria(Employee.class);
		q.setProjection(Projections.distinct(Projections.property("dept"))); 
		
		List<String> lst = q.list();
		lst.forEach(ele->System.out.println(ele)); 
		t.commit();
		s.close();
	}
	
	private static void selectGroupFun() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Criteria q = s.createCriteria(Employee.class);
		q.setProjection(Projections.sum("salary")); 
		
		List<Double> lst = q.list();
		lst.forEach(ele->System.out.println(ele)); 
		t.commit();
		s.close();
	}
	
	private static void selectGroupBy() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Criteria q = s.createCriteria(Employee.class);
		ProjectionList plist = Projections.projectionList();
		plist.add(Projections.groupProperty("dept"));
		plist.add(Projections.sum("salary"));
		q.setProjection(plist);
		
		List<Object[]> lst = q.list();
		lst.forEach(ele->System.out.println(ele[0]+" "+ele[1])); 
		t.commit();
		s.close();
	}
	
	private static void selectSubquery() {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		
		DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
		dc.setProjection(Projections.property("dept"));
		dc.add(Restrictions.eq("name", "shiv"));
		
		Criteria q = s.createCriteria(Employee.class);
		q.add(Subqueries.propertyEq("dept", dc));
		
		List<Employee> lst = q.list();
		lst.forEach(ele->System.out.println(ele)); 
		t.commit();
		s.close();
	}
	
	
}
