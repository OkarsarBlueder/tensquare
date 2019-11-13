package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface ProblemDao extends JpaRepository<Problem,String>, JpaSpecificationExecutor<Problem> {
  @Query(value = "SELECT * FROM tb_problem,tb_pl WHERE id = problemid AND labelid = ? ORDER BY replytime DESC", nativeQuery = true)
  Page<Problem> newList(String labelId, Pageable pageable);

  @Query(value = "select * from tb_problem,tb_pl where id = problemid and lableid = ? order by reply desc", nativeQuery = true)
  Page<Problem> hotList(String labelId, Pageable pageable);

  @Query(value = "select * from tb_problem,tb_pl where id = problemid and labelid = ? and reply = 0 order by createtime desc",nativeQuery = true)
  Page<Problem> waitList(String labelId, Pageable pageable);

}
