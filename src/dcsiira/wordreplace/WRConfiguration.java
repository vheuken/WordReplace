package dcsiira.wordreplace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;



public class WRConfiguration
{
    private WordReplace plugin;
    File configFile;
    
    public WRConfiguration(WordReplace plugin)
    {
        this.plugin = plugin;
    }
    
    public void configCheck()
    {
    	configFile = new File(this.plugin.getDataFolder(), "config.yml");
    	if (!configFile.canRead())
        {
        	try
        	{
        		configFile.getParentFile().mkdirs();
        		JarFile jar = new JarFile(this.plugin.wordreplace);
        		JarEntry entry = jar.getJarEntry("config.yml");
        		InputStream is = jar.getInputStream(entry);
        		FileOutputStream os = new FileOutputStream(configFile);
        		byte[] buf = new byte[(int)entry.getSize()];
        		is.read(buf, 0, (int)entry.getSize());
        		os.write(buf);
        		os.close();
        		
        		// very confused as to what this is supposed to do
        		// getConfiguration() doesn't seem to be implemented
        		//this.plugin.getConfiguration().load();
        		
        		// this may or may not work
        		load();
        		
        		
        		System.out.println("WordReplace: configuration file generated successfully");
        	}
        	catch (Exception e)
        	{
        		System.out.println("WordReplace: could not generate configuration file");
        	}
        }
        readNodes();
    }
    
    public void writeNode(String node, Object value)
    {
        load();
        addComments();
        plugin.getConfig().set(node, value);
        
        try {
        	plugin.getConfig().save(configFile);
        } catch (IOException e) {
        	e.getStackTrace();
        }
    }
    
    public void addComments()
    {
    	String comments = "";
        comments += "# Word Replace Version 8.0\n";
        comments += "# Changing Word1 -> Word2 since 2011 (Not that long)\n";
        comments += "\n";
        comments += "# Supported colors are: (case insensitive)\n";
        comments += "# - Black(&0)             Dark_Gray(&8)\n";
        comments += "# - Dark_Blue(&1)         Blue(&9)\n";
        comments += "# - Dark_Green(&2)        Green(&a)\n";
        comments += "# - Dark_Aqua(&3)         Aqua(&b)\n";
        comments += "# - Dark_Red(&4)          Red(&c)\n";
        comments += "# - Dark_Purple(&5)       Light_Purple(&d)\n";
        comments += "# - Gold(&6)              Yellow(&e)\n";
        comments += "# - Gray(&7)              White(&f)\n";
        comments += "# - You can also use Pink for Light_Purple and RAND for Random Colors\n";
        comments += "# This defaults to WHITE if it finds an error\n";
        comments += "\n";
        comments += "# the replace-user-names node is if you want it so that when a user FULLY\n";
        comments += "# types in the name of another user, it is colored. Set to false to disable,\n";
        comments += "# set to true for random colors, currently cannot set the color.\n";
        comments += "\n";
        comments += "# An Example is \"AQUA,Admin,dcsiira:dc:siira\"\n";
        comments += "# Which replaces the words \"dc\",\"dcsiira\",\"siira\",\"Admin\" with \"Admin\", Colored in AQUA\n";
        
        plugin.getConfig().options().copyHeader(true);
        plugin.getConfig().options().header(comments);
        
    }
    
	public List<String> readStringList(String root){
        load();
        return plugin.getConfig().getStringList(root);
    }
   
    public String readString(String root){
        load();
        return plugin.getConfig().getString(root);
    }
    public boolean readBoolean(String root){
        load();
        return plugin.getConfig().getBoolean(root, false);
    }
    
    public void load()
    {
        try {
            plugin.getConfig().load(configFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    public void readNodes()
    {
    	plugin.log.info("Loading Config File...");
    	
    	try {
    	plugin.normalChatColor = this.plugin.getChatColor(readString("normal-chat-color"));
    	}catch (Exception e) {
    		writeNode("normal-chat-color","WHITE");
    	}

    	try{
        	plugin.wrList = readStringList("word-replace-list");
    	}catch (Exception e) {
        	writeNode("word-replace-list",plugin.wrList);
        }
        	
        try{
        	plugin.replaceNames = readBoolean("replace-user-names");
        }catch (Exception e) {
            writeNode("replace-user-names","FALSE");
        }

    }
}
    