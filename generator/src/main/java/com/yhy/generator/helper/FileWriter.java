package com.yhy.generator.helper;

import com.yhy.generator.core.file.abs.AbsFile;
import com.yhy.generator.generator.base.Generator;
import com.yhy.generator.utils.IOUtils;

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

    private Generator<T> generator;
    private T dataFile;

    public FileWriter(Generator<T> generator, T dataFile) {
        this.generator = generator;
        this.dataFile = dataFile;
    }

    public void write() {
        this.dataFile = dataFile;

        File targetFile = getTargetFile();
        try {
            write(new BufferedOutputStream(new FileOutputStream(targetFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(OutputStream os) {
        if (null == os || null == dataFile) {
            return;
        }

        if (os instanceof PrintStream) {
            ((PrintStream) os).print(dataFile.toString());
        } else {
            byte[] content = dataFile.toString().getBytes();
            if (!(os instanceof BufferedOutputStream)) {
                os = new BufferedOutputStream(os);
            }
            try {
                os.write(content, 0, content.length);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(os);
            }
        }
    }

    private File getTargetFile() {
        String path = generator.getRoot() + generator.getBaseDir() + generator.getTableDir();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = generator.getModelFileName();
        switch (generator.getFileType()) {
            case MODEL:
                filename = generator.getModelFileName();
                break;
            case MAPPER_XML:
                filename = generator.getMapperXmlFileName();
                break;
            case MAPPER_JAVA:
                filename = generator.getMapperFileName();
                break;
            case SERVICE:
                filename = generator.getServiceFileName();
                break;
        }
        file = new File(path, filename);
        return file;
    }
}
