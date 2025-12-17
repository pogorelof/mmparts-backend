package kz.mmparts.backend.Repository;

import kz.mmparts.backend.Models.Part;
import lombok.NonNull;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public interface PartRepository extends
        JpaRepository<Part, Long>,
        JpaSpecificationExecutor<Part> {
}
