package org.qql.vigour.framework.study.designpatterns.singleton;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Hashtable;

public enum SequenceEnum {

	/**
	 * 日志操作对象
	 */
    INSTANCE;
    private static final Hashtable<String, MappedByteBuffer> SERIALS = new Hashtable<String, MappedByteBuffer>();
    private static final Hashtable<String, FileChannel> FCHANNELS = new Hashtable<String, FileChannel>();

    private SequenceEnum() {
    }


    /**
     * 获取序列文件
     * 
     * @param name
     * @return
     */
    public  long getSequence4File(String name) {
	String appKey = name + ".seq";
	FileChannel fc = null;
	MappedByteBuffer serial = null;
	RandomAccessFile RAFile = null;
	try {
	    fc = FCHANNELS.get(appKey);
	    serial = SERIALS.get(appKey);
	    if (serial == null) {
		RAFile = new RandomAccessFile(appKey, "rw");
		if (RAFile.length() < 8) {
		    RAFile.writeLong(0);
		}
		fc = RAFile.getChannel();

		int size = (int) fc.size();

		serial = fc.map(FileChannel.MapMode.READ_WRITE, 0, size);

		FCHANNELS.put(appKey, fc);
		SERIALS.put(appKey, serial);
	    }

	    FileLock flock = fc.lock();
	    serial.rewind();
	    long serno = serial.getLong();
	    serno++;
	    serial.flip();
	    serial.putLong(serno);
	    flock.release();

	    return serno;
	} catch (IOException e) {
	    return -1;
	}
    }

    /**
     * 格式化序列
     * 
     * @param seq
     * @param width
     * @return
     */
    public static String formatSequence(long seq, int width) {
	Long lg = new Long(seq);
	String is = lg.toString();
	if (is.length() < width) {
	    while (is.length() < width) {
		is = "0" + is;
	    }
	} else {
	    is = is.substring(is.length() - width, is.length());
	}
	return is;
    }

    /**
     * 测试序列Main方法
     * 
     * @param args
     */
    public static void main(String[] args) {
    	
    	for(int day=0;day<5;day++) {
    		new Thread() {
				public void run() {
					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(SequenceEnum.INSTANCE.formatSequence(SequenceEnum.INSTANCE.getSequence4File("wapPay"), 3));
					
				}
				
			}.start();
			
    	}
    }
}
