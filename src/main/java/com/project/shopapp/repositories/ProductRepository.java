package com.project.shopapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	boolean existsByName(String name);

    Page<Product> findAll(Pageable pageable);
}
