package com.itsyourpalmike.ld22.screen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.sound.Sound;

public class OpeningMenu extends Menu
{

	static String pluginsTXT = System.getenv("APPDATA") + "/.minicraft/curVer.xml";
	
	Game game;
	public OpeningMenu(Game game)
	{
		this.game = game;
		Sound.load("pluginsSelected", this.getClass().getResource("/pluginsSelected.wav"));
	}

	public void tick()
	{
		if (input.attack.clicked || input.menu.clicked)
		{

			Sound.play("pluginsSelected");
			
			URL website = null;
			try
			{
				website = new URL("http://itsyourpalmike.github.io/Minicraft-Ultimate-Edition/data.xml");
			}
			catch (MalformedURLException e2)
			{
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try (InputStream in = website.openStream()) {
			    try
				{
					Files.copy(in, Paths.get(pluginsTXT), StandardCopyOption.REPLACE_EXISTING);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Document xmlDoc = getDocument(pluginsTXT);
			System.out.println("ROOT: " + xmlDoc.getDocumentElement().getNodeName());
			
			NodeList release = xmlDoc.getElementsByTagName("release");
			System.out.println(release.item(0).getTextContent());
			

			File file = new File(pluginsTXT);
			file.delete();
			
			if(!release.item(0).getTextContent().equals(Game.CURRENT_VERSION))
			{
				game.setMenu(new UpdateMenu());
			}
			else
			{
				game.setMenu(new FirstMenu(game));
			}
		}
	}

	public void render(Screen screen)
	{
		screen.clear(0);

		Font.draw("CONTROLS:", screen, 0 * 8 + 4, 5 * 8, Color.get(0, 333, 333, 333));
		Font.draw("arrow keys,x,and c", screen, 0 * 8 + 4, 6 * 8, Color.get(0, 333, 333, 333));
		Font.draw("Press C to continue", screen, 0 * 8 + 4, 9 * 8, Color.get(0, 333, 333, 333));
	}
	
	private static Document getDocument(String docString)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setValidating(true);
		
		DocumentBuilder builder = null;
		try
		{
			builder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try
		{
			return builder.parse(new InputSource(docString));
		}
		catch (SAXException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return null;
	}
}
