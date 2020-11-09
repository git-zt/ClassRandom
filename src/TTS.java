import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class TTS {
    TTS(String songName) throws IOException, Exception{
        URL url = new URL("http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&spd=5&text=abc");
        //baidu1:http://tts.baidu.com/text2audio?cuid=baiduid&lan=zh&ctp=1&pdt=311&tex=abc
        //baidu2:http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&spd=5&text=abc
        //教书先生：https://api.oioweb.cn/api/tts.php?txt=Madfaq
        URLConnection con = null;
        try{
            con = url.openConnection();
        }catch(IOException e){
            e.printStackTrace();
        }

        BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
        Bitstream bt = new Bitstream(bis);

        //获取mp3时间长度
        Header header = bt.readFrame();
        int mp3Length = con.getContentLength();
        int time = (int) header.total_ms(mp3Length);
        System.out.println(time / 1000);

        Player player = new Player(bis);
        player.play();
    }

    public static void main(String[] args) throws Exception{
        TTS mp3 = new TTS("贝加尔湖畔");
    }
}