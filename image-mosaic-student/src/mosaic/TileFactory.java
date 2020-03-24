/*
 * Copyright 2017 Marc Liberatore.
 */

package mosaic;

import java.awt.image.BufferedImage;

import images.ImageUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TileFactory {
	public final int tileWidth;
	public final int tileHeight;
	// TODO: you will NOT be keeping this array in your final code; 
	// see assignment description for details
	
	private Map<Integer, List<BufferedImage>> hues = new HashMap<>();
	/**
	 * 
	 * @param colors the palette of RGB colors for this TileFactory
	 * @param tileWidth width (in pixels) for each tile
	 * @param tileHeight height (in pixels) for each tile
	 */
	public TileFactory(int[] colors, int tileWidth, int tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		// TODO: when you replace the int[] hues, be sure to replace this code, as well
		for (int i = 0; i < colors.length; i++) {
			hues.put(Math.round(ImageUtils.hue(colors[i])), new ArrayList<>());
		}
	}
	
	/**
	 * Returns the distance between two hues on the circle [0,256).
	 * 
	 * @param hue1 
	 * @param hue2
	 * @return the distance between two hues.
	 */
	static int hueDistance(int hue1, int hue2) {
		if(Math.abs(hue2 - hue1) <= 128) {
			return Math.abs(hue2 - hue1);
		} else {
			return Math.abs(256 - hue2 + hue1);
		}
		
	}
	
	/**
	 * Returns the closest hue from the fixed palette this TileFactory contains.
	 * @param hue
	 * @return the closest hue from the palette
	 */
	Integer closestHue(int hue) {
		int direction = 256;
		int result = 0;
		for(Integer key : hues.keySet()) {
			if(hueDistance(key, hue) < direction) {
				direction = hueDistance(key, hue);
				result = key;
			}
		}
		return result;
	}
	
	/**
	 * Adds an image to this TileFactory for later use.
	 * @param image the image to add
	 */
	public void addImage(BufferedImage image) {
		image = ImageUtils.resize(image, tileWidth, tileHeight);		
		int avgHue = ImageUtils.averageHue(image);
		
		// TODO: add the image to the appropriate place in your map from hues to lists of images
		hues.get(closestHue(avgHue)).add(image);
	}
	
	/**
	 * Returns the next tile from the list associated with the hue most closely matching the input hue.
	 * 
	 * The returned values should cycle through the list. Each time this method is called, the next 
	 * tile in the list will be returned; when the end of the list is reached, the cycle starts over.
	 *  
	 * @param hue the color to match
	 * @return a tile matching hue
	 */
	public BufferedImage getTile(int hue) {
		// TODO:  return an appropriate image from your map of hues to lists of images; 
		// see assignment description for details
		BufferedImage image = null;
		int hold = closestHue(hue);
		image = hues.get(hold).get(0);
		Collections.rotate(hues.get(hold), -1);
		
		return image;
	}
}
