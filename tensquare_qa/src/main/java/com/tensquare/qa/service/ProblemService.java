package com.tensquare.qa.service;

import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import utils.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProblemService {

  @Autowired(required = false)
  private ProblemDao problemDao;



  public List<Problem> findAll() {
    return problemDao.findAll();
  }

  public Page<Problem> findSearch(Map whereMap, int page, int size) {
    Specification<Problem> specification = createSpecification(whereMap);
    PageRequest pageRequest = PageRequest.of(size - 1, page);
    return problemDao.findAll(specification, pageRequest);
  }

  private Specification<Problem> createSpecification(Map searchMap) {

    return new Specification<Problem>() {

          @Override
          public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            Field fields[] = Problem.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
              fields[i].setAccessible(true);
              String name = fields[i].getName();
              System.out.println(name);
              if (searchMap.containsKey(name) && ObjectUtils.isEmpty(searchMap.get(name))) {
                predicateList.add(cb.like(root.get(name), "%" + searchMap.get(name) + "%"));
              }
            }
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
          }


        };
  }

  public Page<Problem> hotList(String labelId, int currentPage, int pageSize) {
    Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
    return problemDao.hotList(labelId, pageable);
  }

  public Page<Problem> newList(String labelId, int currentPage, int pageSize) {
    Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
    return problemDao.newList(labelId, pageable);
  }

  public Page<Problem> waitList(String labelId, int currentPage, int pageSize) {
    Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
    return problemDao.waitList(labelId, pageable);
  }


}
