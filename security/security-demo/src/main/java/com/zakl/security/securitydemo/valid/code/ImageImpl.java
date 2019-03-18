package com.zakl.security.securitydemo.valid.code;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @program: security
 * @description: 生成图片的工具类
 * @author: Mr.Wang
 * @create: 2019-03-18 21:50
 **/
public class ImageImpl {

    private int w = 70; // 宽
    private int h = 35; // 高
    private Color bgColor = new Color(240, 240, 240);
    private Random random = new Random();

    public BufferedImage createImage() {
        /*
         * 创建图片
         * 设置背景色
         */
        // 创建图片
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // 设置画笔颜色
        img.getGraphics().setColor(bgColor);
        // 填充一个与图片一样大小的白色矩形，相当于设置图片背景色为白色
        img.getGraphics().fillRect(0, 0, w, h);
        return img;
    }

    // 生成随机颜色
    private Color randomColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    /*
     * 生成随机字体
     * 字体名，样式，字号
     */
    private String[] fontName = {"宋体", "华文行楷", "黑体", "华文新魏", "华文隶书", "微软雅黑", "楷体_GB2312"};
    private int[] fontSize = {24, 25, 26, 27, 28};

    private Font randomFont() {
        int index = random.nextInt(fontName.length);
        String name = fontName[index];
        int style = random.nextInt(4); // 0,1,2,3
        index = random.nextInt(fontSize.length);
        int size = fontSize[index];
        return new Font(name, style, size);
    }

    /*
     * 生成随机字符
     */
    private String codes = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private String randomChar() {
        int index = random.nextInt(codes.length());
        return codes.charAt(index) + "";
    }

    protected String chars = ""; // 用于存储随机产生的四个字符

    // 用户调用这个方法获取图片
    public BufferedImage getImage() {
        /*
         * 字符：随机，0-9a-zA-Z
         * 字体：
         * 字符颜色：
         */
        BufferedImage img = createImage();
        Graphics g = img.getGraphics();

        // 画东西
        for (int i = 0; i < 4; i++) {
            String ch = randomChar(); // 获取随机字符
            chars += ch; // 将随机产生的字符添加到字符串中
            g.setColor(randomColor()); // 随机颜色
            g.setFont(randomFont()); // 随机字体
            g.drawString(ch, w / 4 * i, h - 5);
        }
        return img;
    }

    //画图片上的干扰线
    private void drawLine(BufferedImage img) {
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1.5F));
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            int x2 = random.nextInt(w);
            int y2 = random.nextInt(h);
            g.drawLine(x1, y1, x2, y2);
        }
    }
}