package com.green.jpaexam.product;

import com.green.jpaexam.product.model.ProductDto;
import com.green.jpaexam.product.model.ProductRes;
import com.green.jpaexam.product.model.ProductResQdsl;
import com.green.jpaexam.product.model.ProductUpdDto;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
        ProductRes res = service.saveProduct2(dto);
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<Page<ProductRes>> getProductAll(
            @PageableDefault(sort="number", direction = Sort.Direction.DESC, size = 20) Pageable page) {
        //참고로 이거는 쿼리스트링만 됨
        return ResponseEntity.ok(service.getProductAll(page)); //200, ok
    }

    @GetMapping("/jpql")
    public ResponseEntity<List<ProductRes>> getProductAllJpql(
            @PageableDefault(sort="number", direction = Sort.Direction.DESC, size = 20) Pageable page) {
        return ResponseEntity.ok(service.getProductAllJpql(page));
    }


    @GetMapping("/qdsl")
    public ResponseEntity<List<ProductResQdsl>> getProductAllQdsl(
            @ParameterObject
            @PageableDefault(page = 0, sort="number", direction = Sort.Direction.DESC, size = 20)
                                        Pageable page   ,
                                        @RequestParam(required = false) String search) {
        //위 처럼 검색어 search하나가 필수값이 아니게하고싶을 때는 리퀘스트파람 써도되지만
        //객체로 받을 때는 리퀘스트파람 쓰면 안된다
        return ResponseEntity.ok(service.getProductAllQdsl(page, search));
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
