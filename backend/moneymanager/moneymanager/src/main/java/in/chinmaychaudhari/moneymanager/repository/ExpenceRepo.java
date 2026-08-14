package in.chinmaychaudhari.moneymanager.repository;

import in.chinmaychaudhari.moneymanager.entity.ExpenceEntity;
import in.chinmaychaudhari.moneymanager.entity.IncomeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenceRepo extends JpaRepository<ExpenceEntity,Long> {

    //select * from tbl_expenses where profile_id = ?1 order by date desc
    List<ExpenceEntity> findByProfileIdOrderByDateDesc(Long profileId);

    //select * from tbl_expenses where profile_id = ?1 order by date desc limit 5
    List<ExpenceEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);

    @Query("SELECT SUM(e.amount) FROM ExpenseEntity e WHERE e.profile.id = :profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);

    //select * from tbl_expenses where profile_id = ?1 and date between ?2 and ?3 and name like %?4%
    List<ExpenceEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            Long profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );

    //select * from tbl_expenses where profile_id = ?1 and date between ?2 and ?3
    List<ExpenceEntity> findByProfileIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);

    //select * from tbl_expenses where profile_id = ?1 and date = ?2
    List<ExpenceEntity> findByProfileIdAndDate(Long profileId, LocalDate date);
}
