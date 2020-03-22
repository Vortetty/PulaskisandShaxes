package net.vortetty.pulaskisandshaxes.gens;

import net.devtech.rrp.api.RuntimeResourcePack;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ShaxeGen {
	public ShaxeGen(String modid, String path, String lightedge, String darkedge, String lightbody, String mediumbody, String darkbody, String sticklight, String stickdark, String stickmiddle){
			Identifier item = new Identifier(modid, path);
		RuntimeResourcePack.INSTANCE.addDefaultItemModel(item);
		RuntimeResourcePack.INSTANCE.addAsyncTexture(new Identifier(modid, "item/"+path), () -> {
				BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				Graphics2D graphics2D = image.createGraphics();
				Graphics2D g2d = graphics2D;
				
				//Light Edge
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(2, 0, 3, 0);
				g2d.drawLine(4, 1, 4, 1);
				g2d.drawLine(5, 2, 10, 2);
				g2d.drawLine(4, 3, 5, 3);
				g2d.drawLine(11, 3, 11, 3);
				g2d.drawLine(10, 4, 10, 4);
				
				//Dark Edge
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(7, 4, 9, 4);
				g2d.drawLine(1, 0, 1, 0);
				g2d.drawLine(0, 1, 0, 3);
				g2d.drawLine(1, 4, 1, 4);
				g2d.drawLine(2, 5, 3, 5);
				g2d.drawLine(3, 4, 3, 4);
				g2d.drawLine(2, 6, 2, 10);
				g2d.drawLine(3, 11, 3, 11);
				g2d.drawLine(4, 7, 4, 10);
				
				//Light Body
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(3, 10, 3, 10);
				g2d.drawLine(10, 3, 10, 3);
				g2d.drawLine(2, 4, 2, 4);
				g2d.drawLine(4, 2, 4, 2);
				g2d.drawLine(1, 2, 1, 3);
				g2d.drawLine(2, 1, 3, 1);
				
				//Medium Body
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(3, 9, 3, 9);
				g2d.drawLine(9, 3, 9, 3);
				g2d.drawLine(3, 6, 3, 6);
				g2d.drawLine(6, 3, 6, 3);
				g2d.drawLine(4, 4, 5, 5);
				g2d.drawLine(2, 3, 3, 2);
				
				//Dark Body
				g2d.setColor(new Color(Integer.parseInt(darkbody, 16)));
				g2d.drawLine(1, 1, 3, 3);
				g2d.drawLine(3, 8, 4, 5);
				g2d.drawLine(8, 3, 5, 4);
				
				//Stick Light
				g2d.setColor(new Color(Integer.parseInt(sticklight, 16)));
				g2d.drawLine(6, 5, 14, 13);
				
				//Stick Dark
				g2d.setColor(new Color(Integer.parseInt(stickdark, 16)));
				g2d.drawLine(5, 6, 13, 14);
				g2d.drawLine(14, 14, 14, 14);
				
				//Stick Middle
				int n = Integer.parseInt(stickmiddle, 16);
				int red = (n >> 16) & 0xff;
				int green = (n >> 8) & 0xff;
				int blue = n & 0xff;
				int r = red - 34;
				int g = green - 34;
				int b = blue - 34;
				int rgb = r << 16 | g << 8 | b;
				
				g2d.setColor(new Color(Integer.parseInt(stickmiddle, 16)));
				g2d.drawLine(6, 6, 13, 13);
				g2d.setColor(new Color(rgb));
				g2d.drawLine(12, 12, 12, 12);
				g2d.drawLine(10, 10, 10, 10);
				g2d.drawLine(8, 8, 8, 8);
				g2d.drawLine(6, 6, 6, 6);
				
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-image.getWidth(null), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
				return image;
			});
	}
}
