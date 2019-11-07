package service;

import dao.LabelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Label;
import utils.IdWorker;

import javax.transaction.Transactional;
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

  public void add(Label label){
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


}
