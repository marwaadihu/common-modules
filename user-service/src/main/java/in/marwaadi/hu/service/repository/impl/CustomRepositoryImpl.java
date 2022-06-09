package in.marwaadi.hu.service.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import in.marwaadi.hu.service.dto.WhereClause;
import in.marwaadi.hu.service.enums.QueryClauseEnum;

/**
 * Custom Repository Class where generic methods are exit
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 * @param <T>
 */
public abstract class CustomRepositoryImpl<T> {

	/**
	 * Entity Class
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	protected Class<T> typeParameterClass;

	/**
	 * EntityManager object
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	protected EntityManager entityManager;

	/**
	 * Default constructor
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	protected CustomRepositoryImpl() {
		super();
	}

	/**
	 * Set EntityMangeer Bean
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param entityManager the entityManager to set
	 */
	protected abstract void setEntityManager(EntityManager entityManager);

	/**
	 * Set Entity class
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param typeParameterClass
	 */
	protected abstract void setTypeParameerClass(Class<T> typeParameterClass);

	/**
	 * Filter Data
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 * @param whereClauseMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<T> getFilterdData(Map<String, WhereClause> whereClauseMap) {
		entityManager.clear();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> createQuery = criteriaBuilder.createQuery(typeParameterClass);
		Root<T> from = createQuery.from(typeParameterClass);

		List<Predicate> predicates = new ArrayList<>();
		for (Entry<String, WhereClause> whereClause : whereClauseMap.entrySet()) {
			QueryClauseEnum queryClause = whereClause.getValue().getQueryClause();
			switch (queryClause) {
			case EQUAL:
				predicates
						.add(criteriaBuilder.equal(from.get(whereClause.getKey()), whereClause.getValue().getValue()));
				break;
			case NOT_EQUAL:
				predicates.add(
						criteriaBuilder.notEqual(from.get(whereClause.getKey()), whereClause.getValue().getValue()));
				break;
			case LESS_THEN:
				predicates.add(criteriaBuilder.lessThan(from.get(whereClause.getKey()),
						Long.parseLong(whereClause.getValue().getValue().toString())));
				break;
			case LESS_THEN_OR_EQUAL_TO:
				predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get(whereClause.getKey()),
						Long.parseLong(whereClause.getValue().getValue().toString())));
				break;
			case GREATER_THEN:
				predicates.add(criteriaBuilder.greaterThan(from.get(whereClause.getKey()),
						Long.parseLong(whereClause.getValue().getValue().toString())));
				break;
			case GREATER_THEN_OR_EQUAL_TO:
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get(whereClause.getKey()),
						Long.parseLong(whereClause.getValue().getValue().toString())));
				break;
			case IN:
				criteriaBuilder.in(from.get(whereClause.getKey())
						.in(((Collection<Object>) whereClause.getValue().getValue()).toArray()));
				break;
			case NOT_IN:
				criteriaBuilder.not(from.get(whereClause.getKey())
						.in(((Collection<Object>) whereClause.getValue().getValue()).toArray()));
				break;
			default:
				break;
			}
		}
		if (!predicates.isEmpty()) {
			createQuery.where(predicates.stream().toArray(Predicate[]::new));
		}
		TypedQuery<T> typedQuery = entityManager.createQuery(createQuery.select(from));
		return typedQuery.getResultList();
	}

}
