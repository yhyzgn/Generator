package com.yhy.generator.helper;

import com.yhy.generator.core.file.abs.AbsFile;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-12-28 12:58
 * version: 1.0.0
 * desc   :
 */
@SuppressWarnings("ALL")
public class FileWriter<T extends AbsFile> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileWriter.class);

    private Generator<T> generator;
    private T dataFile;
    private OnWriteListener listener;

    public FileWriter(Generator<T> generator, T dataFile) {
        this.generator = generator;
        this.dataFile = dataFile;
    }

    public void write() {
        write(null);
    }

    public void write(OutputStream os) {
        write(os, null);
    }

    public void write(OutputStream os, OnWriteListener listener) {
        if (null == dataFile) {
            return;
        }

        if (null == os) {
            File targetFile = getTargetFile();
            try {
                os = new BufferedOutputStream(new FileOutputStream(targetFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        this.listener = listener;

        if (os instanceof PrintStream) {
            ((PrintStream) os).print(dataFile.toString());
            if (null != listener) {
                listener.onFinish(dataFile);
            }
        } else {
            try {
                byte[] content = dataFile.toString().getBytes("UTF-8");
                if (!(os instanceof BufferedOutputStream)) {
                    os = new BufferedOutputStream(os);
                }
                os.write(content, 0, content.length);
                os.flush();

                LOGGER.info("Generator code file finished !");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(os);
                if (null != listener) {
                    listener.onFinish(dataFile);
                }
            }
        }
    }

    private File getTargetFile() {
        String path = generator.getRoot() + generator.getDir();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = generator.getModelFileName();
        switch (generator.getFileType()) {
            case MODEL:
                filename = generator.getModelFileName();
                LOGGER.info("Current file is model file !");
                break;
            case MAPPER_JAVA:
                filename = generator.getMapperFileName();
                LOGGER.info("Current file is mapper java file !");
                break;
            case MAPPER_XML:
                filename = generator.getMapperXmlFileName();
                LOGGER.info("Current file is mapper xml file !");
                break;
            case SERVICE:
                filename = generator.getServiceFileName();
                LOGGER.info("Current file is service file !");
                break;
            case SERVICE_IMPL:
                filename = generator.getServiceImplFileName();
                LOGGER.info("Current file is service impl file !");
                break;
        }
        file = new File(path, filename);
        return file;
    }

    public void setOnWriteListener(OnWriteListener listener) {
        this.listener = listener;
    }

    public interface OnWriteListener<T extends AbsFile> {
        void onFinish(T dataFile);
    }
}
