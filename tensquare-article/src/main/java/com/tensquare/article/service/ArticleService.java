package com.tensquare.article.service;

import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import utils.IdWorker;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {

  @Autowired
  ArticleDao articleDao;

//  @Autowired


  @Autowired
  private IdWorker idWorker;
  @Resource
  private RedisTemplate redisTemplate;

  public void updateState(String id) {
    articleDao.updateState(id);
  }


  public void addThumbup(String id) {
    articleDao.addThumbup(id);
  }


  public Article findById(String id) {
    // 先从缓存中查询当前对象
    Article article = (Article) redisTemplate.opsForValue().get("article_" + id);
    // 如果没有取到
    if (null == article) {
      // 从数据库中查询
      article = articleDao.findById(id).orElse(null);
      // 存入缓存
      redisTemplate.opsForValue().set("article_" + id, article);
    }
    return article;
  }


  /**
   * 查询全部列表
   * @return
   */
  public List<Article> findAll() {
    return articleDao.findAll();
  }


  /**
   * 条件查询+分页
   * @param whereMap
   * @param page
   * @param size
   * @return
   */
  public Page<Article> findSearch(Map whereMap, int page, int size) {
    Specification<Article> specification = createSpecification(whereMap);
    PageRequest pageRequest =  PageRequest.of(page-1, size);
    return articleDao.findAll(specification, pageRequest);
  }


  /**
   * 条件查询
   * @param whereMap
   * @return
   */
  public List<Article> findSearch(Map whereMap) {
    Specification<Article> specification = createSpecification(whereMap);
    return articleDao.findAll(specification);
  }


  /**
   * 增加
   * @param article
   */
  public void add(Article article) {
    article.setId( idWorker.nextId()+"" );
    articleDao.save(article);
  }


  /**
   * 修改
   * @param article
   */
  public void update(Article article) {
    articleDao.save(article);
  }


  /**
   * 删除
   * @param id
   */
  public void deleteById(String id) {
    articleDao.deleteById(id);
  }


  /**
   * 动态条件构建
   * @param searchMap
   * @return
   */
  private Specification<Article> createSpecification(Map searchMap) {

    return new

        Specification<Article>() {

      @Override
      public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicateList = new ArrayList<Predicate>();
        // ID
        Field fields[] = Article.class.getDeclaredFields();
        for (int i=0;i<fields.length;i++){
          String name = fields[i].getName();
          if(searchMap.containsKey(name)&& !ObjectUtils.isEmpty(searchMap.get(name))){
            System.out.println(name);
            predicateList.add(cb.like(root.get(name).as(String.class), "%"+searchMap.get(name)+"%"));
          }
        }

        return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

      }


        };

  }


}
