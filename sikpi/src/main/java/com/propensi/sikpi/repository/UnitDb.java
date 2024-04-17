package com.propensi.sikpi.repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.Unit;
import com.propensi.sikpi.model.UserModel;
import java.util.List;



@Repository
public interface UnitDb extends JpaRepository<Unit, Long>{

}
