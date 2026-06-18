package com.app.ecom.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.Product;
import com.app.ecom.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
       
        Product product = new Product();
        mapToProductRequest(product,productRequest);
        product.setActive(true);
        productRepository.save(product);
        return mapToProductResponse(product);

    }

    public Optional<ProductResponse> updateProduct(ProductRequest productRequest,Long id) {
       
       return productRepository.findById(id).map(existingProduct -> {mapToProductRequest(existingProduct,productRequest);
                                                                    productRepository.save(existingProduct);
                                                                   return mapToProductResponse(existingProduct);
                                                                      });
    }

    public List<ProductResponse> getAllProduct() {

        return productRepository.findByActiveTrue().stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    public boolean deleteProductById(Long id) {
          
        return productRepository.findById(id).map(product ->{
                                                                product.setActive(false);
                                                                productRepository.save(product);
                                                                return true;
                                                            }).orElse(false);
                                                       
       

    }

   public List<ProductResponse> searchProduct(String keyword) {
       
       return productRepository.searchProducts(keyword).stream().map(this::mapToProductResponse).collect(Collectors.toList());

    } 




    /**Helper Method **/

    public void mapToProductRequest(Product product, ProductRequest productRequest) {

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());

        if (productRequest.getActive() != null) {
        product.setActive(productRequest.getActive());
        }
        
    }

    public ProductResponse mapToProductResponse(Product product) {

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setCategory(product.getCategory());
        productResponse.setPrice(product.getPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setActive(product.getActive());
        
        return productResponse;

    }


}
