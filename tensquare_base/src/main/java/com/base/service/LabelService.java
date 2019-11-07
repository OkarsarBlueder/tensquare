package com.base.service;

import com.base.dao.LabelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.base.pojo.Label;
import org.springframework.util.StringUtils;
import utils.IdWorker;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;


@Service
@Transactional
public class LabelService {

  @Autowired
  LabelDao labelDao;

  @Autowired
  private IdWorker idWorker;

  public List<Label> findAll(){
    return labelDao.findAll();
  }

  public Label findById(String Id){
    return labelDao.findById(Id).get();
  }

  public void save(Label label){
    label.setId(idWorker.nextId()+"");
    labelDao.save(label);
  }

//  public List<Label> findSearch(Label label) {
//    labelDao.findBySearch(label);
//  }

  public void update(Label label){
    labelDao.save(label);
  }

  public void deleteById(String Id){
    labelDao.deleteById(Id);
  }


  private Specification<Label> searchMap(Label label){
    return (Specification<Label>)(root,query,cb)->{
      List<Predicate> list = new LinkedList<>();
      if(!StringUtils.isEmpty(label.getLabelname())){
        Predicate labelname = cb.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");
        list.add(labelname);
      }
      if(!StringUtils.isEmpty(label.getState())){
        Predicate state = cb.like(root.get("state").as(String.class),label.getState());
        list.add(state);
      }
      Predicate[] array = new Predicate[list.size()];
      return cb.and(array);
    };
  }

  public List<Label> findSearch(final Label label){
    return labelDao.findAll(searchMap(label));
  }

  public Page<Label> pageQuery(Label label,int currentPage,int pageSize){
    return labelDao.findAll(searchMap(label), PageRequest.of(currentPage-1,pageSize));
  }


}
