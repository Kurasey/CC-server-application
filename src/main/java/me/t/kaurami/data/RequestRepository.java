package me.t.kaurami.data;

import me.t.kaurami.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
