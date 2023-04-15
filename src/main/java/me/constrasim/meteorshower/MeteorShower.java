package me.constrasim.meteorshower;

import me.constrasim.meteorshower.commands.MeteorShowerCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MeteorShower extends JavaPlugin
{

    private static MeteorShower instance;


    @Override
    public void onEnable()
    {
        setInstance(this);
        getLogger().info("MeteorShower has been enabled!");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("meteorshower").setExecutor(new MeteorShowerCommand());
    }

    @Override
    public void onDisable()
    {
        getLogger().info("MeteorShower has been disabled!");
    }

    public static MeteorShower getInstance()
    {
        return instance;
    }

    public static void setInstance(MeteorShower instance)
    {
        MeteorShower.instance = instance;
    }

}