package kz.mmparts.backend.Repository;

import jakarta.persistence.criteria.Join;
import kz.mmparts.backend.Models.Generation;
import kz.mmparts.backend.Models.Part;
import org.springframework.data.jpa.domain.Specification;

public class PartSpecification {

    public static Specification<Part> hasBrand(String brand){
        return (root, query, criteriaBuilder) -> {
            Join<Part, Generation> join = root.join("generations");
            return criteriaBuilder.equal(join.get("brand"), brand);
        };
    }

    public static Specification<Part> hasModel(String model){
        return (root, query, criteriaBuilder) -> {
            Join<Part, Generation> join = root.join("generations");
            return criteriaBuilder.equal(join.get("model"), model);
        };
    }

    public static Specification<Part> hasGeneration(String generation){
        return (root, query, criteriaBuilder) -> {
            Join<Part, Generation> join = root.join("generations");
            return criteriaBuilder.equal(join.get("generation"), generation);
        };
    }

    public static Specification<Part> searchByKeyword(String search) {
        return (root, query, criteriaBuilder) -> {
            String lk = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), lk),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), lk)
            );
        };
    }
}
