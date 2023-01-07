package pl.edu.pw.mini.projektZPOIF.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.projektZPOIF.DTO.RequestTCP;
import pl.edu.pw.mini.projektZPOIF.Utils.ColorUtils;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;


@Service
@Slf4j
public class FFTService {

    final TcpService tcpService;
    final ObjectMapper objectMapper;

    @Autowired
    public FFTService(TcpService tcpService, ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
        this.tcpService = tcpService;
    }


    public class PlayThread extends Thread
    {
        final String serial;
        final String url;

        private ServerSocket serverSocket;
        private Socket clientSocket;
        private PrintWriter out;

        private int port;

        public PlayThread(String serial,String url)
        {
            this.serial = serial;
            this.url = url;
        }

        public void run()
        {
            try {
                openSocketTCP();
                Thread.sleep(1000);

                thread_func();
                closeSocketTCP();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        private void closeSocketTCP() {
            tcpService.setMusic(serial,port,false);
            out.close();
        }

        private void openSocketTCP() throws IOException {
            serverSocket = new ServerSocket(0);
            port = serverSocket.getLocalPort();
            //System.out.println(port);
            tcpService.setMusic(serial,port,true);
            clientSocket = serverSocket.accept();
            clientSocket.setTcpNoDelay(true);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        void thread_func()
        {
            log.info("Starting music mode!");
            AudioInputStream din;
            FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
            try {
                AudioInputStream in = AudioSystem.getAudioInputStream(new URL(url));
                AudioFormat baseFormat = in.getFormat();
                AudioFormat decodedFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        baseFormat.getSampleRate(), 16, 1,
                        2, baseFormat.getSampleRate(),
                        true);
                din = AudioSystem.getAudioInputStream(decodedFormat, in);
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                if(line != null) {
                    line.open(decodedFormat);

                    byte[] data = new byte[16384];
                    // Start
                    line.start();
                    int nBytesRead;
                    while ((nBytesRead = din.read(data, 0, data.length)) != -1) {
                        line.write(data, 0, nBytesRead);
                        double[] values= new double[data.length/2];
                        ByteBuffer bb = ByteBuffer.wrap(data);
                        for(int i = 0; i < values.length; i++) values[i] = bb.getShort();
                        Complex[] fftData = fft.transform(values, TransformType.FORWARD);
                        var res = maxFreq(fftData,0.00000005);
                        send(res);
                    }
                    // Stop
                    line.drain();
                    line.stop();
                    line.close();
                    din.close();
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        private void send(Triple<Integer, Integer, Integer> res) {
            RequestTCP requestTCP1 = new RequestTCP(
                    1,
                    "set_rgb",
                    ColorUtils.toRgb(res.getLeft(), res.getMiddle(), res.getRight()),
                    "sudden",0);
            RequestTCP requestTCP2 = new RequestTCP(
                    1,
                    "set_bright",
                    (int)((res.getLeft()+res.getMiddle()+res.getRight())*0.13),
                    "sudden",0);
            try
            {
                String json,json2;
                json = objectMapper.writeValueAsString(requestTCP1);
                json2 = objectMapper.writeValueAsString(requestTCP2);
                out.write(json+"\r\n"+json2+"\r\n");
                out.flush();
            }
            catch (JsonProcessingException e)
            {
                e.printStackTrace();
            }
        }
    }


    public void play(String serial,String url)
    {
        PlayThread thread = new PlayThread(serial, url);
        thread.start();
    }

    public static Triple<Integer, Integer,Integer> maxFreq(Complex[] fftData, double scale_factor) {
        // Get the number of samples and the sample rate
        int numSamples = fftData.length;
        double sampleRate = 44100; // assuming a sample rate of 44.1 kHz
        int numSamples2 = numSamples/2;
        // Calculate the frequencies of each sample
        double[] frequencies = new double[numSamples2];
        for (int i = 0; i < numSamples2; i++) {
            frequencies[i] = i * sampleRate / numSamples;
        }

        // Calculate the magnitudes of each sample
        double[] magnitudes = new double[numSamples2];
        for (int i = 0; i < numSamples2; i++) {
            magnitudes[i] = fftData[i].abs();
        }

        double bass = 0, middle= 0, sopran= 0;
        for (int i = 0; i < numSamples2; i++)
        {
            if(frequencies[i] < 300) bass += magnitudes[i];
            else if(frequencies[i] < 3000) middle += magnitudes[i];
            else sopran += magnitudes[i];
        }

        int ibass,imiddle,isopran;

        ibass = Math.min(255, (int) (bass*scale_factor));
        imiddle = Math.min(255, (int) (middle*scale_factor));
        isopran = Math.min(255, (int) (sopran*scale_factor));

        return new ImmutableTriple<>(ibass,imiddle,isopran);
    }



}