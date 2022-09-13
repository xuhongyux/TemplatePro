package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuhongyu
 * @create 2022-08-03 11:14 上午
 */
public class MergeOnePersonDataAndWriteFile {
    public static void main(String[] args) {
        List<String> listFiles = new ArrayList<String>();


        String filePath1="/private/var/folders/fx/gt3q0lt92d1f5rbzb___cq8r0000gn/T/7297cd70b2fdcfc6233d774622e0f94c/1.csv";
        String filePath2="/private/var/folders/fx/gt3q0lt92d1f5rbzb___cq8r0000gn/T/7297cd70b2fdcfc6233d774622e0f94c/2.csv";


        listFiles.add(filePath1);
        listFiles.add(filePath2);

        String outputFile = "/Users/临时文件/sp/csv/合并.csv";

        try {
            readAndWriteFileData(listFiles, outputFile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void readAndWriteFileData(List<String> listFiles,String outputFilePath) throws Exception{

        File outputFile = new File(outputFilePath);

        File outputFileParentFolder = new File(outputFile.getParent());
        if(!outputFileParentFolder.exists()) {
            outputFileParentFolder.mkdirs();
        }
        FileWriter fileWriterObj = new FileWriter(outputFile);
        BufferedWriter bufferedWriterObj = new BufferedWriter(fileWriterObj);

        for(int k=0;k<listFiles.size();k++) {
            String indexFilePath = listFiles.get(k);

            File oldFile = new File(indexFilePath);
            FileReader fileReaderObj = new FileReader(oldFile);
            BufferedReader bufferedReaderObj = new BufferedReader(fileReaderObj);

            String lineStr="";
            while((lineStr=bufferedReaderObj.readLine())!=null) {
                if(lineStr !="") {
                    bufferedWriterObj.write(lineStr+"\n");
                }
            }
            bufferedWriterObj.flush();
            bufferedReaderObj.close();
            fileReaderObj.close();
        }
        bufferedWriterObj.flush();
        bufferedWriterObj.close();
        fileWriterObj.close();
    }
}
