package com.green.jpaexam.product;

import com.green.jpaexam.product.model.ProductDto;
import com.green.jpaexam.product.model.ProductRes;
import com.green.jpaexam.product.model.ProductUpdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductRes> postProduct(@RequestBody ProductDto dto){
        ProductRes res = service.saveProduct(dto);
        return ResponseEntity.ok(res);

    }

    @GetMapping
    public ResponseEntity<List<ProductRes>> getProductAll() {
        return ResponseEntity.ok(service.getProductAll()); //200, ok
    }

    @GetMapping("/{number}")
    public ResponseEntity<ProductRes> getProduct(@PathVariable Long number) {
        return ResponseEntity.ok(service.getProduct(number));
    }

    @PutMapping
    public ResponseEntity<ProductRes> updProduct(@RequestBody ProductUpdDto dto) {
        return ResponseEntity.ok(service.updProduct(dto));
    }

    @DeleteMapping
    public ResponseEntity<Integer> delProduct(@RequestParam Long number) {
        service.delProduct(number);
        return ResponseEntity.ok(1);
    }

}
