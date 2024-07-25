package com.hcaptsys.aptrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hcaptsys.aptmodel.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

}
