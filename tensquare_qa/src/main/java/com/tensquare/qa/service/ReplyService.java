package com.tensquare.qa.service;

import com.tensquare.qa.dao.ReplyDao;
import com.tensquare.qa.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class ReplyService {


  @Autowired(required = false)
  private ReplyDao replyDao;

  @Autowired
  private IdWorker idWorker;

  public List<Reply> findAll() {
    return replyDao.findAll();
  }

  public Page<Reply> findSearch(Map whereMap,int page,int size){
    Specification<Reply> specification = createSpecification(whereMap);
    PageRequest pageRequest = PageRequest.of(page,size);
    return replyDao.findAll(specification,pageRequest);
  }

  public List<Reply> findSearch(Map whereMap){
    Specification specification = createSpecification(whereMap);
    return replyDao.findAll(specification);
  }

  public Reply findById(String id){
    return replyDao.findById(id).get();
  }

  public void add(Reply reply){
    reply.setId(idWorker.nextId()+"");
    replyDao.save(reply);
  }

  public void update(Reply reply){
    replyDao.save(reply);
  }

  public void deleteById(String id){
    replyDao.deleteById(id);
  }

  private Specification<Reply> createSpecification(Map searchMap) {
    return new Specification<Reply>() {

          @Override
          public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();
            Field fields[] = Reply.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
              String name = fields[i].getName();
              if (searchMap.containsKey(name) && !ObjectUtils.isEmpty(searchMap.get(name))) {
                predicates.add(criteriaBuilder.like(root.get(name), "%" + searchMap.get(name) + "%"));
              }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
          }


        };
  }


}
