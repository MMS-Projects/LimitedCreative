/*
 * Limited Creative - (Bukkit Plugin)
 * Copyright (C) 2012 jascha@ja-s.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.jaschastarke.minecraft.utils;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Locale {
    protected YamlConfiguration lang;
    private static Locale inst = null;
    
    public Locale(JavaPlugin plugin) {
        String fn = getFilename("default");
        File localefile = new File(plugin.getDataFolder(), fn);
        if (localefile.exists())
            lang = YamlConfiguration.loadConfiguration(localefile);
        else
            lang = YamlConfiguration.loadConfiguration(plugin.getResource(fn));
        inst = this;
    }
    private String getFilename(String locale) {
        return "lang/"+locale+".yml";
    }
    public String get(String msg) {
        if (lang.contains(msg)) {
            if (lang.isList(msg)) {
                List<String> list = lang.getStringList(msg);
                String[] lines = new String[list.size()];
                list.toArray(lines);
                return Util.join(lines, "\n");
            } else {
                return lang.getString(msg);
            }
        }
        return msg;
    }

    public static String L(String msg, Object... objects) {
        if (inst != null)
            msg = inst.get(msg);
        if (objects.length > 0)
            return MessageFormat.format(msg, objects);
        else
            return msg;
    }
    public static void unload() {
        inst = null;
    }
}
