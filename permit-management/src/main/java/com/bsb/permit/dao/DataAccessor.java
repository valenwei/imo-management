package com.bsb.permit.dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsb.permit.exception.PmException;
import com.bsb.permit.model.Permit;
import com.bsb.permit.model.PermitType;
import com.bsb.permit.model.Ship;
import com.bsb.permit.model.StandardPermitTypes;
import com.bsb.permit.util.Constants;

public class DataAccessor implements AutoCloseable {

	private static Logger logger = LoggerFactory.getLogger(DataAccessor.class);

	private java.sql.Connection cnn = null;

	public DataAccessor() {
		try {
			connect();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		// "jdbc:mysql://localhost:3306/tissing"
		String urlFormat = "jdbc:mysql://%s:%s/tissing";
		String url = String.format(urlFormat, System.getProperty("pm.server", "localhost"),
				System.getProperty("pm.port", "3306"));
		Properties prop = new Properties();
		prop.setProperty("user", System.getProperty("pm.user", "root"));
		prop.setProperty("password", System.getProperty("pm.password", "root000"));
		cnn = DriverManager.getConnection(url, prop);
	}

	public List<Permit> getPermits() {

		if (null == cnn) {
			logger.info("No connection");
			return new ArrayList<Permit>();
		}
		PreparedStatement statSelect = null;
		ResultSet rs = null;
		try {
			List<Permit> result = new ArrayList<Permit>();
			String sql = "select permitId, expireDate, rawData, importDate, permitType, imo from t_permit";
			statSelect = cnn.prepareStatement(sql);
			rs = statSelect.executeQuery(sql);
			while (rs.next()) {
				result.add(new Permit(rs.getString("permitId"), rs.getNString("expireDate"), rs.getString("rawData"),
						rs.getString("importDate"), StandardPermitTypes.mapPermitType(rs.getString("permitType")),
						rs.getString("imo")));
			}
			return result;
		} catch (Exception e) {
			return new ArrayList<Permit>();
		} finally {
			closeResource(rs);
			closeResource(statSelect);
		}
	}

	public List<Permit> getPermits(PermitType permitType, String imo) {

		if (null == cnn) {
			logger.info("No connection");
			return new ArrayList<Permit>();
		}
		PreparedStatement statSelect = null;
		ResultSet rs = null;
		try {
			List<Permit> result = new ArrayList<Permit>();
			String sqlFormat = "select permitId, expireDate, rawData, importDate, permitType, imo from t_permit where permitType = '%s' and imo = '%s' order by expireDate, permitId asc";
			String sql = String.format(sqlFormat, permitType.getTypeName(), imo);
			statSelect = cnn.prepareStatement(sql);
			rs = statSelect.executeQuery(sql);
			while (rs.next()) {
				result.add(new Permit(rs.getString("permitId"), rs.getNString("expireDate"), rs.getString("rawData"),
						rs.getString("importDate"), StandardPermitTypes.mapPermitType(rs.getString("permitType")),
						rs.getString("imo")));
			}
			return result;
		} catch (Exception e) {
			return new ArrayList<Permit>();
		} finally {
			closeResource(rs);
			closeResource(statSelect);
		}
	}

	public List<String> exportRawData(Ship ship, String permitType) {

		if (null == cnn) {
			logger.info("No connection");
			return new ArrayList<String>();
		}
		PreparedStatement statSelect = null;
		ResultSet rs = null;
		try {
			List<String> result = new ArrayList<String>();
			String sqlFormat = "select rawData from t_permit where IMO='%s' and permitType='%s'";
			statSelect = cnn.prepareStatement(String.format(sqlFormat, ship.getImo(), permitType));
			rs = statSelect.executeQuery();
			while (rs.next()) {
				result.add(rs.getString("rawData"));
			}
			return result;
		} catch (Exception e) {
			return new ArrayList<String>();
		} finally {
			closeResource(rs);
			closeResource(statSelect);
		}
	}

	public int addOrUpdatePermit(Permit permit) {
		PreparedStatement statSelect = null;
		PreparedStatement statUpdate = null;
		ResultSet rs = null;
		try {
			String sqlFormat = "select permitId, expireDate from t_permit where permitId='%s' and permitType='%s' and imo='%s'";
			String sql = String.format(sqlFormat, permit.getPermitId(), permit.getPermitType().getTypeName(),
					permit.getImo());
			statSelect = cnn.prepareStatement(sql);
			rs = statSelect.executeQuery(sql);
			if (rs.next()) {
				// already exist
				logger.info(permit.getPermitId() + " already exist");
				// check expire date
				String existing = rs.getString("expireDate");
				if (existing.compareToIgnoreCase(permit.getExpireDate()) < 0) {
					// update
					sqlFormat = "update t_permit set expireDate = '%s', rawData = '%s', importDate = '%s' where permitId='%s' and permitType='%s' and imo='%s'";
					sql = String.format(sqlFormat, permit.getExpireDate(), permit.getRawText(), permit.getImportDate(),
							permit.getPermitId(), permit.getPermitType().getTypeName(), permit.getImo());
					statUpdate = cnn.prepareStatement(sql);
					int rows = statUpdate.executeUpdate(sql);
					logger.info("Update affected rows = " + rows);
					return Constants.DATA_ACCESSOR_UPDATE;
				} else {
					logger.info("Skip update permitId = " + permit.getPermitId());
					return Constants.DATA_ACCESSOR_SKIP;
				}
			} else {
				// add
				sqlFormat = "insert into t_permit (permitId, expireDate, rawData, importDate, permitType, imo) values ('%s','%s','%s','%s','%s','%s')";
				sql = String.format(sqlFormat, permit.getPermitId(), permit.getExpireDate(), permit.getRawText(),
						permit.getImportDate(), permit.getPermitType().getTypeName(), permit.getImo());
				statUpdate = cnn.prepareStatement(sql);
				int rows = statUpdate.executeUpdate(sql);
				logger.info("Add affected rows = " + rows);
				return Constants.DATA_ACCESSOR_ADD;
			}
		} catch (Exception e) {
			logger.info(e.getMessage() + ". " + e.getStackTrace().toString());
			return Constants.DATA_ACCESSOR_FAILURE;
		} finally {
			closeResource(rs);
			closeResource(statSelect);
			closeResource(statUpdate);
		}
	}

	public boolean deletePermit(Permit permit) {
		if (null == permit) {
			logger.info("Permit is null");
			return false;
		}

		PreparedStatement statUpdate = null;
		try {
			String sqlFormat = "delete from t_permit where permitId = '%s' and IMO = '%s'";
			String sql = String.format(sqlFormat, permit.getPermitId(), permit.getImo());
			statUpdate = cnn.prepareStatement(sql);
			int rows = statUpdate.executeUpdate();
			logger.info("Delete affected rows = " + rows);
			return true;
		} catch (Exception e) {
			logger.info("Delete permit failed: " + permit.getPermitId() + ", " + permit.getImo());
			logger.info(e.getMessage() + ". " + e.getStackTrace().toString());
		} finally {
			closeResource(statUpdate);
		}

		return false;
	}

	public int addShip(Ship ship) {
		int result = -1;

		PreparedStatement statSelect = null;
		PreparedStatement statUpdate = null;
		ResultSet rs = null;
		try {
			String sql = "select shipName from t_ship where imo='" + ship.getImo() + "'";
			statSelect = cnn.prepareStatement(sql);
			rs = statSelect.executeQuery();
			if (rs.next()) {
				// already exist
				closeResource(statSelect);
				statSelect = null;
				result = 0;
			} else {
				// add
				String sqlFormat = "insert into t_ship (shipName, description, imo, ownerCompany, master, backup,reserve1,reserve2) values ('%s','%s','%s','%s','%s','%s','%s','%s')";
				sql = String.format(sqlFormat, ship.getShipName(), ship.getDescription(), ship.getImo(),
						ship.getOwnerCompany(), ship.getMaster(), ship.getBackup(), ship.getReserve1(),
						ship.getReserve2());
				statUpdate = cnn.prepareStatement(sql);
				int rows = statUpdate.executeUpdate();
				logger.info("Add affected rows = " + rows);
				result = 1;
			}
		} catch (Exception e) {
			result = -1;
			logger.info("Add ship failed.");
			logger.info(e.getMessage() + ". " + e.getStackTrace().toString());
		} finally {
			closeResource(rs);
			closeResource(statSelect);
			closeResource(statUpdate);
		}

		return result;
	}

	public boolean deleteShip(Ship ship) {
		boolean result = true;

		boolean autoCommit = true;

		PreparedStatement statUpdate = null;
		PreparedStatement statUpdate2 = null;
		ResultSet rs = null;
		try {
			autoCommit = cnn.getAutoCommit();

			cnn.setAutoCommit(false);

			// delete permits of the ship
			String sqlFormat = "delete from t_permit where IMO ='%s'";
			String sql = String.format(sqlFormat, ship.getImo());

			statUpdate = cnn.prepareStatement(sql);
			int rows = statUpdate.executeUpdate();
			logger.info(rows + " permits deleted. The ship is: " + ship.getImo());

			// delete the ship
			sqlFormat = "delete from t_ship where IMO ='%s'";
			sql = String.format(sqlFormat, ship.getImo());
			statUpdate2 = cnn.prepareStatement(sql);
			rows = statUpdate2.executeUpdate();

			cnn.commit();
			logger.info(rows + " ships deleted. The ship is: " + ship.getImo());
		} catch (Exception e) {
			result = false;
			try {
				cnn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info("Delete ship failed.");
			logger.info(e.getMessage() + ". " + e.getStackTrace().toString());
		} finally {
			closeResource(rs);
			closeResource(statUpdate);
			closeResource(statUpdate2);
			try {
				cnn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<Ship> getShips() throws PmException {
		if (null == cnn) {
			logger.info("No connection");
			throw new PmException(Constants.PM_EXCEPTION_CONNECTION_FAILURE);
		}
		PreparedStatement statSelect = null;
		ResultSet rs = null;
		try {
			List<Ship> result = new ArrayList<Ship>();
			String sql = "select shipName, description, imo, ownerCompany, master, backup,reserve1,reserve2 from t_ship";
			statSelect = cnn.prepareStatement(sql);
			rs = statSelect.executeQuery(sql);
			while (rs.next()) {
				result.add(new Ship(rs.getString("shipName"), rs.getString("description"), rs.getString("imo"),
						rs.getString("ownerCompany"), rs.getString("master"), rs.getString("backup"),
						rs.getString("reserve1"), rs.getString("reserve2")));
			}
			return result;
		} catch (Exception e) {
			logger.info(e.getMessage() + ". " + e.getStackTrace().toString());
			throw new PmException(Constants.PM_EXCEPTION_CONNECTION_FAILURE, e);
		} finally {
			closeResource(rs);
			closeResource(statSelect);
		}
	}

	private void closeResource(AutoCloseable obj) {
		try {
			if (null == obj) {
				return;
			}

			obj.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage() + ". " + e.getStackTrace().toString());
		}
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		if (null != cnn) {
			cnn.close();
			cnn = null;
		}
	}
}
