package com.mzl.studentmanagesystem.util;

import java.io.File;

/**
 * @ClassName :   UploadUtil
 * @Description: 上传图片工具类
 * @Author: 21989
 * @CreateDate: 2020/7/30 9:39
 * @Version: 1.0
 */
public class UploadUtil {

    //项目根路径下的目录--SpringBoot 的static 目录相当于是根路径下（SpringBoot 默认）
    public final static String IMG_PATH_PREFIX = "static/upload/imgs";

    /**
     * 获取图片的文件目录
     * @return
     */
    public static File getImgDirFile(){
        //构建上传文件的存储的文件夹
        String fileDirPath = new String("src/main/resources/" + IMG_PATH_PREFIX);
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()){
            //递归生成文件夹·
            fileDir.mkdirs();
        }
        //返回生成的文件夹的路径
        return fileDir;
    }

}
