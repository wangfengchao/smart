package com.smart.img;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fc.w on 2017/8/30.
 */
public class DownLoadImg {

    //方式一：直接根据url读取图片
    private static BufferedImage read(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        BufferedImage image = ImageIO.read(url);
        return image;
    }

    private static void save(BufferedImage image, String destImageUrl)
            throws IOException {
        File imageFile = new File(destImageUrl);
        FileOutputStream outStream = new FileOutputStream(imageFile);

        ImageIO.write(image, "jpg", outStream);
        ImageIO.write(image, "gif", outStream);
        ImageIO.write(image, "png", outStream);
        ImageIO.write(image, "jpeg", outStream);
    }

    public static void main(String[] args) {
        Connection conn = JDBCHelper.getConn();
        String sql = "SELECT imgKey from house_image where `status` = 1 ORDER BY id desc limit 100";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println("");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
