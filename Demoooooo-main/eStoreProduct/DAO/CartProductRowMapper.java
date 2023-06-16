package eStoreProduct.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import eStoreProduct.model.Product;

public class CartProductRowMapper implements RowMapper<Product> {
	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new Product();
		product.setProd_id(rs.getInt("Prod_id"));
		product.setProd_title(rs.getString("prod_title"));
		product.setProd_prct_id(rs.getInt("prod_prct_id"));
		product.setProd_gstc_id(rs.getInt("prod_gstc_id"));
		product.setProd_brand(rs.getString("prod_brand"));
		product.setImage_url(rs.getString("image_url"));
		product.setProd_desc(rs.getString("prod_desc"));
		product.setReorderLevel(rs.getInt("reorderLevel"));

		return product;
	}
}
