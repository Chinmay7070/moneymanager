package in.chinmaychaudhari.moneymanager.controller;

import in.chinmaychaudhari.moneymanager.dto.ExpenceDto;
import in.chinmaychaudhari.moneymanager.service.ExpenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenceController {

    private final ExpenceService expenseService;

    @PostMapping
    public ResponseEntity<ExpenceDto> addExpense(@RequestBody ExpenceDto dto) {
        ExpenceDto saved = expenseService.addExpense(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ExpenceDto>> getExpenses() {
        List<ExpenceDto> expenses = expenseService.getCurrentMonthExpensesForCurrentUser();
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
