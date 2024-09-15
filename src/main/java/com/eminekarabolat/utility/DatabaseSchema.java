package com.eminekarabolat.utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Veritabanı şemasını oluşturmak için kullanılan sınıftır.
 * Bu sınıf, gerekli tabloları veritabanında oluşturur.
 * "FOREIGN KEY (takim_id) REFERENCES tbl_takim(id) ON DELETE SET NULL" + // ON DELETE SET NULL,
 *                 // PostgreSQL'de referanslı kayıt silindiğinde ilişkili alanı NULL yapar.
 */
public class DatabaseSchema {

    /**
     * Tabloları oluşturur.
     */
    public static void createTables() {
        // "user" tablosu için SQL sorgusu
        String createUserTable = "CREATE TABLE IF NOT EXISTS tbl_user (" +
                "id BIGSERIAL PRIMARY KEY, " + // PostgreSQL'de BIGSERIAL kullanılır
                "name VARCHAR(255) , " +
                "surname VARCHAR(255) , " +
                "email VARCHAR(255) UNIQUE , " +
                "username VARCHAR(30) UNIQUE , " +
                "password VARCHAR(16)  , " +
                "state INTEGER NOT NULL DEFAULT 1 , " +
                "createat BIGINT DEFAULT EXTRACT(epoch FROM now()), "+
                "updateat BIGINT DEFAULT EXTRACT(epoch FROM now()) "+
                
                ");";
        
        // "video" tablosu için SQL sorgusu
        
        String createVideoTable = "CREATE TABLE IF NOT EXISTS tbl_video (" +
                "id BIGSERIAL PRIMARY KEY, " + // PostgreSQL'de BIGSERIAL kullanılır
                "userId BIGINT NOT NULL REFERENCES tbl_user(id), " +
                "title VARCHAR(255) NOT NULL, " +
                "description VARCHAR(255) , " +
                "state INTEGER NOT NULL DEFAULT 1 , " +
                "createat BIGINT DEFAULT EXTRACT(epoch FROM now()), "+
                "updateat BIGINT DEFAULT EXTRACT(epoch FROM now()) "+
                ");";
        
        
        // "like" tablosu için SQL sorgusu
        String createLikeTable = "CREATE TABLE IF NOT EXISTS tbl_like (" +
                "id BIGSERIAL PRIMARY KEY, " + // PostgreSQL'de BIGSERIAL kullanılır
                "userId BIGINT NOT NULL REFERENCES tbl_user(id), " +
                "videoId BIGINT NOT NULL REFERENCES tbl_video(id), " +
                "status INTEGER NOT NULL DEFAULT 0, " +
                "state INTEGER NOT NULL DEFAULT 1 , " +
                "createat BIGINT DEFAULT EXTRACT(epoch FROM now()), "+
                "updateat BIGINT DEFAULT EXTRACT(epoch FROM now()) "+
                ");";
        
        
        // "comment" tablosu için SQL sorgusu
        String createCommentTable = "CREATE TABLE IF NOT EXISTS tbl_comment (" +
                "id BIGSERIAL PRIMARY KEY, " + // PostgreSQL'de BIGSERIAL kullanılır
                "userId BIGINT NOT NULL REFERENCES tbl_user(id), " +
                "videoId BIGINT NOT NULL REFERENCES tbl_video(id), " +
                "status INTEGER NOT NULL DEFAULT 0, " +
                "state INTEGER NOT NULL DEFAULT 1 , " +
                "createat BIGINT DEFAULT EXTRACT(epoch FROM now()), "+
                "updateat BIGINT DEFAULT EXTRACT(epoch FROM now()) "+
                ");";
        
        
        
        
        // Veritabanı bağlantı sağlayıcısını try-with-resources içinde kullanıyoruz
        try {
            ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
            Connection connection = connectionProvider.getConnection(); // getConnection() metodu eklenmiş
            Statement statement = connection.createStatement();
            // Tablo oluşturma sorgularını çalıştırıyoruz
            statement.execute(createUserTable);
            statement.execute(createVideoTable);
            statement.execute(createLikeTable);
            statement.execute(createCommentTable);

            System.out.println("Tables created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}