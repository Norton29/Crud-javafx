package model.Dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {

	void insert(Seller obj);
	void delete(Seller obj);
	void uptade(Seller obj);
	void deleteById(Integer id);
	Seller findById (Integer id);
	List<Seller> findAll ();
	List<Seller> findByDepartment(Department department);

	
}
