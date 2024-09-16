package com.eminekarabolat.repository;

import com.eminekarabolat.entity.Comment;
import com.eminekarabolat.utility.ConnectionProvider;
import com.eminekarabolat.utility.ICrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentRepository implements ICrud<Comment> {
	private String sql;
	private final ConnectionProvider connectionProvider;
	
	public CommentRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	@Override
	public Optional<Comment> save(Comment comment) {
		sql = "INSERT INTO tbl_comment (userid, videoid,commenttext) VALUES(?, ?,?)";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
		) {
			preparedStatement.setLong(1, comment.getUserId());
			preparedStatement.setLong(2, comment.getVideoId());
			preparedStatement.setString(3, comment.getCommentText());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(comment);
	}
	
	@Override
	public Optional<Comment> update(Comment comment) {
		String sql = "UPDATE tbl_comment SET commentText = ?, status = ? WHERE id = ?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, comment.getCommentText());
			preparedStatement.setInt(2, comment.getStatus());
			preparedStatement.setLong(3, comment.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Yorum güncellenirken bir hata oluştu: " + e.getMessage());
		}
		return Optional.ofNullable(comment);
	}
	
	@Override
	public void delete(Long id) {
		sql = "DELETE FROM tbl_comment WHERE id = ?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Comment> findAll() {
		sql = "SELECT * FROM tbl_like";
		List<Comment> commentList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql);
		     ResultSet resultSet = preparedStatement.executeQuery()) {
			
			while (resultSet.next()) {
				commentList.add(getValueFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return commentList;
	}
	
	@Override
	public Optional<Comment> findById(Long id) {
		sql = "SELECT * FROM tbl_comment WHERE id = ?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, id);
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
	
	private Comment getValueFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		Long userId = resultSet.getLong("userid");
		Long videoId = resultSet.getLong("videoid");
		Integer status = resultSet.getInt("status");
		String commentText = resultSet.getString("commenttext");
		Integer state = resultSet.getInt("state");
		Long createat = resultSet.getLong("createat");
		Long updateat= resultSet.getLong("updateat");
		
		return new Comment(id, userId, videoId,status,commentText ,state, createat, updateat);
	}
	
	public List<Comment> findByVideoId(Long videoId) {
		List<Comment> comments = new ArrayList<>();
		String sql = "SELECT * FROM tbl_comment WHERE videoid = ?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, videoId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					comments.add(getValueFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			System.out.println("Yorumlar alınırken hata oluştu: " + e.getMessage());
		}
		return comments;
	}
}