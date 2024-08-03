package com.project.shopapp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.shopapp.dtos.ProductDto;
import com.project.shopapp.dtos.ProductImageDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.model.Product;
import com.project.shopapp.model.ProductImage;
import com.project.shopapp.responses.ProductResponse;

@Service
public interface IProductService {
	public Product createProduct(ProductDto productDto) throws DataNotFoundException;

	public Product getProductById(Long id) throws Exception;

	Page<ProductResponse> getAllProducts(PageRequest pageRequest);

	Product updateProduct(Long id, ProductDto productDto) throws DataNotFoundException;

	void deleteProduct(Long id) ;

	boolean existByTitle(String title);
	
	ProductImage createProductImage(Long productId, ProductImageDto productImageDto) throws Exception;
}
