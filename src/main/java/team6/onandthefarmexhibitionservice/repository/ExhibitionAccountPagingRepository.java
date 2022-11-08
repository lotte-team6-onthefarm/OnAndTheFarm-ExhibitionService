package team6.onandthefarmexhibitionservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import team6.onandthefarmexhibitionservice.entity.ExhibitionAccount;

public interface ExhibitionAccountPagingRepository extends PagingAndSortingRepository<ExhibitionAccount, Long> {

	@Query(value = "select e from ExhibitionAccount e where e.exhibitionAccountStatus = true",
	countQuery ="select count(e) from ExhibitionAccount e")
	Page<ExhibitionAccount> findExhibitionOrderBy(PageRequest pageRequest);
}
