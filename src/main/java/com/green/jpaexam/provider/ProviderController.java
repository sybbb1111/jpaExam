package com.green.jpaexam.provider;

import com.green.jpaexam.provider.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provier")
@Slf4j
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService service;

    @PostMapping
    public ResponseEntity<ProviderInsVo> postProvider(@RequestBody ProviderReqDto dto){
        ProviderInsVo res = service.save(dto);
        return ResponseEntity.ok(res); //ok쓰면 응답코드는 200
    }

    @PutMapping //put매핑으로 할게요 패치로 하기에는 바꿀 컬럼양이 하나밖에 없으니까~
    public ResponseEntity<ProviderUpdVo> putProvider(@RequestBody ProviderUpdDto dto) {
        ProviderUpdVo res = service.update(dto);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping
    public ResponseEntity<Integer> delProvider(@RequestParam Long id){
        service.delete(id);
        return ResponseEntity.ok(1); //리턴값 1나오게 할게용
    }

    @GetMapping
    public ResponseEntity<List<ProviderSelAll>> getAllProvider() {
        List<ProviderSelAll> res = service.selAllProvider();
        return ResponseEntity.ok(res);

    }





}
