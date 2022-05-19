package com.nguyenkien.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenkien.mms.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
