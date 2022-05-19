package com.nguyenkien.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenkien.mms.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
