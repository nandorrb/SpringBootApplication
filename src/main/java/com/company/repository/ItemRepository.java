package com.company.repository;

import com.company.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository  extends JpaRepository<Item, Integer>, JpaSpecificationExecutor<Item> {
}
