package com.wode.api.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.google.zxing.common.BitMatrix;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;

public class MatrixToImageWriter {
	private static ResourceBundle res = ResourceBundle.getBundle("application");
	// 快递接口Domain
	public static String filePath = res.getString("area.json.file");

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private MatrixToImageWriter() {
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}
	
	public static void writeToStreamEx(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(merge(image), format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}
	
    public static BufferedImage merge(BufferedImage fore) {  
    	  
        try { 
        	BufferedImage back = getBackgroud("background.jpg");
            int w = fore.getWidth();  
            int h = fore.getHeight();  
  
            Graphics2D g = back.createGraphics();  
            g.drawImage(fore, 177, 1119, w, h, null);  
            g.dispose();  
            return back; 
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
        return null;  
    }  

	
	public static void writeToStreamEmp500Ticket(BitMatrix matrix, String format, OutputStream stream,String companyName,
			String limitEnd,String exChange) throws IOException {
		
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(mergeEmp500Ticket(image,companyName,limitEnd,exChange), format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}
    public static BufferedImage mergeEmp500Ticket(BufferedImage fore,String companyName,String limitEnd,String exchange) {  
    	  
        try { 
            boolean isExchange = !StringUtils.isEmpty(exchange);
        	// 读取背景
        	BufferedImage back = getBackgroud(isExchange?"emp_exchange_ticket_bg.png":"emp_500_ticket_bg.png");
            
        	// 合并二维码
        	int w = fore.getWidth();  
            int h = fore.getHeight();
            int qrx=426;
            int qry=2076;
            if(isExchange) {
            	qry=2312;
            } else {
            	limitEnd="";
            }
            
            Graphics2D g = back.createGraphics();  
            g.drawImage(fore, qrx, qry, w, h, null);
            
    		// 公司名称
            if(!StringUtils.isEmpty(companyName)) {
            	int y_com = 588+22;
            	int size_com = 32;
            	Color color_com = new Color(51, 51, 51);
            	if(isExchange) {
            		y_com = 195+20;
            		size_com = 36;
            		color_com = new Color(194, 29, 36);
            	}
            	int x= (640 - companyName.length()*size_com)/2;
            	if(x<0) x=0;
        		Font font1 = new Font("微软雅黑",Font.PLAIN, size_com);
        		g.setFont(font1);
        		g.setColor(color_com);
                /* 消除java.awt.Font字体的锯齿 */  
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                                          RenderingHints.VALUE_ANTIALIAS_ON); 
                
    			g.drawString(companyName, x, y_com);
            }

    		// 有效期（有效期至yyyy-MM-dd） 全角括号
            if(!StringUtils.isEmpty(limitEnd)) {
    			SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd - ");
    			String limits = df.format(new Date());
            	if(limitEnd.contains("有效期至")) {
            		limits += limitEnd.substring(5, 15).replace("-", ".");
            	} else {
            		limits += limitEnd.replace("-", ".");
            	}

            	int x= (640 - (4*18+limits.length()*9))/2;
            	if(x<0) x=0;
	    		Font font2 = new Font("微软雅黑",Font.PLAIN, 18);
	    		g.setFont(font2);
	    		g.setColor(new Color(94, 44, 4));
                /* 消除java.awt.Font字体的锯齿 */  
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                                          RenderingHints.VALUE_ANTIALIAS_ON); 
				g.drawString("有效期："+limits, x, 503+15);
            }


    		// 换领币
            if(!StringUtils.isEmpty(exchange)) {
    			int p= exchange.indexOf(".");
    			String left="";
    			String right="";
				int x= 0;
    			if(p>0) {
    				// 含小数点
    				left = exchange.substring(0, p+1);
    				right = exchange.substring(p+1);
    				x= (640 - (left.length()*36+right.length()*24))/2;
    			} else {
    				// 不含小数点
    				left = exchange;
    				x= (640 - left.length()*36)/2;
    			}
    			x=x-10;
            	if(x<0) x=0;
	    		Font font2 = new Font("微软雅黑",Font.PLAIN, 72);
	    		g.setFont(font2);
	    		g.setColor(new Color(252, 248, 236));
                /* 消除java.awt.Font字体的锯齿 */  
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                                          RenderingHints.VALUE_ANTIALIAS_ON); 
				g.drawString(left, x, 345+36+12);
            	
				if(right.length()>0) {
					x=x+left.length()*36;
		    		Font font3 = new Font("微软雅黑",Font.PLAIN, 48);
		    		g.setFont(font3);
					g.drawString(right, x, 345+36+12);
					
				}
            }
            g.dispose();  
            return back; 
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
        return null;  
    }  
    
    /**  
     * 导入本地图片到缓冲区  
     */  
    private static BufferedImage loadImageLocal(String imgName) {  
        try {  
            return ImageIO.read(new File(imgName));  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
        return null;  
    }  
    
    /**  
     * 导入本地图片到缓冲区  
     */  
    private static BufferedImage getBackgroud(String bgName) {
        return loadImageLocal(filePath.replace("area.json", bgName));
    } 
}


//public class Pic {
//    private Font       font     = new Font("宋体", Font.PLAIN, 12); // 添加字体的属性设置    
//    
//    private Graphics2D g        = null;  
//  
//    private int        fontsize = 0;  
//  
//    private int        x        = 0;  
//  
//    private int        y        = 0;  
//  
//    /**  
//     * 导入本地图片到缓冲区  
//     */  
//    public BufferedImage loadImageLocal(String imgName) {  
//        try {  
//            return ImageIO.read(new File(imgName));  
//        } catch (IOException e) {  
//            System.out.println(e.getMessage());  
//        }  
//        return null;  
//    }  
////  
////    /**  
////     * 导入网络图片到缓冲区  
////     */  
////    public BufferedImage loadImageUrl(String imgName) {  
////        try {  
////            URL url = new URL(imgName);  
////            return ImageIO.read(url);  
////        } catch (IOException e) {  
////            System.out.println(e.getMessage());  
////        }  
////        return null;  
////    }  
////  
////    /**  
////     * 生成新图片到本地  
////     */  
//    public void writeImageLocal(String newImage, BufferedImage img) {  
//        if (newImage != null && img != null) {  
//            try {  
//                File outputfile = new File(newImage);  
//                ImageIO.write(img, "jpg", outputfile);  
//            } catch (IOException e) {  
//                System.out.println(e.getMessage());  
//            }  
//        }  
//    }  
////  
////    /**  
////     * 设定文字的字体等  
////     */  
////    public void setFont(String fontStyle, int fontSize) {  
////        this.fontsize = fontSize;  
////        this.font = new Font(fontStyle, Font.PLAIN, fontSize);  
////    }  
////  
////    /**  
////     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）  
////     */  
////    public BufferedImage modifyImage(BufferedImage img, Object content, int x, int y) {  
////  
////        try {  
////            int w = img.getWidth();  
////            int h = img.getHeight();  
////            g = img.createGraphics();  
////            g.setBackground(Color.WHITE);  
////            g.setColor(Color.orange);//设置字体颜色    
////            if (this.font != null)  
////                g.setFont(this.font);  
////            // 验证输出位置的纵坐标和横坐标    
////            if (x >= h || y >= w) {  
////                this.x = h - this.fontsize + 2;  
////                this.y = w;  
////            } else {  
////                this.x = x;  
////                this.y = y;  
////            }  
////            if (content != null) {  
////                g.drawString(content.toString(), this.x, this.y);  
////            }  
////            g.dispose();  
////        } catch (Exception e) {  
////            System.out.println(e.getMessage());  
////        }  
////  
////        return img;  
////    }  
//  
//    /**  
//     * 修改图片,返回修改后的图片缓冲区（输出多个文本段） xory：true表示将内容在一行中输出；false表示将内容多行输出  
//     */  
////    public BufferedImage modifyImage(BufferedImage img, Object[] contentArr, int x, int y,  
////                                     boolean xory) {  
////        try {  
////            int w = img.getWidth();  
////            int h = img.getHeight();  
////            g = img.createGraphics();  
////            g.setBackground(Color.WHITE);  
////            g.setColor(Color.RED);  
////            if (this.font != null)  
////                g.setFont(this.font);  
////            // 验证输出位置的纵坐标和横坐标    
////            if (x >= h || y >= w) {  
////                this.x = h - this.fontsize + 2;  
////                this.y = w;  
////            } else {  
////                this.x = x;  
////                this.y = y;  
////            }  
////            if (contentArr != null) {  
////                int arrlen = contentArr.length;  
////                if (xory) {  
////                    for (int i = 0; i < arrlen; i++) {  
////                        g.drawString(contentArr[i].toString(), this.x, this.y);  
////                        this.x += contentArr[i].toString().length() * this.fontsize / 2 + 5;// 重新计算文本输出位置    
////                    }  
////                } else {  
////                    for (int i = 0; i < arrlen; i++) {  
////                        g.drawString(contentArr[i].toString(), this.x, this.y);  
////                        this.y += this.fontsize + 2;// 重新计算文本输出位置    
////                    }  
////                }  
////            }  
////            g.dispose();  
////        } catch (Exception e) {  
////            System.out.println(e.getMessage());  
////        }  
////  
////        return img;  
////    }  
////  
//    /**  
//     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）  
//     *   
//     * 时间:2007-10-8  
//     *   
//     * @param img  
//     * @return  
//     */  
////    public BufferedImage modifyImageYe(BufferedImage img) {  
////  
////        try {  
////            int w = img.getWidth();  
////            int h = img.getHeight();  
////            g = img.createGraphics();  
////            g.setBackground(Color.WHITE);  
////            g.setColor(Color.blue);//设置字体颜色    
////            if (this.font != null)  
////                g.setFont(this.font);  
////            g.drawString("www.hi.baidu.com?xia_mingjian", w - 85, h - 5);  
////            g.dispose();  
////        } catch (Exception e) {  
////            System.out.println(e.getMessage());  
////        }  
////  
////        return img;  
////    }  
//  
//    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {  
//  
//        try {  
//            int w = b.getWidth();  
//            int h = b.getHeight();  
//  
//            g = d.createGraphics();  
//            g.drawImage(b, 100, 20, w, h, null);  
//            g.dispose();  
//        } catch (Exception e) {  
//            System.out.println(e.getMessage());  
//        }  
//  
//        return d;  
//    }  
//  
//    public static void main(String[] args) {  
//  
//        Pic tt = new Pic();  
//  
//        BufferedImage d = tt.loadImageLocal("C:\\Users\\user\\Pictures\\nubia\\14412797277110272headimg.jpg");  
//        BufferedImage b = tt.loadImageLocal("C:\\Users\\user\\Pictures\\nubia\\1423620834904.jpg");    
//        //往图片上写文件    
//        //tt.writeImageLocal("E:\\ploanshare\\2\\22.jpg", tt.modifyImage(d, "000000", 90, 90));  
//  
//        tt.writeImageLocal("C:\\Users\\user\\Pictures\\nubia\\1.jpg", tt.modifyImagetogeter(b, d));    
//        //将多张图片合在一起    
//        System.out.println("success");  
//    }  
//}