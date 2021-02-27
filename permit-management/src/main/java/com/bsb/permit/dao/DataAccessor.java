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

import com.bsb.permit.model.Permit;
import com.bsb.permit.model.PermitType;
import com.bsb.permit.model.Ship;
import com.bsb.permit.model.StandardPermitStatuses;
import com.bsb.permit.model.StandardPermitTypes;

public class DataAccessor implements AutoCloseable {

	private static Logger logger = LoggerFactory.getLogger(DataAccessor.class);

	private java.sql.Connection cnn = null;

	public DataAccessor() {
		try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		String url = "jdbc:mysql://localhost:3306/tissing";
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root000");
		cnn = DriverManager.getConnection(url, prop);
	}

	public void addPermit(Permit permit, boolean overwrite) {
		if (null == cnn) {
			logger.info("No connection");
			return;
		}
		if (overwrite) {
			addOrUpdate(permit);
		} else {
			addOnly(permit);
		}
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
			String sql = "select permitId, expireDate, rawData, status, permitType, imo from t_permit";
			statSelect = cnn.prepareStatement(sql);
			rs = statSelect.executeQuery(sql);
			while (rs.next()) {
				result.add(new Permit(rs.getString("permitId"), rs.getNString("expireDate"), rs.getString("rawData"),
						StandardPermitStatuses.mapPermitStatus(rs.getString("status")),
						StandardPermitTypes.mapPermitType(rs.getString("permitType")), rs.getString("imo")));
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
			String sqlFormat = "select permitId, expireDate, rawData, status, permitType, imo from t_permit where permitType = '%s' and imo = '%s'";
			String sql = String.format(sqlFormat, permitType.getTypeName(), imo);
			statSelect = cnn.prepareStatement(sql);
			rs = statSelect.executeQuery(sql);
			while (rs.next()) {
				result.add(new Permit(rs.getString("permitId"), rs.getNString("expireDate"), rs.getString("rawData"),
						StandardPermitStatuses.mapPermitStatus(rs.getString("status")),
						StandardPermitTypes.mapPermitType(rs.getString("permitType")), rs.getString("imo")));
			}
			return result;
		} catch (Exception e) {
			return new ArrayList<Permit>();
		} finally {
			closeResource(rs);
			closeResource(statSelect);
		}
	}

	public List<String> exportRawData() {

		if (null == cnn) {
			logger.info("No connection");
			return new ArrayList<String>();
		}
		PreparedStatement statSelect = null;
		ResultSet rs = null;
		try {
			List<String> result = new ArrayList<String>();
			String sql = "select rawData from t_permit";
			statSelect = cnn.prepareStatement(sql);
			rs = statSelect.executeQuery(sql);
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

	private void addOnly(Permit permit) {
		PreparedStatement statSelect = null;
		PreparedStatement statUpdate = null;
		ResultSet rs = null;
		try {
			String sql = "select permitId from t_permit where permitId='" + permit.getPermitId() + "'";
			statSelect = cnn.prepareStatement(sql);
			rs = statSelect.executeQuery();
			if (rs.next()) {
				// already exist
				closeResource(statSelect);
				statSelect = null;
			} else {
				// add
				String sqlFormat = "insert into t_permit (permitId, expireDate, rawData, status) values ('%s','%s','%s','%s')";
				sql = String.format(sqlFormat, permit.getPermitId(), permit.getExpireDate(), permit.getRawText(), '1');
				statUpdate = cnn.prepareStatement(sql);
				int rows = statUpdate.executeUpdate(sql);
				logger.info("Add affected rows = " + rows);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(rs);
			closeResource(statSelect);
			closeResource(statUpdate);
		}
	}

	private void addOrUpdate(Permit permit) {
		PreparedStatement statSelect = null;
		PreparedStatement statUpdate = null;
		ResultSet rs = null;
		try {
			String sqlFormat = "select permitId, expireDate from t_permit where permitId='%s' and permitType='%s'";
			String sql = String.format(sqlFormat, permit.getPermitId(), permit.getPermitType().getTypeName());
			statSelect = cnn.prepareStatement(sql);
			rs = statSelect.executeQuery(sql);
			if (rs.next()) {
				// already exist
				logger.info(permit.getPermitId() + " already exist");
				// check expire date
				String existing = rs.getString("expireDate");
				if (existing.compareToIgnoreCase(permit.getExpireDate()) < 0) {
					// update
					sqlFormat = "update t_permit set expireDate = '%s', rawData = '%s', status = '%s' where permitId = '%s'";
					sql = String.format(sqlFormat, permit.getExpireDate(), permit.getRawText(),
							StandardPermitStatuses.STATUS_EXTENDED, permit.getPermitId());
					statUpdate = cnn.prepareStatement(sql);
					int rows = statUpdate.executeUpdate(sql);
					logger.info("Update affected rows = " + rows);
				} else {
					logger.info("Skip update permitId = " + permit.getPermitId());
				}
			} else {
				// add
				sqlFormat = "insert into t_permit (permitId, expireDate, rawData, status, permitType, imo) values ('%s','%s','%s','%s','%s','%s')";
				sql = String.format(sqlFormat, permit.getPermitId(), permit.getExpireDate(), permit.getRawText(),
						StandardPermitStatuses.STATUS_NEW, permit.getPermitType().getTypeName(), permit.getImo());
				statUpdate = cnn.prepareStatement(sql);
				int rows = statUpdate.executeUpdate(sql);
				logger.info("Add affected rows = " + rows);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(rs);
			closeResource(statSelect);
			closeResource(statUpdate);
		}
	}

	public void obsoletePermits(List<Permit> permits) {

		if (null == permits) {
			return;
		}

		for (Permit p : permits) {
			this.obsoletePermit(p);
		}
	}

	public boolean obsoletePermit(Permit permit) {
		if (null == permit) {
			logger.info("Permit is null");
			return false;
		}

		PreparedStatement statUpdate = null;
		try {
			String sqlFormat = "update t_permit set status = '%s' where permitId = '%s'";
			String sql = String.format(sqlFormat, StandardPermitStatuses.STATUS_OBSOLETED, permit.getPermitId());
			statUpdate = cnn.prepareStatement(sql);
			int rows = statUpdate.executeUpdate(sql);
			logger.info("Update affected rows = " + rows);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
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
				String sqlFormat = "insert into t_ship (shipName, description, imo, ownerCompany) values ('%s','%s','%s','%s')";
				sql = String.format(sqlFormat, ship.getShipName(), ship.getDescription(), ship.getImo(),
						ship.getOwnerCompany());
				statUpdate = cnn.prepareStatement(sql);
				int rows = statUpdate.executeUpdate(sql);
				logger.info("Add affected rows = " + rows);
				result = 1;
			}
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		} finally {
			closeResource(rs);
			closeResource(statSelect);
			closeResource(statUpdate);
		}

		return result;
	}

	public List<Ship> getShips() {
		if (null == cnn) {
			logger.info("No connection");
			return new ArrayList<Ship>();
		}
		PreparedStatement statSelect = null;
		ResultSet rs = null;
		try {
			List<Ship> result = new ArrayList<Ship>();
			String sql = "select shipName, description, imo, ownerCompany from t_ship";
			statSelect = cnn.prepareStatement(sql);
			rs = statSelect.executeQuery(sql);
			while (rs.next()) {
				result.add(new Ship(rs.getString("shipName"), rs.getString("description"), rs.getString("imo"),
						rs.getString("ownerCompany")));
			}
			return result;
		} catch (Exception e) {
			return new ArrayList<Ship>();
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
			e.printStackTrace();
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
