package com.eminekarabolat.repository;

import com.eminekarabolat.entity.BaseEntity;
import com.eminekarabolat.entity.Like;
import com.eminekarabolat.entity.Video;
import com.eminekarabolat.utility.ConnectionProvider;
import com.eminekarabolat.utility.ICrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LikeRepository implements ICrud<Like> {
	private String sql;
	private final ConnectionProvider connectionProvider;
	
	public LikeRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	@Override
	public Optional<Like> save(Like like) {
		sql = "INSERT INTO tbl_like (user_id,video_id) VALUES (?,?)";
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setLong(1, like.getUserId());
			preparedStatement.setLong(2, like.getVideoId());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("Repository: Like kaydedilirken hata oluştu. " + e.getMessage());
		}
		return Optional.ofNullable(like);
	}
	
	@Override
	public Optional<Like> update(Like like) {
		sql = "UPTADE tbl_like SET user_id=?, video_id=?  WHERE id=?";
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setLong(1, like.getUserId());
			preparedStatement.setLong(2, like.getVideoId());
			preparedStatement.setLong(3, like.getId());
			int updatedRows = preparedStatement.executeUpdate();
			if (updatedRows == 0) {
				System.err.println("Repository: Like güncellenirken hata oluştu. Güncelleme Başarırız");
			}
			
		}
		catch (SQLException e) {
			System.err.println("Repository: Like güncellenirken hata oluştu." + e.getMessage());
		}
		return Optional.ofNullable(like);
	}
	
	@Override
	public void delete(Long id) {
		sql = "DELETE FROM tbl_like WHERE id=?";
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
			
		}
		catch (SQLException e) {
			System.err.println("Repository: Like güncellenirken hata oluştu." + e.getMessage());
		}
		
		
	}
	
	@Override
	public List<Like> findAll() {
		sql = "SELECT * FROM tbl_like ORDER BY id DESC";
		List<Like> likeList = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			{
				while (resultSet.next()) {
					likeList.add(getValueFromResultSet(resultSet));
				}
			}
		}
		catch (SQLException e) {
			System.err.println(("Video Listesi getirirken bir sorun yaşandı..." + e.getMessage()));
		}
		return likeList;
	}
	
	@Override
	public Optional<Like> findById(Long id) {
		sql = "SELECT * FROM tbl_like WHERE id=?";
		
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return Optional.of(getValueFromResultSet(resultSet));
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Like bulunurken hata oluştu:" + e.getMessage());
		}
		return Optional.empty();
	}
	
	private Like getValueFromResultSet(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		Long userId = rs.getLong("user_id");
		Long videoId = rs.getLong("video_id");
		Integer state = rs.getInt("state");
		Long createat = rs.getLong("createat");
		Long updateat = rs.getLong("updateat");
		
		return new Like(id, userId, videoId, state, createat, updateat);
	}
	
	public Optional<Like> findByUsername(String username) {
		sql = "SELECT * FROM tbl_like WHERE username=?";
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return Optional.of(getValueFromResultSet(resultSet));
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}