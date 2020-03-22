package net.vortetty.pulaskisandshaxes.gens;

import net.devtech.rrp.api.RuntimeResourcePack;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PulaskiGen {
	public PulaskiGen(String modid, String path, String lightedge, String darkedge, String lightbody, String mediumbody, String darkbody, String sticklight, String stickdark, String stickmiddle) {
			Identifier item = new Identifier(modid, path);
		RuntimeResourcePack.INSTANCE.addDefaultItemModel(item);
		RuntimeResourcePack.INSTANCE.addAsyncTexture(new Identifier(modid, "item/"+path), () -> {
				BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = image.createGraphics();
				
				//Light Edge
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(7, 1, 9, 1);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(6, 2, 10, 2);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(5, 3, 11, 3);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(4, 4, 4, 6);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(12, 4, 12, 4);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(13, 5, 13, 5);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(14, 6, 14, 6);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(15, 7, 15, 9);
				
				//Dark Edge
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(5, 7, 5, 7);
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(6, 6, 6, 6);
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(7, 5, 9, 5);
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(11, 7, 12, 7);
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(13, 8, 13, 8);
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(14, 9, 14, 9);
				
				//Light Body
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(7, 2, 9, 2);
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(6, 3, 10, 3);
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(5, 4, 6, 4);
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(5, 5, 5, 6);
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(14, 7, 14, 8);
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(13, 6, 13, 6);
				
				//Medium Body
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(7, 4, 11, 4);
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(8, 3, 8, 3);
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(6, 5, 6, 5);
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(10, 5, 10, 5);
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(13, 7, 13, 7);
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(12, 6, 12, 6);
				
				//Dark Body
				g2d.setColor(new Color(Integer.parseInt(darkbody, 16)));
				g2d.drawLine(11, 5, 12, 5);
				g2d.setColor(new Color(Integer.parseInt(darkbody, 16)));
				g2d.drawLine(11, 6, 11, 6);
				
				//Stick Light
				g2d.setColor(new Color(Integer.parseInt(sticklight, 16)));
				g2d.drawLine(10, 6, 3, 13);
				
				//Stick Dark
				g2d.setColor(new Color(Integer.parseInt(stickdark, 16)));
				g2d.drawLine(11, 7, 4, 14);
				g2d.setColor(new Color(Integer.parseInt(stickdark, 16)));
				g2d.drawLine(3, 14, 4, 14);
				
				//Stick Middle
				int n = Integer.parseInt(stickmiddle, 16);
				int red = (n >> 16) & 0xff;
				int green = (n >> 8) & 0xff;
				int blue = n & 0xff;
				int r = red - 34;
				int g = green - 34;
				int b = blue - 34;
				
				g2d.setColor(new Color(Integer.parseInt(stickmiddle, 16)));
				g2d.drawLine(10, 7, 4, 13);
				g2d.setColor(new Color(Integer.parseInt(stickmiddle, 16)));
				g2d.drawLine(13, 4, 13, 4);
				g2d.setColor(new Color(r, g, b));
				g2d.drawLine(4, 13, 4, 13);
				g2d.setColor(new Color(r, g, b));
				g2d.drawLine(6, 11, 6, 11);
				g2d.setColor(new Color(r, g, b));
				g2d.drawLine(9, 8, 9, 8);
				return image;
			});
	}/*
	public PulaskiGen(String modid, String path, String darkblade, String darkhandle) {
		RAPCallback.EVENT.register(q -> {
			Identifier item = new Identifier(modid, path);
			q.registerDefaultItemModel(item);
			q.registerAsyncTexture(new Identifier(modid, path), () -> {
				BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				Graphics2D graphics2D = image.createGraphics();
				Graphics2D g2d = graphics2D;
				
				//Light Edge
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(7, 1, 9, 1);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(6, 2, 10, 2);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(5, 3, 11, 3);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(4, 4, 4, 6);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(12, 4, 12, 4);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(13, 5, 13, 5);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(14, 6, 14, 6);
				g2d.setColor(new Color(Integer.parseInt(lightedge, 16)));
				g2d.drawLine(15, 7, 15, 9);
				
				//Dark Edge
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(5, 7, 5, 7);
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(6, 6, 6, 6);
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(7, 5, 9, 5);
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(11, 7, 12, 7);
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(13, 8, 13, 8);
				g2d.setColor(new Color(Integer.parseInt(darkedge, 16)));
				g2d.drawLine(14, 9, 14, 9);
				
				//Light Body
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(7, 2, 9, 2);
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(6, 3, 10, 3);
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(5, 4, 6, 4);
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(5, 5, 5, 6);
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(14, 7, 14, 8);
				g2d.setColor(new Color(Integer.parseInt(lightbody, 16)));
				g2d.drawLine(13, 6, 13, 6);
				
				//Medium Body
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(7, 4, 11, 4);
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(8, 3, 8, 3);
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(6, 5, 6, 5);
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(10, 5, 10, 5);
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(13, 7, 13, 7);
				g2d.setColor(new Color(Integer.parseInt(mediumbody, 16)));
				g2d.drawLine(12, 6, 12, 6);
				
				//Dark Body
				g2d.setColor(new Color(Integer.parseInt(darkblade, 16)));
				g2d.drawLine(11, 5, 12, 5);
				g2d.setColor(new Color(Integer.parseInt(darkblade, 16)));
				g2d.drawLine(11, 6, 11, 6);
				
				//Stick Light
				g2d.setColor(new Color(Integer.parseInt(sticklight, 16)));
				g2d.drawLine(10, 6, 3, 13);
				
				//Stick Dark
				g2d.setColor(new Color(Integer.parseInt(darkhandle, 16)));
				g2d.drawLine(11, 7, 4, 14);
				g2d.setColor(new Color(Integer.parseInt(darkhandle, 16)));
				g2d.drawLine(3, 14, 4, 14);
				
				//Stick Middle
				int n = Integer.parseInt(stickmiddle, 16);
				long red = (n >> 16) & 0xff;
				long green = (n >> 8) & 0xff;
				long blue = n & 0xff;
				long r = red - 34;
				long g = green - 34;
				long b = blue - 34;
				
				g2d.setColor(new Color(Integer.parseInt(stickmiddle, 16)));
				g2d.drawLine(10, 7, 4, 13);
				g2d.setColor(new Color(Integer.parseInt(stickmiddle, 16)));
				g2d.drawLine(13, 4, 13, 4);
				g2d.setColor(new Color(r, g, b));
				g2d.drawLine(4, 13, 4, 13);
				g2d.setColor(new Color(r, g, b));
				g2d.drawLine(6, 11, 6, 11);
				g2d.setColor(new Color(r, g, b));
				g2d.drawLine(9, 8, 9, 8);
				return image;
			});
		});
	}*/
}
