package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.model.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category,Long> {


   

    Category  findByCategoryName( String categoryName);

  // @Query( value = "select * from Categories where id in (Select category_id from books  )",nativeQuery = true)
  //   List<Category> findAllCategories();

  //Page findByIdOrCategoryName(Long id, String name, Pageable pageable);


  //  @Query(value = "SELECT * FROM dbo.Categories WHERE id LIKE CONCAT('%', :idName, '%') OR category_name LIKE CONCAT('%', :idName, '%')", nativeQuery = true)
  //  Page<Category> findByIdOrCategoryName(@Param("idName") String idName, Pageable pageable);

  @Query("SELECT c FROM Category c WHERE c.id LIKE CONCAT('%', :idName, '%') OR c.categoryName LIKE CONCAT('%', :idName, '%')")
  Page<Category> findByIdOrCategoryName(@Param("idName") String idName, Pageable pageable);


}
