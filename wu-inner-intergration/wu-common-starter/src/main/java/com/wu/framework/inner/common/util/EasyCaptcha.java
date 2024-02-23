package com.wu.framework.inner.common.util;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.img.GraphicsUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 创建验证码
 * @date : 2020/6/17
 */
public class EasyCaptcha extends LineCaptcha {


    public EasyCaptcha(int width, int height, int codeCount, int interfereCount) {
        super(width, height, codeCount, interfereCount);
    }


    @Override
    public Image createImage(String code) {
        BufferedImage image = new BufferedImage(this.width, this.height, 1);
        Graphics2D g = GraphicsUtil.createGraphics(image, (Color) ObjectUtil.defaultIfNull(this.background, Color.WHITE));
        this.drawInterfere(g);
        this.drawString(g, code);
        return image;
    }

    // ----------------------------------------------------------------------------------------------------- Private method start

    /**
     * 绘制字符串
     *
     * @param g    {@link Graphics}画笔
     * @param code 验证码
     */
    private void drawString(Graphics2D g, String code) {
        // 指定透明度
        if (null != this.textAlpha) {
            g.setComposite(this.textAlpha);
        }
        GraphicsUtil.drawStringColourful(g, code, this.font, this.width, this.height);
    }

    /**
     * 绘制干扰线
     *
     * @param g {@link Graphics2D}画笔
     */
    private void drawInterfere(Graphics2D g) {
        final ThreadLocalRandom random = RandomUtil.getRandom();
        // 干扰线
        for (int i = 0; i < this.interfereCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            g.setColor(ImgUtil.randomColor(random));
            g.drawLine(xs, ys, xe, ye);
        }
    }
    // ----------------------------------------------------------------------------------------------------- Private method start

}
