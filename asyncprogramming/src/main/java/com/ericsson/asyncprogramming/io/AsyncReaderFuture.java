package com.ericsson.asyncprogramming.io;

import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Future;

public class AsyncReaderFuture {
  public static void main(String[] args) throws Exception {
    Path path = Paths.get("test.txt");

    try (AsynchronousFileChannel afc = AsynchronousFileChannel.open(path, READ)) {
      int fileSize = (int) afc.size();
      ByteBuffer dataBuffer = ByteBuffer.allocate(fileSize);

      Future<Integer> result = afc.read(dataBuffer, 0);
      int readBytes = result.get();

      System.out.format("%s bytes read   from  %s%n", readBytes, path);
      System.out.format("Read data is:%n");

      byte[] byteData = dataBuffer.array();
      Charset cs = Charset.forName("UTF-8");
      String data = new String(byteData, cs);

      System.out.println(data);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
