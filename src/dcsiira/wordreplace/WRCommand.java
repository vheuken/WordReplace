
package dcsiira.wordreplace;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Handler for the /WR command.
 * @author DCSiira
 */
public class WRCommand implements CommandExecutor
{
    private final WordReplace plugin;

    public WRCommand(WordReplace plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] split)
    {
    	if(!sender.isOp())
    	{
    		sender.sendMessage("[" + ChatColor.AQUA + "WordReplace" + ChatColor.WHITE +"] You are not able to use this command, OP's only");
    		return true;
    	}
        if (split.length == 1)
        {
        	if(split[0].equalsIgnoreCase("version"))
        	{
                String version = plugin.getDescription().getVersion();
                sender.sendMessage("[" + ChatColor.AQUA + "WordReplace" + ChatColor.WHITE +"] Version: " + version);
                return true;
        	}
        	else if(split[0].equalsIgnoreCase("reload"))
        	{
        		plugin.config.readNodes();
        		sender.sendMessage("[" + ChatColor.AQUA + "WordReplace" + ChatColor.WHITE +"] Reload Complete");
        		return true;
        	}
        	else if(split[0].equalsIgnoreCase("list"))
        	{
        		wordList(sender);
            	return true;
        	}
        	else if(split[0].equalsIgnoreCase("help"))
        	{
        		help(sender,"null");
            	return true;
        	}
        }
        if (split.length == 2)
        {
        	if(split[0].equalsIgnoreCase("remove"))
        	{
                remove(Integer.parseInt(split[1]) - 1);
                sender.sendMessage("[" + ChatColor.AQUA + "WordReplace" + ChatColor.WHITE +"] Removing List Complete");
                return true;
        	}
        	if(split[0].equalsIgnoreCase("help"))
        	{
                help(sender,split[1]);
                return true;
        	}
        }
        else
        {
        	if(split[0].equalsIgnoreCase("add"))
        	{
        		add(split);
        		sender.sendMessage("[" + ChatColor.AQUA + "WordReplace" + ChatColor.WHITE +"] Adding Complete");
        		return true;
        	}
        }
        return false;
    }
    
    public boolean wordList(CommandSender sender)
    {
    	sender.sendMessage("[" + ChatColor.AQUA + "WordReplace" + ChatColor.WHITE + "] Replaces:");
        for (int listLoop = 0; listLoop < this.plugin.wrList.size(); listLoop++)
        {
          String wordReplacing = this.plugin.parseWordReplacing(listLoop);
          ChatColor wordColor = this.plugin.parseWordColor(listLoop);
          String wordsBeingReplaced = "[";
          if(this.plugin.parseWordsBeingReplaced(listLoop)!=null)
        	  for (String tempWordBeingReplaced : this.plugin.parseWordsBeingReplaced(listLoop)) {
        		  wordsBeingReplaced = wordsBeingReplaced + tempWordBeingReplaced + ", ";
        	  }
          wordsBeingReplaced = wordsBeingReplaced + wordReplacing + ", ";
          wordsBeingReplaced = wordsBeingReplaced.substring(0, wordsBeingReplaced.length() - 2);
          sender.sendMessage("(" + plugin.getRNDChatColor() +  (listLoop+1) + ChatColor.WHITE + ") " + wordsBeingReplaced + "] with " + wordColor + wordReplacing);
        }
        return true;
      }

    public boolean add(String[] split)
    {
    	try
    	{
    		if(split.length == 2)
    		{
    			this.plugin.wrList.add(split[0]);
    			this.plugin.config.writeNode("word-replace-list",this.plugin.wrList);
    			this.plugin.config.readNodes();
    		}
    		else
    		{
    			String newList = split[1] + "," + split[2] + ",";
    			if(split.length != 3)
    			{
    				for(int loop = 3; loop < split.length; loop++)
    				{
    					newList += split[loop] + ":";
    				}
    			    newList = newList.substring(0, newList.length()-1);
    			}
    			this.plugin.wrList.add(newList);
    			this.plugin.config.writeNode("word-replace-list",this.plugin.wrList);
    			this.plugin.config.readNodes();
    		}
    	}
    	catch (Exception e)
    	{
    		return false;
    	}
    	return true;
    }

    public boolean remove(int removeList)

    {
    	try
    	{
        	this.plugin.wrList.remove(removeList);
        	this.plugin.config.writeNode("word-replace-list",this.plugin.wrList);
        	this.plugin.config.readNodes();
    	}
    	catch (Exception e)
    	{
    		return false;
    	}
    	return true;

    }

    public boolean help(CommandSender sender, String helpSubject)
    {
    	if(helpSubject.equalsIgnoreCase("Version"))
    		sender.sendMessage(ChatColor.AQUA + "/WR Version " + ChatColor.WHITE + "- Replies with the version of WordReplace in use.");
    	if(helpSubject.equalsIgnoreCase("Reload"))
    		sender.sendMessage(ChatColor.AQUA + "/WR Reload  " + ChatColor.WHITE + "- Reloads the configuration file of WordReplace.");
    	if(helpSubject.equalsIgnoreCase("List"))
      		sender.sendMessage(ChatColor.AQUA + "/WR List    " + ChatColor.WHITE + "- Replies with the WordReplace list");
    	else if(helpSubject.equalsIgnoreCase("Add"))
    		sender.sendMessage(ChatColor.AQUA + "/WR Add <ReplaceToColor> <ReplaceToWord> <ReplacingWord> (ReplacingWord) (ReplacingWord)...");
    	else if(helpSubject.equalsIgnoreCase("Remove"))
    		sender.sendMessage(ChatColor.AQUA + "/WR Remove <ListNumber> - The List Number is found by using /WR List");
    	else if(helpSubject.equalsIgnoreCase("Help"))
    		sender.sendMessage(ChatColor.AQUA + "/WR Help (Command) - You're asking for help with the help command? REALLY?");
    	else
    	{
    		sender.sendMessage("[" + ChatColor.AQUA + "WordReplace" + ChatColor.WHITE + "] Command Usage:");
    		sender.sendMessage(ChatColor.AQUA + "/WR Version " + ChatColor.WHITE + "- Replies with the version of WordReplace in use.");
    		sender.sendMessage(ChatColor.AQUA + "/WR Reload  " + ChatColor.WHITE + "- Reloads the configuration file of WordReplace.");
    		sender.sendMessage(ChatColor.AQUA + "/WR List    " + ChatColor.WHITE + "- Replies with the WordReplace list");
    		sender.sendMessage(ChatColor.AQUA + "/WR Add     " + ChatColor.WHITE + "- Adds a word list to WordReplace.");
    		sender.sendMessage(ChatColor.AQUA + "/WR Remove  " + ChatColor.WHITE + "- Removes a word list from WordReplace");
    		sender.sendMessage(ChatColor.AQUA + "/WR Help    " + ChatColor.WHITE + "- Add a command to the end to learn more.");
    	}
    	return true;
    }
}
