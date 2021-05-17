package com.galvanize.coffeeapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    @Query(nativeQuery=true, value="select * from coffees where name like ? and dairy like ?")
    List<Coffee> findByNameContainsAndDairyContains(String name, String dairy);

    Coffee findByNameContains(String name);
}
