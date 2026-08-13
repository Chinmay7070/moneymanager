package in.chinmaychaudhari.moneymanager.controller;

import in.chinmaychaudhari.moneymanager.dto.CategoryEntityDto;
import in.chinmaychaudhari.moneymanager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CatetoryController {

    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<CategoryEntityDto> saveCategory(@RequestBody CategoryEntityDto categoryDTO) {
        CategoryEntityDto savedCategory = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryEntityDto>> getCategories(){
       List<CategoryEntityDto> categories =  categoryService.getCategoriesForCurrentUser();
       return ResponseEntity.ok(categories);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<CategoryEntityDto>> getCategoriesByTypeForCurrentUser(@PathVariable String type) {
        List<CategoryEntityDto> list = categoryService.getCategoriesByTypeForCurrentUser(type);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryEntityDto> updateCategory(@PathVariable Long categoryId,
                                                      @RequestBody CategoryEntityDto categoryDTO) {
        CategoryEntityDto updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }
}
