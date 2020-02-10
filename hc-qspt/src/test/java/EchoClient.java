import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {

    private final Socket mSocket;

    public EchoClient(String host, int port) throws IOException {
        // 创建 socket 并连接服务器
        mSocket = new Socket(host, port);
        System.out.println("服务连接成功！");
    }
    public void run() throws IOException {
        // 和服务端进行通信
        Thread readerThread = new Thread(this::readResponse);
        readerThread.start();
        OutputStream out = mSocket.getOutputStream();
        byte[] buffer = new byte[1024];
        String message = "00000532010123010033123010034M03421    00000500999  newgm       <?xml version=\"1.0\" encoding=\"GB18030\"?><root><req><prcscd>M03421</prcscd><brh_no>0500001</brh_no><opr_no>500999</opr_no><abst_cd>M34</abst_cd><mid_item>11</mid_item><mid_pay_type>1</mid_pay_type><jyje>6279.61</jyje><vou1_acc_no>6228970010110251507</vou1_acc_no><dwmc>name</dwmc><zsjgdm>15204020000</zsjgdm><nsrbm>520103196409112010</nsrbm><wbsbdzxh>352046190100008404</wbsbdzxh><qydm>520103196409112010</qydm><jydm>banktaxpay</jydm><transcode>mid_pay</transcode></req></root>\n";
        buffer = message.getBytes();
        int n;
        while ((n = System.in.read(buffer)) > 0) {
            out.write(buffer);
        }
    }
    private void readResponse() {
        try {
            InputStream in = mSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) > 0) {
                System.out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] argv) {
        try {
            //传入服务器ip地址和端口
            EchoClient client = new EchoClient("172.32.5.150", 50001);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}