package com.zxkj.hc.qspt.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.setu.framework.utils.ImageHelper;

public class QzUitl {
	public static byte[]  qzcl(BufferedImage image,byte[] qzImage,int width,int height) throws IOException {
		ByteArrayInputStream inputStream=new ByteArrayInputStream(qzImage);
		BufferedImage bi = ImageIO.read(inputStream );
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		ImageIO.write(qzcl(image, bi,width,height), "png", byteArrayOutputStream); 
		return byteArrayOutputStream.toByteArray();
	}
	public static byte[]  qzcl(BufferedImage image,byte[] qzImage,int width,int height,Object[][] pressTexts,int size) throws IOException {
		System.out.println("图片合成的文字信息：↓");
		System.out.println(pressTexts[0][0]);
		pressTexts[0][0] = pressTexts[0][0].toString();
		System.out.println(pressTexts[1][0]);

		ByteArrayInputStream inputStream=new ByteArrayInputStream(qzImage);
		BufferedImage bi = ImageIO.read(inputStream );
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		ImageIO.write(qzcl(pressTexts,image, bi,width,height, size), "png", byteArrayOutputStream); 
		return byteArrayOutputStream.toByteArray();
	}
	public static BufferedImage  qzcl(BufferedImage image,BufferedImage qzImage,int width,int height) throws IOException {
		return ImageHelper.watermark(image, ImageHelper.scaleWidth(ImageHelper.alphaProcessAndRemoveWhiteBorder(ImageHelper.rotate(qzImage,270)), 200),width,height , 1) ;
	}
	public static BufferedImage  qzcl(Object[][] pressTexts, BufferedImage image,BufferedImage qzImage,int width,int height,int size) throws IOException {
		return ImageHelper.pressText(pressTexts, "宋体", Font.PLAIN, Color.BLACK, 70,1f, ImageHelper.watermark(image, ImageHelper.scaleWidth(ImageHelper.alphaProcessAndRemoveWhiteBorder(ImageHelper.rotate(qzImage,270)), size),width,height , 1)) ;
	}
	public static void main(String[] args) throws IOException {
//        ImageIO.write(QzUitl.qzcl(new Object[][] {new Object[] {"511502199705145018",1300,1300},},ImageIO.read(new File("C:/Users/asus/Desktop/test/book1.jpg")), ImageIO.read(new File("C:/Users/asus/Desktop/test/sign.jpg")),1820, 1530, 300), "png", new File("C:/Users/asus/Desktop/test/book11.jpg"));
		ImageIO.write(QzUitl.qzcl(new Object[][] {new Object[] {"511502199705145018",1300,1300},},ImageIO.read(new File("E:/hysms.jpg")), ImageIO.read(new File("E:/hysms.jpg")),1820, 1530, 300), "png", new File("E:/hysms1.jpg"));
//		ImageIO.write(QzUitl.qzcl(ImageIO.read(new File("C:/Users/asus/Desktop/test/book1.jpg")), ImageIO.read(new File("C:/Users/asus/Desktop/test/sign.jpg")),420, 500), "png", new File("C:/Users/asus/Desktop/test/book11.jpg"));
		System.out.println("操作成功！");
	}
}
