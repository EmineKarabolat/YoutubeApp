package com.eminekarabolat.repository;

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

public class VideoRepository implements ICrud<Video> {
	private String sql;
	private final ConnectionProvider connectionProvider;
	
	public VideoRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	@Override
	public Optional<Video> save(Video video) {
		sql = "INSERT INTO tbl_video(userid,title,description) VALUES (?,?,?)";
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setLong(1, video.getUserId());
			preparedStatement.setString(2, video.getTitle());
			preparedStatement.setString(3, video.getDescription());
			
			
			preparedStatement.executeUpdate();
			
		}
		catch (SQLException e) {
			System.err.println("Repository: Video kaydedilirken hata oluştu. " + e.getMessage());
		}
		
		return Optional.ofNullable(video);
	}
	
	@Override
	public Optional<Video> update (Video video) {
		
		sql = "UPDATE tbl_video SET title = ?, description = ?  WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setString(1,video.getTitle());
			preparedStatement.setString(2, video.getDescription());
			preparedStatement.setLong(3, video.getId());
			int updatedRows = preparedStatement.executeUpdate();
			if (updatedRows == 0) {
				System.err.println("Repository: Video güncellenirken hata oluştu. Güncelleme Başarırız");
			}
			
		}
		catch (SQLException e) {
			System.err.println("Repository: Video güncellenirken hata oluştu."+ e.getMessage());
		}
		return Optional.ofNullable(video);
	}
	
	@Override
	public void delete(Long id) {
		sql="DELETE FROM tbl_video WHERE id=?";
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
			
		}
		catch (SQLException e) {
			System.err.println("Repository: Video güncellenirken hata oluştu."+ e.getMessage());
		}
	
	}
	
	@Override
	public List<Video> findAll() {
		sql = "SELECT * FROM tbl_video ORDER BY id DESC";
		List<Video> videoList = new ArrayList<>();
		try {PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();{
				while (resultSet.next()) {
					videoList.add(getValueFromResultSet(resultSet));
				}
			}
		}
		catch (SQLException e) {
			System.err.println(("Video Listesi getirirken bir sorun yaşandı..." + e.getMessage()));
		}
		return videoList;
	}
	
	@Override
	public Optional<Video> findById(Long id) {
		sql = "SELECT * FROM tbl_video WHERE id=?";
		Video video = null;
		try {
			PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return Optional.of(getValueFromResultSet(resultSet));
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Video bulunurken hata oluştu:"+ e.getMessage());
		}
		return Optional.ofNullable(video);
	}
	
	private Video getValueFromResultSet(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		Long userId = rs.getLong("userid");
		String title = rs.getString("title");
		String description = rs.getString("description");
		Integer state = rs.getInt("state");
		Long createat = rs.getLong("createat");
		Long updateat= rs.getLong("updateat");
		
		return new Video(id, userId,title,description, state, createat, updateat);
	}
	
	public Optional<Video> findByTitle(String videoTitle) {
		sql = "SELECT * FROM tbl_video WHERE title = ?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, videoTitle);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(getValueFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return Optional.empty();
	}
	public List<Video> findByUserId(Long userId) {
		sql = "SELECT * FROM tbl_video WHERE userid = ?";
		List<Video> videoList = new ArrayList<>();
		
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, userId); // Kullanıcı ID'sini sorguya ekle
			
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					videoList.add(getValueFromResultSet(resultSet)); // Sonuç kümesindeki her kaydı listeye ekle
				}
			}
		} catch (SQLException e) {
			System.out.println("Kullanıcıya ait postlar getirilirken bir hata oluştu... " + e.getMessage());
			throw new RuntimeException(e); // Hata durumunda exception fırlatıyoruz
		}
		
		return videoList;
	}
}