package com.base.utils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateSpecification<T> implements Specification<T>{
  private Map searchMap;
  private Class clazz;
  CreateSpecification(Map searchMap,Class clazz){
    this.searchMap = searchMap;
    this.clazz = clazz;
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
    Field fields[] = clazz.getDeclaredFields();
    List <Predicate> predicates = new ArrayList<>();
    for (int i =0;i<fields.length;i++){
      String name = fields[i].getName();
      if(searchMap.containsKey(name) && !ObjectUtils.isEmpty(searchMap.get(name))){
        predicates.add(criteriaBuilder.like(root.get(name),"%"+searchMap.get(name)+"%"));
      }
    }
    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
  }



}
