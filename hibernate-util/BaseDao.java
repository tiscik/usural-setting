package com.joiniot.lock.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

import com.joiniot.lock.util.Pager;
import com.joiniot.lock.util.SearchParam;


public class BaseDao<T,PK extends Serializable> {
	
	@Inject
	private SessionFactory sessionFactory;
	
	private Class<T> pojoClass;
	
	public BaseDao(){
		Class<?> clazz = this.getClass();
		ParameterizedType parameterizedType = (ParameterizedType) 
				clazz.getGenericSuperclass();
		Type[] types = parameterizedType.getActualTypeArguments();
		pojoClass = (Class<T>) types[0];
		
	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public void save(T pojo){
		getSession().saveOrUpdate(pojo);
	}
	
	public T findById(PK pk){
		return (T) getSession().get(pojoClass, pk);
	}
	
	public void delete(PK pk){
		getSession().delete(pk);
	}
	
	public void delete(T t){
		getSession().delete(t);
	}
	
	public List<T> findAll(String orderProperty,String orderType){
		Criteria criteria = getSession().createCriteria(pojoClass);
		if (orderType.equalsIgnoreCase("desc")) {
			criteria.addOrder(Order.desc(orderProperty));
		}else if (orderType.equalsIgnoreCase("asc")) {
			criteria.addOrder(Order.asc(orderProperty));
		}
		
		return criteria.list();
	}
	public T findByHQL(String hql,Object... params) {
        Query query = getSession().createQuery(hql);
        for (int i = 0;i < params.length;i++) {
            query.setParameter(i,params[i]);
        }
        return (T)query.uniqueResult();
    }

    public List<T> findListByHQL(String hql,Object... params) {
        Query query = getSession().createQuery(hql);
        for (int i = 0;i < params.length;i++) {
            query.setParameter(i,params[i]);
        }
        return query.list();
    }
	
	public Pager<T> findAll(Integer pageNO,List<SearchParam> searchParams){
		Criteria criteria = getSession().createCriteria(pojoClass);
		for (SearchParam searchParam : searchParams) {
			String propertyName = searchParam.getPropertyName();
			if (propertyName.contains("_")) {
				String[] arrayStrings = propertyName.split("_");
				
				Disjunction disjunction = Restrictions.disjunction();
				for (String name : arrayStrings) {
					Criterion criterion = builderCondition(new SearchParam(searchParam.getType(),name,searchParam.getPropertyValue()));
					disjunction.add(criterion);
				}
				criteria.add(disjunction);
			}else {
				criteria.add(builderCondition(searchParam));
			}
		}
		
		Long count = count(criteria);
		Pager<T> pager = new Pager<T>(pageNO,count.intValue(),5);
		criteria.setFirstResult(pager.getStart());
		criteria.setMaxResults(pager.getPageSize());
		pager.setItems(criteria.list());
		
		return pager;
	}
	
	private Long count(Criteria criteria) {
        //获取总记录数  select count(*) from xxx where .....
        ResultTransformer resultTransformer = criteria.ROOT_ENTITY;// xx,xxx,xxx

        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();

        criteria.setProjection(null);
        criteria.setResultTransformer(resultTransformer);

        return  count;
    }
	
	private Criterion builderCondition(SearchParam searchParam) {
        if (searchParam.getType().equalsIgnoreCase("like")) {
            return Restrictions.like(searchParam.getPropertyName(), searchParam.getPropertyValue().toString(), MatchMode.ANYWHERE);
        } else if(searchParam.getType().equalsIgnoreCase("eq")) {
            return Restrictions.eq(searchParam.getPropertyName(), searchParam.getPropertyValue());
        } else if(searchParam.getType().equalsIgnoreCase("gt")) {
            return Restrictions.gt(searchParam.getPropertyName(), searchParam.getPropertyValue());
        } else if(searchParam.getType().equalsIgnoreCase("lt")) {
            return  Restrictions.lt(searchParam.getPropertyName(), searchParam.getPropertyValue());
        } else if(searchParam.getType().equalsIgnoreCase("ge")) {
            return Restrictions.ge(searchParam.getPropertyName(), searchParam.getPropertyValue());
        } else if(searchParam.getType().equalsIgnoreCase("le")) {
            return Restrictions.le(searchParam.getPropertyName(), searchParam.getPropertyValue());
        }
        return null;
    }
}
