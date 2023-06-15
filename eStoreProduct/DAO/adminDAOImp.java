package eStoreProduct.DAO;

import org.springframework.jdbc.core.JdbcTemplate;

import eStoreProduct.model.adminMapper;
import eStoreProduct.model.adminModel;
import eStoreProduct.model.passwordHashing;

public class adminDAOImp implements adminDAO {
	JdbcTemplate jdbcTemplate;

	public adminModel getAdmin(String email, String password) {
		password = passwordHashing.hashString(password);
		System.out.println(email + "" + password);

		String adminSelectQuery = "SELECT * FROM slam_AdminUsers WHERE ausr_email = ? AND ausr_pwd = ?";

		try {
			adminModel am = jdbcTemplate.queryForObject(adminSelectQuery, new Object[] { email, password },
					new adminMapper());
			return am;
		} catch (Exception e) {
			// Handle the exception appropriately (e.g., logging, throwing custom exception, etc.)
			e.printStackTrace();
			return null; // or throw an exception if required
		}
	}

}
