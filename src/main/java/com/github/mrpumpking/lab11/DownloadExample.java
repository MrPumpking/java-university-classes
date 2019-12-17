package com.github.mrpumpking.lab11;

import com.github.mrpumpking.lab7.ExecutionTimer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadExample {

  static String[] toDownload = {
    "http://home.agh.edu.pl/pszwed/wyklad-c/01-jezyk-c-intro.pdf",
    "http://home.agh.edu.pl/~pszwed/wyklad-c/02-jezyk-c-podstawy-skladni.pdf",
    "http://home.agh.edu.pl/~pszwed/wyklad-c/03-jezyk-c-instrukcje.pdf",
    "http://home.agh.edu.pl/~pszwed/wyklad-c/04-jezyk-c-funkcje.pdf",
    "http://home.agh.edu.pl/~pszwed/wyklad-c/05-jezyk-c-deklaracje-typy.pdf",
    //    "http://home.agh.edu.pl/~pszwed/wyklad-c/06-jezyk-c-wskazniki.pdf",
    //    "http://home.agh.edu.pl/~pszwed/wyklad-c/07-jezyk-c-operatory.pdf",
    //    "http://home.agh.edu.pl/~pszwed/wyklad-c/08-jezyk-c-lancuchy-znakow.pdf",
    //    "http://home.agh.edu.pl/~pszwed/wyklad-c/09-jezyk-c-struktura-programow.pdf",
    //    "http://home.agh.edu.pl/~pszwed/wyklad-c/10-jezyk-c-dynamiczna-alokacja-pamieci.pdf",
    //    "http://home.agh.edu.pl/~pszwed/wyklad-c/11-jezyk-c-biblioteka-we-wy.pdf",
    //    "http://home.agh.edu.pl/~pszwed/wyklad-c/preprocesor-make-funkcje-biblioteczne.pdf",
  };

  static AtomicInteger counter = new AtomicInteger(0);
  static Semaphore semaphore = new Semaphore(0);

  public static void main(String[] args) throws InterruptedException {
    System.out.println("==> Starting sequential download...");
    sequentialDownload();

    System.out.println("==> Starting concurrent download v1");
    concurrentDownloadV1();

    System.out.println("==> Starting concurrent download v2");
    concurrentDownloadV2();

    System.out.println("==> Starting concurrent download v3");
    concurrentDownloadV3();
  }

  static void sequentialDownload() {
    ExecutionTimer timer = new ExecutionTimer();
    timer.start();

    for (String url : toDownload) {
      new Downloader(url, 0).run();
    }

    timer.stop();
    System.out.println("==> Sequential download time: " + timer.getExecutionTime());
  }

  static void concurrentDownloadV1() {
    ExecutionTimer timer = new ExecutionTimer();
    timer.start();

    for (String url : toDownload) {
      new Thread(new Downloader(url, 0)).start();
    }

    timer.stop();
    System.out.println("==> Concurrent download v1 time: " + timer.getExecutionTime());
  }

  static void concurrentDownloadV2() {
    ExecutionTimer timer = new ExecutionTimer();
    timer.start();

    for (String url : toDownload) {
      new Thread(new Downloader(url, 1)).start();
    }

    while (counter.get() != toDownload.length) {
      Thread.yield();
    }

    timer.stop();
    counter.set(0);
    System.out.println("==> Concurrent download v2 time: " + timer.getExecutionTime());
  }

  static void concurrentDownloadV3() throws InterruptedException {
    ExecutionTimer timer = new ExecutionTimer();
    timer.start();

    for (String url : toDownload) {
      new Thread(new Downloader(url, 2)).start();
    }

    semaphore.acquire(toDownload.length);

    timer.stop();
    System.out.println("==> Concurrent download v3 time: " + timer.getExecutionTime());
  }

  static class Downloader implements Runnable {
    private final int type;
    private final String url;

    /**
     * @param url - file to download
     * @param type - 0 - sequential, 1 - atomic lock, 2 - semaphore
     */
    Downloader(String url, int type) {
      this.url = url;
      this.type = type;
    }

    public void run() {
      int data;
      String fileName = getFileNameFromURL(url);

      try (InputStream in = new URL(url).openStream();
          FileOutputStream out = new FileOutputStream(fileName)) {

        while ((data = in.read()) != -1) {
          out.write(data);
        }

      } catch (IOException e) {
        e.printStackTrace();
      }

      if (type == 1) {
        counter.incrementAndGet();
      } else if (type == 2) {
        semaphore.release();
      }

      System.out.println(" -> [" + type + "] Done: " + fileName);
    }

    private String getFileNameFromURL(String url) {
      return url.substring(url.lastIndexOf('/') + 1, url.length());
    }
  }
}
