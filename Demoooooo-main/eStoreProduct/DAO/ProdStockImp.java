package eStoreProduct.DAO;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import eStoreProduct.model.ProdStock;
import eStoreProduct.model.ProductStockRowMapper;

public class ProdStockImp implements ProdStockDAO {
	private static final String PD_STK_QUERY = "SELECT * FROM slam_productstock";
	private static final String SELECT_PRD_STK_QUERY = "SELECT * FROM slam_productstock WHERE prod_id = ?";
	private static final String PD_PRICE_QUERY = "SELECT prod_price FROM slam_productstock WHERE prod_id = ?";
	private static final String PD_MRP_QUERY = "SELECT prod_mrp FROM slam_productstock WHERE prod_id = ?";

	private JdbcTemplate jdbcTemplate;

	public ProdStockImp(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<ProdStock> getAllProdStocks() {
		return jdbcTemplate.query(PD_STK_QUERY, new ProductStockRowMapper());
	}

	@Override
	public ProdStock getProdStockById(int prodId) {
		return jdbcTemplate.queryForObject(SELECT_PRD_STK_QUERY, new ProductStockRowMapper(), prodId);
	}

	@Override
	public double getProdPriceById(int prodId) {
		return jdbcTemplate.queryForObject(PD_PRICE_QUERY, Double.class, prodId);
	}

	@Override
	public double getProdMrpById(int prodId) {
		return jdbcTemplate.queryForObject(PD_MRP_QUERY, Double.class, prodId);
	}
}
