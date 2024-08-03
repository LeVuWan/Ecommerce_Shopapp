package com.project.shopapp.services;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.shopapp.dtos.ProductDto;
import com.project.shopapp.dtos.ProductImageDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.model.Category;
import com.project.shopapp.model.Product;
import com.project.shopapp.model.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.responses.ProductResponse;

@Service
public class ProductService implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Override
	public Product createProduct(ProductDto productDto) throws DataNotFoundException {
		Category existCategory = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
				() -> new DataNotFoundException("Can't find category with id: " + productDto.getCategoryId()));

		Product newProduct = new Product();
		newProduct.setName(productDto.getName());
		newProduct.setDescription(productDto.getDescription());
		newProduct.setPrice(productDto.getPrice());
		newProduct.setThumbnail(productDto.getThumbnail());
		newProduct.setCategory(existCategory);

		return productRepository.save(newProduct);
	}

	@Override
	public Product getProductById(Long ProductId) throws Exception {
		return productRepository.findById(ProductId)
				.orElseThrow(() -> new DataNotFoundException("Can't find product with id: " + ProductId));
	}

	@Override
	public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
		return productRepository.findAll(pageRequest).map(product -> ProductResponse.fromProduct(product));
	}

	@Override
	public Product updateProduct(Long id, ProductDto productDto) throws DataNotFoundException {
		Product existProduct = productRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Can't find product with id: " + id));

		if (existProduct != null) {
			Category existCategory = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
					() -> new DataNotFoundException("Can't find category with id: " + productDto.getCategoryId()));
			existProduct.setName(productDto.getName());
			existProduct.setPrice(productDto.getPrice());
			existProduct.setThumbnail(productDto.getThumbnail());
			existProduct.setCategory(existCategory);
			existProduct.setDescription(productDto.getDescription());
			return productRepository.save(existProduct);
		}
		return null;
	}

	@Override
	public void deleteProduct(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		optionalProduct.ifPresent(productRepository::delete);
	}

	@Override
	public boolean existByTitle(String title) {
		return productRepository.existsByName(title);
	}

	@Override
	public ProductImage createProductImage(Long productId, ProductImageDto productImageDTO) throws Exception {
		Product existingProduct = productRepository.findById(productId).orElseThrow(
				() -> new DataNotFoundException("Cannot find product with id: " + productImageDTO.getProductId()));
		ProductImage newProductImage = new ProductImage();
		newProductImage.setImageUrl(productImageDTO.getImageUrl());
		newProductImage.setProductId(existingProduct);
		// Ko cho insert quá 5 ảnh cho 1 sản phẩm
		int size = productImageRepository.findByProduct_Id(productId).size();
		if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
			throw new InvalidParamException("Number of images must be <= " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
		}
		return productImageRepository.save(newProductImage);
	}
}
