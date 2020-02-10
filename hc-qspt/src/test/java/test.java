//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class test {
//    FileOutputStream out;
//    public void main(String[] args) throws Exception{
//        out = new FileOutputStream("D:\\Demo\\春.pdf");
//         Rectangle tRectangle = new Rectangle(PageSize.A4); // 页面大小 
//         tRectangle.setBackgroundColor(BaseColor.WHITE); // 页面背景色 
//         tRectangle.setBorder(1220);// 边框 
//         tRectangle.setBorderColor(BaseColor.BLUE);// 边框颜色 
//         tRectangle.setBorderWidth(244.2f);// 边框宽度 
//         Document doc = new Document(tRectangle);// 定义文档  
//         doc = new Document(tRectangle.rotate());// 横向打印 
//         PdfWriter writer = PdfWriter.getInstance(doc, out);// 书写器 
//         writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);//版本(默认1.4) 
//         //设置PDF文档属性 
//         doc.addTitle("春");// 标题 
//         doc.addAuthor("朱自清");// 作者 
//         doc.addSubject("春天的脚步近了");// 主题 
//         doc.addKeywords("春");// 关键字 
//         doc.addCreator("田野上的风筝");// 创建者 
//         Paragraph tParagraph = new Paragraph("春", getChineseFont());
//         tParagraph.setAlignment(Element.ALIGN_JUSTIFIED);// 对齐方式 
//        Paragraph tParagraphs = new Paragraph("盼望着，盼望着，东风来了春天的脚步近了。一切都像刚睡醒的样子，欣欣然张开了眼。山朗润起来了，水涨起来了，太阳的脸红起来了", getChineseFont());
//        tParagraph.setAlignment(Element.ALIGN_CENTER);// 居中
//        tParagraph.setIndentationLeft(12);// 左缩进 
//        tParagraph.setIndentationRight(12);// 右缩进 
//        tParagraph.setFirstLineIndent(24);// 首行缩进 
//        tParagraph.setLeading(20f);// 行间距 
//        tParagraph.setSpacingBefore(5f);// 设置上空白 
//        tParagraph.setSpacingAfter(10f);// 设置段落下空白
//        doc.setMargins(10, 20, 30, 40);// 页边空白 
//        Image img = Image.getInstance("D:\\Demo\\spring.png");
//        img.setAlignment(Image.MIDDLE);  //设置图片居中
//        img.setBorder(Image.BOX);
//        img.setBorderWidth(10);
//        img.setBorderColor(BaseColor.WHITE);
//        img.scaleToFit(900, 350);// 设置图片大小 
//        doc.open();// 打开文档 
//        ///  doc.add(tRectangle);
//        doc.add(tParagraph); //添加段落
//        doc.add(tParagraphs);
//        doc.add(img); //添加img
//      //  doc.newPage();    //添加下一页 
//      //  writer.setPageEmpty(true);// fasle-显示空内容的页;true-不会显示 
//       doc.close();  //记得关闭document
//    }
//    public Font getChineseFont() {
//          BaseFont simpChinese;
//          Font ChineseFont = null;
//          try {
//          simpChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//          ChineseFont = new Font(simpChinese, 12, Font.NORMAL, BaseColor.BLUE);
//          } catch (DocumentException e) {
//         e.printStackTrace();
//          } catch (IOException e) {
//          e.printStackTrace();
//          }
//          return ChineseFont;
//
//   }
//}
//
//
