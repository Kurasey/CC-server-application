package me.t.kaurami.data;

import me.t.kaurami.entities.Request;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {
}
