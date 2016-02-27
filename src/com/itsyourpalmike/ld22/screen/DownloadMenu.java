package com.itsyourpalmike.ld22.screen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.itsyourpalmike.ld22.Game;
import com.itsyourpalmike.ld22.GameLauncher;
import com.itsyourpalmike.ld22.gfx.Color;
import com.itsyourpalmike.ld22.gfx.Font;
import com.itsyourpalmike.ld22.gfx.Screen;
import com.itsyourpalmike.ld22.plugin.DownloadPlugins;
import com.itsyourpalmike.ld22.sound.Sound;

public class DownloadMenu extends Menu
{
	private Game game;
	private int selected = 0;
	private ArrayList<DownloadablePlugin> plugins = new ArrayList<DownloadablePlugin>();
	String savePath = System.getenv("APPDATA") + "/.minicraft/tmp/";
	static String pluginsTXT = System.getenv("APPDATA") + "/.minicraft/curVer.xml";

	public DownloadMenu(Game game)
	{
		Sound.load("pluginsSelected", this.getClass().getResource("/pluginsSelected.wav"));
		this.game = game;
		for (int i = 0; i < Game.plugins.size(); i++)
		{
			//plugins.add(new PluginMenuItem(Game.plugins.get(i)));
		}
		
		
		
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
		
		NodeList pluginsXML = xmlDoc.getElementsByTagName("plugin");
		String name = "name";
		String url = "url";
		String save_name = "save_name";
		
		for(int i = 0; i < pluginsXML.getLength(); i++)
		{
			Element element = (Element)pluginsXML.item(i);
			
			Element pluginName = (Element)element.getElementsByTagName(name).item(0);
			Element pluginURL = (Element)element.getElementsByTagName(url).item(0);
			Element pluginSaveName = (Element)element.getElementsByTagName(save_name).item(0);

			plugins.add(new DownloadablePlugin(pluginName.getTextContent(), pluginURL.getTextContent(), pluginSaveName.getTextContent()));
			System.out.println(pluginName.getTextContent() + ", " + pluginURL.getTextContent() + ", " + pluginSaveName.getTextContent());
		}
		
		File file = new File(pluginsTXT);
		file.delete();
	}

	public void tick()
	{

		if (input.up.clicked) selected--;
		if (input.down.clicked) selected++;

		int len = plugins.size() + 1;
		if (len == 0) selected = 0;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.attack.clicked || input.menu.clicked)
		{
			if (selected == plugins.size())
			{
				// Remove the disabled plugins from the Game.plugins array list before running game
				for (int i = plugins.size()-1; i >= 0; i--)
				{
					if (!plugins.get(i).enabled)
					{
						plugins.remove(i);
					}
				}
				Sound.play("pluginsSelected");
				
				File file = new File(savePath);
				if (!file.exists())
				{
					file.mkdir();
				}
				
				for(int i = 0; i < plugins.size(); i++)
				{
					System.out.println(plugins.get(i).getName() + " " + plugins.get(i).getFilePath());
					URL website = null;
					try
					{
						website = new URL(plugins.get(i).getFilePath());
					}
					catch (MalformedURLException e2)
					{
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try (InputStream in = website.openStream()) {
					    try
						{
							Files.copy(in, Paths.get(savePath + plugins.get(i).getSimpleFileName()) , StandardCopyOption.REPLACE_EXISTING);
							in.close();
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
				}
				
				game.setMenu(new RestartMenu());
				
				//game.init();
			}
			else
			{
				plugins.get(selected).enabled = !plugins.get(selected).enabled;
			}
		}

	}
	

	public void render(Screen screen)
	{
		screen.clear(0);

		Font.draw("deselect", screen, (8 * 8) + (4 * 8), 8, Color.get(0, 500, 500, 500));
		Font.draw("and", screen, (8 * 8), 8, Color.get(0, 444, 444, 444));
		Font.draw("select", screen, 8, 8, Color.get(0, 050, 050, 050));
		Font.draw("Minicraft plugins", screen, 12, 16, Color.get(0, 444, 444, 444));

		renderPluginList(screen, 1, 3, 12, 12, selected, plugins);

		String msg;
		int cnt = 0;
		for (int j = 0; j < plugins.size(); j++)
		{
			if (plugins.get(j).enabled) cnt++;
		}
		if(cnt > 99) cnt = 99;
		String s = (cnt == 0 || cnt > 1) ? "s" : "";
		msg = "Download " + cnt + "plugin" + s;
		int col = Color.get(0, 333, 333, 333);

		if (selected == plugins.size())
		{
			msg = "" + msg + "";
			col = Color.get(0, 555, 555, 555);
		}

		Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, screen.h - 16, col);

		if (selected == plugins.size())
		{
			Font.draw("" + cnt, screen, ((screen.w - msg.length() * 8) / 2) + 9*8, screen.h - 16, Color.get(0, 050, 050, 050));
		}
		else
		{
			Font.draw("" + cnt, screen, ((screen.w - msg.length() * 8) / 2) + 9*8, screen.h - 16, Color.get(0, 040, 040, 040));
		}
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
