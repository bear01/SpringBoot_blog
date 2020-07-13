package com.lrm.dao;

import com.lrm.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by bear on 2020/3/20.
 * 在整个应用程序中使用数据访问对象(DAO)使我们可以将底层数据访问逻辑与业务逻辑分离开来。
 * 构建为每一个数据源提供 CRUD (创建、读取、更新、删除)操作的 DAO 类。
 */
    //  Dao层创建与JavaBean对应的接口，继承JpaRepository<K，E>接口
    //  Blog对应的是JavaBean实体类，Long是实体类主键的类型
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);


    @Transactional  // 事务
    @Modifying  // 这个注解是通知jpa，这是一个update或者delete操作，在更新或者删除操作时，此注解必须加，JPQL(JPA的查询语句)不支持insert
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
