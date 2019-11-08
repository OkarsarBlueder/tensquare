package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.EnterpriseDao;
import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EnterpriseService {

  @Autowired
  EnterpriseDao enterpriseDao;

  @Autowired
  IdWorker idWorker;

  public void add(Enterprise enterprise) {
    enterprise.setId(idWorker.nextId() + "");
    enterpriseDao.save(enterprise);
  }

  public void deleteById(String id) {
    enterpriseDao.deleteById(id);
  }

  public List<Enterprise> findAll() {
    return enterpriseDao.findAll();
  }

  public Enterprise findById(String id) {
    return enterpriseDao.findById(id).get();
  }

  public List<Enterprise> findSearch(Map wherMap) {
    Specification<Enterprise> specification = createSpecification(wherMap);
    return enterpriseDao.findAll(specification);
  }

  public Page<Enterprise> findSearch(Map whereMap, int page, int size) {
    Specification<Enterprise> specification = createSpecification(whereMap);
    PageRequest pageRequest = PageRequest.of(page - 1, size);
    return enterpriseDao.findAll(specification, pageRequest);
  }

  private Specification<Enterprise> createSpecification(Map searchMap) {
    return (root, criteriaQuery, cb) -> {
  List<Predicate> predicateList = new ArrayList<>();
  if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
  }
  // 企业名称
  if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
    predicateList.add(cb.like(root.get("name").as(String.class), "%" + (String) searchMap.get("name") + "%"));
  }
  // 企业简介
  if (searchMap.get("summary") != null && !"".equals(searchMap.get("summary"))) {
    predicateList.add(cb.like(root.get("summary").as(String.class), "%" + (String) searchMap.get("summary") + "%"));
  }
  // 企业地址
  if (searchMap.get("address") != null && !"".equals(searchMap.get("address"))) {
    predicateList.add(cb.like(root.get("address").as(String.class), "%" + (String) searchMap.get("address") + "%"));
  }
  // 标签列表
  if (searchMap.get("labels") != null && !"".equals(searchMap.get("labels"))) {
    predicateList.add(cb.like(root.get("labels").as(String.class), "%" + (String) searchMap.get("labels") + "%"));
  }
  // 坐标
  if (searchMap.get("coordinate") != null && !"".equals(searchMap.get("coordinate"))) {
    predicateList.add(cb.like(root.get("coordinate").as(String.class), "%" + (String) searchMap.get("coordinate") + "%"));
  }
  // 是否热门
  if (searchMap.get("ishot") != null && !"".equals(searchMap.get("ishot"))) {
    predicateList.add(cb.like(root.get("ishot").as(String.class), "%" + (String) searchMap.get("ishot") + "%"));
  }
  // LOGO
  if (searchMap.get("logo") != null && !"".equals(searchMap.get("logo"))) {
    predicateList.add(cb.like(root.get("logo").as(String.class), "%" + (String) searchMap.get("logo") + "%"));
  }
  // URL
  if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
    predicateList.add(cb.like(root.get("url").as(String.class), "%" + (String) searchMap.get("url") + "%"));
  }

  return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

};
  }

  public List<Enterprise> hostList(String ishot) {
    return enterpriseDao.findByIshot(ishot);
  }

  public void update(Enterprise enterprise) {
    enterpriseDao.save(enterprise);
  }


}
