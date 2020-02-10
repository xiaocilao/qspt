package com.zxkj.hc.qspt.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ImgUtil {

    /**
     * 将图片压缩到指定大小以内
     *
     * @param srcImgData 源图片数据
     * @param maxSize 目的图片大小
     * @return 压缩后的图片数据
     */
    public static byte[] compressUnderSize(byte[] srcImgData, long maxSize) {
        byte[] imgData = Arrays.copyOf(srcImgData, srcImgData.length);

        if (imgData.length > maxSize) {
            do {
                double scale = Math.min(maxSize/(double)imgData.length,0.9);
                try {
                    imgData = compress(imgData, scale);

                } catch (IOException e) {
                    throw new IllegalStateException("压缩图片过程中出错，请及时联系管理员！", e);
                }

            } while (imgData.length > maxSize);
        }

        return imgData;
    }

    /**
     * 按照 宽高 比例压缩
     *
     * @param srcImgData 源图片数据
     * @param scale 压缩刻度
     * @return 压缩后图片数据
     * @throws IOException 压缩图片过程中出错
     */
    public static byte[] compress(byte[] srcImgData, double scale) throws IOException {
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(srcImgData));
        int width = (int) (bi.getWidth() * scale); // 源图宽度
        int height = (int) (bi.getHeight() * scale); // 源图高度
        System.out.println("源图片宽度："+width);
        System.out.println("源图片高度："+height);
        Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null); // 绘制处理后的图
        g.dispose();

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ImageIO.write(tag, "PNG", bOut);

        return bOut.toByteArray();
    }

}
