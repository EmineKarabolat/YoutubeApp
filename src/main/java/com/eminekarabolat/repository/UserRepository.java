package com.eminekarabolat.repository;

import com.eminekarabolat.entity.User;
import com.eminekarabolat.utility.ConnectionProvider;
import com.eminekarabolat.utility.ICrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements ICrud<User> {
	private String sql;
	private final ConnectionProvider connectionProvider;
	
	public UserRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	@Override
	public Optional<User> save(User user) {
		sql = "INSERT INTO tbl_user(name,surname,email,username,password) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getSurname());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getUsername());
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("Repository: User kaydedilirken hata oluştu. " + e.getMessage());
		}
		return Optional.ofNullable(user);
	}
	
	@Override
	public Optional<User> update(User user) {
		sql="UPTADE tbl_user SET name=?,surname=?,email=?,username=?,password=? WHERE id=?";
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getSurname());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getUsername());
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.setLong(6, user.getId());
			int updatedRows = preparedStatement.executeUpdate();
			if (updatedRows ==0) {
				System.err.println("Repository: User güncellenirken hata oluştu. Güncelleme Başarırız");
			}
			
		}
		catch (SQLException e) {
			System.err.println("Repository: User güncellenirken hata oluştu."+ e.getMessage());
		}
		return Optional.ofNullable(user);
	}
	
	@Override
	public void delete(Long id) {
		sql="DELETE FROM tbl_user WHERE id=?";
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
			
		}
		catch (SQLException e) {
			System.err.println("Repository: Futbolcu güncellenirken hata oluştu."+ e.getMessage());
		}
		
	}
	
	@Override
	public List<User> findAll() {
		sql = "SELECT * FROM tbl_user ORDER BY id DESC";
		List<User> userList = new ArrayList<>();
		try {PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();{
				while (resultSet.next()) {
					userList.add(getValueFromResultSet(resultSet));
				}
			}
		}
		catch (SQLException e) {
			System.err.println(("Müşteri Listesi getirirken bir sorun yaşandı..." + e.getMessage()));
		}
		return userList;
	}
	
	@Override
	public Optional<User> findById(Long id) {
		sql = "SELECT * FROM tbl_user WHERE id=?";
		User user = null;
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return Optional.of(getValueFromResultSet(resultSet));
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: User bulunurken hata oluştu:"+ e.getMessage());
		}
		return Optional.ofNullable(user);
	}
	
	private User getValueFromResultSet(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		String name = rs.getString("name");
		String surname = rs.getString("surname");
		String email = rs.getString("email");
		String username = rs.getString("username");
		String password = rs.getString("password");
		Integer state = rs.getInt("state");
		Long createat = rs.getLong("createat");
		Long updateat= rs.getLong("updateat");
		
		return new User(id, name,surname,email,username,password, state, createat, updateat);
	}
	
	public Optional<User> findByUsername(String username) {
		sql = "SELECT * FROM tbl_user WHERE username=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, username);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(getValueFromResultSet(resultSet));
					
				}
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}