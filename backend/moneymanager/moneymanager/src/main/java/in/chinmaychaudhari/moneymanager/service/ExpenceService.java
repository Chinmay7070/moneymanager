package in.chinmaychaudhari.moneymanager.service;


import in.chinmaychaudhari.moneymanager.dto.ExpenceDto;
import in.chinmaychaudhari.moneymanager.dto.IncomeDto;
import in.chinmaychaudhari.moneymanager.entity.CategoryEntity;
import in.chinmaychaudhari.moneymanager.entity.ExpenceEntity;
import in.chinmaychaudhari.moneymanager.entity.IncomeEntity;
import in.chinmaychaudhari.moneymanager.entity.ProfileEntity;
import in.chinmaychaudhari.moneymanager.repository.CategoryRepo;
import in.chinmaychaudhari.moneymanager.repository.ExpenceRepo;
import in.chinmaychaudhari.moneymanager.repository.InocmeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenceService {

    private final CategoryRepo categoryRepository;
    private final ExpenceRepo expenseRepository;
    private final ProfileService profileService;

    // Adds a new expense to the database
    public ExpenceDto addExpense(ExpenceDto dto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        ExpenceEntity newExpense = toEntity(dto, profile, category);
        newExpense = expenseRepository.save(newExpense);
        return toDTO(newExpense);
    }

    // Retrieves all expenses for current month/based on the start date and end date
    public List<ExpenceDto> getCurrentMonthExpensesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<ExpenceEntity> list = expenseRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
        return list.stream().map(this::toDTO).toList();
    }

    public void deleteExpense(Long expenseId) {

        ProfileEntity profile = profileService.getCurrentProfile();

        ExpenceEntity entity = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (entity.getProfile().getId() != profile.getId()) {
            throw new RuntimeException("Unauthorized to delete this expense");
        }

        expenseRepository.delete(entity);
    }

    public List<ExpenceDto> getLatest5ExpensesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenceEntity> list = expenseRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream().map(this::toDTO).toList();
    }

    public BigDecimal getTotalExpenseForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal total = expenseRepository.findTotalExpenseByProfileId(profile.getId());
        return total != null ? total: BigDecimal.ZERO;
    }

    public List<ExpenceDto> filterExpenses(LocalDate startDate, LocalDate endDate, String keyword, Sort sort) {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenceEntity> list = expenseRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(), startDate, endDate, keyword, sort);
        return list.stream().map(this::toDTO).toList();
    }

    //Notifications
    public List<ExpenceDto> getExpensesForUserOnDate(Long profileId, LocalDate date) {
        List<ExpenceEntity> list = expenseRepository.findByProfileIdAndDate(profileId, date);
        return list.stream().map(this::toDTO).toList();
    }
    //helper methods
    private ExpenceEntity toEntity(ExpenceDto dto, ProfileEntity profile, CategoryEntity category) {
        return ExpenceEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .profile(profile)
                .category(category)
                .build();
    }

    private ExpenceDto toDTO(ExpenceEntity entity) {
        return ExpenceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId(): null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName(): "N/A")
                .amount(entity.getAmount())
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
