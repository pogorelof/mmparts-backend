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
}
