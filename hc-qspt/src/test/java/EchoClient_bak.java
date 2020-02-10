//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class EchoClient_bak {
//
//    public static void main(String[] argv)throws Exception {
//
//        FileOutputStream out;
//        String docName = "test";
//        out = new FileOutputStream("D:\\"+docName+".pdf");
//        Rectangle tRectangle = new Rectangle(PageSize.A4); // 页面大小 
//        tRectangle.setBackgroundColor(BaseColor.WHITE); // 页面背景色 
//        tRectangle.setBorder(1220);// 边框 
//        tRectangle.setBorderColor(BaseColor.BLUE);// 边框颜色 
//        tRectangle.setBorderWidth(244.2f);// 边框宽度 
//        Document doc = new Document(tRectangle);// 定义文档  
//        doc = new Document(tRectangle.rotate());// 横向打印 
//        PdfWriter writer = PdfWriter.getInstance(doc, out);// 书写器 
//        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);//版本(默认1.4) 
//        //设置PDF文档属性 
//        doc.addTitle("契税");// 标题 
//        Paragraph tParagraph = new Paragraph("契税", getChineseFont());
//        tParagraph.setAlignment(Element.ALIGN_JUSTIFIED);// 对齐方式 
//        //下载时间
//        Paragraph xzsj = new Paragraph("下载时间：2020-01-29 12:12:21", getChineseFontContent());
//        xzsj.setAlignment(Element.ALIGN_RIGHT);// 对齐方式 
//        Paragraph czdx = new Paragraph("申请人：XXX", getChineseFontContent());
//        czdx.setAlignment(Element.ALIGN_RIGHT);// 对齐方式 
//
//
//        StringBuffer xmstring = new StringBuffer();
//        xmstring.append("姓名：张三");
//        StringBuffer sfzstring = new StringBuffer();
//        sfzstring.append("身份证：500236199509222757");
//        //身份验证
//        Paragraph sfyz = new Paragraph("身份验证", getChineseFont());
//        sfyz.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
//        Paragraph xm = new Paragraph(xmstring.toString(), getChineseFontContent());
//        Paragraph sfz = new Paragraph(sfzstring.toString(), getChineseFontContent());
//        //婚姻状况
//        Paragraph hyzk = new Paragraph("婚姻状态", getChineseFont());
//        hyzk.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
//        StringBuffer hyzkstring = new StringBuffer();
//        hyzkstring.append("婚姻状况：已婚");
//        StringBuffer jhshstring = new StringBuffer();
//        jhshstring.append("结婚时间：2019-12-12");
//        Paragraph hyzkP = new Paragraph(hyzkstring.toString(), getChineseFontContent());
//        Paragraph jhshsP = new Paragraph(jhshstring.toString(), getChineseFontContent());
//        //户口簿信息
//        Paragraph hkbxx = new Paragraph("户口簿信息", getChineseFont());
//        hkbxx.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
//        StringBuffer hkbxmstring = new StringBuffer();
//        hkbxmstring.append("姓名：李四");
//        StringBuffer hkbsfzstring = new StringBuffer();
//        hkbsfzstring.append("身份证号：500236199409222757");
//        Paragraph hkbxmP = new Paragraph(hkbxmstring.toString(), getChineseFontContent());
//        Paragraph hkbsfzP = new Paragraph(hkbsfzstring.toString(), getChineseFontContent());
//        //购房发票
//        Paragraph gffp = new Paragraph("购房发票", getChineseFont());
//        gffp.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
//        StringBuffer xxdistring = new StringBuffer();
//        xxdistring.append("购买房屋的详细地址：四川省自贡市锦城花园232号");
//        StringBuffer djtfstring = new StringBuffer();
//        djtfstring.append("第几套房：2套");
//        StringBuffer hqfsstring = new StringBuffer();
//        hqfsstring.append("获取方式：倒签房");
//        StringBuffer fcsyzzhstring = new StringBuffer();
//        fcsyzzhstring.append("<<房产所有证>>证号：33334533342323545566");
//        Paragraph xxdiP = new Paragraph(xxdistring.toString(), getChineseFontContent());
//        Paragraph djtfP = new Paragraph(djtfstring.toString(), getChineseFontContent());
//        Paragraph hqfsP = new Paragraph(hqfsstring.toString(), getChineseFontContent());
//        Paragraph fcsyzzhP = new Paragraph(fcsyzzhstring.toString(), getChineseFontContent());
//        //购房合同
//        Paragraph gfht = new Paragraph("购房合同", getChineseFont());
//        gfht.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
//        StringBuffer zjlxstring = new StringBuffer();
//        zjlxstring.append("证件类型：购房合同");
//        StringBuffer fwjgstring = new StringBuffer();
//        fwjgstring.append("房屋价格：332323322元");
//        StringBuffer fwlxstring = new StringBuffer();
//        fwlxstring.append("房屋类型：住宅");
//        StringBuffer fwmjstring = new StringBuffer();
//        fwmjstring.append("房屋面积：3232平方米");
//        Paragraph zjlxP = new Paragraph(zjlxstring.toString(), getChineseFontContent());
//        Paragraph fwjgP = new Paragraph(fwjgstring.toString(), getChineseFontContent());
//        Paragraph fwlxP = new Paragraph(fwlxstring.toString(), getChineseFontContent());
//        Paragraph fwmjP = new Paragraph(fwmjstring.toString(), getChineseFontContent());
//        //授权书
//        Paragraph sqs = new Paragraph("授权书", getChineseFont());
//        sqs.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
//        //拆迁补偿
//        Paragraph qqbc = new Paragraph("拆迁补偿", getChineseFont());
//        qqbc.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
//        tParagraph.setAlignment(Element.ALIGN_CENTER);// 居中
//        tParagraph.setIndentationLeft(12);// 左缩进 
//        tParagraph.setIndentationRight(12);// 右缩进 
//        tParagraph.setFirstLineIndent(24);// 首行缩进 
//        tParagraph.setLeading(20f);// 行间距 
//        tParagraph.setSpacingBefore(5f);// 设置上空白 
//        tParagraph.setSpacingAfter(10f);// 设置段落下空白
//        doc.setMargins(10, 20, 30, 40);// 页边空白 
//        Image img = Image.getInstance("E:\\timg.jpg");
////        Image img = Image.getInstance(flowFileEntity.getFileContent());
//        img.setAlignment(Image.MIDDLE);  //设置图片居中
//        img.setBorder(Image.BOX);
//        img.setBorderWidth(10);
//        img.setBorderColor(BaseColor.WHITE);
//        img.scaleToFit(900, 350);// 设置图片大小 
//        doc.open();// 打开文档 
//        ///  doc.add(tRectangle);
//        doc.add(tParagraph); //添加段落
//        doc.add(xzsj);
//        doc.add(czdx);
//        //身份验证
//        doc.add(sfyz);
//        doc.add(xm);
//        doc.add(sfz);
//        doc.add(img); //添加img
//        doc.add(img); //添加img
//        doc.add(img); //添加img
//        doc.add(img); //添加img
//        //婚姻状况
//        doc.add(hyzk);
//        doc.add(hyzkP);
//        doc.add(jhshsP);
//
//
//        //户口簿信息
//        doc.add(hkbxx);
//        doc.add(hkbxmP);
//        doc.add(hkbsfzP);
//
//        //购房发票
//        doc.add(gffp);
//        doc.add(xxdiP);
//        doc.add(djtfP);
//        doc.add(hqfsP);
//        doc.add(fcsyzzhP);
//        //购房合同
//        doc.add(gfht);
//        doc.add(zjlxP);
//        doc.add(fwjgP);
//        doc.add(fwlxP);
//        doc.add(fwmjP);
//        //授权书
//        doc.add(sqs);
//
//        //拆迁补偿
//        doc.add(qqbc);
//
//
//        //  doc.newPage();    //添加下一页 
//        //  writer.setPageEmpty(true);// fasle-显示空内容的页;true-不会显示 
//        doc.close();  //记得关闭document
//    }
//    public static Font getChineseFont() {
//        BaseFont simpChinese;
//        Font ChineseFont = null;
//        try {
//            simpChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//            ChineseFont = new Font(simpChinese, 21, Font.NORMAL, BaseColor.BLACK);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ChineseFont;
//
//    }
//    //正文设置字体
//    public static Font getChineseFontContent() {
//        BaseFont simpChinese;
//        Font ChineseFont = null;
//        try {
//            simpChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//            ChineseFont = new Font(simpChinese, 12, Font.NORMAL, BaseColor.BLUE);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ChineseFont;
//
//    }
//}