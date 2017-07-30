package me.alex.SpawnerShop;

import javafx.event.EventHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class SpawnerShop extends JavaPlugin implements Listener{


    private static Economy econ = null;
    private Inventory shop;
    private ArrayList<String> viewing = new ArrayList<>();
    @Override
    public void onEnable() {
        if (!setupEconomy() ) {
            System.out.println("Disabling SpawnerShop! Economy could not be enabled.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.setupShop();

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
     RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            //if()
        } else
        {
            sender.sendMessage("[SpawnerShop] Must be a player to execute this command");
        }


        return true;
    }

    @org.bukkit.event.EventHandler
    public void onClick(InventoryClickEvent e)
    {



    }

    private void setupShop()
    {
      this.shop = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', getConfig().getString("GUI.Title")));
        for(String s : getConfig().getConfigurationSection("GUI.Spawners").getKeys(false))
        {

            ConfigurationSection cs = getConfig().getConfigurationSection(s);
            ArrayList<String> lore = new ArrayList<>();
            Integer price;
            String type;
            String displayName;
            price = cs.getInt("Price");
            type = cs.getString("Type");
            displayName = cs.getString("DisplayName");
            if(cs.getList("Lore") != null)
            {
                for(String line : (ArrayList<String>)cs.getList("Lore"))
                {
                    lore.add(ChatColor.translateAlternateColorCodes('&', line).replaceAll("<price>", price + ""));


                }
            }


            ItemStack is = new ItemStack(Material.MOB_SPAWNER);
            ItemMeta im = is.getItemMeta();
            BlockStateMeta bsm = (BlockStateMeta) im;
            im.setDisplayName(displayName);
            im.setLore(lore);
            if(EntityType.valueOf(type) != null)
            {
                ((CreatureSpawner)bsm.getBlockState()).setSpawnedType(EntityType.valueOf(type));
            } else
            {
                System.out.println("The type " + type + " is not valid");
            }




        }



    }




}
