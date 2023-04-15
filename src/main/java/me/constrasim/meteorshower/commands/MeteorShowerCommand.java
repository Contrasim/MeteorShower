package me.constrasim.meteorshower.commands;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import me.constrasim.meteorshower.MeteorShower;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class MeteorShowerCommand implements CommandExecutor
{

    MeteorShower plugin = MeteorShower.getInstance();

    boolean shower;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        if (label.equalsIgnoreCase("meteorshower"))
        {
            if (!sender.hasPermission("meteorshower.use"))
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.no-permission")));
                return true;
            }
        }

        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("reload"))
            {
                MeteorShower.getInstance().reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.reload-message")));
            }
        }

        if (args.length == 0)
        {
            for (String msg: this.plugin.getConfig().getStringList("Help.help-message"))
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        msg));
        }

        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("help"))
            {
                for (String msg: this.plugin.getConfig().getStringList("Help.help-message"))
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            msg));

            }
        }

        if (args.length == 1)
            if (args[0].equalsIgnoreCase("start"))
                if (shower)
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.start-command-error")));
                } else {
                    int min = this.plugin.getConfig().getInt("min-player-distance");
                    int max = this.plugin.getConfig().getInt("max-player-distance");
                    int delay = this.plugin.getConfig().getInt("fireball-spawn-delay");
                    int height = this.plugin.getConfig().getInt("fireball-spawn-height");
                    int pitch = this.plugin.getConfig().getInt("fireball-pitch");
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.announce-message")));
                    shower = true;

                    plugin.getServer().getScheduler().runTaskTimer(MeteorShower.getInstance(), () -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            int r = (int) (Math.random() * (max - min)) + min;
                            Location loc = p.getLocation();
                            loc.setY(loc.getY() + height);
                            loc.setX(loc.getX() + r);
                            loc.setZ(loc.getZ() - r);
                            loc.setPitch(pitch);
                            loc.setYaw(r);
                            loc.getWorld().spawnEntity(loc, EntityType.FIREBALL);
                        }
                    }, delay, delay);
                }

        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("stop"))
            {
                if (!shower)
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.stop-command-error")));
                } 
                else
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.stop-command")));
                    Bukkit.getScheduler().cancelTasks(MeteorShower.getInstance());
                    shower = false;
                }
            }

        }

        if (args.length > 1)
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.unknown-command")));
            return true;
        }

        if (args.length == 1)
        {
            if (!args[0].equalsIgnoreCase("start") && !args[0].equalsIgnoreCase("stop") && !args[0].equalsIgnoreCase("reload") && !args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.unknown-command")));
            }
            return true;
        }
        return false;
    }

}