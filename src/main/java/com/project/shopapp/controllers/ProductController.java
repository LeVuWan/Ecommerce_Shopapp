package com.project.shopapp.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.github.javafaker.Faker;
import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.CategoryDto;
import com.project.shopapp.dtos.ProductDto;
import com.project.shopapp.dtos.ProductImageDto;
import com.project.shopapp.model.Product;
import com.project.shopapp.model.ProductImage;
import com.project.shopapp.responses.ProductListResponse;
import com.project.shopapp.responses.ProductResponse;
import com.project.shopapp.services.ProductService;
import com.project.shopapp.utils.MessageKeys;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private LocalizationUtils localizationUtils;

	@GetMapping("")
	public ResponseEntity<ProductListResponse> getAllProduct(@RequestParam("page") int page,
			@RequestParam("limit") int limit) {
		PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createAt").descending());

		Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);

		int totalPages = productPage.getTotalPages();

		List<ProductResponse> products = productPage.getContent();
		ProductListResponse productListResponse = new ProductListResponse();
		productListResponse.setProducts(products);
		productListResponse.setTotalPages(totalPages);

		return ResponseEntity.ok(productListResponse);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable long productId) {
		try {
			Product existProduct = productService.getProductById(productId);
			return ResponseEntity.ok(ProductResponse.fromProduct(existProduct));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

	@PostMapping("")
	// POST http://localhost:8088/v1/api/products
	public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDTO, BindingResult result) {
		try {
			if (result.hasErrors()) {
				List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
						.toList();
				return ResponseEntity.badRequest().body(errorMessages);
			}
			Product newProduct = productService.createProduct(productDTO);
			return ResponseEntity.ok(newProduct);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	// POST http://localhost:8088/v1/api/products
	public ResponseEntity<?> uploadImages(@PathVariable("id") Long productId,
			@ModelAttribute("files") List<MultipartFile> files) {
		try {
			Product existingProduct = productService.getProductById(productId);
			files = files == null ? new ArrayList<MultipartFile>() : files;
			if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
				return ResponseEntity.badRequest()
						.body(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_MAX_5));
			}
			List<ProductImage> productImages = new ArrayList<>();
			for (MultipartFile file : files) {
				if (file.getSize() == 0) {
					continue;
				}
				// Kiểm tra kích thước file và định dạng
				if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
					return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
							.body(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_LARGE));
				}
				String contentType = file.getContentType();
				if (contentType == null || !contentType.startsWith("image/")) {
					return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
							.body(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_MUST_BE_IMAGE));
				}
				// Lưu file và cập nhật thumbnail trong DTO
				String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
				// lưu vào đối tượng product trong DB
				ProductImageDto productImageDTO = new ProductImageDto(filename);
				ProductImage productImage = productService.createProductImage(existingProduct.getId(), productImageDTO);
				productImages.add(productImage);
			}
			return ResponseEntity.ok().body(productImages);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable long id) {
		return ResponseEntity.ok("Updateproduct with id=" + id);
	}

	private String storeFile(MultipartFile file) throws IOException {
		if (!isImageFile(file) || file.getOriginalFilename() == null) {
			throw new IOException("Invalid image format");
		}
		String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		// Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
		String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
		// Đường dẫn đến thư mục mà bạn muốn lưu file
		java.nio.file.Path uploadDir = Paths.get("uploads");
		// Kiểm tra và tạo thư mục nếu nó không tồn tại
		if (!Files.exists(uploadDir)) {
			Files.createDirectories(uploadDir);
		}
		// Đường dẫn đầy đủ đến file
		java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
		// Sao chép file vào thư mục đích
		Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
		return uniqueFilename;
	}

	private boolean isImageFile(MultipartFile file) {
		String contentType = file.getContentType();
		return contentType != null && contentType.startsWith("image/");
	}

	// @PostMapping("/generateFakeProducts")
	private ResponseEntity<String> generateFakeProducts() {
		Faker faker = new Faker();
		for (int i = 0; i < 20; i++) {
			String productName = faker.commerce().productName();
			if (productService.existByTitle(productName)) {
				continue;
			}
			ProductDto productDto = new ProductDto();
			productDto.setName(productName);
			productDto.setPrice(faker.number().numberBetween(10, 10000000));
			productDto.setDescription(faker.lorem().sentence());
			productDto.setThumbnail("");
			productDto.setCategoryId((long) faker.number().numberBetween(2, 3));
			try {
				productService.createProduct(productDto);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.ok("Fake product created successfully");
	}

}
