package cn.mingyuan.niodemos;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author jiangmingyuan@myhaowai.com
 * @version 2016/12/23 16:06
 * @since jdk1.8
 */
public class ChannelAndBufferTest {

    private RandomAccessFile getRandomAccessFile() throws FileNotFoundException {
        String filePath = "data/" + this.getClass().getSimpleName() + ".txt";
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        return file;
    }

    @Test
    public void testChannelAndBuffer() throws IOException, InterruptedException {
        String filePath = "data/" + this.getClass().getSimpleName() + ".txt";
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        int read = channel.read(byteBuffer);
        while (read != -1) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                byte b = byteBuffer.get();
                if (b == '\r' || b == '\n') {
                    System.out.println("-");
                } else {
                    System.out.println("-->" + (char) b + "->" + b);
                }
                byteBuffer.reset();
            }
            read = channel.read(byteBuffer);
            System.out.println("----------------");
        }
        channel.close();
        file.close();
    }

    @Test
    public void testWriteToChannel() throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = getRandomAccessFile();
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("aaa".getBytes());
        buffer.flip();
        int write = channel.write(buffer);


        int read = channel.read(buffer);
        while (read != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println("->"+(char) buffer.get());
            }

            buffer.clear();
            read=channel.read(buffer);
        }
        channel.close();
        randomAccessFile.close();
    }
}
