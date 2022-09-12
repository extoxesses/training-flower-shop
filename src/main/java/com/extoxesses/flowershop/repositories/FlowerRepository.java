package com.extoxesses.flowershop.repositories;

import com.extoxesses.flowershop.entities.Flower;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, String> {

    @EntityGraph(attributePaths = "bundles")
    List<Flower> findAllByCodeIn(List<String> flowerCodes);

}
