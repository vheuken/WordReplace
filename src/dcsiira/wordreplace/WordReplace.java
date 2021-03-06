
package dcsiira.wordreplace;

import java.util.HashMap;
import java.util.List;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

//This has been deprecated.
//Remove when port to new Configuration system is complete
//import org.bukkit.util.config.Configuration;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Word Replacing Plugin for Bukkit
 *
 * @author DCSiira
 */
@SuppressWarnings("unused")
public class WordReplace extends JavaPlugin {
    public WRConfiguration config = new WRConfiguration(this);
    public String enabledstartup = "Enabled On Startup";
    public boolean enabled;
    
    private final WRPlayerListener playerListener = new WRPlayerListener(this);
    private final WRCommand command = new WRCommand(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();


    public List<String>  wrList;
    public ChatColor normalChatColor;
    
    public boolean replaceNames;
    
    private PluginManager pm;
    public Logger log;
   
    public File wordreplace;
    



    public void onDisable() {
        System.out.println("[WordReplace] Plugin Disabled"); //Show Plugin Disabled in Log File
        pm.disablePlugin(this);
    }
    
    public void onEnable()
    {
        this.pm = getServer().getPluginManager();
        this.log = getServer().getLogger();
        this.wordreplace = getFile();
        config.configCheck();
        
        getServer().getPluginManager().registerEvent(PlayerChatEvent.class, playerListener, EventPriority.LOWEST, playerListener , this);
        
        getCommand("WR").setExecutor(command); //Register command "/WR" with Bukkit
        getCommand("WReplace").setExecutor(command); //Register command "/WR" with Bukkit
        getCommand("WordReplace").setExecutor(command); //Register command "/WR" with Bukkit
        
        System.out.println(this.getDescription().getName() + " version " + this.getDescription().getVersion() + " is enabled!" );
    }

    public boolean isDebugging(final Player player)
    {
        if (debugees.containsKey(player))
        {
            return debugees.get(player);
        } 
        else
        {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value)
    {
        debugees.put(player, value);
    }
   
    public ChatColor getChatColor(String color)
    {
    	if (color == null)
    		return ChatColor.WHITE;   	
        if ((color.equalsIgnoreCase("BLACK")) || (color.equalsIgnoreCase("&0")))
          return ChatColor.BLACK;
        if ((color.equalsIgnoreCase("DARK_BLUE")) || (color.equalsIgnoreCase("&1")))
          return ChatColor.DARK_BLUE;
        if ((color.equalsIgnoreCase("DARK_GREEN")) || (color.equalsIgnoreCase("&2")))
          return ChatColor.DARK_GREEN;
        if ((color.equalsIgnoreCase("DARK_AQUA")) || (color.equalsIgnoreCase("&3")))
          return ChatColor.DARK_AQUA;
        if ((color.equalsIgnoreCase("DARK_RED")) || (color.equalsIgnoreCase("&4")))
          return ChatColor.DARK_RED;
        if ((color.equalsIgnoreCase("DARK_PURPLE")) || (color.equalsIgnoreCase("&5")))
          return ChatColor.DARK_PURPLE;
        if ((color.equalsIgnoreCase("GOLD")) || (color.equalsIgnoreCase("&6")) || (color.equalsIgnoreCase("DARK_YELLOW")))
          return ChatColor.GOLD;
        if ((color.equalsIgnoreCase("GRAY")) || (color.equalsIgnoreCase("&7")))
          return ChatColor.GRAY;
        if ((color.equalsIgnoreCase("DARK_GRAY")) || (color.equalsIgnoreCase("&8")))
          return ChatColor.DARK_GRAY;
        if ((color.equalsIgnoreCase("BLUE")) || (color.equalsIgnoreCase("&9")))
          return ChatColor.BLUE;
        if ((color.equalsIgnoreCase("GREEN")) || (color.equalsIgnoreCase("&a")))
          return ChatColor.GREEN;
        if ((color.equalsIgnoreCase("AQUA")) || (color.equalsIgnoreCase("&b")))
          return ChatColor.AQUA;
        if ((color.equalsIgnoreCase("RED")) || (color.equalsIgnoreCase("&c")))
          return ChatColor.RED;
        if ((color.equalsIgnoreCase("LIGHT_PURPLE")) || (color.equalsIgnoreCase("&d")) || (color.equalsIgnoreCase("PINK")))
          return ChatColor.LIGHT_PURPLE;
        if ((color.equalsIgnoreCase("YELLOW")) || (color.equalsIgnoreCase("&e")))
          return ChatColor.YELLOW;
        if ((color.equalsIgnoreCase("WHITE")) || (color.equalsIgnoreCase("&f")))
          return ChatColor.WHITE;
        if ((color.equalsIgnoreCase("RAND")) || (color.equalsIgnoreCase("&f")))
          return getRNDChatColor();
        return getRNDChatColor();
      }

    public ChatColor getRNDChatColor()
    {
    	switch ( (int) (Math.random() * 11.0))
    	{
    	case 0 : return ChatColor.DARK_GREEN;
    	case 1 : return ChatColor.DARK_AQUA;
    	case 2 : return ChatColor.DARK_RED;
    	case 3 : return ChatColor.DARK_PURPLE;
    	case 4 : return ChatColor.GOLD;
    	case 5 : return ChatColor.BLUE;
    	case 6 : return ChatColor.GREEN;
    	case 7 : return ChatColor.AQUA;
    	case 8 : return ChatColor.RED;
    	case 9 : return ChatColor.LIGHT_PURPLE;
    	case 10 : return ChatColor.YELLOW;
    	}
    	return ChatColor.WHITE;
      }

    public ChatColor parseWordColor(int listNumber) {
        String[] split = ((String)wrList.get(listNumber)).split(",");
        return getChatColor(split[0]);
      }

    public String parseWordReplacing(int listNumber) {
        String[] split = ((String)wrList.get(listNumber)).split(",");
        return split[1];
    }

    public String[] parseWordsBeingReplaced(int listNumber) {
        String[] split = ((String)wrList.get(listNumber)).split(",");
        String[] replaceWords = null;
        if(split.length == 3)
        	replaceWords = split[2].split(":");
        return replaceWords;
      }
}
