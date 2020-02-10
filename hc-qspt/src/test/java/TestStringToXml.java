
import java.io.*;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class TestStringToXml {
    public static void main(String[] args) throws Exception {
        String xmlString = "<?xml version=\"1.0\" encoding=\"GB18030\"?><root><req><prcscd>M03421</prcscd><brh_no>0500001</brh_no><opr_no>500999</opr_no><abst_cd>M34</abst_cd><mid_item>11</mid_item><mid_pay_type>1</mid_pay_type><jyje>6279.61</jyje><vou1_acc_no>6228970010110251507</vou1_acc_no><dwmc>name</dwmc><zsjgdm>15204020000</zsjgdm><nsrbm>520103196409112010</nsrbm><wbsbdzxh>352046190100008404</wbsbdzxh><qydm>520103196409112010</qydm><jydm>banktaxpay</jydm><transcode>mid_pay</transcode></req></root>\n";
        InputStream xmlInputStream = getXmlInputStream(xmlString);
        Element rootElement = getRootElementFromIs(xmlInputStream);
        parseElementFromRoot(rootElement);

    }
    //1-获取XML-IO流
    private static InputStream  getXmlInputStream(String xmlString){
        InputStream inputStream = null;
        try {
            //1-把要解析的 XML 文档转化为输入流，以便 DOM 解析器解析它
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlString.getBytes());
             inputStream=byteArrayInputStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    //2-解析XML-IO流 ，获取Document 对象，以及Document对象 的根节点
    private static Element getRootElementFromIs(InputStream inputStream) throws Exception {
        if(inputStream == null){
            return  null;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc =  docBuilder.parse(inputStream);
        Element root = doc.getDocumentElement();
        if(inputStream != null){
            inputStream.close();
        }
        return root;
    }
    //3-从根元素解析得到元素
    private static void parseElementFromRoot(Element root) {
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                getDataFromElement(ele);
                getCertainElementFromParentElement(ele);
            }
        }
    }

    //4-从元素解析得到属性值
    private static void getDataFromElement(Element ele) {
        String name = ele.getAttribute("prcscd");//根据属性名称读取属性值
        System.out.println("name == " + name);
        String className = ele.getAttribute("class");
        System.out.println("className == " + className);
    }
    //5-从元素解析特定子元素并解析(以property为例)
    private static void getCertainElementFromParentElement(Element ele) {
        NodeList propertyEleList = ele.getElementsByTagName("req");//根据标签名称获取标签元素列表
        for (int i = 0; i < propertyEleList.getLength(); i++) {
            Node node = propertyEleList.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("prcscd");
                System.out.println("propertyEle: name == " + name);
                String value = propertyEle.getAttribute("value");
                System.out.println("propertyEle: value == " + value);
            }
        }

    }

}
